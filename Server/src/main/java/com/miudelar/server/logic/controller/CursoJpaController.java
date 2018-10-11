/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.controller.exceptions.NonexistentEntityException;
import com.miudelar.server.logic.entities.Curso;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.miudelar.server.logic.entities.Estudiante_Curso;
import java.util.ArrayList;
import java.util.List;
import com.miudelar.server.logic.entities.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author rmoreno
 */
public class CursoJpaController implements Serializable {

    public CursoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Curso curso) {
        if (curso.getCalificacionesCursos() == null) {
            curso.setCalificacionesCursos(new ArrayList<Estudiante_Curso>());
        }
        if (curso.getInscriptos() == null) {
            curso.setInscriptos(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Estudiante_Curso> attachedCalificacionesCursos = new ArrayList<Estudiante_Curso>();
            for (Estudiante_Curso calificacionesCursosEstudiante_CursoToAttach : curso.getCalificacionesCursos()) {
                calificacionesCursosEstudiante_CursoToAttach = em.getReference(calificacionesCursosEstudiante_CursoToAttach.getClass(), calificacionesCursosEstudiante_CursoToAttach.getUsuario());
                attachedCalificacionesCursos.add(calificacionesCursosEstudiante_CursoToAttach);
            }
            curso.setCalificacionesCursos(attachedCalificacionesCursos);
            List<Usuario> attachedInscriptos = new ArrayList<Usuario>();
            for (Usuario inscriptosUsuarioToAttach : curso.getInscriptos()) {
                inscriptosUsuarioToAttach = em.getReference(inscriptosUsuarioToAttach.getClass(), inscriptosUsuarioToAttach.getCedula());
                attachedInscriptos.add(inscriptosUsuarioToAttach);
            }
            curso.setInscriptos(attachedInscriptos);
            em.persist(curso);
            for (Estudiante_Curso calificacionesCursosEstudiante_Curso : curso.getCalificacionesCursos()) {
                Curso oldCursoOfCalificacionesCursosEstudiante_Curso = calificacionesCursosEstudiante_Curso.getCurso();
                calificacionesCursosEstudiante_Curso.setCurso(curso);
                calificacionesCursosEstudiante_Curso = em.merge(calificacionesCursosEstudiante_Curso);
                if (oldCursoOfCalificacionesCursosEstudiante_Curso != null) {
                    oldCursoOfCalificacionesCursosEstudiante_Curso.getCalificacionesCursos().remove(calificacionesCursosEstudiante_Curso);
                    oldCursoOfCalificacionesCursosEstudiante_Curso = em.merge(oldCursoOfCalificacionesCursosEstudiante_Curso);
                }
            }
            for (Usuario inscriptosUsuario : curso.getInscriptos()) {
                inscriptosUsuario.getCursos().add(curso);
                inscriptosUsuario = em.merge(inscriptosUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Curso curso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Curso persistentCurso = em.find(Curso.class, curso.getId());
            List<Estudiante_Curso> calificacionesCursosOld = persistentCurso.getCalificacionesCursos();
            List<Estudiante_Curso> calificacionesCursosNew = curso.getCalificacionesCursos();
            List<Usuario> inscriptosOld = persistentCurso.getInscriptos();
            List<Usuario> inscriptosNew = curso.getInscriptos();
            List<Estudiante_Curso> attachedCalificacionesCursosNew = new ArrayList<Estudiante_Curso>();
            for (Estudiante_Curso calificacionesCursosNewEstudiante_CursoToAttach : calificacionesCursosNew) {
                calificacionesCursosNewEstudiante_CursoToAttach = em.getReference(calificacionesCursosNewEstudiante_CursoToAttach.getClass(), calificacionesCursosNewEstudiante_CursoToAttach.getUsuario());
                attachedCalificacionesCursosNew.add(calificacionesCursosNewEstudiante_CursoToAttach);
            }
            calificacionesCursosNew = attachedCalificacionesCursosNew;
            curso.setCalificacionesCursos(calificacionesCursosNew);
            List<Usuario> attachedInscriptosNew = new ArrayList<Usuario>();
            for (Usuario inscriptosNewUsuarioToAttach : inscriptosNew) {
                inscriptosNewUsuarioToAttach = em.getReference(inscriptosNewUsuarioToAttach.getClass(), inscriptosNewUsuarioToAttach.getCedula());
                attachedInscriptosNew.add(inscriptosNewUsuarioToAttach);
            }
            inscriptosNew = attachedInscriptosNew;
            curso.setInscriptos(inscriptosNew);
            curso = em.merge(curso);
            for (Estudiante_Curso calificacionesCursosOldEstudiante_Curso : calificacionesCursosOld) {
                if (!calificacionesCursosNew.contains(calificacionesCursosOldEstudiante_Curso)) {
                    calificacionesCursosOldEstudiante_Curso.setCurso(null);
                    calificacionesCursosOldEstudiante_Curso = em.merge(calificacionesCursosOldEstudiante_Curso);
                }
            }
            for (Estudiante_Curso calificacionesCursosNewEstudiante_Curso : calificacionesCursosNew) {
                if (!calificacionesCursosOld.contains(calificacionesCursosNewEstudiante_Curso)) {
                    Curso oldCursoOfCalificacionesCursosNewEstudiante_Curso = calificacionesCursosNewEstudiante_Curso.getCurso();
                    calificacionesCursosNewEstudiante_Curso.setCurso(curso);
                    calificacionesCursosNewEstudiante_Curso = em.merge(calificacionesCursosNewEstudiante_Curso);
                    if (oldCursoOfCalificacionesCursosNewEstudiante_Curso != null && !oldCursoOfCalificacionesCursosNewEstudiante_Curso.equals(curso)) {
                        oldCursoOfCalificacionesCursosNewEstudiante_Curso.getCalificacionesCursos().remove(calificacionesCursosNewEstudiante_Curso);
                        oldCursoOfCalificacionesCursosNewEstudiante_Curso = em.merge(oldCursoOfCalificacionesCursosNewEstudiante_Curso);
                    }
                }
            }
            for (Usuario inscriptosOldUsuario : inscriptosOld) {
                if (!inscriptosNew.contains(inscriptosOldUsuario)) {
                    inscriptosOldUsuario.getCursos().remove(curso);
                    inscriptosOldUsuario = em.merge(inscriptosOldUsuario);
                }
            }
            for (Usuario inscriptosNewUsuario : inscriptosNew) {
                if (!inscriptosOld.contains(inscriptosNewUsuario)) {
                    inscriptosNewUsuario.getCursos().add(curso);
                    inscriptosNewUsuario = em.merge(inscriptosNewUsuario);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = curso.getId();
                if (findCurso(id) == null) {
                    throw new NonexistentEntityException("The curso with id " + id + " no longer exists.");
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
            Curso curso;
            try {
                curso = em.getReference(Curso.class, id);
                curso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The curso with id " + id + " no longer exists.", enfe);
            }
            List<Estudiante_Curso> calificacionesCursos = curso.getCalificacionesCursos();
            for (Estudiante_Curso calificacionesCursosEstudiante_Curso : calificacionesCursos) {
                calificacionesCursosEstudiante_Curso.setCurso(null);
                calificacionesCursosEstudiante_Curso = em.merge(calificacionesCursosEstudiante_Curso);
            }
            List<Usuario> inscriptos = curso.getInscriptos();
            for (Usuario inscriptosUsuario : inscriptos) {
                inscriptosUsuario.getCursos().remove(curso);
                inscriptosUsuario = em.merge(inscriptosUsuario);
            }
            em.remove(curso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Curso> findCursoEntities() {
        return findCursoEntities(true, -1, -1);
    }

    public List<Curso> findCursoEntities(int maxResults, int firstResult) {
        return findCursoEntities(false, maxResults, firstResult);
    }

    private List<Curso> findCursoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Curso.class));
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

    public Curso findCurso(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Curso.class, id);
        } finally {
            em.close();
        }
    }

    public int getCursoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Curso> rt = cq.from(Curso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}