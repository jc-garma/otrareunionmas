package com.curso.orm.otrareunionmas.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.curso.orm.otrareunionmas.utiles.EntityManagerUtil;

/*Para los metodos dao no poner abstract en la clase y generarlos automaticamente con el error, luego poner abstract*/
public abstract class AbstractDao<T> implements Dao<T> {
	
	/*Para acceder a los datos de la base de datos a través de Hibernate*/
	private EntityManager entityManager = EntityManagerUtil.getEntityManager();
	
	/*Clase generica de la entidad, la tabla*/
	private Class<T> clazz;
	
	@Override
	public Optional<T> get(int id) {
		return Optional.ofNullable(getEntityManager().find(clazz, id)); /*Nos devuelve el registro que estamos buscando, como SELECT. Utilizamos ofNullable para que se maneje bien en caso de que no encuentre nada*/
	}

	@Override
	public List<T> getAll() {
		String qlString = "FROM " + clazz.getName(); /*FROM nombre-de-la-tabla*/
		Query query = entityManager.createQuery(qlString); /*EntityManager crea la query y a esa query le pedimos la lista de resultados*/
		return query.getResultList();
	}

	/*Metodos Dao de modificación de base de datos*/
	@Override
	public void save(T t) {
		executeInsideTransaction(entityManager -> entityManager.persist(t)); /*Al EntityManager le pedimos que haga persistir el objeto T recibido, pasandoselo al metodo gestor de las transacciones*/
	}

	@Override
	public void update(T t) {
		executeInsideTransaction(entityManager -> entityManager.merge(t));
	}

	@Override
	public void delete(T t) {
		executeInsideTransaction(entityManager -> entityManager.remove(t));
		
	}
	
	/*Metodo de gestión de transacciones*/
	private void executeInsideTransaction(Consumer<EntityManager> action) {
		EntityTransaction tx = entityManager.getTransaction(); /*Le pedimos una trasaccion al EntityManager*/
		try {
			tx.begin(); /*Iniciamos la trasacción*/
			action.accept(entityManager); /*Realizamos la acción correspondiente*/
			tx.commit(); /*Hacemos commit o guardado de la trasacción*/
		} catch (RuntimeException e) {
			tx.rollback(); /*Si hay algun problema deshacemos*/
			throw e; /*Lanzamos exception*/
		}
	}

	/*Getters y Setters*/
	public void setClazz(Class<T> clazz) {
		this.clazz = clazz;
	}

	/*para obtener el EntityManager desde una EntidadDao, porque es una clase static*/
	public EntityManager getEntityManager() {
		return entityManager;
	}	
}
