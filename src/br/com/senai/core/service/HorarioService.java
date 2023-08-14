package br.com.senai.core.service;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.util.DiaDaSemana;

public class HorarioService {

	private DaoHorario dao;

	public HorarioService() {
		this.dao = FactoryDao.getInstance().getDaoHorario();
	}

	public void salvar(Horario horario) {
		this.validar(horario);
		this.validarHorario(horario);

		boolean isJaSalvo = horario.getId() > 0;
		if (!isJaSalvo) {

			try {
				this.dao.inserir(horario);
				JOptionPane.showMessageDialog(null, "Horario salvo com sucesso");
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao salvar o horario: " + e2.getMessage());
			}

		}

	}

	public void alterar(Horario horario) {
		this.validar(horario);
		this.validarHorario(horario);
		boolean isJaSalvo = horario.getId() > 0;
		if (isJaSalvo) {
			try {
				this.dao.alterar(horario);
				JOptionPane.showMessageDialog(null, "Horario alterado com sucesso");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Ocorreu um erro ao alterar o horario: " + e.getMessage());
			}

		}

	}

	public void excluirPor(int id) {
		if (id > 0) {
			this.dao.excluirPor(id);
		} else {
			throw new IllegalArgumentException("Não foi encontrado horario para o codigo informado");
		}

	}

	public void validar(Horario horario) {
		if (horario != null) {
			if (horario.getRestaurante() != null && horario.getDia_semana() != null) {
				String abertura = horario.getHora_abertura().toString();
				boolean isAberturaOK = horario.getHora_abertura() != null || abertura.length() > 5
						|| !horario.getHora_abertura().isAfter(horario.getHora_fechamento());
				String fechamento = horario.getHora_fechamento().toString();
				boolean isFechamentoOK = horario.getHora_fechamento() != null || fechamento.length() > 5
						|| !horario.getHora_fechamento().isBefore(horario.getHora_abertura());

				boolean isHorarioAberOK = horario.getHora_abertura().isAfter(horario.getHora_fechamento());

				if (!isAberturaOK) {
					throw new IllegalArgumentException(
							"O horario de abertura deve ser valido e conter o formato 00:00!");
				}
				if (!isFechamentoOK) {
					throw new IllegalArgumentException(
							"O horario de fechamento deve ser valido e conter o formato 00:00!");
				}
				if (isHorarioAberOK) {
					throw new IllegalArgumentException(
							"O horario de abertura deve ser menor que o horario de fechamento!");
				}

			} else {
				throw new NullPointerException("O restaurante e dia da semana não pode ser vazio!");

			}

		} else {

			throw new NullPointerException("O horario esta nulo!");
		}

	}

	public List<Horario> listarPor(Restaurante restaurante) {

		return dao.ListarPor(restaurante.getId());

	}

	public void validarHorario(Horario horario) {
		LocalTime horarioAlteradoAbert;
		LocalTime horarioAlteradoFecha;
		DiaDaSemana diaAlterado;
		horarioAlteradoAbert = horario.getHora_abertura();
		horarioAlteradoFecha = horario.getHora_fechamento();
		diaAlterado = horario.getDia_semana();
		List<Horario> list = new ArrayList<Horario>();

		list = this.dao.ListarPor(horario.getRestaurante().getId());
		if (!list.isEmpty()) {
			Horario horarioSalvo;

			for (Horario horariolist : list) {
				horarioSalvo = horariolist;
				if (horariolist.getDia_semana() == diaAlterado) {

					if (horarioAlteradoAbert.isAfter(horarioSalvo.getHora_abertura())
							&& diaAlterado == horario.getDia_semana()) {

						throw new IllegalArgumentException("O horario de abertura está contido no horario salvo. ");

					}

					if (horarioAlteradoFecha.isBefore(horarioSalvo.getHora_fechamento())
							&& diaAlterado == horarioSalvo.getDia_semana()) {

						throw new IllegalArgumentException("O horario de fechamento está contido no horario salvo. ");

					}

					if (horarioAlteradoAbert.isBefore(horarioSalvo.getHora_abertura())
							&& horarioAlteradoFecha.isAfter(horarioSalvo.getHora_fechamento())
							&& diaAlterado == horarioSalvo.getDia_semana()) {

						throw new IllegalArgumentException(
								"O horario de abertura e fechamento ja contem no horario salvo, edite o horario !");

					}

					if (horarioAlteradoAbert.isAfter(horarioSalvo.getHora_abertura())
							&& horarioAlteradoFecha.isBefore(horarioSalvo.getHora_fechamento())
							&& diaAlterado == horarioSalvo.getDia_semana()) {

						throw new IllegalArgumentException(
								"O horario de abertura e fechamento ja contem no horario salvo, edite o horario !");

					}

				}
			}

		}

	}

}
