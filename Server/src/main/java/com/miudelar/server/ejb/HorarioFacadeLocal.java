/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Horario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface HorarioFacadeLocal {

    void create(Horario horario);

    void edit(Horario horario);

    void remove(Horario horario);

    Horario find(Object id);

    List<Horario> findAll();

    List<Horario> findRange(int[] range);

    int count();
    
}
