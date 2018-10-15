/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.logic.controller.CarreraJpaController;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.PathParam;

/**
 *
 * @author rmoreno
 */
public class DirectorServiceImpl implements DirectorService{
    
    CarreraJpaController carreraJpaController = new CarreraJpaController();
    
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException{
        List<DtCarrera> carreras = new ArrayList<>();
        carreraJpaController.findCarreraEntities().forEach(carrera -> {
            carreras.add(new DtCarrera(carrera.getCodigo(), carrera.getNombre()));
        });
        return carreras;
    }
    
    public List<DtAsignatura> getAsignaturasByCarrera(Long idCarrera) throws NoSuchAlgorithmException{
        List<DtAsignatura> asignaturas = new ArrayList<>();
        carreraJpaController.findCarrera(idCarrera).getAsignaturas().forEach(asignatura -> {
            asignaturas.add(new DtAsignatura(asignatura.getCodigo(), asignatura.getNombre()));
        });
        return asignaturas;
    }
    
}
