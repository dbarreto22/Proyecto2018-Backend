package com.miudelar.server.logic.factories;

import com.miudelar.server.logic.managers.SecurityMgr;
import com.miudelar.server.logic.interfaces.SecurityMgt;
import com.miudelar.server.logic.interfaces.initMgt;

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

/*    public initMgt getInitMgt() {
        return new initMgr();
    } */

    public SecurityMgt getSecurityMgt() {
        return new SecurityMgr();
    }
    
}
