/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miudelar.server.ejb.Asignatura_CarreraFacadeLocal;
import com.miudelar.server.ejb.CarreraFacadeLocal;
import com.miudelar.server.ejb.CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_ExamenFacadeLocal;
import com.miudelar.server.ejb.ExamenFacadeLocal;
import com.miudelar.server.ejb.HorarioFacadeLocal;
import com.miudelar.server.ejb.Periodo_ExamenFacadeLocal;
import com.miudelar.server.ejb.UsuarioFacadeLocal;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.datatypes.DtEstudiante_Curso;
import com.miudelar.server.logic.datatypes.DtEstudiante_Examen;
import com.miudelar.server.logic.datatypes.DtExamen;
import com.miudelar.server.logic.datatypes.DtHorario;
import com.miudelar.server.logic.datatypes.DtPeriodo_Examen;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Horario;
import com.miudelar.server.logic.entities.Periodo_Examen;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.interfaces.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import static javax.servlet.SessionTrackingMode.URL;
import javax.sql.DataSource;
import javax.ws.rs.PathParam;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.jboss.resteasy.util.Base64;
import net.sf.jasperreports.engine.JREmptyDataSource;
/**
 *
 * @author rmoreno
 */
public class BedeliaServiceImpl implements BedeliaService {
    
    JsonParser parser = new JsonParser();
    Gson gson = new Gson();
    
    private HorarioFacadeLocal horarioFacade = lookupHorarioFacadeBean();
    private CursoFacadeLocal cursoFacade = lookupCursoFacadeBean();
    private Periodo_ExamenFacadeLocal periodoFacade = lookupPeriodo_ExamenFacadeBean();
    private Estudiante_CursoFacadeLocal e_cJFacade = lookupEstudiante_CursoFacadeBean();
    private Estudiante_ExamenFacadeLocal e_eJFacade = lookupEstudiante_ExamenFacadeBean();
    private UsuarioFacadeLocal usaurioJpaController = lookupUsuarioFacadeBean();
    private ExamenFacadeLocal examenFacade = lookupExamenFacadeBean();
    private CarreraFacadeLocal carreraFacade = lookupCarreraFacadeBean();
    private Asignatura_CarreraFacadeLocal asignatura_CarreraFacade = lookupAsignatura_CarreraFacadeBean();
    InitMgr initMgr = new InitMgr();
    
