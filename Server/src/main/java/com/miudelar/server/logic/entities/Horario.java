/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import com.miudelar.server.logic.datatypes.DiaSemana;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
//@NamedQueries({
//    @NamedQuery(name = "Horario.findAll", query = "Select e from Horario e"),
//    @NamedQuery(name = "Horario.findByHoraInicio", query = "Select h from Horario h where h.horaInicio=:horaInicio"),
//    @NamedQuery(name = "Horario.findByHoraFin", query = "Select h from Horario h where h.horaFin=:horaFin")})
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private DiaSemana dia;

    @Basic
    private int horaInicio;

    @Basic
    private int horaFin;

    public Horario(DiaSemana dia, int horaInicio, int horaFin) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.dia = dia;
    }

    public Horario() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }
    
    public int getHoraInicio() {
        return this.horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return this.horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Horario other = (Horario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
