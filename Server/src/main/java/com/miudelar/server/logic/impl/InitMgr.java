/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.impl;

import com.miudelar.server.exceptions.RolWithInvalidDataException;
import com.miudelar.server.logic.datatypes.DtRol;
import com.miudelar.server.logic.factories.ManagersFactory;
import com.miudelar.server.logic.interfaces.*;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class InitMgr implements InitMgt {

    AdministradorService administradorService = ManagersFactory.getInstance().getAdministradorService();
//    BedeliaService bedeliaService = ManagersFactory.getInstance().getBedeliaService();
//    DirectorService directorService = ManagersFactory.getInstance().getDirectorService();
//    EstudianteService estudianteService = ManagersFactory.getInstance().getEstudianteService();

    private void rolGenerator() throws NoSuchAlgorithmException {
        try {
            System.out.println("getAllRol");
            List<DtRol> listDtRol = administradorService.getAllRol();
            
            if (listDtRol.isEmpty()) {
                System.out.println("isEmpty");
                administradorService.rolSave("ADMIN");
                administradorService.rolSave("BEDELIA");
                administradorService.rolSave("DIRECTOR");
                administradorService.rolSave("ESTUDIANTE");
            }
        } catch (RolWithInvalidDataException ex) {
            Logger.getLogger(InitMgr.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initBaseData() throws NoSuchAlgorithmException {
        rolGenerator();
    }

}
