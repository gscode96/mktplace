package br.com.senai.view.componentes.table;

import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class T5_Principal extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private static T5_Principal frame = new T5_Principal();

	public T5_Principal() {
		setResizable(false);
		setName("frmPrincipal");
		setTitle("Tela Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);

		JMenuBar barraPrincipal = new JMenuBar();
		barraPrincipal.setBounds(0, 0, 784, 22);
		contentPane.add(barraPrincipal);

		JMenu menuCadastros = new JMenu("Cadastros");
		barraPrincipal.add(menuCadastros);

		JMenuItem opcaoCategorias = new JMenuItem("Categorias");
		opcaoCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T1_CadastroCategoria t1 = new T1_CadastroCategoria();
				t1.setVisible(true);

			}
		});
		menuCadastros.add(opcaoCategorias);

		JMenuItem opcaoRestaurantes = new JMenuItem("Restaurantes");
		opcaoRestaurantes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				T2_CadastroRestaurante t2 = new T2_CadastroRestaurante();
				t2.setVisible(true);

			}
		});
		menuCadastros.add(opcaoRestaurantes);

		JMenu menuConfig = new JMenu("Configurações");
		barraPrincipal.add(menuConfig);

		JMenuItem opcaoHorario = new JMenuItem("Horários");
		opcaoHorario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				T6_CadastroHorario T6 = new T6_CadastroHorario();
				T6.setVisible(true);
			}
		});
		menuConfig.add(opcaoHorario);

		JMenu menuSistema = new JMenu("Sistema");
		barraPrincipal.add(menuSistema);

		JMenuItem opcaoSair = new JMenuItem("Sair");
		opcaoSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuSistema.add(opcaoSair);
		setLocationRelativeTo(null);
	}
}
