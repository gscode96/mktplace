package br.com.senai.core.domain;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.Objects;

import br.com.senai.util.DiaDaSemana;

public class Horario {

	private int id;
	private DiaDaSemana dia_semana;
	private LocalTime hora_abertura;
	private LocalTime hora_fechamento;
	private Restaurante restaurante;
	

	public Horario(int id, DiaDaSemana dia_semana, LocalTime hora_abertura, LocalTime hora_fechamento,
			Restaurante restaurante) {

		this(dia_semana, hora_abertura, hora_fechamento, restaurante);
		this.id = id;

	}

	public Horario(DiaDaSemana dia_semana, LocalTime hora_abertura, LocalTime hora_fechamento,
			Restaurante restaurante) {

		this.dia_semana = dia_semana;
		this.hora_abertura = hora_abertura;
		this.hora_fechamento = hora_fechamento;
		this.restaurante = restaurante;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Restaurante getRestaurante() {
		return restaurante;
	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
	}

	public DiaDaSemana getDia_semana() {
		return dia_semana;
	}

	public LocalTime getHora_abertura() {
		return hora_abertura;
	}

	public LocalTime getHora_fechamento() {
		return hora_fechamento;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Horario other = (Horario) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return restaurante.getNome() + ": Data- " + getDia_semana() + "Horario: " + getHora_abertura()
				+ getHora_fechamento();
	}

	public void setDia_semana(DiaDaSemana dia_semana) {
		this.dia_semana = dia_semana;
	}

	public void setHora_abertura(LocalTime hora_abertura) {
		this.hora_abertura = hora_abertura;
	}

	public void setHora_fechamento(LocalTime hora_fechamento) {
		this.hora_fechamento = hora_fechamento;
	}

}
