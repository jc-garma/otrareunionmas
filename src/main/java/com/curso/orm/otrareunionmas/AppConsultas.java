package com.curso.orm.otrareunionmas;

import java.util.List;

import com.curso.orm.otrareunionmas.dao.SalaDao;
import com.curso.orm.otrareunionmas.dominio.Sala;

public class AppConsultas {

	public static void main(String[] args) {	
		SalaDao salaDao = new SalaDao();
		
		List<Sala> salasPara4 = salaDao.findSalasParaN(4);
		System.out.println("Salas para 4: " + salasPara4);
		
		List<Sala> salasPara3 = salaDao.findSalasParaN(3);
		System.out.println("Salas para 3: " + salasPara3);
		
		List<Sala> salasAdecuadasPara3 = salaDao.findSalasAdecuadasParaN(3);
		System.out.println("Salas adecuadas para 3: " + salasAdecuadasPara3);
	}

}
