/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.miudelar.server.ejb;

import com.miudelar.server.logic.entities.Curso;
import com.miudelar.server.logic.entities.Usuario;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author rmoreno
 */
@Local
public interface CursoFacadeLocal {

    void create(Curso curso);

    void edit(Curso curso);

    void remove(Curso curso);

    Curso find(Object id);

    List<Curso> findAll();

    List<Curso> findRange(int[] range);

    int count();
    
    public List<Usuario> getEstudiantesInscriptos(Long idCurso);
    
    public List<Curso> getCursoByFechaAndIdAsigCar(Date fecha, Long idAsigCar);
    
    public List<Curso> getCursosDisponiblesEstudiante(String cedula);
    
}
