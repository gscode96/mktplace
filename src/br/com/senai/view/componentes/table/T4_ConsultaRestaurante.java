package br.com.senai.view.componentes.table;

import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Horario;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.CategoriaService;
import br.com.senai.core.service.HorarioService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.tableModel.RestauranteTableModel;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

public class T4_ConsultaRestaurante extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField edtNome;

	private JComboBox<Categoria> comboBoxCategoria = new JComboBox<>();;

	private List<Restaurante> list;

	private JScrollPane spTable;

	private RestauranteService rs = new RestauranteService();

	private CategoriaService cs = new CategoriaService();

	private HorarioService hs = new HorarioService();

	private JTable tableRestaurantes;

	public void carregarCombo() {
		List<Categoria> list = cs.listarTudo();
		for (Categoria categoria : list) {
			this.comboBoxCategoria.addItem(categoria);
		}
	}

	public T4_ConsultaRestaurante() {
		setResizable(false);
		setName("frmConsultaCategoria");
		setTitle("Gerenciar Restaurante - Listagem");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 664, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblFiltro = new JLabel("Filtros");
		lblFiltro.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblFiltro.setBounds(10, 39, 46, 14);
		contentPane.add(lblFiltro);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNome.setBounds(20, 64, 46, 14);
		contentPane.add(lblNome);

		edtNome = new JTextField();
		edtNome.setBounds(76, 61, 172, 20);
		contentPane.add(edtNome);
		edtNome.setColumns(10);

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					list = rs.listarPor("%%", (Categoria) comboBoxCategoria.getSelectedItem());
					RestauranteTableModel table = new RestauranteTableModel(list);
					tableRestaurantes.setModel(table);
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}
			}
		});
		btnListar.setBounds(549, 60, 89, 23);
		contentPane.add(btnListar);

		JButton btnNovo = new JButton("Novo");
		btnNovo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T2_CadastroRestaurante t2 = new T2_CadastroRestaurante();
				t2.setVisible(true);

				dispose();
			}
		});
		btnNovo.setBounds(550, 6, 89, 23);
		contentPane.add(btnNovo);

		JLabel lblRestaurantesEncontrados = new JLabel("Restaurantes Encontrados");
		lblRestaurantesEncontrados.setName("");
		lblRestaurantesEncontrados.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblRestaurantesEncontrados.setBounds(9, 107, 319, 14);
		contentPane.add(lblRestaurantesEncontrados);

		tableRestaurantes = new JTable();
		spTable = new JScrollPane(tableRestaurantes);
		spTable.setBounds(10, 129, 628, 148);
		contentPane.add(spTable);
		tableRestaurantes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(428, 289, 210, 62);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selecao = tableRestaurantes.getSelectedRow();
				if (selecao >= 0) {
					RestauranteTableModel modelRest = (RestauranteTableModel) tableRestaurantes.getModel();
					Restaurante restauranteSelecao = modelRest.getPor(selecao);
					T2_CadastroRestaurante t2 = new T2_CadastroRestaurante();
					t2.setRestaurante(restauranteSelecao);

					t2.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um restaurante para editar!");
				}
			}
		});
		btnEditar.setBounds(12, 27, 89, 23);
		panel.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selecao = tableRestaurantes.getSelectedRow();
				if (selecao >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja remover o registro selecionado?",
							"Remoção", JOptionPane.YES_NO_OPTION);
					if (opcao == 0) {
						RestauranteTableModel modelRest = (RestauranteTableModel) tableRestaurantes.getModel();
						Restaurante restauranteSelecao = modelRest.getPor(selecao);
						List<Horario> list = new ArrayList<Horario>();
						list = hs.listarPor(restauranteSelecao);

						try {
							if (list.isEmpty()) {
								modelRest.removerPor(selecao);
								rs.removerPor(restauranteSelecao.getId());
								tableRestaurantes.updateUI();
								JOptionPane.showMessageDialog(contentPane, "Restaurante removido com sucesso!");
							} else {
								JOptionPane.showMessageDialog(contentPane,
										"Erro ao excluir. O restaurante informado possui horario vinculado.");
							}

						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage());
						}
					}

				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione um restaurante para excluir!");
				}

			}
		});
		btnExcluir.setBounds(113, 27, 89, 23);
		panel.add(btnExcluir);

		comboBoxCategoria.setBounds(339, 58, 198, 25);
		contentPane.add(comboBoxCategoria);
		this.carregarCombo();

		JLabel lblCategoria = new JLabel("Categoria");
		lblCategoria.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCategoria.setBounds(266, 64, 69, 14);
		contentPane.add(lblCategoria);

	}

}
