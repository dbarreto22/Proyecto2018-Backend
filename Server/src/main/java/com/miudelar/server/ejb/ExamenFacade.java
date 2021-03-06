/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Examen;
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
public class ExamenFacade extends AbstractFacade<Examen> implements ExamenFacadeLocal {

    @PersistenceContext(unitName = "miudelar")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ExamenFacade() {
        super(Examen.class);
    }
    
    @Override
    public List<Usuario> getEstudiantesInscriptos(Long idExamen){
        Query q = getEntityManager().createNamedQuery(Examen.GET_ESTUDIANTES_INSCRIPTOS_EXAMEN).setParameter("idExamen", idExamen);
        return q.getResultList();
    }
    
    @Override
    public List<Examen> getExamenByFechaAndIdAsigCar(Date fecha, Long idAsigCar){
        Query q = getEntityManager().createNamedQuery(Examen.FIND_BY_FECHA_ASIGCAR).setParameter("fecha", fecha).setParameter("idAsigCar", idAsigCar);
        return q.getResultList();
    }
    
    @Override
    public List<Examen> getExamenesDisponiblesEstudiante(String cedula){
        Query q = getEntityManager().createNamedQuery(Examen.FIND_DISPONIBLES_ESTUDIANTE).setParameter("cedula", cedula);
        return q.getResultList();
    }
}
