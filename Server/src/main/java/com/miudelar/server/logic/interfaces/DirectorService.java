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
import javax.ws.rs.POST;
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
    @Path("carrera/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtCarrera getCarrera(@PathParam("codigo")
        final Long codigo) throws NoSuchAlgorithmException;
    
    @POST
    @Path("carrera/{dtCarr}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String saveCarrera(@PathParam("dtCarr") final String dtCarr);
    
    @POST
    @Path("carrera.edit/{dtCarr}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String editCarrera(@PathParam("dtCarr") final String dtCarr);
    
    @POST
    @Path("asignatura/{dtAsig}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAsignatura(@PathParam("dtAsig") final String dtAsig);
    
    @POST
    @Path("asignatura.edit/{dtAsig}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String editAsignatura(@PathParam("dtAsig") final String dtAsig);
    
    @GET
    @Path("asignatura/{idCarrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura> getAsignaturasByCarrera(@PathParam("idCarrera")
        final Long idCarrera) throws NoSuchAlgorithmException;
    
    @GET
    @Path("asignatura/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtAsignatura getAsignatura(@PathParam("codigo")
        final Long codigo) throws NoSuchAlgorithmException;
    
}
