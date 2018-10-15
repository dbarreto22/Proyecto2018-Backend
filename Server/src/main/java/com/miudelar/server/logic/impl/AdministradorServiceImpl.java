package com.miudelar.server.logic.impl;

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
        
        return "Ok";
    }
    
    @Override
    public void saveUsuario(DtUsuario usuario) throws NoSuchAlgorithmException, UsuarioWithInvalidDataException{
        System.out.println("saveUsuario");
        Usuario usuarioEntity = new Usuario(usuario);
        try {
            usuarioJpaController.create(usuarioEntity);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new UsuarioWithInvalidDataException();
        }
    }
    
    @Override
    public void addRol(String cedula, Long idRol) throws NonexistentEntityException{
        Usuario usuario;
        usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(idRol);
        usuario.addRol(rol);
        try {
            usuarioJpaController.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new NonexistentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public void removeRol(String cedula, Long idRol) throws NonexistentEntityException{
        Usuario usuario;
        usuario = usuarioJpaController.findUsuario(cedula);
        Rol rol = rolJpaController.findRol(idRol);
        usuario.removeRol(rol);
        try {
            usuarioJpaController.edit(usuario);
        } catch (Exception ex) {
            Logger.getLogger(AdministradorServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new NonexistentEntityException(ex.getMessage());
        }
    }
    
    @Override
    public List<DtUsuario> getAllUsuario() throws NoSuchAlgorithmException{
        List<DtUsuario> usuarios = new ArrayList<>();
        usuarioJpaController.findUsuarioEntities().forEach(usuario -> {
            usuarios.add(usuario.toDataType());
        });
        return usuarios;
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
