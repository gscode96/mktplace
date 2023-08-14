package br.com.senai.core.dao;

import java.util.List;

import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;

public interface DaoHorario {
	
	public void inserir(Horario horario);
	
	public void alterar(Horario horario);
	
	public void excluirPor(int id);
	
	public List<Horario> ListarPor(int idRestauranre);
	
	
}
