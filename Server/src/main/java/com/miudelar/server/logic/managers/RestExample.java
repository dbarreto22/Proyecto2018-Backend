/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.managers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
 
@Path("/main")
public class RestExample
{
    @GET
    @Path("/users")
    public Response getAllUsers()
    {
        String result = "<h1>RESTful Demo Application</h1>In real world application, a collection of users will be returned !!";
        return Response.status(200).entity(result).build();
    }
    
    @GET
    @Path("/carreras")
    @Produces("application/json")
    public Response getAllCarreras()
    {
        String result = "{ \"carreras\": [ { \"id\": 0, \"name\": \"Ingenieria Electrica\" }, { \"id\": 1, \"name\": \"Licenciatura en Psicologia\" }, { \"id\": 2, \"name\": \"Tecnologo en Informatica\" } ] }";
        return Response.status(200).entity(result).build();
    }
}