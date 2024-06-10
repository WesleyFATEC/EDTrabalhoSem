package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;

import controller.CarrinhoController;
import controller.ClienteController;
import controller.CsvController;
import controller.ProdutoController;
import controller.TipoController;
import lib.Fila;
import lib.Lista;
import lib.ListaTipos;
import model.ClienteCNPJ;
import model.ClienteCPF;
import model.Produto;

import java.awt.Color;
import javax.swing.JTextField;


public class Tela extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Lista<ListaTipos<Produto>> tipos;
	private Fila<ClienteCPF> pf;
	private Fila<ClienteCNPJ> pj;
	private CarrinhoController carc;
	

	/**
	 * Launch the application.
	 */

	public Tela(Lista<ListaTipos<Produto>> tipos, Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj, CarrinhoController carc) {
		setResizable(false);
		this.tipos = tipos;
		this.pf = pf;
		this.pj = pj;
		this.carc = carc;
		//csvc.adicionarCarrinhos(pf, pj, carc, tipos);
		
		setTitle("Trabalho Semestral ED");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 763, 559);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
				
		JButton btnViewCompras = new JButton("COMPRAS");
		btnViewCompras.setBounds(350, 231, 121, 23);
		contentPane.add(btnViewCompras);
		btnViewCompras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaCompras TelaCompras = new TelaCompras(pf, pj, carc, tipos);
				TelaCompras.setVisible(true);
			}
		});
		
		JButton btnViewClients = new JButton("CLIENTES");
		btnViewClients.setBounds(221, 230, 121, 23);
		contentPane.add(btnViewClients);
		btnViewClients.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaClient TelaClient = new TelaClient(pj,pf);
				TelaClient.setVisible(true);
			}
		});
		
		JButton btnViewProd = new JButton("PRODUTOS");
		btnViewProd.setBounds(351, 202, 121, 23);
		contentPane.add(btnViewProd);
		btnViewProd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TelaProd TelaProd = new TelaProd(tipos);
				TelaProd.setVisible(true);
			}
		});
		
		JButton btnViewTipos = new JButton("TIPOS");
		btnViewTipos.setBounds(223, 201, 119, 23);
		contentPane.add(btnViewTipos);
		btnViewTipos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				TelaTipos TelaTipos = new TelaTipos(tipos);
				TelaTipos.setVisible(true);
				
			}
			
		
		});
		
		JLabel lblNewLabel = new JLabel("GERENCIADOR");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNewLabel.setBounds(221, 108, 250, 76);
		contentPane.add(lblNewLabel);
		
	}
}
