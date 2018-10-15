/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rmoreno
 */
@Path("/director")
public interface DirectorService {
    
    @GET
    @Path("carrera")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtCarrera> getAllCarrera() throws NoSuchAlgorithmException;
    
    @GET
    @Path("asignatura/{idCarrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura> getAsignaturasByCarrera(@PathParam("idCarrera")
        final Long idCarrera) throws NoSuchAlgorithmException;
    
}
