package com.curso.orm.otrareunionmas.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.curso.orm.otrareunionmas.dominio.Acta;
import com.curso.orm.otrareunionmas.dominio.Reunion;

public class ActaDao extends AbstractDao<Acta> {

	public ActaDao() {
		setClazz(Acta.class);
	}
	
	/*Operaciones especificas de la entidad acta, para responder a la casuisticas de esta entidad en nuestro negocio*/
	//Listar actas de reuniones pasadas, de mas de 24 horas. Con Query JPQL
	public List<Acta> findActasReunionesAntiguasQuery() {
		/* Las query de SQl no se manejan igual que en JPA (JPQL), con las fechas. JPA INNER JOIN equivale a a.reunion.fecha 
		 * Hacemos Inner Join
		 * */
		String qlString = "FROM " + Acta.class.getName() + " a WHERE a.reunion.fecha < :ayer"; /*?1 equivale con nombre a :ayer*/

		Query query = getEntityManager().createQuery(qlString); /*EntityManager crea la query con la consulta y a esa query le pedimos obtener la lista de actas de reuniones pasadas*/
		query.setParameter("ayer", LocalDateTime.now().minusDays(1)); /*Al parametro ayer, le pasamos la fecha de ayer*/
		return query.getResultList();
	}
	
	//Listar actas de reuniones pasadas, de mas de 24 horas. Con Criteria
	public List<Acta> findActasReunionesAntiguasCriteria() {
		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder(); /*Al getEntityManager de AbstractDao le pedimos obtener un getCriteriaBuilder*/
		CriteriaQuery<Acta> criteriaQuery = cb.createQuery(Acta.class);
		Root<Acta> rootActa = criteriaQuery.from(Acta.class); /*root es una representaciÃ³n de nuestra entidad, que nos servirÃ¡ para contruir nuestros criterios*/
		
		Join<Acta, Reunion> joinReunion = rootActa.join("reunion", JoinType.INNER);/*Inner Join (Con ese root a partir de el id_reunion FK de Acta acceder a Reunion)*/
		
		Predicate fechaAyer = cb.lessThan(joinReunion.get("fecha"), LocalDateTime.now().minusDays(1));/*Solo un criterio lessThan que un dÃ­a menos de la fecha de reunion que obtenemos. NO le porque no comapramos con nÃºmero */
		
		criteriaQuery.where(fechaAyer);
		Query query = getEntityManager().createQuery(criteriaQuery);
		return query.getResultList();
	}
}
