package br.com.senai;

import java.sql.Date;
import java.time.LocalTime;
import java.time.temporal.ChronoField;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Endereco;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioService;
import br.com.senai.util.DiaDaSemana;
import br.com.senai.view.componentes.table.T5_Principal;

public class Principal {

	public static void main(String[] args) {
		new T5_Principal().setVisible(true);

	}

}
