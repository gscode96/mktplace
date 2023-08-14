package br.com.senai.core.service;

import java.util.List;

import javax.swing.JOptionPane;

import br.com.senai.core.dao.DaoRestaurante;
import br.com.senai.core.dao.FactoryDao;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;

public class RestauranteService {

	private DaoRestaurante dao;

	public RestauranteService() {

		this.dao = FactoryDao.getInstance().getDaoRestaurante();

	}

	public void salvar(Restaurante restaurante) {

		this.validar(restaurante);
		boolean isJaSalvo = restaurante.getId() > 0;
		if (isJaSalvo) {
			this.dao.alterar(restaurante);
			JOptionPane.showMessageDialog(null, "Restaurante alterado com sucesso!");
		} else {
			this.dao.inserir(restaurante);
			JOptionPane.showMessageDialog(null, "Restaurante salvo com sucesso!");
		}

	}

	public void removerPor(int id) {
		if (id > 0) {
			this.dao.excluirPor(id);
		} else {
			throw new IllegalArgumentException("Não foi encontrado restaurante para o codigo informado.");
		}

	}

	public Restaurante buscarPor(int id) {
		if (id > 0) {
			Restaurante restauranteEncontrado = this.dao.buscarPor(id);
			if (restauranteEncontrado == null) {
				throw new IllegalArgumentException("Não foi encotrando restaurante para o codigo informado.");
			}
			return restauranteEncontrado;
		} else {
			throw new IllegalArgumentException("O id do restaurante deve ser maior que zero");
		}

	}

	public List<Restaurante> listarPor(String nome, Categoria categoria) {

		boolean isCategoriaInformada = categoria != null && categoria.getId() > 0;

		boolean isNomeInformado = nome != null && !nome.isBlank();

		if (!isCategoriaInformada && !isNomeInformado) {
			throw new IllegalArgumentException("Informe o nome e/ou categoria para a listagem");
		}
		String filtroNome = "";

		if (isCategoriaInformada) {
			filtroNome = nome + "%";
		} else {
			filtroNome = "%" + nome + "%";
		}

		return dao.listarPor(filtroNome, categoria);
	}

	public List<Restaurante> listarTodos() {
		return dao.listarPor("%%", null);

	}

	private void validar(Restaurante restaurante) {
		// metodo para validar os campos antes de salvar no banco
		if (restaurante != null) {

			if (restaurante.getEndereco() != null) {

				if (restaurante.getCategoria() != null && restaurante.getCategoria().getId() > 0) {
					boolean isNomeValido = restaurante.getNome() == null || restaurante.getNome().isBlank()
							|| restaurante.getNome().length() > 250;
					boolean isDescValido = restaurante.getNome() == null || restaurante.getDescricao().isBlank();
					boolean isCidValido = restaurante.getEndereco().getCidade() == null
							|| restaurante.getEndereco().getCidade().isBlank()
							|| restaurante.getEndereco().getCidade().length() > 80;
					boolean isLogValido = restaurante.getEndereco().getLogradouro() == null
							|| restaurante.getEndereco().getLogradouro().isBlank()
							|| restaurante.getEndereco().getLogradouro().length() > 200;
					boolean isBairValido = restaurante.getEndereco().getBairro() == null
							|| restaurante.getEndereco().getBairro().isBlank()
							|| restaurante.getEndereco().getBairro().length() > 250;
					boolean isCategoriaValido = restaurante.getCategoria() == null;

					// outra forma de fazer o if
					// String nomeDoBairro = restaurante.getEndereco() != null ?
					// restaurante.getEndereco().getBairro() : "";
					// boolean isbairrovalido = nomeDoBairro.isBlank() || nomeDoBairro.length() >
					// 50;
					if (isNomeValido) {
						throw new IllegalArgumentException("O nome deve ser valido e conter no maximo 200 caracteres.");
					}

					if (isDescValido) {
						throw new IllegalArgumentException("A descrição deve ser valida");
					}
					if (isCidValido) {
						throw new IllegalArgumentException("A cidade deve ser valida e conter no maximo 80 caracteres");
					}

					if (isLogValido) {
						throw new IllegalArgumentException(
								"O logradouro deve ser valido e conter no maximo 200 caracteres");
					}

					if (isBairValido) {
						throw new IllegalArgumentException(
								"O bairro deve ser valido e conter no maximo 250 caracteres");
					}
					if (isCategoriaValido) {
						throw new IllegalArgumentException("A categoria não pode ser nula");
					}

				} else {
					throw new IllegalArgumentException("A categoria do restaurante é obrigatoria.");

				}

			} else {
				throw new NullPointerException("O endereço do restaurante não pode ser nulo.");
			}

		} else {
			throw new NullPointerException("O restaurante nao pode ser nulo");

		}

	}

}
