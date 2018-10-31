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
import com.miudelar.server.ejb.AsignaturaFacade;
import com.miudelar.server.ejb.AsignaturaFacadeLocal;
import com.miudelar.server.ejb.Asignatura_CarreraFacade;
import com.miudelar.server.ejb.Asignatura_CarreraFacade;
import com.miudelar.server.ejb.Asignatura_CarreraFacadeLocal;
import com.miudelar.server.ejb.CarreraFacade;
import com.miudelar.server.ejb.CarreraFacadeLocal;
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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.PathParam;

/**
 *
 * @author rmoreno
 */
public class DirectorServiceImpl implements DirectorService{

    private Asignatura_CarreraFacadeLocal asignatura_CarreraFacade = lookupAsignatura_CarreraFacadeBean();

    private AsignaturaFacadeLocal asignaturaFacade = lookupAsignaturaFacadeBean();

    private CarreraFacadeLocal carreraFacade = lookupCarreraFacadeBean();
    
    InitMgr initMgr = new InitMgr();
    
//    Gson gson = new Gson();
    JsonParser parser = new JsonParser();
    
       private CarreraFacadeLocal lookupCarreraFacadeBean() {
        try {
            Context c = new InitialContext();
            return (CarreraFacadeLocal) c.lookup("java:app/miudelar-server/CarreraFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private AsignaturaFacadeLocal lookupAsignaturaFacadeBean() {
        try {
            Context c = new InitialContext();
            return (AsignaturaFacadeLocal) c.lookup("java:app/miudelar-server/AsignaturaFacade");
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
    
    
    @Override
    public List<DtCarrera> getAllCarrera(){
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
    public Asignatura getAsignatura(Long codigo) {
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
                    if (asignatura == null){
                        message = "No existe asignatura";
                    }else{
                        Carrera carrera = carreraFacade.find(codigoCarrera);
                        if (carrera == null){
                            message = "No existe carrera";
                        }else{
                            carrera.addAsignatura(asignatura);

                            ////        Creo entidad relación
                            Asignatura_Carrera asignatura_carreraEntity = new Asignatura_Carrera(asignatura, carrera);
                            asignatura_CarreraFacade.create(asignatura_carreraEntity);

                            ////        Asocio entidad relación
                            asignatura.addAsignatura_Carrera(asignatura_carreraEntity);
                            asignaturaFacade.edit(asignatura);

                            carrera.addAsignatura_Carrera(asignatura_carreraEntity);
                            carreraFacade.edit(carrera);
                        }
                    }
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
                    Asignatura_Carrera madre = asignatura_CarreraFacade.find(idMadre);
                    if (madre == null){
                        message = "No existe asignatura: " + idMadre.toString();
                    }else{
                        Asignatura_Carrera previa = asignatura_CarreraFacade.find(idPrevia);
                        if (previa == null){
                            message = "No existe asignatura: " + idPrevia.toString();
                        }else{
                            if (!madre.getCarrera().getCodigo().equals(previa.getCarrera().getCodigo())){
                                message = "Las asignaturas no pertenecen a las misma carrera";
                            }else{
                                 if (initMgr.getAllPrevias(madre).contains(previa)){
                                    message = "Error: La asignatura es previa de esta u otra asignatura previa";
                                }else{
                                    if (initMgr.getAllPrevias(previa).contains(madre)){
                                        message = "Error: Asignatura no puede ser previa de si misma";
                                    }else{
                                        madre.addPrevia(previa);
                                        asignatura_CarreraFacade.edit(madre);
                                    }
                                }
                            }
                           
                        }
                    }
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
                    Asignatura_Carrera madre = asignatura_CarreraFacade.find(idMadre);
                    if (madre == null){
                        message = "No existe asignatura: " + idMadre.toString();
                    }else{
                        Asignatura_Carrera previa = asignatura_CarreraFacade.find(idPrevia);
                        if (previa == null){
                            message = "No existe asignatura: " + idPrevia.toString();
                        }else{
                            madre.removePrevia(previa);
                            asignatura_CarreraFacade.edit(madre);
                        }
                    }
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
    public DtAsignatura_Carrera getPrevias(Long idMadre){
        DtAsignatura_Carrera asigCar = asignatura_CarreraFacade.find(idMadre).toDataType();
        return asigCar;
    }
    
    @Override
    public List<DtAsignatura_Carrera> getAllAsignaturaCarrera(){
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asignatura_CarreraFacade.findAll().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(), 
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }
    
    
        
            
}
