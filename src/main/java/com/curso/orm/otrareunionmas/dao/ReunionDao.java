package com.curso.orm.otrareunionmas.dao;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.curso.orm.otrareunionmas.dominio.Persona;
//import com.curso.orm.otrareunionmas.dominio.Persona;
import com.curso.orm.otrareunionmas.dominio.Reunion;

/* Hereda de la clase AbstractDao<T>. Ha llegado el momento de concretar, sustituir la T genérica por una clase concreta
 * a la que queremos hacerle el Dao 
 * 
 * public class ReunionDao extends AbstractDao<T> {
 * }
 * 
 * */

/* ReunionDao hereda las operaciones básicas de AbstractDao<T> que utilizan todas las entidades. 
 * Pero hay que poner las operaciones específicas también*/
public class ReunionDao extends AbstractDao<Reunion> {
	
	public ReunionDao() {
		setClazz(Reunion.class); /*Informamos de nuestra nueva clase a la clase padre AbstractDao*/
	}
	
	/*Operaciones especificas de la entidad Reunion, para responder a la casuisticas de esta entidad en nuestro negocio*/
	public Reunion proximaReunion() {
		String qlString = "FROM " + Reunion.class.getName() + " WHERE fecha > now() order by fecha "; /*FROM nombre-de-la-tabla*/
		Query query = getEntityManager().createQuery(qlString).setMaxResults(1); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la proxima reunion, como solo queremos la primera consulta mas cercana setMaxResults(1)*/
		return (Reunion) query.getSingleResult();
	}
	
	public List<Reunion> reunionesManana() {
		String qlString = "FROM " + Reunion.class.getName() + " WHERE fecha between ?1 and ?2"; /*Hacer la consulta con una query de dos parÃ¡metros*/
		Query query = getEntityManager().createQuery(qlString); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la lista de reuniones para maÃ±ana*/
		LocalDate manana = LocalDate.now().plus(1, ChronoUnit.DAYS); /*Obtengo maÃ±ana*/
		query.setParameter(1, manana.atStartOfDay()); /*Al parametro de posicion 1, le decimos que desde las 00:00 de hoy*/
		query.setParameter(2, manana.plus(1, ChronoUnit.DAYS).atStartOfDay()); /*Al parametro de posicion 2, le decimos que desde las 00:00 de maÃ±ana*/
		return query.getResultList();
	}
	
	/*Listar las reuniones de una persona por numeroEmpleado. Con Criteria. Persona-Reunion-PersonaReunion*/
	public List<Reunion> reunionesParticipante(String numEmple) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Reunion> criteriaQuery = cb.createQuery(Reunion.class);
		
		Root<Persona> fromPersona = criteriaQuery.from(Persona.class); /*Filtrar por clase Persona*/
		criteriaQuery.where(cb.equal(fromPersona.get("numeroEmpleado"), numEmple));
		
		Join<Persona, Reunion> joinReunion = fromPersona.join("reuniones", JoinType.INNER);/*Inner Join (Con ese root a partir del id_reunion FK de Acta acceder a Reunion)*/
		
		CriteriaQuery<Reunion> cq = criteriaQuery.multiselect(joinReunion); /*Multiselect de la anterior*/
		TypedQuery<Reunion> query = getEntityManager().createQuery(cq);
		return query.getResultList();
	}
}
