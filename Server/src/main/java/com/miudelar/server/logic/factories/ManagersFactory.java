package com.miudelar.server.logic.factories;

import com.miudelar.server.logic.impl.AdministradorServiceImpl;
import com.miudelar.server.logic.impl.DirectorServiceImpl;
import com.miudelar.server.logic.impl.InitMgr;
import com.miudelar.server.logic.impl.EstudianteServiceImpl;
import com.miudelar.server.logic.impl.BedeliaServiceImpl;
import com.miudelar.server.logic.interfaces.*;

/* @author Romina */
public class ManagersFactory {

    private ManagersFactory() {
    }

    public static ManagersFactory getInstance() {
        return ManagersFactoryHolder.INSTANCE;
    }

    private static class ManagersFactoryHolder {

        private static final ManagersFactory INSTANCE = new ManagersFactory();
    }

    public InitMgt getInitMgt() {
        return new InitMgr();
    }

    public AdministradorService getAdministradorService() {
        return new AdministradorServiceImpl();
    }
    
//    public BedeliaService getBedeliaService() {
//        return new BedeliaServiceImpl();
//    }
//    
//    public DirectorService getDirectorService() {
//        return new DirectorServiceImpl();
//    }
//    
//    public EstudianteService getEstudianteService() {
//        return new EstudianteServiceImpl();
//    }
    
}
