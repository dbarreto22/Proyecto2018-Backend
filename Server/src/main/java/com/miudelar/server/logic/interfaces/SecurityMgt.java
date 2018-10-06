package com.miudelar.server.logic.interfaces;

import java.security.NoSuchAlgorithmException;
import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;


/* @author Romina*/
@Path("/security")
public interface SecurityMgt {
    @GET
    @Path("verifyThePassword/input/{inputPass}/stored/{storedPass}")
    @Produces("text/plain")
    public boolean verifyThePassword(@PathParam("inputPass")
        final String inputPass, @PathParam("storedPass")
        final String storedPass) throws NoSuchAlgorithmException;

    @GET
    @Path("generatePassword/input/{inputPass}")
    @Produces("text/plain")
    public String generatePassword(@PathParam("inputPass")
        final String inputPass) throws NoSuchAlgorithmException;
}
