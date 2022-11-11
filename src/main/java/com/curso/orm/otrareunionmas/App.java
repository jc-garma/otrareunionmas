package com.curso.orm.otrareunionmas;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.NoResultException;

import com.curso.orm.otrareunionmas.dao.ActaDao;
//import com.curso.orm.otrareunionmas.dao.ActaDao;
import com.curso.orm.otrareunionmas.dao.ReunionDao;
import com.curso.orm.otrareunionmas.dao.SalaDao;
import com.curso.orm.otrareunionmas.dominio.Acta;
import com.curso.orm.otrareunionmas.dominio.Persona;
//import com.curso.orm.otrareunionmas.dao.SalaDao;
//import com.curso.orm.otrareunionmas.dominio.Acta;
//import com.curso.orm.otrareunionmas.dominio.Persona;
import com.curso.orm.otrareunionmas.dominio.Reunion;
import com.curso.orm.otrareunionmas.dominio.Sala;
//import com.curso.orm.otrareunionmas.dominio.Sala;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		/*Vaciar la base de datos antes, drop tables. Cuidado al usar truncate tables tener cuidado el orden al hacerse porque tenemos claves foráneas*/
		// DAOs
		ReunionDao reunionDao = new ReunionDao();
		ActaDao actaDao = new ActaDao();
		SalaDao salaDao = new SalaDao();

		// Creación de objetos
		//Ids generados a mano
		Sala s099 = new Sala("S099", "Trastero", 1);
		Sala s101 = new Sala("S101", "Reunión primera planta", 10);
		Sala s109 = new Sala("S109", "Entrevistas primera planta", 3);
		Sala s203 = new Sala("S203", "Sala grande", 25);

		salaDao.save(s099);
		salaDao.save(s101);
		salaDao.save(s109);
		salaDao.save(s203);

		Persona marta = new Persona("E001", "Marta", "García López"); //Ids generados a mano. Cambiar numeroEmpleado E0001 y E0002 cada vez que se ejecuta para que no se repitan y salte un error
		Persona pedro = new Persona("E002", "Pedro", "Gómez Fernández");
		Persona santi = new Persona("E003", "Santi", "Pérez Pérez");
		Persona luisa = new Persona("E004", "Luisa", "Gutiérrez González");

		// LocalDateTime porque las reuniones tienen hora. Ids autogenerados
		Reunion r0 = new Reunion(LocalDateTime.now(), "Reunión de Test");
		Reunion r1 = new Reunion(LocalDateTime.now().plus(2, ChronoUnit.HOURS), "Otra Reunión de Test");
		Reunion r2 = new Reunion(LocalDateTime.now().plus(2, ChronoUnit.DAYS), "Reunión de pasado mañana");
		Reunion r3 = new Reunion(LocalDateTime.now().plus(1, ChronoUnit.DAYS), "Reunión de mañana");
		Reunion r4 = new Reunion(LocalDateTime.now().minus(1, ChronoUnit.DAYS), "Reunión de ayer");

		/*Se lo paso al addParticipante de Reunion que tiene un DAO generado y los
		 * añado a cada uno sin necesidad de DAO de Persona.
		 */
		r0.addParticipante(marta);
		r0.setSala(s099);
		reunionDao.save(r0);
		Acta a0 = new Acta("Marta se reúne sola, solo para descansar un rato", r0); //Tiene acta porque es de hoy
		actaDao.save(a0);
		reunionDao.update(r0);

		r1.addParticipante(marta);
		r1.addParticipante(pedro);
		r1.addParticipante(santi);
		r1.addParticipante(luisa);
		r1.setSala(s101);
		reunionDao.save(r1);

		r2.addParticipante(pedro);
		r2.addParticipante(santi);
		r2.setSala(s109);
		reunionDao.save(r2);

		r3.addParticipante(marta);
		r3.addParticipante(luisa);
		r3.setSala(s109);
		reunionDao.save(r3);

		r4.addParticipante(marta);
		r4.addParticipante(pedro);
		r4.addParticipante(santi);
		r4.addParticipante(luisa);
		r4.setSala(s203);
		reunionDao.save(r4);

		Acta a4 = new Acta("Participantes: M. García, P. Gómez, S. Pérez y L. Gutierrez. Duración: 1h. "
				+ "Se reunieron en la sala 203 para preparar el lanzamiento de la aplicación "
				+ "\"Otra Reunión Más\".", r4); //Tiene acta porque es de ayer
		actaDao.save(a4);
		reunionDao.update(r4);

		// Recuperación de datos
		List<Reunion> reuniones = reunionDao.getAll();
		System.out.println("*** " + reuniones);

		try {
			System.out.println("Tu próxima reunión es: " + reunionDao.proximaReunion());
		} catch (NoResultException nre) {
			System.out.println("No tienes ninguna reunión a la vista");
		}
		List<Reunion> reunionesManyana = reunionDao.reunionesManana();
		System.out.println("Para mañana: " + reunionesManyana);
			
		/***********************************PRUEBAS***********************************/
		/*ReunionDao dao = new ReunionDao();
		List<Reunion> reuniones = dao.getAll();
		System.out.println("*** " + reuniones);*/
		
		// NO DESCOMENTAR
		//Persona marta = new Persona("E001e", "Marta", "García López"); //Ids generados a mano. Cambiar claves E0001 y E0002 cada vez que se ejecuta para que no se repitan y salte un error
		//Persona pedro = new Persona("E002f", "Pedro", "Gómez Fernández");
		
		//Set<Persona> equipo = new HashSet();
		//equipo.add(marta);
		//equipo.add(pedro);
		
		//Reunion r = new Reunion(LocalDateTime.now(), "Reunión de Test");
		//System.out.println(r);
		
		//r.setParticipantes(equipo); //bidireccional añadimos a Reunion las personas
		//dao.save(r);
		//System.out.println(r);
		// HASTA AQUÍ
		
		/*Persona marta = new Persona("E001e", "Marta", "García López"); //Ids generados a mano. Cambiar claves E0001 y E0002 cada vez que se ejecuta para que no se repitan y salte un error
		Persona pedro = new Persona("E002f", "Pedro", "Gómez Fernández");
		
		Reunion r = new Reunion(LocalDateTime.now(), "Reunión de Test");
		System.out.println(r);
				
		dao.save(r);
		System.out.println(r);*/
		
		//Asignamos un acta a la reunión r
		/*ActaDao actaDao = new ActaDao();
		Acta a = new Acta("Reunión anulada", r);
		actaDao.save(a);
		
		Reunion r1 = new Reunion(LocalDateTime.now(), "Otra Reunión de Test");
		dao.save(r1);*/
		
		// NO DESCOMENTAR
		//Set<Reunion> reunionesMarta = new HashSet();
		//reunionesMarta.add(r);
		//marta.setReuniones(reunionesMarta); //bidireccional añadimos a Persona reuniones
		
		//Set<Reunion> reunionesPedro = new HashSet();
		//reunionesPedro.add(r);
		//reunionesPedro.add(r1);
		//pedro.setReuniones(reunionesPedro);
		//HASTA AQUÍ
		
		/*pedro.addReunion(r1);
		marta.addReunion(r);
		pedro.addReunion(r);
		
		Reunion r2 = new Reunion(LocalDateTime.now().plus(2, ChronoUnit.DAYS), "Reunión de pasado mañana");
		dao.save(r2);
		
		try {
			System.out.println("Tu próxima reunión es: " + dao.proximaReunion());
		} catch (NoResultException nre) {
			System.out.println("No tienes ninguna reunión a la vista");
		}
		
		Reunion r3= new Reunion(LocalDateTime.now().plus(1, ChronoUnit.DAYS), "Reunión de mañana");
		dao.save(r3);*/
		
		/*List<Reunion> reunionesManana = dao.reunionesManana();
		System.out.println("Para mañana: " + reunionesManana);
		
		SalaDao salaDao = new SalaDao();
		Sala s = new Sala("S03", "Sala grande", 25);
		salaDao.save(s);
		
		System.out.println("Paso 1: " + salaDao.getAll());
		
		s.setDescripcion("Sala grande reformada");
		salaDao.update(s);
		
		System.out.println("Paso 2: " + salaDao.getAll());
		
		Sala s2 = new Sala("99", "Trastero", 1);
		salaDao.save(s2);
		
		System.out.println("Paso 3: " + salaDao.getAll());
		
		salaDao.delete(s2);
		
		System.out.println("Paso 4: " + salaDao.getAll());*/	
	}
}
