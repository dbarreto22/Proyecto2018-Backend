/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.sessionbeans;

import com.miudelar.server.logic.entities.Estudiante_Examen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rmoreno
 */
@Stateless
public class Estudiante_ExamenFacade extends AbstractFacade<Estudiante_Examen> {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Estudiante_ExamenFacade() {
        super(Estudiante_Examen.class);
    }
    
}
