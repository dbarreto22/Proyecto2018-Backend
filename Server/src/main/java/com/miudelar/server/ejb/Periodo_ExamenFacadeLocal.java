/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Periodo_Examen;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface Periodo_ExamenFacadeLocal {

    void create(Periodo_Examen periodo_Examen);

    void edit(Periodo_Examen periodo_Examen);

    void remove(Periodo_Examen periodo_Examen);

    Periodo_Examen find(Object id);

    List<Periodo_Examen> findAll();

    List<Periodo_Examen> findRange(int[] range);

    int count();
    
}
