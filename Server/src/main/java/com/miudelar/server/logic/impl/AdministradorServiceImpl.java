package com.miudelar.server.logic.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.miudelar.server.ejb.RolFacade;
import com.miudelar.server.ejb.RolFacadeLocal;
import com.miudelar.server.ejb.UsuarioFacade;
import com.miudelar.server.ejb.UsuarioFacadeLocal;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import java.security.NoSuchAlgorithmException;
import com.miudelar.server.logic.interfaces.AdministradorService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class AdministradorServiceImpl implements AdministradorService {

    JsonParser parser = new JsonParser();

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

    @Override
    public void rolSave(String tipo) throws RolWithInvalidDataException {
        System.out.println("tipo: " + tipo);
        Rol rolEntity = new Rol(tipo);
        try {
            rolFacade.create(rolEntity);
        } catch (Exception ex) {
            System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage());
            throw new RolWithInvalidDataException();
        }
    }

    @Override
    public List<DtRol> getAllRol() throws NoSuchAlgorithmException {
        List<DtRol> roles = new ArrayList<>();
        rolFacade.findAll().forEach(tipo -> {
            roles.add(new DtRol(tipo.getId(), tipo.getTipo()));
        });
        return roles;
    }

    @Override
    public String login(String json) {
        String message;

        JsonElement jsonTree = parser.parse(json);
        if (jsonTree.isJsonObject()) {
            JsonObject jsonObject = jsonTree.getAsJsonObject();
            String username = jsonObject.get("username").getAsString();
            String password = jsonObject.get("password").getAsString();
            System.out.println("jsonObject: " + username + password);
            try {
                Usuario usuario = usuarioFacade.find(username);
                if (usuario == null || usuario.getActivo() == false) {
                    message = "Error: Usuario o contraseña incorrecta";
                } else {
                    if (usuario.getPassword().equals(password)) {
                        message = security.createAndSignToken(username, password);
                    } else {
                        message = "Error: Usuario o contraseña incorrecta";
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage() + " " + json);
                message = ex.getMessage() + " " + json;
            }

        } else {
            message = "Esto no es un json o no lo entiendo: " + json;
        }

        return message;
    }

    @Override
    public String saveUsuario(DtUsuario usuario) {
//        DtUsuario usuario = gson.fromJson(dtUsrStr, DtUsuario.class);
        Usuario usuarioEntity = new Usuario(usuario);
        String message = "OK";
        try {
            usuarioFacade.create(usuarioEntity);
        } catch (Exception ex) {
            System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }

//    @Override
//    public String saveUsuario(DtUsuario usuario){
//        System.out.println("usuarioToJson: " + gson.toJson(usuario));
//        Usuario usuarioEntity = new Usuario(usuario);
//        String message = "OK";
//        try {
//            usuarioFacade.create(usuarioEntity);
//        } catch (Exception ex) {
//            System.out.println("Class:AdministradorServiceImpl: "+ ex.getMessage());
//            message = ex.getMessage();
//        }
//        return message;
//    }
    @Override
    public String editUsuario(Usuario usuario) {
//        Usuario usuario = gson.fromJson(UsrStr, Usuario.class);
        String message = "OK";
        try {
            usuarioFacade.edit(usuario);
        } catch (Exception ex) {
            System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage());
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String deleteUsuario(final Usuario usuario){
        String message = "OK";
        Usuario usr = usuarioFacade.find(usuario.getCedula());
        usr.setActivo(Boolean.FALSE);
        return message;
    }

    @Override
    public String addRol(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                String cedula = jsonObject.get("cedula").getAsString();
                Long idRol = jsonObject.get("idRol").getAsLong();

                Usuario usuario = usuarioFacade.find(cedula);
                if (usuario == null) {
                    message = "El usuario no existe";
                } else {
                    Rol rol = rolFacade.find(idRol);
                    if (rol == null) {
                        message = "El rol no existe";
                    } else {
                        usuario.addRol(rol);
                        usuarioFacade.edit(usuario);
                    }
                }

            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage() + " " + json);
            message = ex.getMessage() + " " + json;
        }
        return message;
    }

    @Override
    public String removeRol(String json) {
        String message = "OK";
        try {
            JsonElement jsonTree = parser.parse(json);

            if (jsonTree.isJsonObject()) {
                JsonObject jsonObject = jsonTree.getAsJsonObject();
                String cedula = jsonObject.get("cedula").getAsString();
                Long idRol = jsonObject.get("idRol").getAsLong();

                Usuario usuario = usuarioFacade.find(cedula);
                if (usuario == null) {
                    message = "El usuario no existe";
                } else {
                    Rol rol = rolFacade.find(idRol);
                    if (rol == null) {
                        message = "El rol no existe";
                    } else {
                        usuario.removeRol(rol);
                        usuarioFacade.edit(usuario);
                    }
                }
            } else {
                message = "Esto no es un json o no lo entiendo: " + json;
            }
        } catch (Exception ex) {
            System.out.println("Class:AdministradorServiceImpl: " + ex.getMessage()+ " " +json);
            message = ex.getMessage()+ " " +json;
        }
        return message;
    }

    @Override
    public List<DtUsuario> getAllUsuario() throws NoSuchAlgorithmException {
        List<DtUsuario> usuarios = new ArrayList<>();
        usuarioFacade.findAll().forEach(usuario -> {
            usuarios.add(usuario.toDataType());
        });
        return usuarios;
    }

    @Override
    public Usuario getUsuario(String cedula) {
        Usuario usuario = usuarioFacade.find(cedula);
        return usuario;
    }

//    @Override
//    public boolean verifyThePassword(String inputPass, String storedPass) throws NoSuchAlgorithmException {
//         String SALT2 = "InYourFace";
//        boolean isAuthenticated = false;
//        String hashedPassword = generateHash(inputPass + SALT2);
//        if (hashedPassword.equals(storedPass)) {
//            isAuthenticated = true;
//        }
//        return isAuthenticated;
//    }
//
//    @Override
//    public String generatePassword(String inputPass) throws NoSuchAlgorithmException {
//        System.out.println("input: " + inputPass);
//        String hash = generateHash(inputPass + SALT);
//        System.out.println("hash: " + hash);
//        return hash;
//    }
//    private static String generateHash(String input) throws NoSuchAlgorithmException {
//        StringBuilder hash = new StringBuilder();
//        MessageDigest sha = MessageDigest.getInstance("SHA-1");
//        byte[] hashedBytes = sha.digest(input.getBytes());
//        char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
//            'a', 'b', 'c', 'd', 'e', 'f'};
//        for (int idx = 0; idx < hashedBytes.length; ++idx) {
//            byte b = hashedBytes[idx];
//            hash.append(digits[(b & 0xf0) >> 4]);
//            hash.append(digits[b & 0x0f]);
//        }
//        return hash.toString();
//    }
}
