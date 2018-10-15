package com.miudelar.server;

import com.miudelar.server.logic.factories.ManagersFactory;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import com.miudelar.server.logic.interfaces.InitMgt;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class main implements ServletContextListener {
        
        static InitMgt init = ManagersFactory.getInstance().getInitMgt();
	        
        @Override
        public void contextInitialized(ServletContextEvent servletContextEvent)
        {
            //Context initialized code here
            System.out.println("DFAJDSKFLADJSFKL");

            try {
                init.initBaseData();
            } catch (Exception e) {
                  e.printStackTrace();
            }
            
        }
        
        @Override
        public void contextDestroyed(ServletContextEvent servletContextEvent) {
             //Context destroyed code here
        }
        
}