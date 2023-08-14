package br.com.senai.view.componentes.table;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.domain.Endereco;
import br.com.senai.core.domain.Restaurante;
import br.com.senai.core.service.CategoriaService;
import br.com.senai.core.service.RestauranteService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class T2_CadastroRestaurante extends JFrame {

	private JPanel contentPane;
	private JTextField textNome;
	private JTextField textDescricao;
	private JTextField textLogradouro;
	private JTextField textCidade;
	private JTextField textComplemento;
	private JTextField textBairro;
	private static T2_CadastroRestaurante frame = new T2_CadastroRestaurante();
	private CategoriaService cs = new CategoriaService();
	private RestauranteService rs = new RestauranteService();
	private Restaurante restaurante = null;
	JComboBox<Categoria> comboBoxCategoria = new JComboBox(); // istanciando combobox com a lista que se deseja

//Metodo para carregar o comboBox
	public void carregarCombo() {
		List<Categoria> list = cs.listarTudo();
		for (Categoria categoria : list) {
			this.comboBoxCategoria.addItem(categoria);
		}

	}

	public T2_CadastroRestaurante() {
		setTitle("Gerenciar Restaurante - Cadastro");

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 571, 366);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Categoria:");
		lblNewLabel.setBounds(249, 11, 65, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Nome:");
		lblNewLabel_1.setBounds(33, 11, 46, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblDescrio = new JLabel("Logradouro:");
		lblDescrio.setBounds(33, 155, 74, 14);
		contentPane.add(lblDescrio);

		JLabel lblCidade = new JLabel("Cidade:");
		lblCidade.setBounds(33, 196, 55, 14);
		contentPane.add(lblCidade);

		JLabel lblBairro = new JLabel("Bairro:");
		lblBairro.setBounds(393, 155, 40, 14);
		contentPane.add(lblBairro);

		JLabel lblComplemento = new JLabel("Complemento:");
		lblComplemento.setBounds(33, 238, 100, 14);
		contentPane.add(lblComplemento);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					// Verifica se é alteracao
					if (restaurante == null) {
						String nome = textNome.getText();
						String descricao = textDescricao.getText();
						String logradouro = textLogradouro.getText();
						String cidade = textCidade.getText();
						String complemento = textComplemento.getText();
						String bairro = textBairro.getText();
						restaurante = new Restaurante(nome, descricao,
								new Endereco(cidade, logradouro, bairro, complemento),
								(Categoria) comboBoxCategoria.getSelectedItem());// selecionando item do combo
						// no objeto
						rs.salvar(restaurante);

						restaurante = null;
					}else {

						restaurante.setNome(textNome.getText());
						restaurante.setDescricao(textDescricao.getText());
						restaurante.setEndereco(new Endereco(textCidade.getText(), textLogradouro.getText(),
								textBairro.getText(), textComplemento.getText()));

						rs.salvar(restaurante);
						restaurante = null;

					}
					
					// apagando os campos depois de inserir
					textNome.setText("");
					textDescricao.setText("");
					textLogradouro.setText("");
					textCidade.setText("");
					textComplemento.setText("");
					textBairro.setText("");

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(contentPane, e2.getMessage());
				}

			}
		});
		btnSalvar.setBounds(314, 266, 107, 23);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int option = JOptionPane.showConfirmDialog(contentPane, "Deseja mesmo cancelar ?", "Cancelar",
						JOptionPane.YES_NO_OPTION);

				if (option == 0) {
					textNome.setText("");
					textDescricao.setText("");
					textLogradouro.setText("");
					textCidade.setText("");
					textComplemento.setText("");
					textBairro.setText("");
				}
			}
		});
		btnCancelar.setBounds(431, 266, 100, 23);
		contentPane.add(btnCancelar);

		JButton btnPesquisar = new JButton("Pesquisar");
		btnPesquisar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T4_ConsultaRestaurante t4 = new T4_ConsultaRestaurante();
				t4.setVisible(true);
				dispose();

			}
		});
		btnPesquisar.setBounds(446, 7, 99, 23);
		contentPane.add(btnPesquisar);

		JLabel lblDescrio_2 = new JLabel("Descrição:");
		lblDescrio_2.setBounds(33, 46, 100, 14);
		contentPane.add(lblDescrio_2);

		textNome = new JTextField();
		textNome.setBounds(71, 8, 145, 20);
		contentPane.add(textNome);
		textNome.setColumns(10);

		textDescricao = new JTextField();
		textDescricao.setBounds(33, 71, 422, 73);
		contentPane.add(textDescricao);
		textDescricao.setColumns(10);

		textLogradouro = new JTextField();
		textLogradouro.setBounds(117, 152, 243, 20);
		contentPane.add(textLogradouro);
		textLogradouro.setColumns(10);

		textCidade = new JTextField();
		textCidade.setBounds(117, 193, 243, 20);
		contentPane.add(textCidade);
		textCidade.setColumns(10);

		textComplemento = new JTextField();
		textComplemento.setBounds(117, 235, 243, 20);
		contentPane.add(textComplemento);
		textComplemento.setColumns(10);

		textBairro = new JTextField();
		textBairro.setBounds(436, 152, 109, 20);
		contentPane.add(textBairro);
		textBairro.setColumns(10);

		comboBoxCategoria.setBounds(324, 7, 97, 22);
		contentPane.add(comboBoxCategoria);
		this.carregarCombo(); // chamando metodo para carregar o combo

	}

	public void setRestaurante(Restaurante restaurante) {
		this.restaurante = restaurante;
		this.textNome.setText(restaurante.getNome());
		this.textDescricao.setText(restaurante.getDescricao());
		this.textBairro.setText(restaurante.getEndereco().getBairro());
		this.textCidade.setText(restaurante.getEndereco().getCidade());
		this.textLogradouro.setText(restaurante.getEndereco().getLogradouro());
		this.textComplemento.setText(restaurante.getEndereco().getComplemento());

	}

}
