/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Examen;
import com.miudelar.server.logic.entities.Usuario;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface ExamenFacadeLocal {

    void create(Examen examen);

    void edit(Examen examen);

    void remove(Examen examen);

    Examen find(Object id);

    List<Examen> findAll();

    List<Examen> findRange(int[] range);

    int count();
    
    public List<Usuario> getEstudiantesInscriptos(Long idExamen);
    
}
