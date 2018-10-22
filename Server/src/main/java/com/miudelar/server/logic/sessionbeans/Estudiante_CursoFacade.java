/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.sessionbeans;

import com.miudelar.server.logic.entities.Estudiante_Curso;
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
public class Estudiante_CursoFacade extends AbstractFacade<Estudiante_Curso> {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Estudiante_CursoFacade() {
        super(Estudiante_Curso.class);
    }
    
    public List<Estudiante_Curso> findEstudiante_CursoByUsuario_Asignatura(String cedula, Long asignatura_carrera) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery(Estudiante_Curso.FINDBY_USUARIO_ASIGNATURA).setParameter("cedula", cedula).setParameter("asignatura_carrera", asignatura_carrera);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}
