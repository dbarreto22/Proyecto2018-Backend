package com.miudelar.server.logic.interfaces;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


/* @author Romina*/
@Path("/admin")
@RolesAllowed({"ADMIN"})
public interface AdministradorService {
     
    //Services
    @GET
    @Path("rol")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtRol> getAllRol() throws NoSuchAlgorithmException;
    
    @POST
    @Path("login")
    @PermitAll
    @Produces(MediaType.TEXT_PLAIN)
    public String login(String json);
    
    @POST
    @Path("usuario")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveUsuario(final DtUsuario usuario);
    
    @POST
    @Path("usuario.edit")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String editUsuario(final DtUsuario usuario);
    
    @POST
    @Path("usuario.delete")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteUsuario(DtUsuario usuario);
        
    @POST
    @Path("usuario.addRol")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addRol(String json);
    
    @POST
    @Path("usuario.removeRol")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeRol(String json);
    
    @GET
    @Path("usuario")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtUsuario> getAllUsuario() throws NoSuchAlgorithmException;
    
    @GET
    @Path("usuario/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public DtUsuario getUsuario(@PathParam("cedula")
        final String cedula);
             
//    @GET
//    @Path("verifyThePassword/input/{inputPass}/stored/{storedPass}")
//    @Produces("text/plain")
//    public boolean verifyThePassword(@PathParam("inputPass")
//        final String inputPass, @PathParam("storedPass")
//        final String storedPass) throws NoSuchAlgorithmException;

//    @GET
//    @Path("generatePassword/input/{inputPass}")
//    @Produces("text/plain")
//    public String generatePassword(@PathParam("inputPass")
//        final String inputPass) throws NoSuchAlgorithmException;
    
    //Managers
    void rolSave(String tipo) throws RolWithInvalidDataException;
    
//    public String saveUsuario(DtUsuario usuario);
   
}
