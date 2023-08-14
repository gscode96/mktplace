package br.com.senai.view.componentes.table;

import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.CategoriaService;
import br.com.senai.core.service.RestauranteService;
import br.com.senai.view.componentes.tableModel.CategoriaTableModel;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class T3_ConsultaCategoria extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextField edtNome;

	private JScrollPane spTable;

	private JTable tableCategorias;

	private CategoriaService cs = new CategoriaService();

	private RestauranteService rs = new RestauranteService();

	private List<Categoria> list;

	public T3_ConsultaCategoria() {

		setResizable(false);
		setName("frmConsultaCategoria");
		setTitle("Gerenciar Categoria - Listagem");
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
		edtNome.setBounds(76, 61, 463, 20);
		contentPane.add(edtNome);
		edtNome.setColumns(10);

		JButton btnListar = new JButton("Listar");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					list = cs.listarPor(edtNome.getText());

					CategoriaTableModel table = new CategoriaTableModel(list);
					tableCategorias.setModel(table);

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
				T1_CadastroCategoria t1 = new T1_CadastroCategoria();
				t1.setVisible(true);

				dispose();
			}
		});
		btnNovo.setBounds(550, 6, 89, 23);
		contentPane.add(btnNovo);

		JLabel lblCategoriasEncontradas = new JLabel("Categorias Encontradas");
		lblCategoriasEncontradas.setName("");
		lblCategoriasEncontradas.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblCategoriasEncontradas.setBounds(9, 107, 319, 14);
		contentPane.add(lblCategoriasEncontradas);

		tableCategorias = new JTable();
		spTable = new JScrollPane(tableCategorias);
		spTable.setBounds(10, 129, 628, 148);
		contentPane.add(spTable);
		tableCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "A\u00E7\u00F5es", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(428, 289, 210, 62);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btnEditar = new JButton("Editar");
		btnEditar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selecao = tableCategorias.getSelectedRow();
				if (selecao >= 0) {
					CategoriaTableModel modelCat = (CategoriaTableModel) tableCategorias.getModel();
					Categoria categoriaSelecao = modelCat.getPor(selecao);
					T1_CadastroCategoria t1 = new T1_CadastroCategoria();
					t1.setCategoria(categoriaSelecao);
					t1.setVisible(true);
					dispose();
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma categoria para editar!");
				}
			}
		});
		btnEditar.setBounds(12, 27, 89, 23);
		panel.add(btnEditar);

		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int selecao = tableCategorias.getSelectedRow();
				if (selecao >= 0) {
					int opcao = JOptionPane.showConfirmDialog(contentPane, "Deseja remover o registro selecionado?",
							"Remoção", JOptionPane.YES_NO_OPTION);

					if (opcao == 0) {
						CategoriaTableModel modelCat = (CategoriaTableModel) tableCategorias.getModel();
						Categoria categoriaSelecao = modelCat.getPor(selecao);
						List<Restaurante> list = new ArrayList<Restaurante>();
						list = rs.listarPor("%%", categoriaSelecao);
						try {
							if (list.isEmpty()) {
								try {
									modelCat.removerPor(selecao);
									cs.removerPor(categoriaSelecao.getId());
									tableCategorias.updateUI();
									JOptionPane.showMessageDialog(contentPane, "Categoria removida com sucesso");
								} catch (Exception e2) {
									JOptionPane.showMessageDialog(contentPane, e2.getMessage());

								}

							} else {
								throw new IllegalArgumentException(
										"Erro ao excluir. A categoria selecionada possui restaurante vinculado!");
							}
						} catch (Exception e2) {
							JOptionPane.showMessageDialog(contentPane, e2.getMessage());
						}

					}
				} else {
					JOptionPane.showMessageDialog(contentPane, "Selecione uma categoria para excluir!");
				}
			}
		});
		btnExcluir.setBounds(113, 27, 89, 23);
		panel.add(btnExcluir);
	}

}
