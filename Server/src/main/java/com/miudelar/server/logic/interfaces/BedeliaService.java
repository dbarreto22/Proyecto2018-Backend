/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.interfaces;

import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.datatypes.DtExamen;
import com.miudelar.server.logic.datatypes.DtPeriodo_Examen;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Horario;
import com.miudelar.server.logic.entities.Periodo_Examen;
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
@Path("/bedelia")
@RolesAllowed({"BEDELIA"})
public interface BedeliaService {
    
    @GET
    @Path("estudiantesExamen/{idExamen}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtUsuario> getEstudiantesInscriptosExamen(@PathParam("idExamen")
        final Long idExamen);
        
    @GET
    @Path("estudiantesCurso/{idCurso}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtUsuario> getEstudiantesInscriptosCurso(@PathParam("idCurso")
        final Long idCurso);
    
    @GET
    @Path("asignaturaCarrera/{idCarrera}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtAsignatura_Carrera> getAsignaturaCarreraByCarrera(@PathParam("idCarrera")
        final Long idCarrera);
    
    @POST
    @Path("curso")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveCurso(String json);
    
    @POST
    @Path("examen")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveExamen(String json);
    
    @POST
    @Path("removecurso")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeCurso(String json);
    
    @POST
    @Path("removeexamen")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeExamen(String json);
    
    @POST
    @Path("horario")
    @Produces(MediaType.TEXT_PLAIN)
    public String saveHorario(String json);
    
    @POST
    @Path("horario.edit")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editHorario(Horario horario);
    
    @POST
    @Path("periodo")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String savePeriodoExamen(final DtPeriodo_Examen periodo);
    
    @POST
    @Path("periodo.edit")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editPeriodoExamen(final Periodo_Examen periodo);
    
    @POST
    @Path("notasCurso")
    @Produces(MediaType.TEXT_PLAIN)
    public String cargarNotasCurso(String json);
    
    @POST
    @Path("notasExamen")
    @Produces(MediaType.TEXT_PLAIN)
    public String cargarNotasExamen(String json);
    
    @GET
    @Path("actaCurso/{idCurso}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getActaFinCurso(@PathParam("idCurso")
        final Long idCurso);
    
    @GET
    @Path("actaExamen/{idExamen}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getActaExamen(@PathParam("idExamen")
        final Long idExamen);
    
    @GET
    @Path("escolaridad/{cedula}/{codigoCarrera}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getEscolaridad(@PathParam("cedula")
        final String cedula, @PathParam("codigoCarrera")
        final Long codigoCarrera);
}
