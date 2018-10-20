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
import com.miudelar.server.logic.controller.CursoJpaController;
import com.miudelar.server.logic.controller.Estudiante_CursoJpaController;
import com.miudelar.server.logic.controller.Estudiante_ExamenJpaController;
import com.miudelar.server.logic.controller.ExamenJpaController;
import com.miudelar.server.logic.controller.HorarioJpaController;
import com.miudelar.server.logic.controller.Periodo_ExamenJpaController;
import com.miudelar.server.logic.controller.UsuarioJpaController;
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

/**
 *
 * @author rmoreno
 */
public class BedeliaServiceImpl implements BedeliaService {
    
    JsonParser parser = new JsonParser();
    Gson gson = new Gson();
    
    HorarioJpaController horarioJpaController = new HorarioJpaController();
    CursoJpaController cursoJpaController = new CursoJpaController();
    Periodo_ExamenJpaController periodoJpaController = new Periodo_ExamenJpaController();
    Estudiante_CursoJpaController e_cJpaController = new Estudiante_CursoJpaController();
    Estudiante_ExamenJpaController e_eJpaController = new Estudiante_ExamenJpaController();
    UsuarioJpaController usaurioJpaController = new UsuarioJpaController();
    ExamenJpaController examenJpaController = new ExamenJpaController();
    
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
    public String saveHorario(String json){
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                if (jsonObject.get("idCurso").isJsonObject() && jsonObject.get("jsonHora").isJsonObject()) {
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    JsonObject jsonHora = jsonObject.get("jsonHora").getAsJsonObject();
                    DtHorario dtHorario = gson.fromJson(jsonHora, DtHorario.class);

                    //        Creo Horario
                    Horario horario = new Horario(dtHorario.getHoraInicio(), dtHorario.getHoraFin());
                    horarioJpaController.create(horario);
                    
                    ////        Creo realación
                    Curso curso = cursoJpaController.findCurso(idCurso);
                    curso.addHorario(horario);
                    cursoJpaController.edit(curso);
                    
                }else{
                    message = "Formato incorrecto";
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String editHorario(Horario horario){
        String message = "OK";
        try {
            horarioJpaController.edit(horario);
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
            periodoJpaController.create(periodo);
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
            periodoJpaController.edit(periodo);
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
                if (jsonObject.get("cedula").isJsonObject() && jsonObject.get("idCurso").isJsonObject() && jsonObject.get("calificacion").isJsonObject()) {
                    Long idCurso = jsonObject.get("idCurso").getAsLong();
                    String cedula = jsonObject.get("cedula").getAsString();
                    Long calificacion = jsonObject.get("calificacion").getAsLong();
                    
                    if (calificacion < 13 && calificacion >= 0) {
                        Usuario usuario = usaurioJpaController.findUsuario(cedula);
                        Curso curso = cursoJpaController.findCurso(idCurso);
                        e_cJpaController.create(new Estudiante_Curso(calificacion, usuario, curso));
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
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
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
                        Usuario usuario = usaurioJpaController.findUsuario(cedula);
                        Examen examen = examenJpaController.findExamen(idExamen);
                        e_eJpaController.create(new Estudiante_Examen(usuario, examen, calificacion));
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
            System.out.println("Class:BedeliaServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
} 
