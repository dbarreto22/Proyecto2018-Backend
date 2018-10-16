/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Examen;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class ExamenJpaController implements Serializable {

    public ExamenJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Examen examen) {
        if (examen.getCalificacionesExamenes() == null) {
            examen.setCalificacionesExamenes(new ArrayList<Estudiante_Examen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignatura_Carrera asignatura_Carrera = examen.getAsignatura_Carrera();
            if (asignatura_Carrera != null) {
                asignatura_Carrera = em.getReference(asignatura_Carrera.getClass(), asignatura_Carrera.getId());
                examen.setAsignatura_Carrera(asignatura_Carrera);
            }
            List<Estudiante_Examen> attachedCalificacionesExamenes = new ArrayList<Estudiante_Examen>();
            for (Estudiante_Examen calificacionesExamenesEstudiante_ExamenToAttach : examen.getCalificacionesExamenes()) {
                calificacionesExamenesEstudiante_ExamenToAttach = em.getReference(calificacionesExamenesEstudiante_ExamenToAttach.getClass(), calificacionesExamenesEstudiante_ExamenToAttach.getExamen());
                attachedCalificacionesExamenes.add(calificacionesExamenesEstudiante_ExamenToAttach);
            }
            examen.setCalificacionesExamenes(attachedCalificacionesExamenes);
            em.persist(examen);
            if (asignatura_Carrera != null) {
                asignatura_Carrera.getExamenes().add(examen);
                asignatura_Carrera = em.merge(asignatura_Carrera);
            }
            for (Estudiante_Examen calificacionesExamenesEstudiante_Examen : examen.getCalificacionesExamenes()) {
                Examen oldExamenOfCalificacionesExamenesEstudiante_Examen = calificacionesExamenesEstudiante_Examen.getExamen();
                calificacionesExamenesEstudiante_Examen.setExamen(examen);
                calificacionesExamenesEstudiante_Examen = em.merge(calificacionesExamenesEstudiante_Examen);
                if (oldExamenOfCalificacionesExamenesEstudiante_Examen != null) {
                    oldExamenOfCalificacionesExamenesEstudiante_Examen.getCalificacionesExamenes().remove(calificacionesExamenesEstudiante_Examen);
                    oldExamenOfCalificacionesExamenesEstudiante_Examen = em.merge(oldExamenOfCalificacionesExamenesEstudiante_Examen);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Examen examen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Examen persistentExamen = em.find(Examen.class, examen.getId());
            Asignatura_Carrera asignatura_CarreraOld = persistentExamen.getAsignatura_Carrera();
            Asignatura_Carrera asignatura_CarreraNew = examen.getAsignatura_Carrera();
            List<Estudiante_Examen> calificacionesExamenesOld = persistentExamen.getCalificacionesExamenes();
            List<Estudiante_Examen> calificacionesExamenesNew = examen.getCalificacionesExamenes();
            if (asignatura_CarreraNew != null) {
                asignatura_CarreraNew = em.getReference(asignatura_CarreraNew.getClass(), asignatura_CarreraNew.getId());
                examen.setAsignatura_Carrera(asignatura_CarreraNew);
            }
            List<Estudiante_Examen> attachedCalificacionesExamenesNew = new ArrayList<Estudiante_Examen>();
            for (Estudiante_Examen calificacionesExamenesNewEstudiante_ExamenToAttach : calificacionesExamenesNew) {
                calificacionesExamenesNewEstudiante_ExamenToAttach = em.getReference(calificacionesExamenesNewEstudiante_ExamenToAttach.getClass(), calificacionesExamenesNewEstudiante_ExamenToAttach.getExamen());
                attachedCalificacionesExamenesNew.add(calificacionesExamenesNewEstudiante_ExamenToAttach);
            }
            calificacionesExamenesNew = attachedCalificacionesExamenesNew;
            examen.setCalificacionesExamenes(calificacionesExamenesNew);
            examen = em.merge(examen);
            if (asignatura_CarreraOld != null && !asignatura_CarreraOld.equals(asignatura_CarreraNew)) {
                asignatura_CarreraOld.getExamenes().remove(examen);
                asignatura_CarreraOld = em.merge(asignatura_CarreraOld);
            }
            if (asignatura_CarreraNew != null && !asignatura_CarreraNew.equals(asignatura_CarreraOld)) {
                asignatura_CarreraNew.getExamenes().add(examen);
                asignatura_CarreraNew = em.merge(asignatura_CarreraNew);
            }
            for (Estudiante_Examen calificacionesExamenesOldEstudiante_Examen : calificacionesExamenesOld) {
                if (!calificacionesExamenesNew.contains(calificacionesExamenesOldEstudiante_Examen)) {
                    calificacionesExamenesOldEstudiante_Examen.setExamen(null);
                    calificacionesExamenesOldEstudiante_Examen = em.merge(calificacionesExamenesOldEstudiante_Examen);
                }
            }
            for (Estudiante_Examen calificacionesExamenesNewEstudiante_Examen : calificacionesExamenesNew) {
                if (!calificacionesExamenesOld.contains(calificacionesExamenesNewEstudiante_Examen)) {
                    Examen oldExamenOfCalificacionesExamenesNewEstudiante_Examen = calificacionesExamenesNewEstudiante_Examen.getExamen();
                    calificacionesExamenesNewEstudiante_Examen.setExamen(examen);
                    calificacionesExamenesNewEstudiante_Examen = em.merge(calificacionesExamenesNewEstudiante_Examen);
                    if (oldExamenOfCalificacionesExamenesNewEstudiante_Examen != null && !oldExamenOfCalificacionesExamenesNewEstudiante_Examen.equals(examen)) {
                        oldExamenOfCalificacionesExamenesNewEstudiante_Examen.getCalificacionesExamenes().remove(calificacionesExamenesNewEstudiante_Examen);
                        oldExamenOfCalificacionesExamenesNewEstudiante_Examen = em.merge(oldExamenOfCalificacionesExamenesNewEstudiante_Examen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = examen.getId();
                if (findExamen(id) == null) {
                    throw new NonexistentEntityException("The examen with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Examen examen;
            try {
                examen = em.getReference(Examen.class, id);
                examen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The examen with id " + id + " no longer exists.", enfe);
            }
            Asignatura_Carrera asignatura_Carrera = examen.getAsignatura_Carrera();
            if (asignatura_Carrera != null) {
                asignatura_Carrera.getExamenes().remove(examen);
                asignatura_Carrera = em.merge(asignatura_Carrera);
            }
            List<Estudiante_Examen> calificacionesExamenes = examen.getCalificacionesExamenes();
            for (Estudiante_Examen calificacionesExamenesEstudiante_Examen : calificacionesExamenes) {
                calificacionesExamenesEstudiante_Examen.setExamen(null);
                calificacionesExamenesEstudiante_Examen = em.merge(calificacionesExamenesEstudiante_Examen);
            }
            em.remove(examen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Examen> findExamenEntities() {
        return findExamenEntities(true, -1, -1);
    }

    public List<Examen> findExamenEntities(int maxResults, int firstResult) {
        return findExamenEntities(false, maxResults, firstResult);
    }

    private List<Examen> findExamenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Examen.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Examen findExamen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Examen.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Examen> rt = cq.from(Examen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
