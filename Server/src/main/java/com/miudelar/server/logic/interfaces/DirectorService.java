/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Carrera;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
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
@Path("/director")
@RolesAllowed({"DIRECTOR"})
public interface DirectorService {
    
    @GET
    @Path("carrera")
    @RolesAllowed({"DIRECTOR", "ESTUDIANTE", "BEDELIA"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtCarrera> getAllCarrera();
    
    @GET
    @Path("carrera/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtCarrera getCarrera(@PathParam("codigo")
        final Long codigo);
    
    @POST
    @Path("carrera")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveCarrera(final DtCarrera dtCarr);
    
    @POST
    @Path("carrera.edit")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editCarrera(DtCarrera carrera);
    
    @POST
    @Path("asignatura")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAsignatura(DtAsignatura dtasignatura);
    
    @POST
    @Path("asignatura.edit")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editAsignatura(DtAsignatura asignatura);
    
    @GET
    @Path("asignatura/carrera/{idCarrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura> getAsignaturasByCarrera(@PathParam("idCarrera")
        final Long idCarrera) throws NoSuchAlgorithmException;
    
    @GET
    @Path("asignatura/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtAsignatura getAsignatura(@PathParam("codigo")
        final Long codigo) throws NoSuchAlgorithmException;
    
    @POST
    @Path("asignaturacarrera")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveAsignaturaCarrera(String jsonObject);
    
    @POST
    @Path("asignaturacarrera.delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeAsignaturaCarrera(String json);
    
    @POST
    @Path("previas.addPrevia")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addPrevia(String json);
    
    @POST
    @Path("previas.removePrevia")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removePrevia(String json);
    
    @POST
    @Path("asignatura.delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String asignaturaDelete(String json);
    
    @POST
    @Path("carrera.delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String carreraDelete(String json);
    
    @GET
    @Path("previas/{idAignatura_Carrera_Madre}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtAsignatura_Carrera getPrevias(@PathParam("idAignatura_Carrera_Madre") final Long idMadre);
    
    @GET
    @Path("asignaturacarrera")
    @RolesAllowed({"DIRECTOR","BEDELIA"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura_Carrera> getAllAsignaturaCarrera();
    
    @GET
    @Path("asignatura")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura> getAllAsignatura();
    
//    public void saveCarrera(DtCarrera dtCarr);
        
//    public void saveAsignatura(DtAsignatura dtAsig);
    
}
