/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

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
public class Estudiante_CursoFacade extends AbstractFacade<Estudiante_Curso> implements Estudiante_CursoFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Estudiante_CursoFacade() {
        super(Estudiante_Curso.class);
    }
   
    @Override
    public List<Estudiante_Curso> findEstudiante_CursoByUsuario_Asignatura(String cedula, Long asignatura_carrera) {
        Query q = getEntityManager().createNamedQuery(Estudiante_Curso.FINDBY_ESTUDIANTE_CURSO_ASIGNATURA).setParameter("cedula", cedula).setParameter("asignatura_carrera", asignatura_carrera);
        return q.getResultList();
    }
    
    @Override
    public Long getMaxCalificacionAsignatura(String cedula, Long asignatura_carrera){
        Query q = getEntityManager().createNamedQuery(Estudiante_Curso.GET_MAX_CALIF_ASIG).setParameter("cedula", cedula).setParameter("asignatura_carrera", asignatura_carrera);
        if(q.getSingleResult() instanceof Long){
            return (Long)q.getSingleResult();
        }else{
            return 0L;
        }
    }
    
    @Override
    public List<Estudiante_Curso> findByCurso(Long idCurso){
        Query q = getEntityManager().createNamedQuery(Estudiante_Curso.FINDBY_CURSO).setParameter("idCurso",idCurso);
        return q.getResultList();
    }
    
    
    @Override
    public Estudiante_Curso find(Long idCurso, String cedula){
        Query q = getEntityManager().createNamedQuery(Estudiante_Curso.FIND).setParameter("cedula", cedula).setParameter("idCurso",idCurso);
        if(q.getSingleResult() instanceof Estudiante_Curso){
            return (Estudiante_Curso)q.getSingleResult();
        }else{
            return null;
        }
    }
}
