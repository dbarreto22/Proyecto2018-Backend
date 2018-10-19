/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtCalificaciones;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rmoreno
 */
@Path("/estudiante")
public interface EstudianteService {
    
    @GET
    @Path("carrera")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException;
    
    @GET
    @Path("curso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException;
    
    @GET
    @Path("consultarCalificaciones/{cedula}/{idAsig_Carrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Estudiante_Curso> getCalificaciones(@PathParam("cedula")
        final String cedula, @PathParam("idAsig_Carrera")
        final Long idAsig_Carrera);
    
    @POST
    @Path("inscripcionCurso")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String inscripcionCurso(String json);
    
    @POST
    @Path("inscripcionCarrera")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String inscripcionCarrera(String json);
    
    @POST
    @Path("inscripcionExamen")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String inscripcionExamen(String json);
}
