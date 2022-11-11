package com.curso.orm.otrareunionmas.dao;

import java.util.List;
import java.util.Optional;

/*Como vamos a tratar distintos tipos de datos, entidades en cada dato, la haremos parametrizable*/
public interface Dao<T> {

	/*Tipos genéricos para operaciones comunes de cualquier interfaz*/
	/*Recuperar un registro por su ID*/
	Optional<T> get(int id);
	
	/*Devuelve una lista de objetos*/
	List<T> getAll();
	
	/*Guardar en base de datos el objeto recibido*/
	void save(T t);
	
	/*Actualizar el objeto recibido*/
	void update(T t);
	
	/*Eliminar el objeto recibido*/
	void delete(T t);
}
