package com.example.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Evento")
@Entity
public class EventoEntity  implements Serializable {

    @GeneratedValue( generator = "uuid2" )
    @GenericGenerator( name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column( name = "id", columnDefinition = "VARCHAR(36)" )
	@Id
    private String id;

    private String nombre;
    private Date fecha;
    private String descripcion;
    private boolean estado;
}
