/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.logic.controller.CursoJpaController;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rmoreno
 */
public class EstudianteServiceImpl implements EstudianteService {
    
    CursoJpaController cursoJpaController = new CursoJpaController();
    
     public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException{
        List<DtCurso> cursos = new ArrayList<>();
        cursoJpaController.findCursoEntities().forEach(curso -> {
            cursos.add(curso.toDataType());
        });
         return cursos;
     }
}
