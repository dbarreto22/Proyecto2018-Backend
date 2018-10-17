/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.datatypes;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


public class DtAsignatura_Carrera implements Serializable {

    private Long id;
    
    private List<DtExamen> examenes;

    private List<DtCurso> cursos;

    private DtCarrera carrera;

    private DtAsignatura asignatura;

    private List<DtAsignatura_Carrera> previas;

    private List<DtAsignatura_Carrera> esPreviaDe;

    public DtAsignatura_Carrera(Long id) {
        this.id = id;
    }
    
    public DtAsignatura_Carrera(Long id, DtCarrera carrera, DtAsignatura asignatura) {
        this.id = id;
        this.carrera = carrera;
        this.asignatura = asignatura;
    }

    public DtAsignatura_Carrera() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DtExamen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<DtExamen> examenes) {
        this.examenes = examenes;
    }

    public List<DtCurso> getCursos() {
        return cursos;
    }

    public void setCursos(List<DtCurso> cursos) {
        this.cursos = cursos;
    }

    public DtCarrera getCarrera() {
        return this.carrera;
    }

    public void setCarrera(DtCarrera carrera) {
        this.carrera = carrera;
    }

    public DtAsignatura getAsignatura() {
        return this.asignatura;
    }

    public void setAsignatura(DtAsignatura asignatura) {
        this.asignatura = asignatura;
    }

    public List<DtAsignatura_Carrera> getPrevias() {
        return this.previas;
    }

    public void setPrevias(List<DtAsignatura_Carrera> previas) {
        this.previas = previas;
    }

    public List<DtAsignatura_Carrera> getEsPreviaDe() {
        return this.esPreviaDe;
    }

    public void setEsPreviaDe(List<DtAsignatura_Carrera> esPreviaDe) {
        this.esPreviaDe = esPreviaDe;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + Objects.hashCode(this.id);
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
        final DtAsignatura_Carrera other = (DtAsignatura_Carrera) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    

}
