/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.controller.exceptions.PreexistingEntityException;
import com.miudelar.server.logic.entities.Estudiante_Examen;
import com.miudelar.server.logic.entities.Examen;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author rmoreno
 */
public class Estudiante_ExamenJpaController implements Serializable {

    public Estudiante_ExamenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante_Examen estudiante_Examen) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estudiante_Examen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante_Examen(estudiante_Examen.getExamen()) != null) {
                throw new PreexistingEntityException("Estudiante_Examen " + estudiante_Examen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Estudiante_Examen estudiante_Examen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estudiante_Examen = em.merge(estudiante_Examen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Examen id = estudiante_Examen.getExamen();
                if (findEstudiante_Examen(id) == null) {
                    throw new NonexistentEntityException("The estudiante_Examen with id " + id + " no longer exists.");
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
            Estudiante_Examen estudiante_Examen;
            try {
                estudiante_Examen = em.getReference(Estudiante_Examen.class, id);
                estudiante_Examen.getExamen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante_Examen with id " + id + " no longer exists.", enfe);
            }
            em.remove(estudiante_Examen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Examen id) throws NonexistentEntityException {
        destroy(id.getId());
    }

    public List<Estudiante_Examen> findEstudiante_ExamenEntities() {
        return findEstudiante_ExamenEntities(true, -1, -1);
    }

    public List<Estudiante_Examen> findEstudiante_ExamenEntities(int maxResults, int firstResult) {
        return findEstudiante_ExamenEntities(false, maxResults, firstResult);
    }

    private List<Estudiante_Examen> findEstudiante_ExamenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante_Examen.class));
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

    public Estudiante_Examen findEstudiante_Examen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante_Examen.class, id);
        } finally {
            em.close();
        }
    }

    public Estudiante_Examen findEstudiante_Examen(Examen id) {
        return findEstudiante_Examen(id.getId());
    }

    public int getEstudiante_ExamenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante_Examen> rt = cq.from(Estudiante_Examen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
