package com.miudelar.server.logic.interfaces;

import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Rol;
import java.security.NoSuchAlgorithmException;
import java.util.List;
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
public interface AdministradorService {
     
    @GET
    @Path("rol")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DtRol> getAllRol() throws NoSuchAlgorithmException;
//    
//    @POST
//    @Path("login")
//    @Produces(MediaType.TEXT_PLAIN)
//    public String login(
//            @FormParam("username") String username, 
//            @FormParam("password") String password) throws NoSuchAlgorithmException;
//    
//    @POST
//    @Path("usuario")
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_XML)
//    public String saveUsuario(DtUsuario usuario) throws NoSuchAlgorithmException;
    
    void rolSave(String tipo) throws RolWithInvalidDataException;
         
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
}
