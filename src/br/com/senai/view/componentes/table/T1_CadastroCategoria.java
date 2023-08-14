package br.com.senai.view.componentes.table;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import br.com.senai.core.domain.Categoria;
import br.com.senai.core.service.CategoriaService;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class T1_CadastroCategoria extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldNome;
	private String nome;
	private CategoriaService cs = new CategoriaService();

	private Categoria categoria;

	public T1_CadastroCategoria() {

		setResizable(false);

		setTitle("Gerenciar Categoria - Cadastro");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 230);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JLabel lblNome = new JLabel("Nome:");
		lblNome.setBounds(27, 53, 46, 14);
		contentPane.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setBounds(67, 50, 357, 20);
		contentPane.add(textFieldNome);
		textFieldNome.setColumns(10);

		JButton btnPesquisa = new JButton("Pesquisar");
		btnPesquisa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				T3_ConsultaCategoria t3 = new T3_ConsultaCategoria();
				t3.setVisible(true);
				dispose();

			}
		});
		btnPesquisa.setBounds(27, 98, 112, 23);
		contentPane.add(btnPesquisa);

		JButton btnSalvar = new JButton("Salvar");
		btnSalvar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				try {
					if (categoria == null) {
						nome = textFieldNome.getText();
						categoria = new Categoria(nome);

					} else {
						nome = textFieldNome.getText();
						categoria.setNome(nome);

					}
					cs.salvar(categoria);

				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());

				}

			}
		});
		btnSalvar.setBounds(174, 98, 89, 23);
		contentPane.add(btnSalvar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(contentPane, "Deseja mesmo cancelar ?", "Cancelar",
						JOptionPane.YES_NO_OPTION);
				if (option == 0) {
					textFieldNome.setText("");
				}

			}
		});
		btnCancelar.setBounds(290, 98, 89, 23);
		contentPane.add(btnCancelar);
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
		this.textFieldNome.setText(categoria.getNome());

	}
}
