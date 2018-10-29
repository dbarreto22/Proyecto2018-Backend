/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Estudiante_Examen;
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
public class Estudiante_ExamenFacade extends AbstractFacade<Estudiante_Examen> implements Estudiante_ExamenFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Estudiante_ExamenFacade() {
        super(Estudiante_Examen.class);
    }
    
    @Override
    public List<Estudiante_Examen> findEstudiante_ExamenByUsuario_Asignatura(String cedula, Long asignatura_carrera) {
            Query q = getEntityManager().createNamedQuery(Estudiante_Examen.FINDBY_ESTUDIANTE_EXAMEN_ASIGNATURA).setParameter("cedula", cedula).setParameter("asignatura_carrera", asignatura_carrera);
            return q.getResultList();
    }
    
}
