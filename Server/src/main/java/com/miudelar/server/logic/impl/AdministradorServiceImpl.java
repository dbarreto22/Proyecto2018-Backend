package com.miudelar.server.logic.impl;

import com.google.gson.Gson;
import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.logic.controller.RolJpaController;
import com.miudelar.server.logic.controller.UsuarioJpaController;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
import com.miudelar.server.logic.entities.Rol;
import com.miudelar.server.logic.entities.Usuario;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import com.miudelar.server.logic.interfaces.AdministradorService;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;

public class AdministradorServiceImpl implements AdministradorService {

    String SALT = "InYourFace";
    Gson gson = new Gson();
       
    RolJpaController rolJpaController = new RolJpaController();
    UsuarioJpaController usuarioJpaController = new UsuarioJpaController();

    @Override
    public void rolSave(String tipo) throws RolWithInvalidDataException{
        System.out.println("tipo: " + tipo);
        Rol rolEntity = new Rol(tipo);
        try {
            rolJpaController.create(rolEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new RolWithInvalidDataException();
        }
    }
    
    @Override
    public List<DtRol> getAllRol() throws NoSuchAlgorithmException{
        List<DtRol> roles = new ArrayList<>();
        rolJpaController.findRolEntities().forEach(tipo -> {
            roles.add(new DtRol(tipo.getId(),tipo.getTipo()));
        });
        return roles;
    }
    
    @Override
    public String login(String username, String password){
        Usuario usuario = usuarioJpaController.findUsuario(username);
        if (usuario.getPassword() == password){
            return "OK";
        }else{
            return "Error: Usuario o contraseña incorrecta";
        }
    }
    
    @Override
    public String saveUsuario(String dtUsrStr){
        DtUsuario usuario = gson.fromJson(dtUsrStr, DtUsuario.class);
        Usuario usuarioEntity = new Usuario(usuario);
        String message = "OK";
        try {
            usuarioJpaController.create(usuarioEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String saveUsuario(DtUsuario usuario){
        System.out.println("usuarioToJson: " + gson.toJson(usuario));
        Usuario usuarioEntity = new Usuario(usuario);
        String message = "OK";
        try {
            usuarioJpaController.create(usuarioEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String editUsuario(String UsrStr){
        Usuario usuario = gson.fromJson(UsrStr, Usuario.class);
        String message = "OK";
        try {
            usuarioJpaController.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public String addRol(String cedula, Long idRol){
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(idRol);
        usuario.addRol(rol);
        String message = "OK";
        try {
        usuarioJpaController.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
       return message;
    }
    
    @Override
    public String removeRol(String cedula, Long idRol) {
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(idRol);
        usuario.removeRol(rol);
        String message = "OK";
        try {
            usuarioJpaController.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            message = ex.getMessage();
        }
        return message;
    }
    
    @Override
    public List<DtUsuario> getAllUsuario() throws NoSuchAlgorithmException{
        List<DtUsuario> usuarios = new ArrayList<>();
        usuarioJpaController.findUsuarioEntities().forEach(usuario -> {
            usuarios.add(usuario.toDataType());
        });
        return usuarios;
    }
    
    @Override
    public Usuario getUsuario(String cedula){
        Usuario usuario = usuarioJpaController.findUsuario(cedula);
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
