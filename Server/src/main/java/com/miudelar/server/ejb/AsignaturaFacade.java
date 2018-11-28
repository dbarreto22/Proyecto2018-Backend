/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Asignatura;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rmoreno
 */
@Stateless
public class AsignaturaFacade extends AbstractFacade<Asignatura> implements AsignaturaFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AsignaturaFacade() {
        super(Asignatura.class);
    }
    
    @Override
    public List<Asignatura> findByCarrera(Long codigo){
        Query q = getEntityManager().createNamedQuery(Asignatura.FIND_BY_CARRERA).setParameter("codigo", codigo);
        return q.getResultList();
    }
    
    @Override
    public List<Asignatura> findByNombre(String nombre){
        Query q = getEntityManager().createNamedQuery(Asignatura.FIND_BY_NAME).setParameter("nombre", nombre);
        return q.getResultList();
    }
    
}
