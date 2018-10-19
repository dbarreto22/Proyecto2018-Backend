/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.datatypes;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class DtCalificaciones implements Serializable {

    private DtAsignatura asignatura;

    private List<DtEstudiante_Curso> estudiante_curso;

    private List<DtEstudiante_Examen> estudiante_examen;

    public DtCalificaciones(DtAsignatura asignatura, List<DtEstudiante_Curso> estudiante_curso, List<DtEstudiante_Examen> estudiante_examen) {
        this.asignatura = asignatura;
        this.estudiante_curso = estudiante_curso;
        this.estudiante_examen = estudiante_examen;
    }

    public DtCalificaciones() {
    }

    public DtAsignatura getAsignatura() {
        return asignatura;
    }

    public List<DtEstudiante_Curso> getEstudiante_curso() {
        return estudiante_curso;
    }

    public List<DtEstudiante_Examen> getEstudiante_examen() {
        return estudiante_examen;
    }

    public void setAsignatura(DtAsignatura asignatura) {
        this.asignatura = asignatura;
    }

    public void setEstudiante_curso(List<DtEstudiante_Curso> estudiante_curso) {
        this.estudiante_curso = estudiante_curso;
    }

    public void setEstudiante_examen(List<DtEstudiante_Examen> estudiante_examen) {
        this.estudiante_examen = estudiante_examen;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.asignatura);
        hash = 71 * hash + Objects.hashCode(this.estudiante_curso);
        hash = 71 * hash + Objects.hashCode(this.estudiante_examen);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DtCalificaciones other = (DtCalificaciones) obj;
        if (!Objects.equals(this.asignatura, other.asignatura)) {
            return false;
        }
        if (!Objects.equals(this.estudiante_curso, other.estudiante_curso)) {
            return false;
        }
        if (!Objects.equals(this.estudiante_examen, other.estudiante_examen)) {
            return false;
        }
        return true;
    }
}
