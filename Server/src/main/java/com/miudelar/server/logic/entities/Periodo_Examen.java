/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import java.io.Serializable;
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
//    @NamedQuery(name = "Periodo_Examen.findAll", query = "Select e from Periodo_Examen e"),
//    @NamedQuery(name = "Periodo_Examen.findByTipo", query = "Select p from Periodo_Examen p where p.tipo=:tipo"),
//    @NamedQuery(name = "Periodo_Examen.findByFechaInicio", query = "Select p from Periodo_Examen p where p.fechaInicio=:fechaInicio"),
//    @NamedQuery(name = "Periodo_Examen.findByFechaFin", query = "Select p from Periodo_Examen p where p.fechaFin=:fechaFin")})
public class Periodo_Examen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String tipo;

    @Basic
    private String fechaInicio;

    @Basic
    private String fechaFin;

    public Periodo_Examen(Long id, String tipo, String fechaInicio, String fechaFin) {
        this.id = id;
        this.tipo = tipo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Periodo_Examen() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFechaInicio() {
        return this.fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return this.fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

}
