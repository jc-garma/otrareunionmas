package com.curso.orm.otrareunionmas.utiles;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {

	/*Método estático que no recibe nada, solo devuelve objeto EntityManager. Para hacer conexiones con la base de dados*/
	public static EntityManager getEntityManager() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("OtraReunionMas"); /*persistence-unit del persistence.xml*/
		EntityManager manager = factory.createEntityManager();
		return manager;
	}
	
	public static void main(String[] args) {
		EntityManager manager = EntityManagerUtil.getEntityManager();
		System.out.println("EntityManager class ==> " + manager.getClass().getCanonicalName());
	}
}
