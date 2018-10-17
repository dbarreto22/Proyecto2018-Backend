/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.logic.controller;

import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author rmoreno
 */
public class Asignatura_CarreraExtController extends Asignatura_CarreraJpaController {
      
//    public List<Asignatura_Carrera> findAsignatura_CarreraByCods(Long carrera, Long asignatura) {
//        EntityManager em = getEntityManager();
//        try {
//            Query q = em.createNamedQuery(Asignatura_Carrera.FIND_BY_CODS).setParameter("carrera", carrera).setParameter("asignatura", asignatura);
//            return q.getResultList();
//        } finally {
//            em.close();
//        }
//    }
}
