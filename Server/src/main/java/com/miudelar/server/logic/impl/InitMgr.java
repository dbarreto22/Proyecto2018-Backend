/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.factories.ManagersFactory;
import com.miudelar.server.logic.interfaces.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

public class InitMgr implements InitMgt {

    private static final String ClaveFireBase = "AAAAVd9KhGs:APA91bH9rgqW514ylHKsYuBz0d-gJxO7fvyljn6GqgNl2FHYp4WOtXiXpGWsmXw67wTB_AwlqIhFpn4eBCJR9XmdiPoQ9L-fCyu9qDBO-Yp2M0PY7b7Kb3IIDSD5dPnseVV9TS3cFJ1f";

    @Override
    public List<Asignatura_Carrera> getAllPrevias(Asignatura_Carrera asigcar) {
        List<Asignatura_Carrera> listAsigCar = new ArrayList<>();
        addAllNodes(asigcar, listAsigCar);
        return listAsigCar;
    }

    public static void addAllNodes(Asignatura_Carrera asic_car, List<Asignatura_Carrera> listOfNodes) {
        if (asic_car != null && !listOfNodes.contains(asic_car)) {
            listOfNodes.add(asic_car);
            Set<Asignatura_Carrera> children = asic_car.getPrevias();
            if (children != null) {
                for (Asignatura_Carrera child : children) {
                    addAllNodes(child, listOfNodes);
                }
            }
        }
    }

    @Override
    public String sendMail(Object notaObj) {
        System.out.println("sendMail");
        try {
            final String username = "grupo4.miudelar@gmail.com";
            final String password = "tecnoinf.grupo4";

            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("grupo4.miudelar@gmail.com"));

            if (notaObj instanceof Estudiante_Curso) {
                Estudiante_Curso curso = (Estudiante_Curso) notaObj;
                System.out.println("curso: " + curso.getCalificacion());
                armarMsg(message, curso.getUsuario(), "curso", curso.getCurso().getAsignatura_Carrera().getAsignatura().getNombre(), curso.getCalificacion());
            } else {
                if (notaObj instanceof Estudiante_Examen) {
                    Estudiante_Examen examen = (Estudiante_Examen) notaObj;
                    System.out.println("examen: " + examen.getCalificacion());
                    armarMsg(message, examen.getUsuario(), "examen", examen.getExamen().getAsignatura_Carrera().getAsignatura().getNombre(), examen.getCalificacion());
                }
            }
            
            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException ex) {
            Logger.getLogger(InitMgr.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "ok";
    }

    public static Message armarMsg(Message message, Usuario usuario, String tipo, String asignatura, Long calificacion) {
        System.out.println("armarMsg");
        try {
            if (!usuario.getEmail().isEmpty()){
                System.out.println("usuario.getEmail(): " + usuario.getEmail());
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(usuario.getEmail()));
                message.setSubject("Se ha cargado una calificación");
                message.setText("Estimado/a " + usuario.getNombre() + ", "
                    + "\n\n Usted ha obtenido un " + calificacion + " en el " + tipo + " de " + asignatura + "."
                    + "\n\n\n\n MiUdelar");
            }else{
                System.out.println("EMAIL VACIO");
            }
            
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
    }

    @Override
    public int sendPushWithSimpleAndroid(Object notaObj) {
        String title = "Se ha cargado una calificación";
        String message = "Usted ha obtenido un ";
        String deviceToken = "";
        String tipo = "";
        int response = 0;
        if (notaObj instanceof Estudiante_Curso) {
            Estudiante_Curso curso = (Estudiante_Curso) notaObj;
            deviceToken = curso.getUsuario().getDeviceToken();
            tipo = "curso";
            message += curso.getCalificacion();
            message += " en el curso de " + curso.getCurso().getAsignatura_Carrera().getAsignatura().getNombre() + ".";
        } else {
            if (notaObj instanceof Estudiante_Examen) {
                Estudiante_Examen examen = (Estudiante_Examen) notaObj;
                deviceToken = examen.getUsuario().getDeviceToken();
                tipo = "examen";
                message += examen.getCalificacion();
                message += " en el examen de " + examen.getExamen().getAsignatura_Carrera().getAsignatura().getNombre() + ".";
            }
        }

        if (deviceToken != null && deviceToken != "") {
            System.out.println("deviceToken: " + deviceToken); 
            JsonObject info = new JsonObject();
            JsonObject data = new JsonObject();
            JsonObject json = new JsonObject();

            data.addProperty("title", title);
            data.addProperty("body", message);
            data.addProperty("tipo", tipo);
            json.addProperty("to", deviceToken);
            json.add("data", data);

            URL url;
            //        System.out.println("pushMessage: "+ pushMessage);

            try {
                url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization", "key=" + ClaveFireBase);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                // Send FCM message content.
                OutputStream outputStream = conn.getOutputStream();
                String jsonString = new Gson().toJson(json);
                System.out.println("jsonString: " + jsonString);
                byte[] utf8JsonString = jsonString.getBytes("UTF8");
                outputStream.write(utf8JsonString);
                System.out.println("utf8JsonString: " + utf8JsonString.toString());

                response = conn.getResponseCode();
                System.out.println(conn.getResponseCode());
                System.out.println(conn.getResponseMessage());
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                StringBuilder sb = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    sb.append(output);
                }
                System.out.println("sb.toString(): " + sb.toString());
            } catch (Exception ex) {
                response = 505;
                Logger.getLogger(InitMgr.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            }
            return response;
        } else {
            return 0;

        }
    }

//    AdministradorService administradorService = ManagersFactory.getInstance().getAdministradorService();
//    BedeliaService bedeliaService = ManagersFactory.getInstance().getBedeliaService();
//    DirectorService directorService = ManagersFactory.getInstance().getDirectorService();
//    EstudianteService estudianteService = ManagersFactory.getInstance().getEstudianteService();
//    private void rolGenerator() throws NoSuchAlgorithmException, RolWithInvalidDataException {
//        List<DtRol> listDtRol = administradorService.getAllRol();
//
//        if (listDtRol.isEmpty()) {
//            administradorService.rolSave("ADMIN");
//            administradorService.rolSave("BEDELIA");
//            administradorService.rolSave("DIRECTOR");
//            administradorService.rolSave("ESTUDIANTE");
//        }
//    }
//    
//    private void createDefaultUser() throws NoSuchAlgorithmException, UsuarioWithInvalidDataException, NonexistentEntityException{
//        List<DtUsuario> listDtUsuario = administradorService.getAllUsuario();
//        if (listDtUsuario.isEmpty()){
//            DtUsuario usuario = new DtUsuario("1111111", "Admin", "Admin", "admin@admin.com", "admin123");
//            administradorService.saveUsuario(usuario);
////            administradorService.addRol(usuario.getCedula(),1L);
//        }
//    }
//    
//    private void carrera_asginaturaGenerator(){
//        DtCarrera carrera = new DtCarrera(2001L, "Ing. en computación");
//        directorService.saveCarrera(carrera);
//        DtAsignatura asignatura = new DtAsignatura(1001L, "Cálculo1");
//        directorService.saveAsignatura(asignatura);
////        directorService.saveAsignaturaCarrera(1001L, 2001L);
//    }
//
//    @Override
//    public void initBaseData() throws NoSuchAlgorithmException, RolWithInvalidDataException, UsuarioWithInvalidDataException, NonexistentEntityException{
//// TODO: hacelo bien 
////        rolGenerator();
////        createDefaultUser();
////        carrera_asginaturaGenerator();
//    }
}
