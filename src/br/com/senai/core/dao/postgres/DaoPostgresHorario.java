package br.com.senai.core.dao.postgres;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.RuntimeCryptoException;

import br.com.senai.core.dao.DaoHorario;
import br.com.senai.core.dao.ManagerDb;
import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Endereco;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.util.DiaDaSemana;

public class DaoPostgresHorario implements DaoHorario {

	private final String INSERT = "INSERT INTO horarios_atendimento (dia_semana, hora_abertura, hora_fechamento, id_restaurante) VALUES (?,?,?,?)";
	private final String UPDATE = "UPDATE horarios_atendimento SET dia_semana = ?, hora_abertura = ?, hora_fechamento = ? WHERE id = ?";
	private final String DELETE = "DELETE FROM horarios_atendimento WHERE id = ?";
	private final String SELECT_BY_RES = "SELECT h.id id_horario, h.dia_semana, h.hora_abertura, h.hora_fechamento, r.id id_restaurante, r.nome nome_restaurante, r.descricao, r.cidade, r.logradouro, r.bairro, r.complemento, c.id id_categoria, c.nome nome_categoria FROM horarios_atendimento h inner join restaurantes r on h.id_restaurante = r.id inner join categorias c on r.id_categoria = c.id  where h.id_restaurante = ?";

	private Connection conexao;

	public DaoPostgresHorario() {
		this.conexao = ManagerDb.getInstance().getConexao();

	}

	@Override
	public void inserir(Horario horario) {
		PreparedStatement ps = null;

		try {
			ps = conexao.prepareStatement(INSERT);
			ps.setString(1, horario.getDia_semana().toString());
			ps.setTime(2, Time.valueOf(horario.getHora_abertura()));
			ps.setTime(3, Time.valueOf(horario.getHora_fechamento()));
			ps.setInt(4, horario.getRestaurante().getId());
			ps.execute();
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao inserrir o horario. Motivo: " + e.getMessage());
		} finally {
			ManagerDb.getInstance().fechar(ps);
		}

	}

	@Override
	public void alterar(Horario horario) {
		PreparedStatement ps = null;

		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(UPDATE);
			ps.setString(1, horario.getDia_semana().toString());
			ps.setTime(2, Time.valueOf(horario.getHora_abertura()));
			ps.setTime(3, Time.valueOf(horario.getHora_fechamento()));
			ps.setInt(4, horario.getRestaurante().getId());
			ps.setInt(5, horario.getId());

			boolean isUpdateOK = ps.executeUpdate() == 1;
			if (isUpdateOK) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao realizar a alteração. " + e.getMessage());
		}
	}

	@Override
	public void excluirPor(int id) {
		PreparedStatement ps = null;
		try {
			ManagerDb.getInstance().configurarAutocommitDa(conexao, false);
			ps = conexao.prepareStatement(DELETE);
			ps.setInt(1, id);
			boolean isDeleteOK = ps.executeUpdate() == 1;
			if (isDeleteOK) {
				this.conexao.commit();
			} else {
				this.conexao.rollback();
			}
			ManagerDb.getInstance().configurarAutocommitDa(conexao, true);
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao realizar a exclussão. " + e.getMessage());
		}
	}

	@Override
	public List<Horario> ListarPor(int idRestaurante) {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Horario> list = new ArrayList<Horario>();
		try {
			ps = conexao.prepareStatement(SELECT_BY_RES);
			ps.setInt(1, idRestaurante);
			rs = ps.executeQuery();
			while (rs.next()) {
				list.add(extrairDo(rs));
			}
		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao listar os horarios!" + e.getMessage());
		}

		return list;
	}

	private Horario extrairDo(ResultSet rs) {
		try {
			int idHorario = rs.getInt("id_horario");
			String diaDaSemana = rs.getString("dia_semana");
			String dataAbertura = rs.getString("hora_abertura");
			String dataFechamento = rs.getString("hora_fechamento");
			int idDoRestaurante = rs.getInt("id_restaurante");

			String nomeDoRestaurante = rs.getString("nome_restaurante");
			String descricao = rs.getString("descricao");
			String cidade = rs.getString("cidade");
			String logradouro = rs.getString("logradouro");
			String bairro = rs.getString("bairro");
			String complemento = rs.getString("complemento");

			int idDaCategoria = rs.getInt("id_categoria");
			String nomeDaCategoria = rs.getString("nome_categoria");
			Endereco endereco = new Endereco(cidade, logradouro, bairro, complemento);
			Categoria categoria = new Categoria(idDaCategoria, nomeDaCategoria);
			Restaurante restaurante = new Restaurante(idDoRestaurante, nomeDoRestaurante, descricao, endereco,
					categoria);

			return new Horario(idHorario, DiaDaSemana.valueOf(diaDaSemana), LocalTime.parse(dataAbertura),
					LocalTime.parse(dataFechamento), restaurante);

		} catch (Exception e) {
			throw new RuntimeException("Ocorreu um erro ao extrair o restaurante. Motivo:" + e.getMessage());
		}

	}

}
