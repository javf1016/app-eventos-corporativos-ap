package com.example.model;

import java.util.Date;
import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Lugar")
@Entity
public class LugarEntity  implements Serializable {

    @GeneratedValue( generator = "uuid2" )
    @GenericGenerator( name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator" )
    @Column( name = "id", columnDefinition = "VARCHAR(36)" )
	@Id
    private String id;

    private String nombre;
    private int capacidad;

    @ManyToMany(mappedBy = "lugares")
    private Set<EventoEntity> eventos;
}
