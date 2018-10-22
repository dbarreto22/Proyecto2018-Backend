/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.entities.Asignatura;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Carrera;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class AsignaturaJpaController implements Serializable {

    public AsignaturaJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignatura asignatura) {
        if (asignatura.getAsignatura_Carreras() == null) {
            asignatura.setAsignatura_Carreras(new ArrayList<Asignatura_Carrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignatura_Carrera> attachedAsignatura_Carreras = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_CarreraToAttach : asignatura.getAsignatura_Carreras()) {
                asignatura_CarrerasAsignatura_CarreraToAttach = em.getReference(asignatura_CarrerasAsignatura_CarreraToAttach.getClass(), asignatura_CarrerasAsignatura_CarreraToAttach.getId());
                attachedAsignatura_Carreras.add(asignatura_CarrerasAsignatura_CarreraToAttach);
            }
            asignatura.setAsignatura_Carreras(attachedAsignatura_Carreras);
            List<Carrera> attachedCarreras = new ArrayList<Carrera>();
            em.persist(asignatura);
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_Carrera : asignatura.getAsignatura_Carreras()) {
                Asignatura oldAsignaturaOfAsignatura_CarrerasAsignatura_Carrera = asignatura_CarrerasAsignatura_Carrera.getAsignatura();
                asignatura_CarrerasAsignatura_Carrera.setAsignatura(asignatura);
                asignatura_CarrerasAsignatura_Carrera = em.merge(asignatura_CarrerasAsignatura_Carrera);
                if (oldAsignaturaOfAsignatura_CarrerasAsignatura_Carrera != null) {
                    oldAsignaturaOfAsignatura_CarrerasAsignatura_Carrera.getAsignatura_Carreras().remove(asignatura_CarrerasAsignatura_Carrera);
                    oldAsignaturaOfAsignatura_CarrerasAsignatura_Carrera = em.merge(oldAsignaturaOfAsignatura_CarrerasAsignatura_Carrera);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Asignatura asignatura) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignatura persistentAsignatura = em.find(Asignatura.class, asignatura.getCodigo());
            List<Asignatura_Carrera> asignatura_CarrerasOld = persistentAsignatura.getAsignatura_Carreras();
            List<Asignatura_Carrera> asignatura_CarrerasNew = asignatura.getAsignatura_Carreras();
            List<Asignatura_Carrera> attachedAsignatura_CarrerasNew = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera asignatura_CarrerasNewAsignatura_CarreraToAttach : asignatura_CarrerasNew) {
                asignatura_CarrerasNewAsignatura_CarreraToAttach = em.getReference(asignatura_CarrerasNewAsignatura_CarreraToAttach.getClass(), asignatura_CarrerasNewAsignatura_CarreraToAttach.getId());
                attachedAsignatura_CarrerasNew.add(asignatura_CarrerasNewAsignatura_CarreraToAttach);
            }
            asignatura_CarrerasNew = attachedAsignatura_CarrerasNew;
            asignatura.setAsignatura_Carreras(asignatura_CarrerasNew);
            asignatura = em.merge(asignatura);
            for (Asignatura_Carrera asignatura_CarrerasOldAsignatura_Carrera : asignatura_CarrerasOld) {
                if (!asignatura_CarrerasNew.contains(asignatura_CarrerasOldAsignatura_Carrera)) {
                    asignatura_CarrerasOldAsignatura_Carrera.setAsignatura(null);
                    asignatura_CarrerasOldAsignatura_Carrera = em.merge(asignatura_CarrerasOldAsignatura_Carrera);
                }
            }
            for (Asignatura_Carrera asignatura_CarrerasNewAsignatura_Carrera : asignatura_CarrerasNew) {
                if (!asignatura_CarrerasOld.contains(asignatura_CarrerasNewAsignatura_Carrera)) {
                    Asignatura oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera = asignatura_CarrerasNewAsignatura_Carrera.getAsignatura();
                    asignatura_CarrerasNewAsignatura_Carrera.setAsignatura(asignatura);
                    asignatura_CarrerasNewAsignatura_Carrera = em.merge(asignatura_CarrerasNewAsignatura_Carrera);
                    if (oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera != null && !oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera.equals(asignatura)) {
                        oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera.getAsignatura_Carreras().remove(asignatura_CarrerasNewAsignatura_Carrera);
                        oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera = em.merge(oldAsignaturaOfAsignatura_CarrerasNewAsignatura_Carrera);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = asignatura.getCodigo();
                if (findAsignatura(id) == null) {
                    throw new NonexistentEntityException("The asignatura with id " + id + " no longer exists.");
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
            Asignatura asignatura;
            try {
                asignatura = em.getReference(Asignatura.class, id);
                asignatura.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignatura with id " + id + " no longer exists.", enfe);
            }
            List<Asignatura_Carrera> asignatura_Carreras = asignatura.getAsignatura_Carreras();
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_Carrera : asignatura_Carreras) {
                asignatura_CarrerasAsignatura_Carrera.setAsignatura(null);
                asignatura_CarrerasAsignatura_Carrera = em.merge(asignatura_CarrerasAsignatura_Carrera);
            }
            em.remove(asignatura);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignatura> findAsignaturaEntities() {
        return findAsignaturaEntities(true, -1, -1);
    }

    public List<Asignatura> findAsignaturaEntities(int maxResults, int firstResult) {
        return findAsignaturaEntities(false, maxResults, firstResult);
    }

    private List<Asignatura> findAsignaturaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignatura.class));
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

    public Asignatura findAsignatura(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignatura.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignaturaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignatura> rt = cq.from(Asignatura.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
