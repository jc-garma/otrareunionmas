package com.curso.orm.otrareunionmas.dao;

import com.curso.orm.otrareunionmas.dominio.Persona;

public class PersonaDao extends AbstractDao<Persona> {
	
	public PersonaDao() {
		setClazz(Persona.class); /*Informamos de nuestra nueva clase*/
	}
	
	/*Operaciones especificas de la entidad persona, para responder a la casuisticas de esta entidad en nuestro negocio*/
}


