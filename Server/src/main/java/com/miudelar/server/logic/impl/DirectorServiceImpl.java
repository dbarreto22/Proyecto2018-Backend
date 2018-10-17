/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.google.gson.Gson;
import com.miudelar.server.logic.controller.AsignaturaJpaController;
import com.miudelar.server.logic.controller.Asignatura_CarreraExtController;
import com.miudelar.server.logic.controller.Asignatura_CarreraJpaController;
import com.miudelar.server.logic.controller.CarreraJpaController;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
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
//    Asignatura_CarreraJpaController asig_carJpaController = new Asignatura_CarreraJpaController();
    Asignatura_CarreraExtController asig_carJpaController = new Asignatura_CarreraExtController();
    
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
    public Carrera getCarrera(Long codigo) {
        Carrera carrera = carreraJpaController.findCarrera(codigo);
        return carrera;
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
    public String editCarrera(String Carr){
        Carrera carrera = gson.fromJson(Carr, Carrera.class);
        String message = "OK";
        try {
            carreraJpaController.edit(carrera);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
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
    public String editAsignatura(String Asig){
        Asignatura asignatura = gson.fromJson(Asig, Asignatura.class);
        String message = "OK";
        try {
            asignaturaJpaController.edit(asignatura);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public Asignatura getAsignatura(Long codigo) throws NoSuchAlgorithmException{
        Asignatura asignatura = asignaturaJpaController.findAsignatura(codigo);
        return asignatura;
    }
    
    @Override
    public String saveAsignaturaCarrera(Long codigoAsig, Long codigoCarrera){
        String message = "OK";
        try {
//        Creo asociacion
            Asignatura asignatura = asignaturaJpaController.findAsignatura(codigoAsig);
            Carrera carrera = carreraJpaController.findCarrera(codigoCarrera);
            carrera.addAsignatura(asignatura);
            carreraJpaController.edit(carrera);

////        Creo entidad relación
            Asignatura_Carrera asignatura_carreraEntity = new Asignatura_Carrera(asignatura, carrera);
            asig_carJpaController.create(asignatura_carreraEntity);
 
////        Asocio entidad relación
            asignatura.addAsignatura_Carrera(asignatura_carreraEntity);
            asignaturaJpaController.edit(asignatura);
            
            carrera.addAsignatura_Carrera(asignatura_carreraEntity);
            carreraJpaController.edit(carrera);
            
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public void saveCarrera(DtCarrera carrera){
        Carrera carreraEntity = new Carrera(carrera.getCodigo(),carrera.getNombre());
        String message = "OK";
        try {
            carreraJpaController.create(carreraEntity);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
    }
    
    @Override
    public void saveAsignatura(DtAsignatura asignatura){
        Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(),asignatura.getNombre());
        String message = "OK";
        try {
            asignaturaJpaController.create(asignaturaEntity);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
    }
    
    @Override
    public String addPrevia(Long idMadre, Long idPrevia){
        String message = "OK";
        Asignatura_Carrera madre = asig_carJpaController.findAsignatura_Carrera(idMadre);
        Asignatura_Carrera previa = asig_carJpaController.findAsignatura_Carrera(idPrevia);
        madre.addPrevia(previa);
        try {
            asig_carJpaController.edit(madre);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = "No existe la asignatura madre en Asignatura_Carrera";
        }
        return message;   
    }
    
    @Override
    public String removePrevia(Long idMadre, Long idPrevia){
        String message = "OK";
        Asignatura_Carrera madre = asig_carJpaController.findAsignatura_Carrera(idMadre);
        Asignatura_Carrera previa = asig_carJpaController.findAsignatura_Carrera(idPrevia);
        madre.removePrevia(previa);
        try {
            asig_carJpaController.edit(madre);
        } catch (Exception ex) {
            Logger.getLogger(DirectorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = "No existe la asignatura madre en Asignatura_Carrera";
        }
        return message;   
    }
    
    @Override
    public List<DtAsignatura_Carrera> getPrevias(Long idMadre){
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asig_carJpaController.findAsignatura_Carrera(idMadre).getPrevias().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(), 
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }
    
    @Override
    public List<DtAsignatura_Carrera> getAllAsignaturaCarrera(){
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asig_carJpaController.findAsignatura_CarreraEntities().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(), 
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }
            
}
