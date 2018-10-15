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
import com.miudelar.server.logic.entities.Carrera;
import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Examen;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class Asignatura_CarreraJpaController implements Serializable {

    public Asignatura_CarreraJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Asignatura_Carrera asignatura_Carrera) {
        if (asignatura_Carrera.getExamenes() == null) {
            asignatura_Carrera.setExamenes(new ArrayList<Examen>());
        }
        if (asignatura_Carrera.getPrevias() == null) {
            asignatura_Carrera.setPrevias(new ArrayList<Asignatura_Carrera>());
        }
        if (asignatura_Carrera.getEsPreviaDe() == null) {
            asignatura_Carrera.setEsPreviaDe(new ArrayList<Asignatura_Carrera>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Carrera carrera = asignatura_Carrera.getCarrera();
            if (carrera != null) {
                carrera = em.getReference(carrera.getClass(), carrera.getCodigo());
                asignatura_Carrera.setCarrera(carrera);
            }
            Asignatura asignatura = asignatura_Carrera.getAsignatura();
            if (asignatura != null) {
                asignatura = em.getReference(asignatura.getClass(), asignatura.getCodigo());
                asignatura_Carrera.setAsignatura(asignatura);
            }
            List<Examen> attachedExamenes = new ArrayList<Examen>();
            for (Examen examenesExamenToAttach : asignatura_Carrera.getExamenes()) {
                examenesExamenToAttach = em.getReference(examenesExamenToAttach.getClass(), examenesExamenToAttach.getId());
                attachedExamenes.add(examenesExamenToAttach);
            }
            asignatura_Carrera.setExamenes(attachedExamenes);
            List<Asignatura_Carrera> attachedPrevias = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera previasAsignatura_CarreraToAttach : asignatura_Carrera.getPrevias()) {
                previasAsignatura_CarreraToAttach = em.getReference(previasAsignatura_CarreraToAttach.getClass(), previasAsignatura_CarreraToAttach.getId());
                attachedPrevias.add(previasAsignatura_CarreraToAttach);
            }
            asignatura_Carrera.setPrevias(attachedPrevias);
            List<Asignatura_Carrera> attachedEsPreviaDe = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera esPreviaDeAsignatura_CarreraToAttach : asignatura_Carrera.getEsPreviaDe()) {
                esPreviaDeAsignatura_CarreraToAttach = em.getReference(esPreviaDeAsignatura_CarreraToAttach.getClass(), esPreviaDeAsignatura_CarreraToAttach.getId());
                attachedEsPreviaDe.add(esPreviaDeAsignatura_CarreraToAttach);
            }
            asignatura_Carrera.setEsPreviaDe(attachedEsPreviaDe);
            em.persist(asignatura_Carrera);
            if (carrera != null) {
                carrera.getAsignatura_Carreras().add(asignatura_Carrera);
                carrera = em.merge(carrera);
            }
            if (asignatura != null) {
                asignatura.getAsignatura_Carreras().add(asignatura_Carrera);
                asignatura = em.merge(asignatura);
            }
            for (Examen examenesExamen : asignatura_Carrera.getExamenes()) {
                Asignatura_Carrera oldAsignatura_CarreraOfExamenesExamen = examenesExamen.getAsignatura_Carrera();
                examenesExamen.setAsignatura_Carrera(asignatura_Carrera);
                examenesExamen = em.merge(examenesExamen);
                if (oldAsignatura_CarreraOfExamenesExamen != null) {
                    oldAsignatura_CarreraOfExamenesExamen.getExamenes().remove(examenesExamen);
                    oldAsignatura_CarreraOfExamenesExamen = em.merge(oldAsignatura_CarreraOfExamenesExamen);
                }
            }
            for (Asignatura_Carrera previasAsignatura_Carrera : asignatura_Carrera.getPrevias()) {
                previasAsignatura_Carrera.getPrevias().add(asignatura_Carrera);
                previasAsignatura_Carrera = em.merge(previasAsignatura_Carrera);
            }
            for (Asignatura_Carrera esPreviaDeAsignatura_Carrera : asignatura_Carrera.getEsPreviaDe()) {
                esPreviaDeAsignatura_Carrera.getPrevias().add(asignatura_Carrera);
                esPreviaDeAsignatura_Carrera = em.merge(esPreviaDeAsignatura_Carrera);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Asignatura_Carrera asignatura_Carrera) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Asignatura_Carrera persistentAsignatura_Carrera = em.find(Asignatura_Carrera.class, asignatura_Carrera.getId());
            Carrera carreraOld = persistentAsignatura_Carrera.getCarrera();
            Carrera carreraNew = asignatura_Carrera.getCarrera();
            Asignatura asignaturaOld = persistentAsignatura_Carrera.getAsignatura();
            Asignatura asignaturaNew = asignatura_Carrera.getAsignatura();
            List<Examen> examenesOld = persistentAsignatura_Carrera.getExamenes();
            List<Examen> examenesNew = asignatura_Carrera.getExamenes();
            List<Asignatura_Carrera> previasOld = persistentAsignatura_Carrera.getPrevias();
            List<Asignatura_Carrera> previasNew = asignatura_Carrera.getPrevias();
            List<Asignatura_Carrera> esPreviaDeOld = persistentAsignatura_Carrera.getEsPreviaDe();
            List<Asignatura_Carrera> esPreviaDeNew = asignatura_Carrera.getEsPreviaDe();
            if (carreraNew != null) {
                carreraNew = em.getReference(carreraNew.getClass(), carreraNew.getCodigo());
                asignatura_Carrera.setCarrera(carreraNew);
            }
            if (asignaturaNew != null) {
                asignaturaNew = em.getReference(asignaturaNew.getClass(), asignaturaNew.getCodigo());
                asignatura_Carrera.setAsignatura(asignaturaNew);
            }
            List<Examen> attachedExamenesNew = new ArrayList<Examen>();
            for (Examen examenesNewExamenToAttach : examenesNew) {
                examenesNewExamenToAttach = em.getReference(examenesNewExamenToAttach.getClass(), examenesNewExamenToAttach.getId());
                attachedExamenesNew.add(examenesNewExamenToAttach);
            }
            examenesNew = attachedExamenesNew;
            asignatura_Carrera.setExamenes(examenesNew);
            List<Asignatura_Carrera> attachedPreviasNew = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera previasNewAsignatura_CarreraToAttach : previasNew) {
                previasNewAsignatura_CarreraToAttach = em.getReference(previasNewAsignatura_CarreraToAttach.getClass(), previasNewAsignatura_CarreraToAttach.getId());
                attachedPreviasNew.add(previasNewAsignatura_CarreraToAttach);
            }
            previasNew = attachedPreviasNew;
            asignatura_Carrera.setPrevias(previasNew);
            List<Asignatura_Carrera> attachedEsPreviaDeNew = new ArrayList<Asignatura_Carrera>();
            for (Asignatura_Carrera esPreviaDeNewAsignatura_CarreraToAttach : esPreviaDeNew) {
                esPreviaDeNewAsignatura_CarreraToAttach = em.getReference(esPreviaDeNewAsignatura_CarreraToAttach.getClass(), esPreviaDeNewAsignatura_CarreraToAttach.getId());
                attachedEsPreviaDeNew.add(esPreviaDeNewAsignatura_CarreraToAttach);
            }
            esPreviaDeNew = attachedEsPreviaDeNew;
            asignatura_Carrera.setEsPreviaDe(esPreviaDeNew);
            asignatura_Carrera = em.merge(asignatura_Carrera);
            if (carreraOld != null && !carreraOld.equals(carreraNew)) {
                carreraOld.getAsignatura_Carreras().remove(asignatura_Carrera);
                carreraOld = em.merge(carreraOld);
            }
            if (carreraNew != null && !carreraNew.equals(carreraOld)) {
                carreraNew.getAsignatura_Carreras().add(asignatura_Carrera);
                carreraNew = em.merge(carreraNew);
            }
            if (asignaturaOld != null && !asignaturaOld.equals(asignaturaNew)) {
                asignaturaOld.getAsignatura_Carreras().remove(asignatura_Carrera);
                asignaturaOld = em.merge(asignaturaOld);
            }
            if (asignaturaNew != null && !asignaturaNew.equals(asignaturaOld)) {
                asignaturaNew.getAsignatura_Carreras().add(asignatura_Carrera);
                asignaturaNew = em.merge(asignaturaNew);
            }
            for (Examen examenesOldExamen : examenesOld) {
                if (!examenesNew.contains(examenesOldExamen)) {
                    examenesOldExamen.setAsignatura_Carrera(null);
                    examenesOldExamen = em.merge(examenesOldExamen);
                }
            }
            for (Examen examenesNewExamen : examenesNew) {
                if (!examenesOld.contains(examenesNewExamen)) {
                    Asignatura_Carrera oldAsignatura_CarreraOfExamenesNewExamen = examenesNewExamen.getAsignatura_Carrera();
                    examenesNewExamen.setAsignatura_Carrera(asignatura_Carrera);
                    examenesNewExamen = em.merge(examenesNewExamen);
                    if (oldAsignatura_CarreraOfExamenesNewExamen != null && !oldAsignatura_CarreraOfExamenesNewExamen.equals(asignatura_Carrera)) {
                        oldAsignatura_CarreraOfExamenesNewExamen.getExamenes().remove(examenesNewExamen);
                        oldAsignatura_CarreraOfExamenesNewExamen = em.merge(oldAsignatura_CarreraOfExamenesNewExamen);
                    }
                }
            }
            for (Asignatura_Carrera previasOldAsignatura_Carrera : previasOld) {
                if (!previasNew.contains(previasOldAsignatura_Carrera)) {
                    previasOldAsignatura_Carrera.getPrevias().remove(asignatura_Carrera);
                    previasOldAsignatura_Carrera = em.merge(previasOldAsignatura_Carrera);
                }
            }
            for (Asignatura_Carrera previasNewAsignatura_Carrera : previasNew) {
                if (!previasOld.contains(previasNewAsignatura_Carrera)) {
                    previasNewAsignatura_Carrera.getPrevias().add(asignatura_Carrera);
                    previasNewAsignatura_Carrera = em.merge(previasNewAsignatura_Carrera);
                }
            }
            for (Asignatura_Carrera esPreviaDeOldAsignatura_Carrera : esPreviaDeOld) {
                if (!esPreviaDeNew.contains(esPreviaDeOldAsignatura_Carrera)) {
                    esPreviaDeOldAsignatura_Carrera.getPrevias().remove(asignatura_Carrera);
                    esPreviaDeOldAsignatura_Carrera = em.merge(esPreviaDeOldAsignatura_Carrera);
                }
            }
            for (Asignatura_Carrera esPreviaDeNewAsignatura_Carrera : esPreviaDeNew) {
                if (!esPreviaDeOld.contains(esPreviaDeNewAsignatura_Carrera)) {
                    esPreviaDeNewAsignatura_Carrera.getPrevias().add(asignatura_Carrera);
                    esPreviaDeNewAsignatura_Carrera = em.merge(esPreviaDeNewAsignatura_Carrera);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = asignatura_Carrera.getId();
                if (findAsignatura_Carrera(id) == null) {
                    throw new NonexistentEntityException("The asignatura_Carrera with id " + id + " no longer exists.");
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
            Asignatura_Carrera asignatura_Carrera;
            try {
                asignatura_Carrera = em.getReference(Asignatura_Carrera.class, id);
                asignatura_Carrera.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The asignatura_Carrera with id " + id + " no longer exists.", enfe);
            }
            Carrera carrera = asignatura_Carrera.getCarrera();
            if (carrera != null) {
                carrera.getAsignatura_Carreras().remove(asignatura_Carrera);
                carrera = em.merge(carrera);
            }
            Asignatura asignatura = asignatura_Carrera.getAsignatura();
            if (asignatura != null) {
                asignatura.getAsignatura_Carreras().remove(asignatura_Carrera);
                asignatura = em.merge(asignatura);
            }
            List<Examen> examenes = asignatura_Carrera.getExamenes();
            for (Examen examenesExamen : examenes) {
                examenesExamen.setAsignatura_Carrera(null);
                examenesExamen = em.merge(examenesExamen);
            }
            List<Asignatura_Carrera> previas = asignatura_Carrera.getPrevias();
            for (Asignatura_Carrera previasAsignatura_Carrera : previas) {
                previasAsignatura_Carrera.getPrevias().remove(asignatura_Carrera);
                previasAsignatura_Carrera = em.merge(previasAsignatura_Carrera);
            }
            List<Asignatura_Carrera> esPreviaDe = asignatura_Carrera.getEsPreviaDe();
            for (Asignatura_Carrera esPreviaDeAsignatura_Carrera : esPreviaDe) {
                esPreviaDeAsignatura_Carrera.getPrevias().remove(asignatura_Carrera);
                esPreviaDeAsignatura_Carrera = em.merge(esPreviaDeAsignatura_Carrera);
            }
            em.remove(asignatura_Carrera);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Asignatura_Carrera> findAsignatura_CarreraEntities() {
        return findAsignatura_CarreraEntities(true, -1, -1);
    }

    public List<Asignatura_Carrera> findAsignatura_CarreraEntities(int maxResults, int firstResult) {
        return findAsignatura_CarreraEntities(false, maxResults, firstResult);
    }

    private List<Asignatura_Carrera> findAsignatura_CarreraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Asignatura_Carrera.class));
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

    public Asignatura_Carrera findAsignatura_Carrera(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Asignatura_Carrera.class, id);
        } finally {
            em.close();
        }
    }

    public int getAsignatura_CarreraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Asignatura_Carrera> rt = cq.from(Asignatura_Carrera.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
