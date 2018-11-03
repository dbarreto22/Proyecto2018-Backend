/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Estudiante_Curso;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface Estudiante_CursoFacadeLocal {

    void create(Estudiante_Curso estudiante_Curso);

    void edit(Estudiante_Curso estudiante_Curso);

    void remove(Estudiante_Curso estudiante_Curso);

    Estudiante_Curso find(Object id);

    List<Estudiante_Curso> findAll();

    List<Estudiante_Curso> findRange(int[] range);
    
    public List<Estudiante_Curso> findEstudiante_CursoByUsuario_Asignatura(String cedula, Long asignatura_carrera);
    
    public Long getMaxCalificacionAsignatura(String cedula, Long asignatura_carrera);
    
    public List<Estudiante_Curso> findByCurso(Long idCurso);

    int count();
    
}
