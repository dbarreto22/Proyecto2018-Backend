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
    @NamedQuery(name = "Rol.findAll", query = "Select e from Rol e"),
    @NamedQuery(name = "Rol.findByTipo", query = "Select r from Rol r where r.tipo=:tipo")})
public class Rol implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Basic
    private String tipo;

    public Rol(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Rol() {
    }

    public Rol(String tipo) {
        this.tipo = tipo;
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

}
