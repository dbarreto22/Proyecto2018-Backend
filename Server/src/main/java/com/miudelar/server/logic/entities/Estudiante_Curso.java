/**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

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
//    @NamedQuery(name = "Estudiante_Curso.findAll", query = "Select e from Estudiante_Curso e"),
//    @NamedQuery(name = "Estudiante_Curso.findByCalificacion", query = "Select e from Estudiante_Curso e where e.calificacion=:calificacion")
        @NamedQuery(name = Estudiante_Curso.FINDBY_USUARIO_ASIGNATURA, 
                query = "SELECT C FROM Estudiante_Curso C, Usuario U, Asignatura_Carrera A \n"
                + "WHERE U.cedula = :cedula AND C.usuario = U \n"
                + "AND A.id = :asignatura_carrera AND C.curso member of A.cursos")})
public class Estudiante_Curso implements Serializable {
    public final static String FINDBY_USUARIO_ASIGNATURA = "Estudiante_Curso.FINDBY_USUARIO_ASIGNATURA";
    @Basic
    private Long calificacion;
    
   @Id
    @ManyToOne(targetEntity = Usuario.class)
    private Usuario usuario;
   
   @Id
    @ManyToOne(targetEntity = Curso.class)
    private Curso curso;

    public Estudiante_Curso() {
    }

    public Estudiante_Curso(Long calificacion, Usuario usuario, Curso curso) {
        this.calificacion = calificacion;
        this.usuario = usuario;
        this.curso = curso;
    }
    
     public Long getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(Long calificacion) {
        this.calificacion = calificacion;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Curso getCurso() {
        return this.curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.usuario);
        hash = 37 * hash + Objects.hashCode(this.curso);
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
        final Estudiante_Curso other = (Estudiante_Curso) obj;
        if (!Objects.equals(this.usuario, other.usuario)) {
            return false;
        }
        if (!Objects.equals(this.curso, other.curso)) {
            return false;
        }
        return true;
    }

}
