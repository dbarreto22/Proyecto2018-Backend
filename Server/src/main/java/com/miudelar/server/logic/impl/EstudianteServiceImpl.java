/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miudelar.server.logic.controller.CarreraJpaController;
import com.miudelar.server.logic.controller.CursoJpaController;
import com.miudelar.server.logic.controller.ExamenJpaController;
import com.miudelar.server.logic.controller.RolJpaController;
import com.miudelar.server.logic.controller.UsuarioJpaController;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.entities.Carrera;
import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.PathParam;

/**
 *
 * @author rmoreno
 */
public class EstudianteServiceImpl implements EstudianteService {
    
    CursoJpaController cursoJpaController = new CursoJpaController();
    UsuarioJpaController usuarioJpaController = new UsuarioJpaController();
    RolJpaController rolJpaController = new RolJpaController();
    ExamenJpaController examenJpaController = new ExamenJpaController();
    CarreraJpaController carreraJpaController = new CarreraJpaController();
    JsonParser parser = new JsonParser();
    
    @Override
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException{
        List<DtCarrera> carreras = new ArrayList<>();
        carreraJpaController.findCarreraEntities().forEach(carrera -> {
            carreras.add(new DtCarrera(carrera.getCodigo(), carrera.getNombre()));
        });
        return carreras;
    }
    
    @Override
    public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException{
        List<DtCurso> cursos = new ArrayList<>();
        cursoJpaController.findCursoEntities().forEach(curso -> {
            cursos.add(curso.toDataType());
        });
         return cursos;
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

                Usuario usuario = usuarioJpaController.findUsuario(cedula);
                Rol rol = rolJpaController.findRol(4L);
                if (usuario.getRoles().contains(rol)) {
                    Curso curso = cursoJpaController.findCurso(idCurso);
                    usuario.addCurso(curso);

                    usuarioJpaController.edit(usuario);

                } else {
                    message = "El usuario no tiene rol estudiante";
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            Logger.getLogger(EstudianteServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
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

                Usuario usuario = usuarioJpaController.findUsuario(cedula);
                Rol rol = rolJpaController.findRol(4L);
                if (usuario.getRoles().contains(rol)) {
                    Carrera carrera = carreraJpaController.findCarrera(codigo);
                    usuario.addCarrera(carrera);

                    usuarioJpaController.edit(usuario);

                } else {
                    message = "El usuario no tiene rol estudiante";
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
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

                Usuario usuario = usuarioJpaController.findUsuario(cedula);
                Rol rol = rolJpaController.findRol(4L);
                if (usuario.getRoles().contains(rol)) {
                    Examen examen = examenJpaController.findExamen(idExamen);
                    usuario.addInscripcionesExamenes(examen);

                    usuarioJpaController.edit(usuario);

                } else {
                    message = "El usuario no tiene rol estudiante";
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
}
