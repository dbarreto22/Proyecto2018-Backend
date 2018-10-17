/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtCurso;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
    @Path("curso")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtCurso> getAllCurso() throws NoSuchAlgorithmException;
    
    @POST
    @Path("inscripcionCurso/{cedula}/{idCurso}")
    @Produces(MediaType.TEXT_PLAIN)
    public String inscripcionCurso(@PathParam("cedula") final String cedula,
            @PathParam("idCurso") final Long idCurso);
    
    @POST
    @Path("inscripcionCarrera/{cedula}/{codigo}")
    @Produces(MediaType.TEXT_PLAIN)
    public String inscripcionCarrera(@PathParam("cedula") final String cedula,
            @PathParam("codigo") final Long codigo);
    
    @POST
    @Path("inscripcionExamen/{cedula}/{idExmamen}")
    @Produces(MediaType.TEXT_PLAIN)
    public String inscripcionExamen(@PathParam("cedula") final String cedula,
            @PathParam("idExmamen") final Long idExmamen);
}
