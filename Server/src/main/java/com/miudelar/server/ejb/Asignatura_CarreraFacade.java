/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Curso;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rmoreno
 */
@Stateless
public class Asignatura_CarreraFacade extends AbstractFacade<Asignatura_Carrera> implements Asignatura_CarreraFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Asignatura_CarreraFacade() {
        super(Asignatura_Carrera.class);
    }
    
    @Override
    public Asignatura_Carrera findByCods(Long carrera, Long asignatura){
        Query q = getEntityManager().createNamedQuery(Asignatura_Carrera.FIND_BY_CODS).setParameter("carrera", carrera).setParameter("asignatura", asignatura);
        if(q.getResultList() != null && q.getSingleResult() != null && q.getSingleResult() instanceof Asignatura_Carrera){
            return (Asignatura_Carrera)q.getSingleResult();
        }else{
            return null;
        }
        
    }
    
}
