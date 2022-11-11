package com.curso.orm.otrareunionmas.dominio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sala")
public class Sala {
	
	@Id
	@Column(length = 20)
	private String id;
	
	//@Column(name = "descripcion")
	private String descripcion;
	
	//@Column(name = "capacidad")
	private int capacidad;
	
	/*Refenciado por la tabla sala*/
	@OneToMany(mappedBy = "sala")
	private List<Reunion> reuniones;
	
	/*Lo utiliza Hibernate para crear los objetos, lo indico porque puse otro constructor, si no lo utiliza por defecto*/
	public Sala() {
		
	}
	
	public Sala(String id, String descripcion, int capacidad) {
		this.id = id;
		this.descripcion = descripcion;
		this.capacidad = capacidad;
	}
	
	/*Getters y Setters*/
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int getCapacidad() {
		return capacidad;
	}

	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	@Override
	public String toString() {
		return "Sala [id=" + id + ", descripcion=" + descripcion + ", capacidad=" + capacidad + "]";
	}
}
