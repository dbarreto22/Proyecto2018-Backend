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
public class DirectorServiceImpl implements DirectorService {

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
    public List<DtCarrera> getAllCarrera() {
        List<DtCarrera> carreras = new ArrayList<>();
        carreraFacade.findAll().forEach(carrera -> {
            carreras.add(new DtCarrera(carrera.getCodigo(), carrera.getNombre()));
        });
        return carreras;
    }

    @Override
    public List<DtAsignatura> getAsignaturasByCarrera(Long idCarrera) throws NoSuchAlgorithmException {
        List<DtAsignatura> asignaturas = new ArrayList<>();
        asignaturaFacade.findByCarrera(idCarrera).forEach(asignatura -> {
            asignaturas.add(new DtAsignatura(asignatura.getCodigo(), asignatura.getNombre()));
        });
        return asignaturas;
    }

    @Override
    public DtCarrera getCarrera(Long codigo) {
        Carrera carrera = carreraFacade.find(codigo);
        return carrera.toDataType();
    }

    @Override
    public String removeAsignaturaCarrera(String json) {
        String message = "OK";
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            Long idAsigCar = jsonObject.get("idAsigCar").getAsLong();
            Asignatura_Carrera asig_car = asignatura_CarreraFacade.find(idAsigCar);
            if (!asig_car.getCursos().isEmpty()) {
                message = "Error, hay cursos creados para esta Asignatura y Carrera";
            } else {
                if (!asig_car.getPrevias().isEmpty()) {
                    message = "Error, hay previas creadas para esta Asignatura y Carrera";
                } else {
                    asignatura_CarreraFacade.remove(asig_car);
                }
            }
        }
        return message;
    }

    @Override
    public String editCarrera(DtCarrera carrera) {
//        Carrera carrera = gson.fromJson(Carr, Carrera.class);
        String message = "OK";
        try {
            Carrera car = carreraFacade.find(carrera.getCodigo());
            car.setNombre(carrera.getNombre());
            carreraFacade.edit(car);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: " + ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }

    @Override
    public String asignaturaDelete(String json) {
        String message = "OK";
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            Long codigo = jsonObject.get("codigo").getAsLong();
            Asignatura asignatura = asignaturaFacade.find(codigo);
            if (!asignatura.getAsignatura_Carreras().isEmpty()) {
                message = "Error, no es posible eliminar la asignatura, tiene carreras asociadas";
            } else {
                asignaturaFacade.remove(asignatura);
            }
        }
        return message;
    }

    @Override
    public String carreraDelete(String json) {
        String message = "OK";
        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            Long codigo = jsonObject.get("codigo").getAsLong();
            Carrera carrera = carreraFacade.find(codigo);
            if (!carrera.getAsignatura_Carreras().isEmpty()) {
                message = "Error, no es posible eliminar la carrera, tiene asignaturas asociadas";
            } else {
                carreraFacade.remove(carrera);
            }
        }
        return message;
    }

    @Override
    public String editAsignatura(DtAsignatura asignatura) {
//        Asignatura asignatura = gson.fromJson(Asig, Asignatura.class);
        String message = "OK";
        try {
            Asignatura asig = asignaturaFacade.find(asignatura.getCodigo());
            asig.setNombre(asignatura.getNombre());
            asignaturaFacade.edit(asig);
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: " + ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }

    @Override
    public DtAsignatura getAsignatura(Long codigo) {
        Asignatura asignatura = asignaturaFacade.find(codigo);
        return asignatura.toDataType();
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
                if (asignatura == null) {
                    message = "No existe asignatura";
                } else {
                    Carrera carrera = carreraFacade.find(codigoCarrera);
                    if (carrera == null) {
                        message = "No existe carrera";
                    } else {
                        if (asignatura_CarreraFacade.findByCods(codigoCarrera, codigoAsignatura) != null) {
                            message = "Ya existe la relación";
                        } else {
                            System.out.println("asignatura: " + asignatura.getCodigo());
                            System.out.println("carrera: " + carrera.getCodigo());

                            ////        Creo entidad relación
                            Asignatura_Carrera asignatura_carreraEntity = new Asignatura_Carrera(asignatura, carrera);
                            asignatura_CarreraFacade.create(asignatura_carreraEntity);
                        }
                    }
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: " + ex.getMessage() + " " + json);
            message = ex.getMessage() + " " + json;
        }
        return message;
    }

    @Override
    public String saveCarrera(DtCarrera carrera) {
        String message = "";
        if (carreraFacade.find(carrera.getCodigo()) != null){
            return "La carrera con código : " + carrera.getCodigo() + " ya fue ingresada";
        }else{
            if (!carreraFacade.findByNombre(carrera.getNombre()).isEmpty()){
                message = "Ya existe carrera con nombre: " + carrera.getNombre();
            }else{
                Carrera carreraEntity = new Carrera(carrera.getCodigo(), carrera.getNombre());
                message = "OK";
                try {
                    carreraFacade.create(carreraEntity);
                } catch (Exception ex) {
                    System.out.println("Class:DirectorServiceImpl: " + ex.getMessage());
                    message = ex.getMessage();
                }
            }
        return message;
        }
    }

    @Override
    public String saveAsignatura(DtAsignatura asignatura) {
        String message = "";
        if (asignaturaFacade.find(asignatura.getCodigo()) != null){
            return "La carrera con código : " + asignatura.getCodigo() + " ya fue ingresada";
        }else{
            if (!asignaturaFacade.findByNombre(asignatura.getNombre()).isEmpty()){
                message = "Ya existe asignatura con nombre: " + asignatura.getNombre();
            }else{
                Asignatura asignaturaEntity = new Asignatura(asignatura.getCodigo(), asignatura.getNombre());
                message = "OK";
                try {
                    asignaturaFacade.create(asignaturaEntity);
                } catch (Exception ex) {
                    System.out.println("Class:DirectorServiceImpl: " + ex.getMessage());
                    message = ex.getMessage();
                }
            }
        
        return message;
        }
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
                if (madre == null) {
                    message = "No existe asignatura: " + idMadre.toString();
                } else {
                    Asignatura_Carrera previa = asignatura_CarreraFacade.find(idPrevia);
                    if (previa == null) {
                        message = "No existe asignatura: " + idPrevia.toString();
                    } else {
                        if (!madre.getCarrera().getCodigo().equals(previa.getCarrera().getCodigo())) {
                            message = "Las asignaturas no pertenecen a las misma carrera";
                        } else {
                            if (initMgr.getAllPrevias(madre).contains(previa)) {
                                message = "Error: La asignatura es previa de esta u otra asignatura previa";
                            } else {
                                if (initMgr.getAllPrevias(previa).contains(madre)) {
                                    message = "Error: Asignatura no puede ser previa de si misma";
                                } else {
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
            System.out.println("Class:DirectorServiceImpl: " + ex.getMessage() + " " + json);
            message = ex.getMessage() + " " + json;
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
                if (madre == null) {
                    message = "No existe asignatura: " + idMadre.toString();
                } else {
                    Asignatura_Carrera previa = asignatura_CarreraFacade.find(idPrevia);
                    if (previa == null) {
                        message = "No existe asignatura: " + idPrevia.toString();
                    } else {
                        madre.removePrevia(previa);
                        asignatura_CarreraFacade.edit(madre);
                    }
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:DirectorServiceImpl: " + ex.getMessage() + " " + json);
            message = ex.getMessage() + " " + json;
        }
        return message;
    }

    @Override
    public DtAsignatura_Carrera getPrevias(Long idMadre) {
        DtAsignatura_Carrera asigCar = asignatura_CarreraFacade.find(idMadre).toDataType();
        return asigCar;
    }

    @Override
    public List<DtAsignatura_Carrera> getAllAsignaturaCarrera() {
        List<DtAsignatura_Carrera> asigCar = new ArrayList<>();
        asignatura_CarreraFacade.findAll().forEach(previa -> {
            asigCar.add(new DtAsignatura_Carrera(previa.getId(),
                    new DtCarrera(previa.getCarrera().getCodigo(), previa.getCarrera().getNombre()),
                    new DtAsignatura(previa.getAsignatura().getCodigo(), previa.getAsignatura().getNombre())));
        });
        return asigCar;
    }

    @Override
    public List<DtAsignatura> getAllAsignatura() {
        List<DtAsignatura> asignaturas = new ArrayList<>();
        asignaturaFacade.findAll().forEach(asig -> {
            asignaturas.add(asig.toDataType());
        });
        return asignaturas;
    }

}
