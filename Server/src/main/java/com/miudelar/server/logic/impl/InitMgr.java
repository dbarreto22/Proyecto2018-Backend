/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.factories.ManagersFactory;
import com.miudelar.server.logic.interfaces.*;
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

public class InitMgr implements InitMgt {

    @Override
    public List<Asignatura_Carrera> getAllPrevias(Asignatura_Carrera asigcar) {
        List<Asignatura_Carrera> listAsigCar = new ArrayList<>();
        addAllNodes(asigcar, listAsigCar);
        return listAsigCar;
    }

    public static void addAllNodes(Asignatura_Carrera asic_car, List<Asignatura_Carrera> listOfNodes) {
        if (asic_car != null && !listOfNodes.contains(asic_car)) {
            listOfNodes.add(asic_car);
            List<Asignatura_Carrera> children = asic_car.getPrevias();
            if (children != null) {
                for (Asignatura_Carrera child : children) {
                    addAllNodes(child, listOfNodes);
                }
            }
        }
    }

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
            
            if (notaObj instanceof Estudiante_Curso){
                Estudiante_Curso curso = (Estudiante_Curso)notaObj;
                System.out.println("curso: " + curso.getCalificacion());
                armarMsg(message, curso.getUsuario(), "curso", curso.getCurso().getAsignatura_Carrera().getAsignatura().getNombre(), curso.getCalificacion());
            }else{
                if (notaObj instanceof Estudiante_Examen){
                    Estudiante_Examen examen = (Estudiante_Examen)notaObj;
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
            System.out.println("usuario.getEmail(): " + usuario.getEmail());
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(usuario.getEmail()));
            message.setSubject("Se ha cargado una calificación");
            message.setText("Estimado/a " + usuario.getNombre() + ", "
                + "\n\n Usted ha obtenido un " + calificacion + " en el " + tipo + " de " + asignatura +"."
                + "\n\n\n\n MiUdelar");
                } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return message;
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
