/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
    public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException{
        List<DtCurso> cursos = new ArrayList<>();
        cursoFacade.findAll().forEach(curso -> {
            cursos.add(curso.toDataType());
        });
         return cursos;
     }
    
    @Override
    public DtCalificaciones getCalificaciones(String cedula, Long idAsig_Carrera){
        //TODO
        List<DtEstudiante_Curso> cursos = new ArrayList<>();
        List<DtEstudiante_Examen> examenes = new ArrayList<>();
        Asignatura_Carrera asig_car = asignatura_CarreraFacade.find(idAsig_Carrera);
        if (asig_car == null){
            System.out.println("getCalificaciones: asig_car is null");
            return new DtCalificaciones();
        }else{
            estudiante_cursoFacade.findEstudiante_CursoByUsuario_Asignatura(cedula, idAsig_Carrera).forEach(curso -> {
            cursos.add(curso.toDataType());
            });
            estudiante_ExamenFacade.findEstudiante_ExamenByUsuario_Asignatura(cedula, idAsig_Carrera).forEach(examen -> {
                examenes.add(examen.toDataType());
            });
            return new DtCalificaciones(cursos, examenes);
        }
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
                            usuario.addCurso(curso);
                            usuarioFacade.edit(usuario);
                        }
                    }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:EstudianteServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
     
    @Override
    public String inscripcionCarrera(String json) {
        String message = "OK";
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
                            message = "No existe el carrera";
                        }else{
                            usuario.addCarrera(carrera);
                            usuarioFacade.edit(usuario);
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
                            usuario.addInscripcionesExamenes(examen);
                            usuarioFacade.edit(usuario);
                        }
                    }
                }else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
            }catch (Exception ex) {
            System.out.println("Class:EstudianteServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
            return message;
        }
}
