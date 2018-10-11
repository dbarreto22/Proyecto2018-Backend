/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.datatypes;

import java.io.Serializable;
import java.util.Objects;

public class DtEstudiante_Examen implements Serializable {

    private Long calificacion;

    private DtExamen examen;

    private DtUsuario usuario;

    public DtEstudiante_Examen(Long id, Long calificacion) {
        this.calificacion = calificacion;
    }

    public DtEstudiante_Examen() {
    }

    public Long getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Long calificacion) {
        this.calificacion = calificacion;
    }

    public DtExamen getExamen() {
        return this.examen;
    }

    public void setExamen(DtExamen examen) {
        this.examen = examen;
    }

    public DtUsuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(DtUsuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.examen);
        hash = 53 * hash + Objects.hashCode(this.usuario);
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
        final DtEstudiante_Examen other = (DtEstudiante_Examen) obj;
        if (!Objects.equals(this.examen, other.examen)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }

    

}