     private Asignatura_CarreraFacadeLocal lookupAsignatura_CarreraFacadeBean() {
        try {
            Context c = new InitialContext();
            return (Asignatura_CarreraFacadeLocal) c.lookup("java:app/miudelar-server/Asignatura_CarreraFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private CarreraFacadeLocal lookupCarreraFacadeBean() {
        try {
            Context c = new InitialContext();
            return (CarreraFacadeLocal) c.lookup("java:app/miudelar-server/CarreraFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private UsuarioFacadeLocal lookupUsuarioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UsuarioFacadeLocal) c.lookup("java:app/miudelar-server/UsuarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private HorarioFacadeLocal lookupHorarioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (HorarioFacadeLocal) c.lookup("java:app/miudelar-server/HorarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private CursoFacadeLocal lookupCursoFacadeBean() {
        try {
            Context c = new InitialContext();
            return (CursoFacadeLocal) c.lookup("java:app/miudelar-server/CursoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private Periodo_ExamenFacadeLocal lookupPeriodo_ExamenFacadeBean() {
        try {
            Context c = new InitialContext();
            return (Periodo_ExamenFacadeLocal) c.lookup("java:app/miudelar-server/Periodo_ExamenFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private Estudiante_CursoFacadeLocal lookupEstudiante_CursoFacadeBean() {
        try {
            Context c = new InitialContext();
            return (Estudiante_CursoFacadeLocal) c.lookup("java:app/miudelar-server/Estudiante_CursoFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private Estudiante_ExamenFacadeLocal lookupEstudiante_ExamenFacadeBean() {
        try {
            Context c = new InitialContext();
            return (Estudiante_ExamenFacadeLocal) c.lookup("java:app/miudelar-server/Estudiante_ExamenFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private ExamenFacadeLocal lookupExamenFacadeBean() {
        try {
            Context c = new InitialContext();
            return (ExamenFacadeLocal) c.lookup("java:app/miudelar-server/ExamenFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    @Override
    public List<DtUsuario> getEstudiantesInscriptosExamen(Long idExamen){
        //TODO
        List<DtUsuario> usuarios = new ArrayList<>();
        return usuarios;
    }
    
    @Override
    public List<DtUsuario> getEstudiantesInscriptosCurso(Long idCurso){
        List<DtUsuario> usuarios = new ArrayList<>();
        cursoFacade.getEstudiantesInscriptos(idCurso).forEach(estudiante -> {
        usuarios.add(estudiante.toDataType());});
        return usuarios;
    }
    
    @Override
    public List<DtEstudiante_Curso> getEstudiantesCalificacionesCurso(Long idCurso){
        List<DtEstudiante_Curso> cursos = new ArrayList<>();
        e_cJFacade.findByCurso(idCurso).forEach(curso -> {
        cursos.add(curso.toDataType());});
        return cursos;
    }
    
    @Override
    public List<DtEstudiante_Examen> getEstudiantesCalificacionesExamen(Long idExamen){
        List<DtEstudiante_Examen> examenes = new ArrayList<>();
        e_eJFacade.findByExamen(idExamen).forEach(examen -> {
        examenes.add(examen.toDataType());});
        return examenes;
    }
    
    @Override
    public List<DtAsignatura_Carrera> getAsignaturaCarreraByCarrera(Long idCarrera){
        List<DtAsignatura_Carrera> asignaturas = new ArrayList<>();
        List<Asignatura_Carrera> list = carreraFacade.find(idCarrera).getAsignatura_Carreras();
        System.out.println("list.size(): " + list.size());
        list.forEach(asignatura -> {
            asignaturas.add(new DtAsignatura_Carrera(asignatura.getId(), 
                    new DtCarrera(asignatura.getCarrera().getCodigo(), asignatura.getCarrera().getNombre()),
                    new DtAsignatura(asignatura.getAsignatura().getCodigo(), asignatura.getAsignatura().getNombre())));
        });
        return asignaturas;
    }
    
    @Override
    public DtCurso getCurso(Long idCurso){
        return cursoFacade.find(idCurso).toDataType();
    }
    
    @Override
    public DtExamen getExamen(Long idExamen){
        return examenFacade.find(idExamen).toDataType();
    }
    
    @Override
    public String saveCurso(String json){
        String message = "OK"; 
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    String sfecha = jsonObject.get("fecha").getAsString();
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(sfecha);
                    Long idAsigCar = jsonObject.get("idAsigCar").getAsLong();
                    
                    if(idCurso.equals(0L)){
                        message = "El curso se ha creado correctamente";      
                        Asignatura_Carrera asigcar = asignatura_CarreraFacade.find(idAsigCar);
                        Curso curso = new Curso(fecha, asigcar);
                        cursoFacade.create(curso);
                    }else{
                        if(tieneEstudiantesInscriptosCurso(idCurso)){
                             message = "Error: El curso tiene estudiantes inscriptos"; 
                        }else{
                            message = "El curso se ha editado correctamente";     
                            Curso curso = cursoFacade.find(idCurso);
                            curso.setFecha(fecha);
                            cursoFacade.edit(curso);
                        }
                        
                    }
                   
            }        
            
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String saveExamen(String json){
        String message = "OK"; 
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idExamen = jsonObject.get("idExamen").getAsLong();
                    String sfecha = jsonObject.get("fecha").getAsString();
                    Date fecha = new SimpleDateFormat("dd/MM/yyyy").parse(sfecha);
                    Long idAsigCar = jsonObject.get("idAsigCar").getAsLong();
                    
                    if(idExamen.equals(0L)){
                        message = "El examen se ha creado correctamente";      
                        Asignatura_Carrera asigcar = asignatura_CarreraFacade.find(idAsigCar);
                        Examen examen = new Examen(fecha, asigcar);
                        examenFacade.create(examen);
                    }else{
                        if(tieneEstudiantesInscriptosExamen(idExamen)){
                             message = "Error: El examen tiene estudiantes inscriptos"; 
                        }else{
                            message = "El examen se ha editado correctamente";     
                            Examen examen = examenFacade.find(idExamen);
                            examen.setFecha(fecha);
                            examenFacade.edit(examen);
                        }
                    }
            }        
            
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String removeCurso(String json){
        String message = "OK"; 
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    if(cursoFacade.getEstudiantesInscriptos(idCurso).size() > 0){
                        message = "El curso tiene estudiantes inscriptos";
                    }else{
                        Curso curso = cursoFacade.find(idCurso);
                        cursoFacade.remove(curso);
                        message = "El curso fue eliminado";
                    }
            }   
             } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String removeExamen(String json){
        String message = "OK"; 
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idExamen = jsonObject.get("idExamen").getAsLong();
                    if(examenFacade.getEstudiantesInscriptos(idExamen).size() > 0){
                        message = "El examen tiene estudiantes inscriptos";
                    }else{
                        Examen examen = examenFacade.find(idExamen);
                        examenFacade.remove(examen);
                        message = "El examen fue eliminado";
                    }
            }   
             } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String saveHorario(String json){
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    JsonObject jsonHora = jsonObject.get("jsonHora").getAsJsonObject();
                    DtHorario dtHorario = gson.fromJson(jsonHora, DtHorario.class);
                    
                    Curso curso = cursoFacade.find(idCurso);
                    if (curso == null){
                        message = "El curso no existe";
                    }else{
                        //        Creo Horario
                        Horario horario = new Horario(dtHorario.getDia(), dtHorario.getHoraInicio(), dtHorario.getHoraFin());
                        horarioFacade.create(horario);

                        ////        Creo realación
                        curso.addHorario(horario);
                        cursoFacade.edit(curso);
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
    
    @Override
    public String editHorario(Horario horario){
        String message = "OK";
        try {
            horarioFacade.edit(horario);
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String savePeriodoExamen(DtPeriodo_Examen dtPeriodo){
    String message = "OK";
        try {
            Periodo_Examen periodo = new Periodo_Examen(dtPeriodo);
            periodoFacade.create(periodo);
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override 
    public String editPeriodoExamen(Periodo_Examen periodo){
        String message = "OK";
        try {
            periodoFacade.edit(periodo);
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String cargarNotasCurso(String json){
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long calificacion = jsonObject.get("calificacion").getAsLong();
                    if (calificacion < 13 && calificacion >= 0) {
                        Usuario usuario = usaurioJpaController.find(cedula);
                        if (usuario == null){
                            message = "No existe el usuario";
                        }else{
                            Curso curso = cursoFacade.find(idCurso);
                            if (curso == null){
                                message = "No existe el curso";
                            }else{
                                if (usuario.getCursos().contains(curso)){
                                    System.out.println("contains(curso)");
                                    Estudiante_Curso e_c = e_cJFacade.find(idCurso,cedula);
                                    if(e_c != null){
                                        e_c.setCalificacion(calificacion);
                                         e_cJFacade.edit(e_c);
                                        initMgr.sendMail(e_c); 
    //                                    usuario.addcalificacionesCursos(e_c);
    //                                    usaurioJpaController.edit(usuario);
    //                                    curso.addCalificacionesCursos(e_c);
//                                    cursoFacade.edit(curso);
                                    }else{
                                        message = "Error, El estudiante " + usuario.getCedula() +" no se encuentra inscripto al curso";
                                    }
                                }else{
                                   message = "Error, El estudiante " + usuario.getCedula() +" no se encuentra inscripto al curso";
                                }
                            }
                        }
                    }else{
                        message = "Error, Calificacion: " + calificacion.toString() + " no es un valor válido";
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
    
    @Override
    public String cargarNotasExamen(String json){
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                if (jsonObject.get("cedula").isJsonObject() && jsonObject.get("idExamen").isJsonObject() && jsonObject.get("calificacion").isJsonObject()) {
                    Long idExamen = jsonObject.get("idExamen").getAsLong();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long calificacion = jsonObject.get("calificacion").getAsLong();
                    
                    if (calificacion < 13 && calificacion >= 0) {
                        Usuario usuario = usaurioJpaController.find(cedula);
                        if (usuario == null){
                            message = "No existe el usuario";
                        }else{
                            Examen examen = examenFacade.find(idExamen);
                            if (examen == null){
                               message = "No existe el examen";
                            }else{
                                if (usuario.getInscripcionesExamenes().contains(examen)){
                                    Estudiante_Examen e_e = e_eJFacade.find(idExamen,cedula);
                                    if (e_e != null){
                                        e_e.setCalificacion(calificacion);
                                        e_eJFacade.edit(e_e);
                                        initMgr.sendMail(e_e);
//                                    usuario.addcalificacionesExamenes(e_e);
//                                    usaurioJpaController.edit(usuario);
//                                    examen.addCalificacionesExamens(e_e);
//                                    examenFacade.edit(examen);
                                    }else{
                                        message = "El estutdiante " + usuario.getCedula() +" no se encuentra inscripto al examen";
                                    }
                                }else{
                                    message = "El estutdiante " + usuario.getCedula() +" no se encuentra inscripto al examen";
                                }
                            }
                        }
                    }else{
                        message = "Calificacion: " + calificacion.toString() + " no es un valor válido";
                    }
                }else{
                    message = "Formato incorrecto";
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
    
  
    @Override
    public String getActaFinCurso(Long idCurso) {
        String output = "";
        InputStream inputStream = null;
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup("java:jboss/datasources/PostgresqlDS");
            Connection conn = dataSource.getConnection();
//            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/miudelar", "postgres", "gxsalud");
            System.out.println(conn.toString());
            
            URL url1 = BedeliaServiceImpl.class.getResource("ActaCurso.jasper");
            System.out.println("url: " + url1);
            URL url2 = BedeliaServiceImpl.class.getResource("ActaCurso_body.jasper");
            System.out.println("url: " + url2);
            inputStream = BedeliaServiceImpl.class.getResourceAsStream("Logo_MiUdelar.png");
            System.out.println("url: " + inputStream);
            
            Map parameters = new HashMap();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url1);
            JasperReport body = (JasperReport) JRLoader.loadObject(url2);
            
            parameters.put("cursoId", idCurso);
            parameters.put("subReport", body);
            parameters.put("logo", inputStream);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
            output = Base64.encodeBytes(JasperExportManager.exportReportToPdf(jasperPrint));

        } catch (JRException | SQLException | NamingException ex) {
            output = "Error ";
            Logger.getLogger(BedeliaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    @Override
    public String getActaExamen(Long idExamen){
        String output = "";
        InputStream inputStream = null;
        try {
            System.out.println("idExamen: " + idExamen);
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup("java:jboss/datasources/PostgresqlDS");
            Connection conn = dataSource.getConnection();
            System.out.println(conn.toString());
            
            URL url1 = BedeliaServiceImpl.class.getResource("ActaExamen.jasper");
            System.out.println("url1: " + url1);
            URL url2 = BedeliaServiceImpl.class.getResource("ActaExamen_body.jasper");
            System.out.println("url2: " + url2);
            inputStream = BedeliaServiceImpl.class.getResourceAsStream("Logo_MiUdelar.png");
            System.out.println("url3: " + inputStream);
            
            Map parameters = new HashMap();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url1);
            JasperReport body = (JasperReport) JRLoader.loadObject(url2);
            
            parameters.put("examenId", idExamen);
            parameters.put("subReport", body);
            parameters.put("logo", inputStream);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            output = Base64.encodeBytes(JasperExportManager.exportReportToPdf(jasperPrint));

        } catch (JRException | SQLException | NamingException ex) {
            output = "Error ";
            Logger.getLogger(BedeliaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
    
    @Override
    public String getEscolaridad(String cedula, Long codigoCarrera){
        String output = "";
        InputStream inputStream = null;
        try {
            System.out.println("codigoCarrera: " + codigoCarrera);
            System.out.println("cedula: " + cedula);
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource)initialContext.lookup("java:jboss/datasources/PostgresqlDS");
            Connection conn = dataSource.getConnection();
            System.out.println(conn.toString());
            
            URL url1 = BedeliaServiceImpl.class.getResource("Escolaridad.jasper");
            System.out.println("url1: " + url1);
            URL url2 = BedeliaServiceImpl.class.getResource("Escolaridad_body.jasper");
            System.out.println("url2: " + url2);
            inputStream = BedeliaServiceImpl.class.getResourceAsStream("Logo_MiUdelar.png");
            System.out.println("url3: " + inputStream);
            
            Map parameters = new HashMap();
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url1);
            JasperReport body = (JasperReport) JRLoader.loadObject(url2);
            
            parameters.put("codCarrera", codigoCarrera);
            parameters.put("cedula", cedula);
            parameters.put("subReport", body);
            parameters.put("logo", inputStream);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);
            output = Base64.encodeBytes(JasperExportManager.exportReportToPdf(jasperPrint));

        } catch (JRException | SQLException | NamingException ex) {
            output = "Error ";
            Logger.getLogger(BedeliaServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }
    
    private boolean tieneEstudiantesInscriptosCurso(Long idCurso){
        if (cursoFacade.getEstudiantesInscriptos(idCurso).size() > 0){
            return true;
        }else{
             return false;
        }
    }
    
    private boolean tieneEstudiantesInscriptosExamen(Long idExamen){
        if (examenFacade.getEstudiantesInscriptos(idExamen).size() > 0){
            return true;
        }else{
             return false;
        }
    }

} 
