/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Asignatura;
import com.miudelar.server.logic.entities.Asignatura_Carrera;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface AsignaturaFacadeLocal {

    void create(Asignatura asignatura);

    void edit(Asignatura asignatura);

    void remove(Asignatura asignatura);

    Asignatura find(Object id);

    List<Asignatura> findAll();

    List<Asignatura> findRange(int[] range);

    int count();
    
    List<Asignatura> findByCarrera(Long codigo);
    
}
