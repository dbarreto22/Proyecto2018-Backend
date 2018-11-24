/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface Asignatura_CarreraFacadeLocal {

    void create(Asignatura_Carrera asignatura_Carrera);

    void edit(Asignatura_Carrera asignatura_Carrera);

    void remove(Asignatura_Carrera asignatura_Carrera);

    Asignatura_Carrera find(Object id);

    List<Asignatura_Carrera> findAll();

    List<Asignatura_Carrera> findRange(int[] range);

    int count();
    
    public Asignatura_Carrera findByCods(Long carrera, Long asignatura);
    
}
