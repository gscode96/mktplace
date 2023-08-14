package br.com.senai.view.componentes.table;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.HorarioService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.util.DiaDaSemana;
import br.com.senai.util.MascaraTexto;
import br.com.senai.view.componentes.tableModel.HorarioTableModel;

import javax.swing.JTable;

public class T6_CadastroHorario extends JFrame {

	private JPanel contentPane;
	private JTextField textAbertura = new JTextField();
	private JTextField textFechamento = new JTextField();

	private RestauranteService rs = new RestauranteService();
	JComboBox<Restaurante> comboBoxRestaurante = new JComboBox();
	JComboBox<DiaDaSemana> comboBoxDiaDaSemana = new JComboBox();
	LocalTime horaAber;
	LocalTime horaFech;
	HorarioService hs = new HorarioService();
	private List<Horario> list = new ArrayList<Horario>();
	private JScrollPane spTable;
	private JTable tableHorario;
	HorarioTableModel table;
	private Horario horario = null;

	public void carregarComboRestaurante() {
		List<Restaurante> list = rs.listarTodos();
		for (Restaurante restaurante : list) {
			this.comboBoxRestaurante.addItem(restaurante);
		}

	}

	public void carregarComboDiaSemana() {
		List<DiaDaSemana> list = Arrays.asList(DiaDaSemana.values());
		for (DiaDaSemana diaDaSemana : list) {
			this.comboBoxDiaDaSemana.addItem(diaDaSemana);
		}
	}

	public T6_CadastroHorario() {

		setTitle("Gerenciar Horários - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 690, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JPanel panel_Acoes = new JPanel();
		panel_Acoes.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Acoes.setToolTipText("");
		panel_Acoes.setBounds(453, 123, 211, 78);
		contentPane.add(panel_Acoes);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setBounds(39, 5, 138, 23);

		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selecao = tableHorario.getSelectedRow();
				if (selecao >= 0) {
					HorarioTableModel modelHora = (HorarioTableModel) tableHorario.getModel();
					Horario horarioSelecao = modelHora.getPor(selecao);
					horario = horarioSelecao;

					textAbertura.setText(horarioSelecao.getHora_abertura().toString());

					textFechamento.setText(horarioSelecao.getHora_fechamento().toString());
					comboBoxDiaDaSemana.setSelectedItem(horarioSelecao.getDia_semana().toString());

				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um horario para editar!");
				}

			}
		});

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(39, 39, 138, 23);
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selecao = tableHorario.getSelectedRow();
				if (selecao >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja remover o registro selecionado?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						HorarioTableModel modelHora = (HorarioTableModel) tableHorario.getModel();
						Horario horarioSelecao = modelHora.getPor(selecao);
						try {
							modelHora.removerPor(selecao);
							hs.excluirPor(horarioSelecao.getId());
							tableHorario.updateUI();
							JOptionPane.showMessageDialog(contentPane, "Horario removido com sucesso!");
						} catch (Exception e2) {
							throw new RuntimeException("Ocorreu um erro ao excluir.");
						}
					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um horario para excluir!");
				}
			}
		});
		panel_Acoes.setLayout(null);
		panel_Acoes.add(btnExcluir);
		panel_Acoes.add(btnEditar);

		JPanel panel_Horarios = new JPanel();
		panel_Horarios.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_Horarios.setBounds(10, 102, 430, 198);
		contentPane.add(panel_Horarios);
		panel_Horarios.setLayout(null);

		tableHorario = new JTable(table);
		spTable = new JScrollPane(tableHorario);
		spTable.setBounds(10, 22, 413, 165);
		panel_Horarios.add(spTable);
		tableHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JLabel lblRestaurante = new JLabel("Restaurante");
		lblRestaurante.setBounds(10, 11, 82, 14);
		contentPane.add(lblRestaurante);

		comboBoxRestaurante.setBounds(102, 7, 502, 22);
		contentPane.add(comboBoxRestaurante);
		this.carregarComboRestaurante();
		comboBoxRestaurante.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Restaurante restaurante = (Restaurante) comboBoxRestaurante.getSelectedItem();
				list = hs.listarPor(restaurante);
				table = new HorarioTableModel(list);
				tableHorario.setModel(table);
				tableHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				tableHorario.updateUI();
			}
		});

		JLabel lblDiaDaSemana = new JLabel("Dia da Semana");
		lblDiaDaSemana.setBounds(10, 55, 94, 14);
		contentPane.add(lblDiaDaSemana);

		comboBoxDiaDaSemana.setBounds(102, 51, 126, 22);
		contentPane.add(comboBoxDiaDaSemana);
		this.carregarComboDiaSemana();

		JLabel lblAbertura = new JLabel("Abertura");
		lblAbertura.setBounds(246, 55, 57, 14);
		contentPane.add(lblAbertura);

		textAbertura.setDocument(new MascaraTexto(5, MascaraTexto.TipoEntrada.hora));
		textAbertura.setBounds(302, 52, 86, 20);
		contentPane.add(textAbertura);

		JLabel lblFechamento = new JLabel("Fechamento");
		lblFechamento.setBounds(407, 55, 82, 14);
		contentPane.add(lblFechamento);

		textFechamento.setDocument(new MascaraTexto(5, MascaraTexto.TipoEntrada.hora));
		textFechamento.setBounds(487, 52, 86, 20);
		contentPane.add(textFechamento);

		JButton btnAdicionar = new JButton("Adicionar");
		btnAdicionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (horario == null) {
					try {
						Restaurante restaurante = (Restaurante) comboBoxRestaurante.getSelectedItem();
						horaAber = LocalTime.parse(textAbertura.getText());
						horaFech = LocalTime.parse(textFechamento.getText());
						horario = new Horario(DiaDaSemana.valueOf(comboBoxDiaDaSemana.getSelectedItem().toString()),
								horaAber, horaFech, restaurante);

						hs.salvar(horario);

						list = hs.listarPor(restaurante);
						table = new HorarioTableModel(list);
						tableHorario.setModel(table);
						tableHorario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						tableHorario.updateUI();
						horario = null;

						textAbertura.setText("");
						textFechamento.setText("");
					} catch (DateTimeParseException e2) {
						JOptionPane.showMessageDialog(contentPane, "Informe um horario valido!");
						textAbertura.setText("");
						textFechamento.setText("");
						horario = null;
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPane, e2.getMessage());
						textAbertura.setText("");
						textFechamento.setText("");
						horario = null;
					}

				} else {
					try {
						hs.alterar(horario);
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(contentPane, e2.getMessage());
					}

				}

			}
		});
		btnAdicionar.setBounds(575, 51, 89, 23);
		contentPane.add(btnAdicionar);

		JLabel lblHorarios = new JLabel("Horarios");
		lblHorarios.setBounds(10, 80, 82, 14);
		contentPane.add(lblHorarios);

		JLabel lblAcoes = new JLabel("Ações");
		lblAcoes.setBounds(456, 102, 46, 14);
		contentPane.add(lblAcoes);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(contentPane, "Deseja mesmo cancelar ?", "Cancelar",
						JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					textAbertura.setText("");
					textFechamento.setText("");

				}

			}
		});
		btnCancelar.setBounds(575, 277, 89, 23);
		contentPane.add(btnCancelar);

	}
}
