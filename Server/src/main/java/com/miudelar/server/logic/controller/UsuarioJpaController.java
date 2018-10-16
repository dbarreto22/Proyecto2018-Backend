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
import com.miudelar.server.logic.entities.Estudiante_Examen;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Usuario;
import com.miudelar.server.logic.factories.EntityManagerFactoryRepository;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController() {
        this.emf = EntityManagerFactoryRepository.getEntityManagerFactory();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

   public void create(Usuario usuario) throws PreexistingEntityException, Exception {
        if (usuario.getCalificacionesExamenes() == null) {
            usuario.setCalificacionesExamenes(new ArrayList<Estudiante_Examen>());
        }
        if (usuario.getCalificacionesCursos() == null) {
            usuario.setCalificacionesCursos(new ArrayList<Estudiante_Curso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudiante_Examen> attachedCalificacionesExamenes = new ArrayList<Estudiante_Examen>();
            for (Estudiante_Examen calificacionesExamenesEstudiante_ExamenToAttach : usuario.getCalificacionesExamenes()) {
                calificacionesExamenesEstudiante_ExamenToAttach = em.getReference(calificacionesExamenesEstudiante_ExamenToAttach.getClass(), calificacionesExamenesEstudiante_ExamenToAttach.getExamen());
                attachedCalificacionesExamenes.add(calificacionesExamenesEstudiante_ExamenToAttach);
            }
            usuario.setCalificacionesExamenes(attachedCalificacionesExamenes);
            List<Estudiante_Curso> attachedCalificacionesCursos = new ArrayList<Estudiante_Curso>();
            for (Estudiante_Curso calificacionesCursosEstudiante_CursoToAttach : usuario.getCalificacionesCursos()) {
                calificacionesCursosEstudiante_CursoToAttach = em.getReference(calificacionesCursosEstudiante_CursoToAttach.getClass(), calificacionesCursosEstudiante_CursoToAttach.getUsuario());
                attachedCalificacionesCursos.add(calificacionesCursosEstudiante_CursoToAttach);
            }
            usuario.setCalificacionesCursos(attachedCalificacionesCursos);
            em.persist(usuario);
            for (Estudiante_Examen calificacionesExamenesEstudiante_Examen : usuario.getCalificacionesExamenes()) {
                Usuario oldUsuarioOfCalificacionesExamenesEstudiante_Examen = calificacionesExamenesEstudiante_Examen.getUsuario();
                calificacionesExamenesEstudiante_Examen.setUsuario(usuario);
                calificacionesExamenesEstudiante_Examen = em.merge(calificacionesExamenesEstudiante_Examen);
                if (oldUsuarioOfCalificacionesExamenesEstudiante_Examen != null) {
                    oldUsuarioOfCalificacionesExamenesEstudiante_Examen.getCalificacionesExamenes().remove(calificacionesExamenesEstudiante_Examen);
                    oldUsuarioOfCalificacionesExamenesEstudiante_Examen = em.merge(oldUsuarioOfCalificacionesExamenesEstudiante_Examen);
                }
            }
            for (Estudiante_Curso calificacionesCursosEstudiante_Curso : usuario.getCalificacionesCursos()) {
                Usuario oldUsuarioOfCalificacionesCursosEstudiante_Curso = calificacionesCursosEstudiante_Curso.getUsuario();
                calificacionesCursosEstudiante_Curso.setUsuario(usuario);
                calificacionesCursosEstudiante_Curso = em.merge(calificacionesCursosEstudiante_Curso);
                if (oldUsuarioOfCalificacionesCursosEstudiante_Curso != null) {
                    oldUsuarioOfCalificacionesCursosEstudiante_Curso.getCalificacionesCursos().remove(calificacionesCursosEstudiante_Curso);
                    oldUsuarioOfCalificacionesCursosEstudiante_Curso = em.merge(oldUsuarioOfCalificacionesCursosEstudiante_Curso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUsuario(usuario.getCedula()) != null) {
                throw new PreexistingEntityException("Usuario " + usuario + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getCedula());
            List<Estudiante_Examen> calificacionesExamenesOld = persistentUsuario.getCalificacionesExamenes();
            List<Estudiante_Examen> calificacionesExamenesNew = usuario.getCalificacionesExamenes();
            List<Estudiante_Curso> calificacionesCursosOld = persistentUsuario.getCalificacionesCursos();
            List<Estudiante_Curso> calificacionesCursosNew = usuario.getCalificacionesCursos();
            List<Estudiante_Examen> attachedCalificacionesExamenesNew = new ArrayList<Estudiante_Examen>();
            for (Estudiante_Examen calificacionesExamenesNewEstudiante_ExamenToAttach : calificacionesExamenesNew) {
                calificacionesExamenesNewEstudiante_ExamenToAttach = em.getReference(calificacionesExamenesNewEstudiante_ExamenToAttach.getClass(), calificacionesExamenesNewEstudiante_ExamenToAttach.getExamen());
                attachedCalificacionesExamenesNew.add(calificacionesExamenesNewEstudiante_ExamenToAttach);
            }
            calificacionesExamenesNew = attachedCalificacionesExamenesNew;
            usuario.setCalificacionesExamenes(calificacionesExamenesNew);
            List<Estudiante_Curso> attachedCalificacionesCursosNew = new ArrayList<Estudiante_Curso>();
            for (Estudiante_Curso calificacionesCursosNewEstudiante_CursoToAttach : calificacionesCursosNew) {
                calificacionesCursosNewEstudiante_CursoToAttach = em.getReference(calificacionesCursosNewEstudiante_CursoToAttach.getClass(), calificacionesCursosNewEstudiante_CursoToAttach.getUsuario());
                attachedCalificacionesCursosNew.add(calificacionesCursosNewEstudiante_CursoToAttach);
            }
            calificacionesCursosNew = attachedCalificacionesCursosNew;
            usuario.setCalificacionesCursos(calificacionesCursosNew);
            usuario = em.merge(usuario);
            for (Estudiante_Examen calificacionesExamenesOldEstudiante_Examen : calificacionesExamenesOld) {
                if (!calificacionesExamenesNew.contains(calificacionesExamenesOldEstudiante_Examen)) {
                    calificacionesExamenesOldEstudiante_Examen.setUsuario(null);
                    calificacionesExamenesOldEstudiante_Examen = em.merge(calificacionesExamenesOldEstudiante_Examen);
                }
            }
            for (Estudiante_Examen calificacionesExamenesNewEstudiante_Examen : calificacionesExamenesNew) {
                if (!calificacionesExamenesOld.contains(calificacionesExamenesNewEstudiante_Examen)) {
                    Usuario oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen = calificacionesExamenesNewEstudiante_Examen.getUsuario();
                    calificacionesExamenesNewEstudiante_Examen.setUsuario(usuario);
                    calificacionesExamenesNewEstudiante_Examen = em.merge(calificacionesExamenesNewEstudiante_Examen);
                    if (oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen != null && !oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen.equals(usuario)) {
                        oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen.getCalificacionesExamenes().remove(calificacionesExamenesNewEstudiante_Examen);
                        oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen = em.merge(oldUsuarioOfCalificacionesExamenesNewEstudiante_Examen);
                    }
                }
            }
            for (Estudiante_Curso calificacionesCursosOldEstudiante_Curso : calificacionesCursosOld) {
                if (!calificacionesCursosNew.contains(calificacionesCursosOldEstudiante_Curso)) {
                    calificacionesCursosOldEstudiante_Curso.setUsuario(null);
                    calificacionesCursosOldEstudiante_Curso = em.merge(calificacionesCursosOldEstudiante_Curso);
                }
            }
            for (Estudiante_Curso calificacionesCursosNewEstudiante_Curso : calificacionesCursosNew) {
                if (!calificacionesCursosOld.contains(calificacionesCursosNewEstudiante_Curso)) {
                    Usuario oldUsuarioOfCalificacionesCursosNewEstudiante_Curso = calificacionesCursosNewEstudiante_Curso.getUsuario();
                    calificacionesCursosNewEstudiante_Curso.setUsuario(usuario);
                    calificacionesCursosNewEstudiante_Curso = em.merge(calificacionesCursosNewEstudiante_Curso);
                    if (oldUsuarioOfCalificacionesCursosNewEstudiante_Curso != null && !oldUsuarioOfCalificacionesCursosNewEstudiante_Curso.equals(usuario)) {
                        oldUsuarioOfCalificacionesCursosNewEstudiante_Curso.getCalificacionesCursos().remove(calificacionesCursosNewEstudiante_Curso);
                        oldUsuarioOfCalificacionesCursosNewEstudiante_Curso = em.merge(oldUsuarioOfCalificacionesCursosNewEstudiante_Curso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = usuario.getCedula();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getCedula();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Estudiante_Examen> calificacionesExamenes = usuario.getCalificacionesExamenes();
            for (Estudiante_Examen calificacionesExamenesEstudiante_Examen : calificacionesExamenes) {
                calificacionesExamenesEstudiante_Examen.setUsuario(null);
                calificacionesExamenesEstudiante_Examen = em.merge(calificacionesExamenesEstudiante_Examen);
            }
            List<Estudiante_Curso> calificacionesCursos = usuario.getCalificacionesCursos();
            for (Estudiante_Curso calificacionesCursosEstudiante_Curso : calificacionesCursos) {
                calificacionesCursosEstudiante_Curso.setUsuario(null);
                calificacionesCursosEstudiante_Curso = em.merge(calificacionesCursosEstudiante_Curso);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
