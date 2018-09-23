/**
 * This file was generated by the JPA Modeler
 */
package Entidades;

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
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQueries({
    @NamedQuery(name = "Horario.findAll", query = "Select e from Horario e"),
    @NamedQuery(name = "Horario.findByHoraInicio", query = "Select h from Horario h where h.horaInicio=:horaInicio"),
    @NamedQuery(name = "Horario.findByHoraFin", query = "Select h from Horario h where h.horaFin=:horaFin")})
public class Horario implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private int horaInicio;

    @Basic
    private int horaFin;

    public Horario(Long id, int horaInicio, int horaFin) {
        this.id = id;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    public Horario() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
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

}
