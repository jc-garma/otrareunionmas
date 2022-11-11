package com.curso.orm.otrareunionmas.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.curso.orm.otrareunionmas.dominio.Reunion;
import com.curso.orm.otrareunionmas.dominio.Sala;

public class SalaDao extends AbstractDao<Sala> {

	public SalaDao() {
		setClazz(Sala.class);
	}
	
	//Operaciones especificas de la entidad sala, para responder a la casuisticas de esta entidad en nuestro negocio
	//Busqueda de salas para un numero de capacidad. Con Query JPQL
	public List<Sala> findSalasParaNViejo(int num){
		String qlString = "FROM " + Sala.class.getName() + " WHERE capacidad >= ?1"; /*Hacer la consulta con una query*/
		Query query = getEntityManager().createQuery(qlString); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la lista de salas con esa capacidad*/
		query.setParameter(1, num); /*Al parametro de posicion 1, le pasamos num*/
		return query.getResultList();
	}
	
	//Busqueda de salas para un numero de capacidad. Con Criteria
	public List<Sala> findSalasParaN(int num) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); /*Al getEntityManager de AbstractDao le pedimos obtener un getCriteriaBuilder*/
		CriteriaQuery<Sala> criteriaQuery = cb.createQuery(Sala.class);
		Root<Sala> root = criteriaQuery.from(Sala.class); /*root es una representaciÃ³n de nuestra entidad, que nos servirÃ¡ para contruir nuestros criterios. Filtramos por clase Sala*/
		
		criteriaQuery.select(root).where(cb.ge(root.get("capacidad"), num)); /*Solo un criterio ge greater or equal que capacidad num */
		Query query = getEntityManager().createQuery(criteriaQuery); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la lista de salas con esa capacidad*/
		return query.getResultList();
	}
	
	//Busqueda de salas con dos o mas criterios. Con Criteria
	public List<Sala> findSalasAdecuadasParaN(int num) {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); /*Al getEntityManager de AbstractDao le pedimos obtener un getCriteriaBuilder*/
		CriteriaQuery<Sala> criteriaQuery = cb.createQuery(Sala.class);
		Root<Sala> root = criteriaQuery.from(Sala.class); /*root es una representaciÃ³n de nuestra entidad, que nos servirÃ¡ para contruir nuestros criterios. Filtramos por clase Sala*/
		
		//Un predicado por cada condición
		Predicate capacidadMinima = cb.ge(root.get("capacidad"), num); /*Criterio corto ge greater or equal que capacidad num, se puede poner como abajo en largo*/
		Predicate capacidadMaxima = cb.lessThanOrEqualTo(root.get("capacidad"), num * 2); /*Criterio largo  menor que or equal que capacidad num * 2, se poodria poner le */
		//Unimos cada dos predicados
		Predicate rangoCapacidad = cb.and(capacidadMinima, capacidadMaxima); /*Unimos ambos criterios con and (tambien existe or)*/
		
		criteriaQuery.select(root).where(rangoCapacidad);
		Query query = getEntityManager().createQuery(criteriaQuery); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la lista de salas con esa capacidad*/
		return query.getResultList();
	}
}
