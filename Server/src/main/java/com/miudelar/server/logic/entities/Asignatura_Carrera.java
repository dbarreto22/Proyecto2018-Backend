/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
//@NamedQuery(name = "Asignatura_Carrera.findAll", query = "Select e from Asignatura_Carrera e")
public class Asignatura_Carrera implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(targetEntity = Examen.class)
    private Examen examen;

    @OneToOne(targetEntity = Curso.class)
    private Curso curso;

    @ManyToOne(targetEntity = Carrera.class)
    private Carrera carrera;

    @ManyToOne(targetEntity = Asignatura.class)
    private Asignatura asignatura;

    @ManyToMany(targetEntity = Asignatura_Carrera.class)
    private List<Asignatura_Carrera> previas;

    @XmlTransient
    @ManyToMany(targetEntity = Asignatura_Carrera.class, mappedBy = "previas")
    private List<Asignatura_Carrera> esPreviaDe;

    public Asignatura_Carrera(Long id) {
        this.id = id;
    }

    public Asignatura_Carrera() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Examen getExamen() {
        return this.examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
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

    public List<Asignatura_Carrera> getEsPreviaDe() {
        return this.esPreviaDe;
    }

    public void setEsPreviaDe(List<Asignatura_Carrera> esPreviaDe) {
        this.esPreviaDe = esPreviaDe;
    }

}
