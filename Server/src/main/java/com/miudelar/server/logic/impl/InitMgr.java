/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.exceptions.UsuarioWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.datatypes.DtUsuario;
import com.miudelar.server.logic.factories.ManagersFactory;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitMgr implements InitMgt {

    AdministradorService administradorService = ManagersFactory.getInstance().getAdministradorService();
    BedeliaService bedeliaService = ManagersFactory.getInstance().getBedeliaService();
    DirectorService directorService = ManagersFactory.getInstance().getDirectorService();
    EstudianteService estudianteService = ManagersFactory.getInstance().getEstudianteService();

    private void rolGenerator() throws NoSuchAlgorithmException, RolWithInvalidDataException {
        List<DtRol> listDtRol = administradorService.getAllRol();

        if (listDtRol.isEmpty()) {
            administradorService.rolSave("ADMIN");
            administradorService.rolSave("BEDELIA");
            administradorService.rolSave("DIRECTOR");
            administradorService.rolSave("ESTUDIANTE");
        }
    }
    
    private void createDefaultUser() throws NoSuchAlgorithmException, UsuarioWithInvalidDataException, NonexistentEntityException{
        List<DtUsuario> listDtUsuario = administradorService.getAllUsuario();
        if (listDtUsuario.isEmpty()){
            DtUsuario usuario = new DtUsuario("1111111", "Admin", "Admin", "admin@admin.com", "admin123");
            administradorService.saveUsuario(usuario);
//            administradorService.addRol(usuario.getCedula(),1L);
        }
    }
    
    private void carrera_asginaturaGenerator(){
        DtCarrera carrera = new DtCarrera(2001L, "Ing. en computación");
        directorService.saveCarrera(carrera);
        DtAsignatura asignatura = new DtAsignatura(1001L, "Cálculo1");
        directorService.saveAsignatura(asignatura);
//        directorService.saveAsignaturaCarrera(1001L, 2001L);
    }

    @Override
    public void initBaseData() throws NoSuchAlgorithmException, RolWithInvalidDataException, UsuarioWithInvalidDataException, NonexistentEntityException{
// TODO: hacelo bien 
//        rolGenerator();
//        createDefaultUser();
//        carrera_asginaturaGenerator();
    }

}
