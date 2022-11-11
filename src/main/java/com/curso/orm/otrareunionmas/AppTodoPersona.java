package com.curso.orm.otrareunionmas;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.curso.orm.otrareunionmas.dao.PersonaDao;
import com.curso.orm.otrareunionmas.dominio.Acta;
import com.curso.orm.otrareunionmas.dominio.Persona;
import com.curso.orm.otrareunionmas.dominio.Reunion;
import com.curso.orm.otrareunionmas.dominio.Sala;

public class AppTodoPersona {

	public static void main(String[] args) {
		PersonaDao personaDao = new PersonaDao();

		//Recuperar el objeto persona con id 1
		Optional<Persona> optPersona = personaDao.get(1);
		if (optPersona.isPresent()) {
			Persona p = optPersona.get();
			System.out.println("Persona: " + p);

			Set<Reunion> reuniones = p.getReuniones();
			System.out.println("Reuniones: " + reuniones);

			Set<Sala> salas = new HashSet();
			Set<Persona> compis = new HashSet(); //Lista de compañeros que estan en las reuniones con Marta
			Set<Acta> actas = new HashSet();
			for (Reunion reunion : reuniones) { //Por cada una de las reuniones en las que participa Marta
				salas.add(reunion.getSala());
				compis.addAll(reunion.getParticipantes());
				actas.add(reunion.getActa());
			}
			System.out.println("Salas: " + salas);
			System.out.println("Compis: " + compis);
			System.out.println("Actas: " + actas);
		}
	}
}
