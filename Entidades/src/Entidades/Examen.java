/**
 * This file was generated by the JPA Modeler
 */
package Entidades;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQueries({
    @NamedQuery(name = "Examen.findAll", query = "Select e from Examen e"),
    @NamedQuery(name = "Examen.findByFecha", query = "Select e from Examen e where e.fecha=:fecha")})
public class Examen implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private Date fecha;

    @XmlTransient
    @OneToOne(targetEntity = Asignatura_Carrera.class, mappedBy = "examen")
    private Asignatura_Carrera asignatura_Carrera;

    @XmlTransient
    @OneToMany(targetEntity = Estudiante_Examen.class, mappedBy = "examen")
    private List<Estudiante_Examen> calificacionesExamenes;

    @XmlTransient
    @ManyToMany(targetEntity = Usuario.class, mappedBy = "inscripcionesExamenes")
    private List<Usuario> inscriptos;

    public Examen(Long id, Date fecha) {
        this.id = id;
        this.fecha = fecha;
    }

    public Examen() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Asignatura_Carrera getAsignatura_Carrera() {
        return this.asignatura_Carrera;
    }

    public void setAsignatura_Carrera(Asignatura_Carrera asignatura_Carrera) {
        this.asignatura_Carrera = asignatura_Carrera;
    }

    public List<Estudiante_Examen> getCalificacionesExamenes() {
        return this.calificacionesExamenes;
    }

    public void setCalificacionesExamenes(List<Estudiante_Examen> calificacionesExamenes) {
        this.calificacionesExamenes = calificacionesExamenes;
    }

    public List<Usuario> getInscriptos() {
        return this.inscriptos;
    }

    public void setInscriptos(List<Usuario> inscriptos) {
        this.inscriptos = inscriptos;
    }

}
