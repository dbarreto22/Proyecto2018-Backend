/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.controller.exceptions.PreexistingEntityException;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Usuario;
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
public class Estudiante_CursoJpaController implements Serializable {

    public Estudiante_CursoJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Estudiante_Curso estudiante_Curso) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(estudiante_Curso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findEstudiante_Curso(estudiante_Curso.getUsuario()) != null) {
                throw new PreexistingEntityException("Estudiante_Curso " + estudiante_Curso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    public void edit(Estudiante_Curso estudiante_Curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            estudiante_Curso = em.merge(estudiante_Curso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Usuario id = estudiante_Curso.getUsuario();
                if (findEstudiante_Curso(id) == null) {
                    throw new NonexistentEntityException("The estudiante_Curso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Estudiante_Curso estudiante_Curso;
            try {
                estudiante_Curso = em.getReference(Estudiante_Curso.class, id);
                estudiante_Curso.getUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The estudiante_Curso with id " + id + " no longer exists.", enfe);
            }
            em.remove(estudiante_Curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Usuario id) throws NonexistentEntityException {
        destroy(id.getCedula());
    }

    public List<Estudiante_Curso> findEstudiante_CursoEntities() {
        return findEstudiante_CursoEntities(true, -1, -1);
    }

    public List<Estudiante_Curso> findEstudiante_CursoEntities(int maxResults, int firstResult) {
        return findEstudiante_CursoEntities(false, maxResults, firstResult);
    }

    private List<Estudiante_Curso> findEstudiante_CursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Estudiante_Curso.class));
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

    public Estudiante_Curso findEstudiante_Curso(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Estudiante_Curso.class, id);
        } finally {
            em.close();
        }
    }

    public Estudiante_Curso findEstudiante_Curso(Usuario id) {
        return findEstudiante_Curso(id.getCedula());
    }

    public int getEstudiante_CursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Estudiante_Curso> rt = cq.from(Estudiante_Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
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
