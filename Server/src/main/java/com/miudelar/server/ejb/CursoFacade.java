/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Usuario;
import java.util.Date;
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
public class CursoFacade extends AbstractFacade<Curso> implements CursoFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CursoFacade() {
        super(Curso.class);
    }
    
    @Override
    public List<Usuario> getEstudiantesInscriptos(Long idCurso){
        Query q = getEntityManager().createNamedQuery(Curso.GET_ESTUDIANTES_INSCRIPTOS_CURSO).setParameter("idCurso", idCurso);
        return q.getResultList();
    }
    
    @Override
    public List<Curso> getCursoByFechaAndIdAsigCar(Date fecha, Long idAsigCar){
        Query q = getEntityManager().createNamedQuery(Curso.FIND_BY_FECHA_ASIGCAR).setParameter("fecha", fecha).setParameter("idAsigCar", idAsigCar);
        return q.getResultList();
    }
    
}
