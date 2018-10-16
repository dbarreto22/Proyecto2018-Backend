/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Carrera;
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
    public Carrera getCarrera(@PathParam("codigo")
        final Long codigo) throws NoSuchAlgorithmException;
    
    @POST
    @Path("carrera/{dtCarr}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String saveCarrera(@PathParam("dtCarr") final String dtCarr);
    
    @POST
    @Path("carrera.edit/{Carr}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String editCarrera(@PathParam("Carr") final String Carr);
    
    @POST
    @Path("asignatura/{dtAsig}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAsignatura(@PathParam("dtAsig") final String dtAsig);
    
    @POST
    @Path("asignatura.edit/{Asig}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String editAsignatura(@PathParam("Asig") final String Asig);
    
    @GET
    @Path("asignatura/carrera/{idCarrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura> getAsignaturasByCarrera(@PathParam("idCarrera")
        final Long idCarrera) throws NoSuchAlgorithmException;
    
    @GET
    @Path("asignatura/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Asignatura getAsignatura(@PathParam("codigo")
        final Long codigo) throws NoSuchAlgorithmException;
    
    @POST
    @Path("asignaturacarrera/{codigoAsig}/{codigoCarrera}")
    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAsignaturaCarrera(@PathParam("codigoAsig") final Long codigoAsig,
            @PathParam("codigoCarrera") final Long codigoCarrera);
    
}
