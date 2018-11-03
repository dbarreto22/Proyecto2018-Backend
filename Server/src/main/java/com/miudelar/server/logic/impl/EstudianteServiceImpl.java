/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.miudelar.server.ejb.Asignatura_CarreraFacade;
import com.miudelar.server.ejb.Asignatura_CarreraFacadeLocal;
import com.miudelar.server.ejb.CarreraFacade;
import com.miudelar.server.ejb.CarreraFacadeLocal;
import com.miudelar.server.ejb.CursoFacade;
import com.miudelar.server.ejb.CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_CursoFacade;
import com.miudelar.server.ejb.Estudiante_CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_ExamenFacade;
import com.miudelar.server.ejb.Estudiante_ExamenFacadeLocal;
import com.miudelar.server.ejb.ExamenFacade;
import com.miudelar.server.ejb.ExamenFacadeLocal;
import com.miudelar.server.ejb.RolFacade;
import com.miudelar.server.ejb.RolFacadeLocal;
import com.miudelar.server.ejb.UsuarioFacade;
import com.miudelar.server.ejb.UsuarioFacadeLocal;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCalificaciones;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.datatypes.DtEstudiante_Curso;
import com.miudelar.server.logic.datatypes.DtEstudiante_Examen;
import com.miudelar.server.logic.datatypes.DtExamen;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Carrera;
import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;

/**
 *
 * @author rmoreno
 */
public class EstudianteServiceImpl implements EstudianteService {
   
    private UsuarioFacadeLocal usuarioFacade = lookupUsuarioFacadeBean();
    private RolFacadeLocal rolFacade = lookupRolFacadeBean();
    private CursoFacadeLocal cursoFacade = lookupCursoFacadeBean();
    private ExamenFacadeLocal examenFacade = lookupExamenFacadeBean();
    private CarreraFacadeLocal carreraFacade = lookupCarreraFacadeBean();
    private Estudiante_CursoFacadeLocal estudiante_cursoFacade = lookupEstudiante_CursoFacadeBean();
    private Estudiante_ExamenFacadeLocal estudiante_ExamenFacade = lookupEstudiante_ExamenFacadeBean();
    private Asignatura_CarreraFacadeLocal asignatura_CarreraFacade = lookupAsignatura_CarreraFacadeBean();
    InitMgr initMgr = new InitMgr();
    
