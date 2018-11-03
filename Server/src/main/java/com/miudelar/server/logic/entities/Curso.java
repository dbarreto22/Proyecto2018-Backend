    /**
 * This file was generated by the JPA Modeler
 */
package com.miudelar.server.logic.entities;

import com.miudelar.server.logic.datatypes.DtAsignatura;
import com.miudelar.server.logic.datatypes.DtAsignatura_Carrera;
import com.miudelar.server.logic.datatypes.DtCarrera;
import com.miudelar.server.logic.datatypes.DtCurso;
import com.miudelar.server.logic.datatypes.DtHorario;
import java.io.Serializable;
import java.util.Date;
import java.util.ArrayList;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.*;

/**
 * @author Windows XP
 */
//@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@NamedQueries({
//    @NamedQuery(name = "Estudiante_Curso.findAll", query = "Select e from Estudiante_Curso e"),
    @NamedQuery(name = Curso.FIND_BY_FECHA_ASIGCAR, query = "Select C from Curso C, Asignatura_Carrera A where C.fecha = :fecha AND \n"
            + "C.asignatura_Carrera = A \n"
            + "AND A.id = :idAsigCar"),
        @NamedQuery(name = Curso.GET_ESTUDIANTES_INSCRIPTOS_CURSO, 
                query = "SELECT U FROM Curso C, Usuario U \n"
                + "WHERE C.id = :idCurso \n"
                + "AND C member of U.cursos")
})
public class Curso implements Serializable {
    
    public final static String GET_ESTUDIANTES_INSCRIPTOS_CURSO = "Curso.GET_ESTUDIANTES_INSCRIPTOS_CURSO";
    public final static String FIND_BY_FECHA_ASIGCAR = "Curso.FIND_BY_FECHA_ASIGCAR";
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Basic
    private Date fecha;

    @ManyToOne(targetEntity = Asignatura_Carrera.class, fetch = FetchType.EAGER)
    private Asignatura_Carrera asignatura_Carrera;

    @OneToMany(targetEntity = Horario.class, fetch = FetchType.EAGER)
    private List<Horario> horarios;

    @OneToMany(targetEntity = Estudiante_Curso.class, fetch = FetchType.EAGER)
    private List<Estudiante_Curso> calificacionesCursos;

//    @XmlTransient
//    @ManyToMany(targetEntity = Usuario.class, mappedBy = "cursos", fetch = FetchType.EAGER)
//    private List<Usuario> inscriptos;


    public Curso(Date fecha, Asignatura_Carrera asignatura_Carrera) {
        this.fecha = fecha;
        this.asignatura_Carrera = asignatura_Carrera;
    }
    
    public Curso(Long id, Date fecha, Asignatura_Carrera asignatura_Carrera) {
        this.id = id;
        this.fecha = fecha;
        this.asignatura_Carrera = asignatura_Carrera;
    }
    
    public Curso(DtCurso curso) {
        this.fecha = curso.getFecha();
        Asignatura_Carrera asig_carrera = new Asignatura_Carrera(curso.getAsignatura_Carrera().getAsignatura(), curso.getAsignatura_Carrera().getCarrera()); 
        this.asignatura_Carrera = asig_carrera;
    }

    public Curso() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Horario> getHorarios() {
        return this.horarios;
    }

    public void setHorarios(List<Horario> horarios) {
        this.horarios = horarios;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Asignatura_Carrera getAsignatura_Carrera() {
        System.out.println("asignatura_Carrera: " + asignatura_Carrera.getId());
        return asignatura_Carrera;
    }

    public void setAsignatura_Carrera(Asignatura_Carrera asignatura_Carrera) {
        this.asignatura_Carrera = asignatura_Carrera;
    }

    public List<Estudiante_Curso> getCalificacionesCursos() {
        return this.calificacionesCursos;
    }

    public void setCalificacionesCursos(List<Estudiante_Curso> calificacionesCursos) {
        this.calificacionesCursos = calificacionesCursos;
    }
    
    public void addCalificacionesCursos(Estudiante_Curso calificacionCurso) {
        this.calificacionesCursos.add(calificacionCurso);
    }

    public void addHorario (Horario horario){
        this.horarios.add(horario);
    }
    
    public void removeHorario (Horario horario){
        this.horarios.remove(horario);
    }
    
    public DtCurso toDataType(){
        DtAsignatura_Carrera asignatura_Carrera = new DtAsignatura_Carrera(this.asignatura_Carrera.getId(),
                new DtCarrera(this.asignatura_Carrera.getCarrera().getCodigo(), this.asignatura_Carrera.getCarrera().getNombre()),
                new DtAsignatura(this.asignatura_Carrera.getAsignatura().getCodigo(), this.asignatura_Carrera.getAsignatura().getNombre())
        );
        List <DtHorario> dthorarios = new ArrayList<DtHorario>();
        this.horarios.forEach(horario -> {
            dthorarios.add(new DtHorario(horario.getDia(), horario.getHoraInicio(), horario.getHoraFin()));
        });
        DtCurso dtcurso = new DtCurso(id, fecha, asignatura_Carrera, dthorarios);
        return dtcurso;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final Curso other = (Curso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    

}
