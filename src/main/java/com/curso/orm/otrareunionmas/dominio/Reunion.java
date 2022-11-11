package com.curso.orm.otrareunionmas.dominio;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name ="reunion") /*Si no quiero llamarla como la clase, Reunion, le doy un nombre*/
public class Reunion {
	
	//@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) /*Autoincremento*/
	private int id;
	
	//@Column(name = "fecha")
	private LocalDateTime fecha;
	
	//@Column(name = "asunto")
	private String asunto;
	
	/* Anadir una columna con el id de la Sala a reunion == Entidad atributo Sala
	 * Cuando recupero los datos de la base de datos:
	 * EAGER (ávido, recupera el dato cuando se obtiene la entidad de la base de datos (toda la fila)).
	 * LAZY (perezoso, recupera el dato cuando se accede al atributo (solo el atributo)).
	 * */
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Sala sala;
	
	@OneToOne(mappedBy="reunion")
	private Acta acta;
	
	@ManyToMany(mappedBy = "reuniones", cascade = CascadeType.ALL) /*CascadeType.ALL cuando cree la reunion también tiene que crear las personas*/
	private Set<Persona> participantes;
	
	//Lo utiliza Hibernate para crear los objetos, lo indico porque puse otro constructor, si no lo utiliza por defecto
	public Reunion() {
		participantes = new HashSet();
	}
	
	public Reunion(LocalDateTime fecha, String asunto) {
		this();
		this.fecha = fecha;
		this.asunto = asunto;
	}
	
	/*Se usa en listar las reuniones de una persona*/
	public Reunion(Reunion r) {
		this.fecha = r.fecha;
		this.asunto = r.asunto;
	}

	/*Getters y Setters*/
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	public String getAsunto() {
		return asunto;
	}

	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	public Acta getActa() {
		return acta;
	}

	public void setActa(Acta acta) {
		this.acta = acta;
	}
	
	public Sala getSala() {
		return sala;
	}

	public void setSala(Sala sala) {
		this.sala = sala;
	}

	public Set<Persona> getParticipantes() {
		return participantes;
	}

	//Solo vale con asignacion en cada lado
	/*public void setParticipantes(Set<Persona> participantes) {
		this.participantes = participantes;
	}*/
	
	//Para bidireccionalidad: añadir una persona a una reunion.
	public void addParticipante(Persona participante) {
		//Creación de colección SET participantes (sin duplicados) al crear una instancia nueva Reunion en el constructor
		participantes.add(participante);
		//Para evitar bucle infinito. Comprobar si el participante ya tiene este objeto Reunion
		if(!participante.getReuniones().contains(this)) {
			participante.addReunion(this);
		}
	}
	
	@Override
	public String toString() {
		return "Reunion [id=" + id + ", fecha=" + fecha + ", asunto=" + asunto + "]";
	}
}
