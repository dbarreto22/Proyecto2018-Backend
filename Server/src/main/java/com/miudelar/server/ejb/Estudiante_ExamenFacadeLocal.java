/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Estudiante_Examen;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface Estudiante_ExamenFacadeLocal {

    void create(Estudiante_Examen estudiante_Examen);

    void edit(Estudiante_Examen estudiante_Examen);

    void remove(Estudiante_Examen estudiante_Examen);

    Estudiante_Examen find(Object id);

    List<Estudiante_Examen> findAll();

    List<Estudiante_Examen> findRange(int[] range);

    int count();
    
    public List<Estudiante_Examen> findEstudiante_ExamenByUsuario_Asignatura(String cedula, Long asignatura_carrera);
    
    public Long getMaxCalificacionAsignatura(String cedula, Long asignatura_carrera);
    
    public List<Estudiante_Examen> findByExamen(Long idExamen);
    
}
