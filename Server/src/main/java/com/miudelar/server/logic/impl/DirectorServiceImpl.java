/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.Gson;
import com.miudelar.server.logic.controller.AsignaturaJpaController;
import com.miudelar.server.logic.controller.CarreraJpaController;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Carrera;
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
public class DirectorServiceImpl implements DirectorService{
    
    Gson gson = new Gson();
    
    CarreraJpaController carreraJpaController = new CarreraJpaController();
    AsignaturaJpaController asignaturaJpaController = new AsignaturaJpaController();
    
    @Override
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException{
        List<DtCarrera> carreras = new ArrayList<>();
        carreraJpaController.findCarreraEntities().forEach(carrera -> {
            carreras.add(new DtCarrera(carrera.getCodigo(), carrera.getNombre()));
        });
        return carreras;
    }
    
    @Override
    public List<DtAsignatura> getAsignaturasByCarrera(Long idCarrera) throws NoSuchAlgorithmException{
        List<DtAsignatura> asignaturas = new ArrayList<>();
        carreraJpaController.findCarrera(idCarrera).getAsignaturas().forEach(asignatura -> {
            asignaturas.add(new DtAsignatura(asignatura.getCodigo(), asignatura.getNombre()));
        });
        return asignaturas;
    }
    
    @Override
    public DtCarrera getCarrera(Long codigo) {
        Carrera carrera = carreraJpaController.findCarrera(codigo);
        DtCarrera dtcarrera = new DtCarrera(carrera.getCodigo(), carrera.getNombre());
        return dtcarrera;
    }
    
    @Override
    public String saveCarrera(String dtCarr){
        DtCarrera carrera = gson.fromJson(dtCarr, DtCarrera.class);
        Carrera carreraEntity = new Carrera(carrera.getCodigo(),carrera.getNombre());
        String message = "OK";
        try {
            carreraJpaController.create(carreraEntity);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String editCarrera(String dtCarr){
        DtCarrera carrera = gson.fromJson(dtCarr, DtCarrera.class);
        Carrera carreraEntity = new Carrera(carrera.getCodigo(),carrera.getNombre());
        String message = "OK";
        try {
            carreraJpaController.edit(carreraEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String saveAsignatura(String dtAsig){
        DtAsignatura asignatura = gson.fromJson(dtAsig, DtAsignatura.class);
        Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(),asignatura.getNombre());
        String message = "OK";
        try {
            asignaturaJpaController.create(asignaturaEntity);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String editAsignatura(String dtAsig){
        DtAsignatura asignatura = gson.fromJson(dtAsig, DtAsignatura.class);
        Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(),asignatura.getNombre());
        String message = "OK";
        try {
            asignaturaJpaController.edit(asignaturaEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public DtAsignatura getAsignatura(Long codigo) throws NoSuchAlgorithmException{
        Asignatura asignatura = asignaturaJpaController.findAsignatura(codigo);
        DtAsignatura dtasignatura = new DtAsignatura(asignatura.getCodigo(), asignatura.getNombre());
        return dtasignatura;
    }
    
}
