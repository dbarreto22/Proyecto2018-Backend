/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.logic.controller.CarreraJpaController;
import com.miudelar.server.logic.controller.CursoJpaController;
import com.miudelar.server.logic.controller.ExamenJpaController;
import com.miudelar.server.logic.controller.RolJpaController;
import com.miudelar.server.logic.controller.UsuarioJpaController;
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
    
    @Override
    public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException{
        List<DtCurso> cursos = new ArrayList<>();
        cursoJpaController.findCursoEntities().forEach(curso -> {
            cursos.add(curso.toDataType());
        });
         return cursos;
     }
    
    @Override
    public String inscripcionCurso(String cedula, Long idCurso){
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(4L);
        if (usuario.getRoles().contains(rol)){
            Curso curso = cursoJpaController.findCurso(idCurso);
            usuario.addCurso(curso);
            String message = "OK";
            try {
            usuarioJpaController.edit(usuario);
            } catch (Exception ex) {
                Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                message = ex.getMessage();
            }
           return message;
        }else
            return "El usuario no tiene rol estudiante";
     }
     
    @Override
     public String inscripcionCarrera(String cedula, Long codigo){
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(4L);
        if (usuario.getRoles().contains(rol)){
            Carrera carrera = carreraJpaController.findCarrera(codigo);
            usuario.addCarrera(carrera);
            String message = "OK";
            try {
            usuarioJpaController.edit(usuario);
            } catch (Exception ex) {
                Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                message = ex.getMessage();
            }
           return message;
        }else
            return "El usuario no tiene rol estudiante";
     }
     
     @Override
     public String inscripcionExamen(String cedula, Long idExmamen){
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(4L);
        if (usuario.getRoles().contains(rol)){
            Examen examen = examenJpaController.findExamen(idExmamen);
            usuario.addInscripcionesExamenes(examen);
            String message = "OK";
            try {
            usuarioJpaController.edit(usuario);
            } catch (Exception ex) {
                Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                message = ex.getMessage();
            }
           return message;
        }else
            return "El usuario no tiene rol estudiante";
     }
}
