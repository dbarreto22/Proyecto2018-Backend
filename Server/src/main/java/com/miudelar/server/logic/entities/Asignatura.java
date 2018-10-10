/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
//@NamedQueries({
//    @NamedQuery(name = "Asignatura.findAll", query = "Select e from Asignatura e"),
//    @NamedQuery(name = "Asignatura.findByNombre", query = "Select a from Asignatura a where a.nombre=:nombre")})
public class Asignatura implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long codigo;

    @Basic
    @Column(unique = true)
    private String nombre;

    @XmlTransient
    @OneToMany(targetEntity = Asignatura_Carrera.class, mappedBy = "asignatura", fetch = FetchType.EAGER)
    private List<Asignatura_Carrera> asignatura_Carreras;

    @XmlTransient
    @ManyToMany(targetEntity = Carrera.class, mappedBy = "asignaturas", fetch = FetchType.EAGER)
    private List<Carrera> carreras;

    public Asignatura(Long codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Asignatura() {
    }

    public Long getCodigo() {
        return this.codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Asignatura_Carrera> getAsignatura_Carreras() {
        return this.asignatura_Carreras;
    }

    public void setAsignatura_Carreras(List<Asignatura_Carrera> asignatura_Carreras) {
        this.asignatura_Carreras = asignatura_Carreras;
    }

    public List<Carrera> getCarreras() {
        return this.carreras;
    }

    public void setCarreras(List<Carrera> carreras) {
        this.carreras = carreras;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.codigo);
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
        final Asignatura other = (Asignatura) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

}
