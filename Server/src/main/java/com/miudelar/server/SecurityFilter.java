/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.factories.ManagersFactory;
import com.miudelar.server.logic.interfaces.SecurityMgt;
import com.miudelar.server.ejb.RolFacade;
import com.miudelar.server.ejb.RolFacadeLocal;
import com.miudelar.server.ejb.UsuarioFacade;
import com.miudelar.server.ejb.UsuarioFacadeLocal;
import com.miudelar.server.logic.impl.SecurityMgr;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Priority;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.core.Headers;
import org.jboss.resteasy.core.ResourceMethodInvoker;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.util.Base64;

/**
 *
 * @author rmoreno
 */
@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements javax.ws.rs.container.ContainerRequestFilter {

    private RolFacadeLocal rolFacade = lookupRolFacadeBean();
    
    private UsuarioFacadeLocal usuarioFacade = lookupUsuarioFacadeBean();
    
    SecurityMgr security = new SecurityMgr();
    
    private RolFacadeLocal lookupRolFacadeBean() {
        try {
            Context c = new InitialContext();
            return (RolFacadeLocal) c.lookup("java:app/miudelar-server/RolFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private UsuarioFacadeLocal lookupUsuarioFacadeBean() {
        try {
            Context c = new InitialContext();
            return (UsuarioFacadeLocal) c.lookup("java:app/miudelar-server/UsuarioFacade");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    SecurityMgt securityMgt = ManagersFactory.getInstance().getSecurityMgt();

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final ServerResponse ACCESS_DENIED = new ServerResponse("Access denied for this resource", 401, new Headers<Object>());
    private static final ServerResponse ACCESS_FORBIDDEN = new ServerResponse("Nobody can access this resource", 403, new Headers<Object>());
    private static final ServerResponse SERVER_ERROR = new ServerResponse("INTERNAL SERVER ERROR", 500, new Headers<Object>());


    @Override
    public void filter(ContainerRequestContext requestContext) {
        ResourceMethodInvoker methodInvoker = (ResourceMethodInvoker) requestContext.getProperty("org.jboss.resteasy.core.ResourceMethodInvoker");
        Method method = methodInvoker.getMethod();
        //Access allowed for all
        if (!method.isAnnotationPresent(PermitAll.class)) {
            //Access denied for all
            if (method.isAnnotationPresent(DenyAll.class)) {
                requestContext.abortWith(ACCESS_FORBIDDEN);
                return;
            }

            //Get request headers
            final MultivaluedMap<String, String> headers = requestContext.getHeaders();

            //Fetch authorization header
            final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

            //If no authorization information present; block access
            UriInfo uriinfo = requestContext.getUriInfo();
            if (!uriinfo.getPath().isEmpty()) {
                System.out.println("uriinfo.getPath(): " + uriinfo.getPath());
                    if ((authorization == null || authorization.isEmpty())) {
                        requestContext.abortWith(ACCESS_DENIED);
                        return;
                    }

                    try {
                        //getToken
                        System.out.println("authorization.get(0): " + authorization.get(0));
                        String token = authorization.get(0).replaceFirst("Bearer ", "");
                        System.out.println("token: " + token);
                        Algorithm algorithm = Algorithm.HMAC256(securityMgt.getSecret());
                        JWTVerifier verifier = JWT.require(algorithm)
                                .withIssuer("miUdelar")
                                .build();

                        DecodedJWT jwt = verifier.verify(token);
                        System.out.println("jwt: " + jwt);
                        
                        DecodedJWT decodedToken = JWT.decode(token);
                        System.out.println("decodedToken: " + decodedToken);
                        
                        String encodedUserName = decodedToken.getClaim("username").asString();
                        String encodedPassword = decodedToken.getClaim("password").asString();
                        
                        System.out.println("encodedUserName: " + encodedUserName);
                        System.out.println("encodedPassword: " + encodedPassword);
                        
                        //Decode username
                        String username, password = null;
                        username = new String(Base64.decode(encodedUserName));
                        password = new String(Base64.decode(encodedPassword));

                        //Verifying Username and password
                        System.out.println(username);
                        System.out.println(password);

                        //Verify user access
                        if (method.isAnnotationPresent(RolesAllowed.class)) {
                            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);

                            Set<String> rolesSet = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

                            //Is user valid?
                            if (!isUserAllowed(username, password, rolesSet)) {
                                requestContext.abortWith(ACCESS_DENIED);
                                return;
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("SecurityFilter: " + e.getMessage());
                        requestContext.abortWith(ACCESS_FORBIDDEN);
                        return;
                    }
            }

        }
    }

    private boolean isUserAllowed(final String username, final String password, final Set<String> rolesSet) {
        boolean isAllowed = false;

        Usuario usuario = usuarioFacade.find(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            List<Rol> roles = usuario.getRoles();
            for (Rol rol : roles) {
                if (rolesSet.contains(rol.getTipo())) {
                    isAllowed = true;
                    break;
                }
            }
            return isAllowed;
        } else {
            return isAllowed = false;
        }

        //Step 1. Fetch password from database and match with password in argument
        //If both match then get the defined role for user from database and continue; else return isAllowed [false]
        //Access the database and do this part yourself
        //String userRole = userMgr.getUserRole(username);
    }

}
