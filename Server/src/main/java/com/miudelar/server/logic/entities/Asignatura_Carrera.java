/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
//@NamedQuery(name = Asignatura_Carrera.FIND_BY_CODS, query = "SELECT A FROM Asignatura_Carrera A "
//            + "WHERE A.carrera_codigo = :carrera AND A.asignatura_codigo = :asignatura)")
public class Asignatura_Carrera implements Serializable {

//    public final static String FIND_BY_CODS = "Asignatura_Carrera.FIND_BY_CODS";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @XmlTransient
    @OneToMany(targetEntity = Examen.class, fetch = FetchType.EAGER)
    private List<Examen> examenes;
    
    @XmlTransient
    @OneToMany(targetEntity = Curso.class, fetch = FetchType.EAGER)
    private List<Curso> cursos;
    
    @ManyToOne(targetEntity = Carrera.class)
    private Carrera carrera;

    @ManyToOne(targetEntity = Asignatura.class)
    private Asignatura asignatura;
    
    @ManyToMany(targetEntity = Periodo_Examen.class, fetch = FetchType.EAGER)
    private List<Periodo_Examen> periodos_Examen;

    @ManyToMany(targetEntity = Asignatura_Carrera.class, fetch = FetchType.EAGER)
    private List<Asignatura_Carrera> previas;

    @XmlTransient
    @ManyToMany(targetEntity = Asignatura_Carrera.class, mappedBy = "previas", fetch = FetchType.EAGER)
    private List<Asignatura_Carrera> esPreviaDe;

    public Asignatura_Carrera(Asignatura asignatura, Carrera carrera) {
        this.carrera = carrera;
        this.asignatura = asignatura;
    }
    
    public Asignatura_Carrera() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Examen> getExamenes() {
        return examenes;
    }

    public void setExamenes(List<Examen> examenes) {
        this.examenes = examenes;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }

    public Carrera getCarrera() {
        return this.carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Asignatura getAsignatura() {
        return this.asignatura;
    }

    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    public List<Asignatura_Carrera> getPrevias() {
        return this.previas;
    }

    public void setPrevias(List<Asignatura_Carrera> previas) {
        this.previas = previas;
    }
    
    public void addPrevia(Asignatura_Carrera previa) {
        this.previas.add(previa);
    }
    
    public void removePrevia(Asignatura_Carrera previa) {
        this.previas.remove(previa);
    }

    public List<Asignatura_Carrera> getEsPreviaDe() {
        return this.esPreviaDe;
    }

    public void setEsPreviaDe(List<Asignatura_Carrera> esPreviaDe) {
        this.esPreviaDe = esPreviaDe;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final Asignatura_Carrera other = (Asignatura_Carrera) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
