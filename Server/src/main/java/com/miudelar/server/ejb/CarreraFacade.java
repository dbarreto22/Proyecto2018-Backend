/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Carrera;
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
public class CarreraFacade extends AbstractFacade<Carrera> implements CarreraFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CarreraFacade() {
        super(Carrera.class);
    }
    
    @Override
    public List<Carrera> findByNombre(String nombre){
        Query q = getEntityManager().createNamedQuery(Carrera.FIND_BY_NAME).setParameter("nombre", nombre);
        return q.getResultList();
    }
    
    @Override
    public List<Asignatura_Carrera> findAsignaturasCarrera(Long codigo){
        Query q = getEntityManager().createNamedQuery(Carrera.FIND_ASIGNATURAS_CARRERA).setParameter("codigo", codigo);
        return q.getResultList();
    }
    
}
