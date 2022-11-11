package com.curso.orm.otrareunionmas.dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="persona")
public class Persona {

	//@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true) /*Atributo Único, no admite valores repetidos*/
	private String numeroEmpleado;
	
	private String nombre;
	private String apellidos;
	
	@ManyToMany
	private Set<Reunion> reuniones;
	
	/*Lo utiliza Hibernate para crear los objetos, lo indico porque puse otro constructor, si no lo utiliza por defecto*/
	public Persona () {
		reuniones = new HashSet();
		
	}
	
	public Persona(String numeroEmpleado, String nombre, String apellidos) {
		this();
		this.numeroEmpleado = numeroEmpleado;
		this.nombre = nombre;
		this.apellidos = apellidos;
	}
	
	/*Getters y Setters*/
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNumeroEmpleado() {
		return numeroEmpleado;
	}
	public void setNumeroEmpleado(String numeroEmpleado) {
		this.numeroEmpleado = numeroEmpleado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellidos() {
		return apellidos;
	}
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}
	
	public Set<Reunion> getReuniones() {
		return reuniones;
	}

	//Solo vale con asignacion en cada lado
	/*public void setReuniones(Set<Reunion> reuniones) {
		this.reuniones = reuniones;
	}*/
	
	//Para bidireccionalidad: anadir una reunion a una persona
	public void addReunion(Reunion reunion) {
		//Creación de colección SET reuniones (sin duplicados) al crear una instancia nueva Persona en el constructor
		reuniones.add(reunion);
		//Para evitar bucle infinito. Comprobar si la reunion ya tiene este objeto Persona
		if(!reunion.getParticipantes().contains(this)) {
			reunion.addParticipante(this);
		}
	}
	
	@Override
	public String toString() {
		return "Persona [id=" + id + ", numeroEmpleado=" + numeroEmpleado + ", nombre=" + nombre + ", apellidos="
				+ apellidos + "]";
	}	
}
