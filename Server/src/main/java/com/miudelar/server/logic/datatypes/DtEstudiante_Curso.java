/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.datatypes;

import java.io.Serializable;
import java.util.Objects;

public class DtEstudiante_Curso implements Serializable {

    private Long calificacion;

    private DtUsuario usuario;

    private DtCurso curso;

    public DtEstudiante_Curso() {
    }

    public DtEstudiante_Curso(Long calificacion, DtUsuario usuario, DtCurso curso) {
        this.calificacion = calificacion;
        this.usuario = usuario;
        this.curso = curso;
    }
    
     public Long getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Long calificacion) {
        this.calificacion = calificacion;
    }

    public DtUsuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(DtUsuario usuario) {
        this.usuario = usuario;
    }

    public DtCurso getCurso() {
        return this.curso;
    }

    public void setCurso(DtCurso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.usuario);
        hash = 89 * hash + Objects.hashCode(this.curso);
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
        final DtEstudiante_Curso other = (DtEstudiante_Curso) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

    
}
