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
import com.miudelar.server.logic.sessionbeans.AsignaturaFacade;
import com.miudelar.server.logic.sessionbeans.Asignatura_CarreraFacade;
import com.miudelar.server.logic.sessionbeans.Asignatura_CarreraFacade;
import com.miudelar.server.logic.sessionbeans.CarreraFacade;
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
    
//    Gson gson = new Gson();
    JsonParser parser = new JsonParser();
    
    CarreraFacade carreraFacade = new CarreraFacade();
    AsignaturaFacade asignaturaFacade = new AsignaturaFacade();
    Asignatura_CarreraFacade asig_carFacade = new Asignatura_CarreraFacade();
    
    @Override
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException{
        List<DtCarrera> carreras = new ArrayList<>();
        carreraFacade.findAll().forEach(carrera -> {
            carreras.add(new DtCarrera(carrera.getCodigo(), carrera.getNombre()));
        });
        return carreras;
    }
    
    @Override
    public List<DtAsignatura> getAsignaturasByCarrera(Long idCarrera) throws NoSuchAlgorithmException{
        List<DtAsignatura> asignaturas = new ArrayList<>();
        carreraFacade.find(idCarrera).getAsignaturas().forEach(asignatura -> {
            asignaturas.add(new DtAsignatura(asignatura.getCodigo(), asignatura.getNombre()));
        });
        return asignaturas;
    }
    
    @Override
    public Carrera getCarrera(Long codigo) {
        Carrera carrera = carreraFacade.find(codigo);
        return carrera;
    }
    
//    @Override
//    public String saveCarrera(String dtCarr){
//        DtCarrera carrera = gson.fromJson(dtCarr, DtCarrera.class);
//        Carrera carreraEntity = new Carrera(carrera.getCodigo(),carrera.getNombre());
//        String message = "OK";
//        try {
//            carreraFacade.create(carreraEntity);
//        } catch (Exception ex) {
//            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
//            message = ex.getMessage();
//        }
//        return message;
//    }
    
    @Override
    public String editCarrera(Carrera carrera){
//        Carrera carrera = gson.fromJson(Carr, Carrera.class);
        String message = "OK";
        try {
            carreraFacade.edit(carrera);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
//    @Override
//    public String saveAsignatura(String dtAsig){
//        DtAsignatura asignatura = gson.fromJson(dtAsig, DtAsignatura.class);
//        Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(),asignatura.getNombre());
//        String message = "OK";
//        try {
//            asignaturaFacade.create(asignaturaEntity);
//        } catch (Exception ex) {
//            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
//            message = ex.getMessage();
//        }
//        return message;
//    }
    
    @Override
    public String editAsignatura(Asignatura asignatura){
//        Asignatura asignatura = gson.fromJson(Asig, Asignatura.class);
        String message = "OK";
        try {
            asignaturaFacade.edit(asignatura);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public Asignatura getAsignatura(Long codigo) throws NoSuchAlgorithmException{
        Asignatura asignatura = asignaturaFacade.find(codigo);
        return asignatura;
    }
    
    @Override
    public String saveAsignaturaCarrera(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long codigoAsignatura = jsonObject.get("codigoAsignatura").getAsLong();
                    Long codigoCarrera = jsonObject.get("codigoCarrera").getAsLong();

                    //        Creo asociacion
                    Asignatura asignatura = asignaturaFacade.find(codigoAsignatura);
                    Carrera carrera = carreraFacade.find(codigoCarrera);
                    carrera.addAsignatura(asignatura);

                    ////        Creo entidad relación
                    Asignatura_Carrera asignatura_carreraEntity = new Asignatura_Carrera(asignatura, carrera);
                    asig_carFacade.create(asignatura_carreraEntity);

                    ////        Asocio entidad relación
                    asignatura.addAsignatura_Carrera(asignatura_carreraEntity);
                    asignaturaFacade.edit(asignatura);

                    carrera.addAsignatura_Carrera(asignatura_carreraEntity);
                    carreraFacade.edit(carrera);
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage() + " " +json;
        }
        return message;
    }
    
    @Override
    public String saveCarrera(DtCarrera carrera){
        Carrera carreraEntity = new Carrera(carrera.getCodigo(),carrera.getNombre());
        String message = "OK";
        try {
            carreraFacade.create(carreraEntity);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String saveAsignatura(DtAsignatura asignatura){
        Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(),asignatura.getNombre());
        String message = "OK";
        try {
            asignaturaFacade.create(asignaturaEntity);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String addPrevia(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);
            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idMadre = jsonObject.get("idMadre").getAsLong();
                    Long idPrevia = jsonObject.get("idPrevia").getAsLong();
                    Asignatura_Carrera madre = asig_carFacade.find(idMadre);
                    Asignatura_Carrera previa = asig_carFacade.find(idPrevia);
                    madre.addPrevia(previa);

                    asig_carFacade.edit(madre);
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;   
    }
    
    @Override
    public String removePrevia(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                    Long idMadre = jsonObject.get("idMadre").getAsLong();
                    Long idPrevia = jsonObject.get("idPrevia").getAsLong();
                    Asignatura_Carrera madre = asig_carFacade.find(idMadre);
                    Asignatura_Carrera previa = asig_carFacade.find(idPrevia);
                    madre.removePrevia(previa);
                    asig_carFacade.edit(madre);
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: "+ ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }
    
    @Override
    public List<DtAsignatura_Carrera> getPrevias(Long idMadre){
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asig_carFacade.find(idMadre).getPrevias().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(), 
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }
    
    @Override
    public List<DtAsignatura_Carrera> getAllAsignaturaCarrera(){
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asig_carFacade.findAll().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(), 
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }
            
}
