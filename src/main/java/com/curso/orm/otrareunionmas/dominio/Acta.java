package com.curso.orm.otrareunionmas.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "acta")
public class Acta {

	//@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	//@Column(name = "contenido")
	private String contenido;
	
	@OneToOne
	@JoinColumn /*Vinculo se hace por esta columna. En esta tabla se añade la columna que haga referencia al id de la otra.*/
	private Reunion reunion;
	
	/*Lo utiliza Hibernate para crear los objetos, lo indico porque puse otro constructor, si no lo utiliza por defecto*/
	public Acta() {
		
	}
	
	public Acta(String contenido, Reunion reunion) {
		this.contenido = contenido;
		this.reunion = reunion;
		/*Siempre asociamos el acta creada a la reunion correspondiente en el propio constructor*/
		reunion.setActa(this);
	}

	/*Getters y Setters*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getContenido() {
		return contenido;
	}

	public void setContenido(String contenido) {
		this.contenido = contenido;
	}

	public Reunion getReunion() {
		return reunion;
	}

	public void setReunion(Reunion reunion) {
		this.reunion = reunion;
	}

	@Override
	public String toString() {
		return "Acta [id=" + id + ", contenido=" + contenido + "]";
	}
}


