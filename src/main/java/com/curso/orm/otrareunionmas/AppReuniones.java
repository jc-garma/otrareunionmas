package com.curso.orm.otrareunionmas;

import com.curso.orm.otrareunionmas.dao.ReunionDao;

public class AppReuniones {

	public static void main(String[] args) {
		ReunionDao reunionDao = new ReunionDao();
		System.out.println("Reuniones de Pedro: " + reunionDao.reunionesParticipante("E002"));
	}
}
