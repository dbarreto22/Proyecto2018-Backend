/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.entities.Periodo_Examen;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
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
public class Periodo_ExamenJpaController implements Serializable {

   public Periodo_ExamenJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Periodo_Examen periodo_Examen) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(periodo_Examen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Periodo_Examen periodo_Examen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            periodo_Examen = em.merge(periodo_Examen);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = periodo_Examen.getId();
                if (findPeriodo_Examen(id) == null) {
                    throw new NonexistentEntityException("The periodo_Examen with id " + id + " no longer exists.");
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
            Periodo_Examen periodo_Examen;
            try {
                periodo_Examen = em.getReference(Periodo_Examen.class, id);
                periodo_Examen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The periodo_Examen with id " + id + " no longer exists.", enfe);
            }
            em.remove(periodo_Examen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Periodo_Examen> findPeriodo_ExamenEntities() {
        return findPeriodo_ExamenEntities(true, -1, -1);
    }

    public List<Periodo_Examen> findPeriodo_ExamenEntities(int maxResults, int firstResult) {
        return findPeriodo_ExamenEntities(false, maxResults, firstResult);
    }

    private List<Periodo_Examen> findPeriodo_ExamenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Periodo_Examen.class));
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

    public Periodo_Examen findPeriodo_Examen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Periodo_Examen.class, id);
        } finally {
            em.close();
        }
    }

    public int getPeriodo_ExamenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Periodo_Examen> rt = cq.from(Periodo_Examen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
