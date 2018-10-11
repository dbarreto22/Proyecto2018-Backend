/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.controller.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Carrera;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class CarreraJpaController implements Serializable {

    public CarreraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Carrera carrera) throws PreexistingEntityException, Exception {
        if (carrera.getAsignatura_Carreras() == null) {
            carrera.setAsignatura_Carreras(new ArrayList<Asignatura_Carrera>());
        }
        if (carrera.getAsignaturas() == null) {
            carrera.setAsignaturas(new ArrayList<Asignatura>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Asignatura_Carrera> attachedAsignatura_Carreras = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_CarreraToAttach : carrera.getAsignatura_Carreras()) {
                asignatura_CarrerasAsignatura_CarreraToAttach = em.getReference(asignatura_CarrerasAsignatura_CarreraToAttach.getClass(), asignatura_CarrerasAsignatura_CarreraToAttach.getId());
                attachedAsignatura_Carreras.add(asignatura_CarrerasAsignatura_CarreraToAttach);
            }
            carrera.setAsignatura_Carreras(attachedAsignatura_Carreras);
            List<Asignatura> attachedAsignaturas = new ArrayList<Asignatura>();
            for (Asignatura asignaturasAsignaturaToAttach : carrera.getAsignaturas()) {
                asignaturasAsignaturaToAttach = em.getReference(asignaturasAsignaturaToAttach.getClass(), asignaturasAsignaturaToAttach.getCodigo());
                attachedAsignaturas.add(asignaturasAsignaturaToAttach);
            }
            carrera.setAsignaturas(attachedAsignaturas);
            em.persist(carrera);
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_Carrera : carrera.getAsignatura_Carreras()) {
                Carrera oldCarreraOfAsignatura_CarrerasAsignatura_Carrera = asignatura_CarrerasAsignatura_Carrera.getCarrera();
                asignatura_CarrerasAsignatura_Carrera.setCarrera(carrera);
                asignatura_CarrerasAsignatura_Carrera = em.merge(asignatura_CarrerasAsignatura_Carrera);
                if (oldCarreraOfAsignatura_CarrerasAsignatura_Carrera != null) {
                    oldCarreraOfAsignatura_CarrerasAsignatura_Carrera.getAsignatura_Carreras().remove(asignatura_CarrerasAsignatura_Carrera);
                    oldCarreraOfAsignatura_CarrerasAsignatura_Carrera = em.merge(oldCarreraOfAsignatura_CarrerasAsignatura_Carrera);
                }
            }
            for (Asignatura asignaturasAsignatura : carrera.getAsignaturas()) {
                asignaturasAsignatura.getCarreras().add(carrera);
                asignaturasAsignatura = em.merge(asignaturasAsignatura);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCarrera(carrera.getCodigo()) != null) {
                throw new PreexistingEntityException("Carrera " + carrera + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Carrera carrera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera persistentCarrera = em.find(Carrera.class, carrera.getCodigo());
            List<Asignatura_Carrera> asignatura_CarrerasOld = persistentCarrera.getAsignatura_Carreras();
            List<Asignatura_Carrera> asignatura_CarrerasNew = carrera.getAsignatura_Carreras();
            List<Asignatura> asignaturasOld = persistentCarrera.getAsignaturas();
            List<Asignatura> asignaturasNew = carrera.getAsignaturas();
            List<Asignatura_Carrera> attachedAsignatura_CarrerasNew = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera asignatura_CarrerasNewAsignatura_CarreraToAttach : asignatura_CarrerasNew) {
                asignatura_CarrerasNewAsignatura_CarreraToAttach = em.getReference(asignatura_CarrerasNewAsignatura_CarreraToAttach.getClass(), asignatura_CarrerasNewAsignatura_CarreraToAttach.getId());
                attachedAsignatura_CarrerasNew.add(asignatura_CarrerasNewAsignatura_CarreraToAttach);
            }
            asignatura_CarrerasNew = attachedAsignatura_CarrerasNew;
            carrera.setAsignatura_Carreras(asignatura_CarrerasNew);
            List<Asignatura> attachedAsignaturasNew = new ArrayList<Asignatura>();
            for (Asignatura asignaturasNewAsignaturaToAttach : asignaturasNew) {
                asignaturasNewAsignaturaToAttach = em.getReference(asignaturasNewAsignaturaToAttach.getClass(), asignaturasNewAsignaturaToAttach.getCodigo());
                attachedAsignaturasNew.add(asignaturasNewAsignaturaToAttach);
            }
            asignaturasNew = attachedAsignaturasNew;
            carrera.setAsignaturas(asignaturasNew);
            carrera = em.merge(carrera);
            for (Asignatura_Carrera asignatura_CarrerasOldAsignatura_Carrera : asignatura_CarrerasOld) {
                if (!asignatura_CarrerasNew.contains(asignatura_CarrerasOldAsignatura_Carrera)) {
                    asignatura_CarrerasOldAsignatura_Carrera.setCarrera(null);
                    asignatura_CarrerasOldAsignatura_Carrera = em.merge(asignatura_CarrerasOldAsignatura_Carrera);
                }
            }
            for (Asignatura_Carrera asignatura_CarrerasNewAsignatura_Carrera : asignatura_CarrerasNew) {
                if (!asignatura_CarrerasOld.contains(asignatura_CarrerasNewAsignatura_Carrera)) {
                    Carrera oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera = asignatura_CarrerasNewAsignatura_Carrera.getCarrera();
                    asignatura_CarrerasNewAsignatura_Carrera.setCarrera(carrera);
                    asignatura_CarrerasNewAsignatura_Carrera = em.merge(asignatura_CarrerasNewAsignatura_Carrera);
                    if (oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera != null && !oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera.equals(carrera)) {
                        oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera.getAsignatura_Carreras().remove(asignatura_CarrerasNewAsignatura_Carrera);
                        oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera = em.merge(oldCarreraOfAsignatura_CarrerasNewAsignatura_Carrera);
                    }
                }
            }
            for (Asignatura asignaturasOldAsignatura : asignaturasOld) {
                if (!asignaturasNew.contains(asignaturasOldAsignatura)) {
                    asignaturasOldAsignatura.getCarreras().remove(carrera);
                    asignaturasOldAsignatura = em.merge(asignaturasOldAsignatura);
                }
            }
            for (Asignatura asignaturasNewAsignatura : asignaturasNew) {
                if (!asignaturasOld.contains(asignaturasNewAsignatura)) {
                    asignaturasNewAsignatura.getCarreras().add(carrera);
                    asignaturasNewAsignatura = em.merge(asignaturasNewAsignatura);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = carrera.getCodigo();
                if (findCarrera(id) == null) {
                    throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.");
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
            Carrera carrera;
            try {
                carrera = em.getReference(Carrera.class, id);
                carrera.getCodigo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The carrera with id " + id + " no longer exists.", enfe);
            }
            List<Asignatura_Carrera> asignatura_Carreras = carrera.getAsignatura_Carreras();
            for (Asignatura_Carrera asignatura_CarrerasAsignatura_Carrera : asignatura_Carreras) {
                asignatura_CarrerasAsignatura_Carrera.setCarrera(null);
                asignatura_CarrerasAsignatura_Carrera = em.merge(asignatura_CarrerasAsignatura_Carrera);
            }
            List<Asignatura> asignaturas = carrera.getAsignaturas();
            for (Asignatura asignaturasAsignatura : asignaturas) {
                asignaturasAsignatura.getCarreras().remove(carrera);
                asignaturasAsignatura = em.merge(asignaturasAsignatura);
            }
            em.remove(carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Carrera> findCarreraEntities() {
        return findCarreraEntities(true, -1, -1);
    }

    public List<Carrera> findCarreraEntities(int maxResults, int firstResult) {
        return findCarreraEntities(false, maxResults, firstResult);
    }

    private List<Carrera> findCarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Carrera.class));
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

    public Carrera findCarrera(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getCarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Carrera> rt = cq.from(Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
