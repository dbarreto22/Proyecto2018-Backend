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
import com.miudelar.server.ejb.CursoFacade;
import com.miudelar.server.ejb.CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_CursoFacade;
import com.miudelar.server.ejb.Estudiante_CursoFacadeLocal;
import com.miudelar.server.ejb.Estudiante_ExamenFacade;
import com.miudelar.server.ejb.Estudiante_ExamenFacadeLocal;
import com.miudelar.server.ejb.ExamenFacade;
import com.miudelar.server.ejb.ExamenFacadeLocal;
import com.miudelar.server.ejb.HorarioFacade;
import com.miudelar.server.ejb.HorarioFacadeLocal;
import com.miudelar.server.ejb.Periodo_ExamenFacade;
import com.miudelar.server.ejb.Periodo_ExamenFacadeLocal;
import com.miudelar.server.ejb.UsuarioFacade;
import com.miudelar.server.ejb.UsuarioFacadeLocal;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.datatypes.DtHorario;
import com.miudelar.server.logic.datatypes.DtPeriodo_Examen;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Horario;
import com.miudelar.server.logic.entities.Periodo_Examen;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.interfaces.*;
import java.util.ArrayList;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
        //TODO
        List<DtUsuario> usuarios = new ArrayList<>();
        return usuarios;
    }
    
    @Override
    public String saveCurso(DtCurso dtCurso){
        String message = "OK";
        try {
            Curso curso = new Curso(dtCurso);
            cursoFacade.create(curso);
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
                                e_cJFacade.create(new Estudiante_Curso(calificacion, usuario, curso));
                            }
                        }
                        
                    }else{
                        message = "Calificacion: " + calificacion.toString() + " no es un valor válido";
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
                                e_eJFacade.create(new Estudiante_Examen(usuario, examen, calificacion));
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

} 