    private RolFacadeLocal lookupRolFacadeBean() {
        try {
            Context c = new InitialContext();
            return (RolFacadeLocal) c.lookup("java:app/miudelar-server/RolFacade");
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
    
    private CursoFacadeLocal lookupCursoFacadeBean() {
        try {
            Context c = new InitialContext();
            return (CursoFacadeLocal) c.lookup("java:app/miudelar-server/CursoFacade");
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
    
    private CarreraFacadeLocal lookupCarreraFacadeBean() {
        try {
            Context c = new InitialContext();
            return (CarreraFacadeLocal) c.lookup("java:app/miudelar-server/CarreraFacade");
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
    
    private Asignatura_CarreraFacadeLocal lookupAsignatura_CarreraFacadeBean() {
        try {
            Context c = new InitialContext();
            return (Asignatura_CarreraFacadeLocal) c.lookup("java:app/miudelar-server/Asignatura_CarreraFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    JsonParser parser = new JsonParser();
    
    @Override
    public List<DtCurso> getAllCurso(){
        List<DtCurso> cursos = new ArrayList<>();
        cursoFacade.findAll().forEach(curso -> {
            cursos.add(curso.toDataType());
        });
         return cursos;
     }
    
    @Override
    public List<DtExamen> getAllExamen(){
        List<DtExamen> examenes = new ArrayList<>();
        examenFacade.findAll().forEach(examen -> {
            examenes.add(examen.toDataType());
        });
         return examenes;
     }
    
    @Override
    public List<DtCurso> getCursoByCedula(String cedula) {
        List<DtCurso> cursos = new ArrayList<>();
        Usuario usuario = usuarioFacade.find(cedula);
        if (usuario == null) {
            System.out.println("No existe usuario");
            return cursos;
        } else {
            usuario.getCarreras().forEach(carrera -> {
                carrera.getAsignatura_Carreras().forEach(asig_car -> {
                    asig_car.getCursos().forEach(curso -> {
                        cursos.add(curso.toDataType());
                    });
                });
            });
        }
        return cursos;
    }
    
    @Override
    public List<DtExamen> getExamenByCedula(String cedula){
        List<DtExamen> examenes = new ArrayList<>();
        Usuario usuario = usuarioFacade.find(cedula);
        if (usuario == null) {
            System.out.println("No existe usuario");
            return examenes;
        } else {
            usuario.getCarreras().forEach(carrera -> {
                carrera.getAsignatura_Carreras().forEach(asig_car -> {
                    asig_car.getExamenes().forEach(examen -> {
                        examenes.add(examen.toDataType());
                    });
                });
            });
        }
        return examenes;
    }
    
    @Override
    public DtCalificaciones getCalificaciones(String cedula, Long idAsig_Carrera) {
        List<DtEstudiante_Curso> cursos = new ArrayList<>();
        List<DtEstudiante_Examen> examenes = new ArrayList<>();

        Usuario usuario = usuarioFacade.find(cedula);
        if (usuario == null) {
            System.out.println("getCalificaciones: usuario is null");
            return new DtCalificaciones();
        } else {
            usuario.getCalificacionesCursos().forEach(curso -> {
//                System.out.println("curso.getCurso().getAsignatura_Carrera().getId(): "+ curso.getCurso().getAsignatura_Carrera().getId());
//                System.out.println("idAsig_Carrera: " + idAsig_Carrera);
                if (curso.getCurso().getAsignatura_Carrera().getId().equals(idAsig_Carrera)) {
                    if (curso.getCalificacion() != null){
                        System.out.println("entra");
                        cursos.add(curso.toDataType());
                    }
                }
            });
            usuario.getCalificacionesExamenes().forEach(examen -> {
                if (examen.getExamen().getAsignatura_Carrera().getId().equals(idAsig_Carrera)) {
                    if (examen.getCalificacion() != null){
                        System.out.println("entra");
                        examenes.add(examen.toDataType());
                    }
                    
                }
            });
        }
    return new DtCalificaciones(cursos, examenes);
}
    
    @Override
    public DtCalificaciones getCalificacionesSAsig(String cedula) {
        List<DtEstudiante_Curso> cursos = new ArrayList<>();
        List<DtEstudiante_Examen> examenes = new ArrayList<>();

        Usuario usuario = usuarioFacade.find(cedula);
        if (usuario == null) {
            System.out.println("getCalificaciones: usuario is null");
            return new DtCalificaciones();
        } else {
            usuario.getCalificacionesCursos().forEach(curso -> {
                if (curso.getCalificacion() != null){
                    cursos.add(curso.toDataType());
                }
            });
            usuario.getCalificacionesExamenes().forEach(examen -> {
                if (examen.getCalificacion() != null){
                    examenes.add(examen.toDataType());
                }
            });
        }
    return new DtCalificaciones(cursos, examenes);
}
    
    @Override
    public String inscripcionCurso(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long idCurso = jsonObject.get("idCurso").getAsLong();

                    Usuario usuario = usuarioFacade.find(cedula);
                    if (usuario == null){
                        message = "No existe el usuario";
                    }else{
                        Curso curso = cursoFacade.find(idCurso);
                        if (curso == null){
                            message = "No existe el curso";
                        }else{
                            Asignatura_Carrera asig_car = curso.getAsignatura_Carrera();
                            Long max = estudiante_cursoFacade.getMaxCalificacionAsignatura(cedula, asig_car.getId());
                            if (max.compareTo(6L) > 0){
                                return "Error, el estudiante ya tiene aprobada esta asignatura";
                            }else{
                                if (usuario.getCursos().contains(curso)){
                                return "Error, el estudiante ya se encuentra inscripto a este curso";
                            }else{
                                //Valido previas
                                List<Asignatura_Carrera> previas = initMgr.getAllPrevias(asig_car);
                                for (Asignatura_Carrera previa : previas) {
                                    max = estudiante_cursoFacade.getMaxCalificacionAsignatura(cedula, previa.getId());
                                    System.out.println("max: " + max);
                                    System.out.println("previa.getId(): " + previa.getId());
                                    System.out.println("asig_car.getId(): " + asig_car.getId());
                                    if (max.compareTo(6L) < 0 && !asig_car.getId().equals(previa.getId())){
                                        return "Error, el estudiante no tiene aprobada la asignatura: " + previa.getAsignatura().getNombre();
                                    }
                                }
                                
                                Estudiante_Curso e_c = new Estudiante_Curso(usuario, curso);
                                estudiante_cursoFacade.create(e_c);
                                usuario.addCurso(curso);
                                usuario.addcalificacionesCursos(e_c);
                                usuarioFacade.edit(usuario);
                                curso.addCalificacionesCursos(e_c);
                                cursoFacade.edit(curso);
                                }
                            }
                            
                        }
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Class:EstudianteServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
     
    @Override
    public String inscripcionCarrera(String json) {
        String message = "OK";
        System.out.println("Class:EstudianteServiceImpl: " + json);
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long codigo = jsonObject.get("codigo").getAsLong();

                    Usuario usuario = usuarioFacade.find(cedula);
                    if (usuario == null) {
                        message = "No existe el usuario";
                    } else {
                        Carrera carrera = carreraFacade.find(codigo);
                        if (carrera == null){
                            message = "No existe la carrera";
                        }else{
                            if (usuario.getCarreras().contains(carrera)){
                                message = "El usuario ya está inscripto en: " + carrera.getNombre();
                            }else{
                                usuario.addCarrera(carrera);
                                usuarioFacade.edit(usuario);
                            }
                        }
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:EstudianteServiceImpl: " + ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
     
    @Override
    public String inscripcionExamen(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long idExamen = jsonObject.get("idExamen").getAsLong();

                    Usuario usuario = usuarioFacade.find(cedula);
                    if (usuario == null){
                        message = "No existe el usuario";
                    }else{
                        Examen examen = examenFacade.find(idExamen);
                        if (examen == null){
                            message = "No existe el examen";
                        }else{
                            Asignatura_Carrera asig_car = examen.getAsignatura_Carrera();
                            Long max = estudiante_ExamenFacade.getMaxCalificacionAsignatura(cedula, asig_car.getId());
                            if (max.compareTo(3L) > 0){
                                return "Error, el estudiante ya tiene aprobado este examen";
                            }else{
                                if (usuario.getInscripcionesExamenes().contains(examen)){
                                return "Error, el estudiante ya se encuentra inscripto a este examen";
                            }else{
                                //Valido previas
                                List<Asignatura_Carrera> previas = initMgr.getAllPrevias(asig_car);
                                for (Asignatura_Carrera previa : previas) {
                                    max = estudiante_cursoFacade.getMaxCalificacionAsignatura(cedula, previa.getId());
                                    System.out.println("max: " + max);
                                    System.out.println("previa.getId(): " + previa.getId());
                                    System.out.println("asig_car.getId(): " + asig_car.getId());
                                    if (max.compareTo(6L) < 0 && !asig_car.getId().equals(previa.getId())){
                                        return "Error, el estudiante no tiene aprobada la asignatura: " + previa.getAsignatura().getNombre();
                                    }
                                }
                                Estudiante_Examen e_e = new Estudiante_Examen(usuario, examen);
                                estudiante_ExamenFacade.create(e_e);
                                usuario.addInscripcionesExamenes(examen);
                                usuario.addcalificacionesExamenes(e_e);
                                examen.addCalificacionesExamens(e_e);
                                usuarioFacade.edit(usuario);
                                examenFacade.edit(examen);
                                }
                            }
                            
                        }
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (JsonSyntaxException ex) {
            System.out.println("Class:EstudianteServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
}
