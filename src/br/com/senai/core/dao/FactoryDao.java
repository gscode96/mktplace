package br.com.senai.core.dao;

import br.com.senai.core.dao.postgres.DaoPostgresCategoria;
import br.com.senai.core.dao.postgres.DaoPostgresHorario;
import br.com.senai.core.dao.postgres.DaoPostgresRestaurante;

public class FactoryDao {

	private static FactoryDao instance;

	private FactoryDao() {
	}

	// se precisar trocar o banco so muda o return
	public DaoCategoria getDaoCategoria() {
		return new DaoPostgresCategoria();

	}

	public DaoRestaurante getDaoRestaurante() {
		return new DaoPostgresRestaurante();

	}

	public DaoHorario getDaoHorario() {
		return new DaoPostgresHorario();

	}

	public static FactoryDao getInstance() {
		if (instance == null) {
			instance = new FactoryDao();

		}

		return instance;
	}

}
