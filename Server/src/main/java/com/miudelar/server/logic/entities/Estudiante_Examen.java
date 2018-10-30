/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import com.miudelar.server.logic.datatypes.DtEstudiante_Examen;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQueries({
//    @NamedQuery(name = "Estudiante_Examen.findAll", query = "Select e from Estudiante_Examen e"),
//    @NamedQuery(name = "Estudiante_Examen.findByCalificacion", query = "Select e from Estudiante_Examen e where e.calificacion=:calificacion")})
    @NamedQuery(name = Estudiante_Examen.FINDBY_ESTUDIANTE_EXAMEN_ASIGNATURA, 
                query = "SELECT C FROM Estudiante_Examen C, Usuario U, Asignatura_Carrera A \n"
                + "WHERE U.cedula = :cedula AND C.usuario = U \n"
                + "AND A.id = :asignatura_carrera AND C.examen member of A.examenes"),
@NamedQuery(name = Estudiante_Examen.GET_MAX_CALIF_ASIG, 
                query = "SELECT max(C.calificacion) FROM Estudiante_Examen C, Usuario U, Asignatura_Carrera A \n"
                + "WHERE U.cedula = :cedula AND C.usuario = U \n"
                + "AND A.id = :asignatura_carrera AND C.examen member of A.examenes")})
public class Estudiante_Examen implements Serializable {
    
    public final static String FINDBY_ESTUDIANTE_EXAMEN_ASIGNATURA = "Estudiante_Examen.FINDBY_ESTUDIANTE_EXAMEN_ASIGNATURA";
    public final static String GET_MAX_CALIF_ASIG = "Estudiante_Examen.GET_MAX_CALIF_ASIG";
    @Basic
    private Long calificacion;

    @Id
    @ManyToOne(targetEntity = Examen.class)
    private Examen examen;

    @Id
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario;

    public Estudiante_Examen(Long id, Long calificacion) {
        this.calificacion = calificacion;
    }
    
    public Estudiante_Examen(Usuario usuario,Examen examen, Long calificacion) {
        this.usuario = usuario;
        this.examen = examen;
        this.calificacion = calificacion;
    }

    public Estudiante_Examen() {
    }

    public Long getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Long calificacion) {
        this.calificacion = calificacion;
    }

    public Examen getExamen() {
        return this.examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public DtEstudiante_Examen toDataType(){
        return new DtEstudiante_Examen(this.calificacion, this.examen.toDataType(), this.usuario.toDataType());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.examen);
        hash = 17 * hash + Objects.hashCode(this.usuario);
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
        final Estudiante_Examen other = (Estudiante_Examen) obj;
        if (!Objects.equals(this.examen, other.examen)) {
            return false;
        }
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        return true;
    }
    
    

}
