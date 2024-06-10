package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;

import controller.CarrinhoController;
import controller.ClienteController;
import controller.CsvController;
import controller.ProdutoController;
import controller.TipoController;
import lib.Fila;
import lib.Lista;
import lib.ListaTipos;
import lib.Pilha;
import model.ClienteCNPJ;
import model.ClienteCPF;
import model.Produto;
import utils.FormatNumber;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.DropMode;
import controller.*;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import model.Produto;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;



public class TelaCart extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Lista<ListaTipos<Produto>> tipos;
	DefaultTableModel model;
	private JTable table;
	private Fila<ClienteCPF> pf;
	private Fila<ClienteCNPJ> pj;
	private Pilha<Produto> cart;
	private String reg;
	
		

	public TelaCart(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj, Lista<ListaTipos<Produto>> tipos, Pilha<Produto> cart, String reg, String nome) {
		
		this.tipos = tipos;
		this.pf=pf;
		this.pj=pj;
		this.cart=cart;
		this.reg = reg;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 830, 738);
		contentPane = new JPanel();
		contentPane.setToolTipText("int");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//restringir campo field//
		NumberFormatter maskInt = FormatNumber.formatint();
		NumberFormatter maskDouble = FormatNumber.formatDouble();
		
		//alerta se campo não preenchido
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 72, 1, 2);
		contentPane.add(separator);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(0, 90, 814, 2);
		contentPane.add(separator_1_2);
		
		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(0, 238, 814, 2);
		contentPane.add(separator_1_2_2);
		
		//box CadastraProd
		JLabel lblNewLabel = new JLabel("Montar Carrinho");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 108, 814, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewProd_Cod = new JLabel("Cod Produto");
		lblNewProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Cod.setBounds(280, 146, 121, 14);
		contentPane.add(lblNewProd_Cod);
		
		JFormattedTextField tfCodProd = new JFormattedTextField(maskInt);
		tfCodProd.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfCodProd.setDropMode(DropMode.INSERT);
		tfCodProd.setToolTipText("Código do produto no formato inteiro");
		tfCodProd.setBounds(280, 159, 121, 20);
		contentPane.add(tfCodProd);
		
		JButton btnCadTipo = new JButton("Adicionar");
		btnCadTipo.setBounds(280, 190, 121, 23);
		contentPane.add(btnCadTipo);
				
		//Box Consultar Produto
		JLabel titleConsult = new JLabel("Consultar Carrinho");
		titleConsult.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleConsult.setHorizontalAlignment(SwingConstants.CENTER);
		titleConsult.setBounds(0, 11, 814, 14);
		contentPane.add(titleConsult);		
	
						
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(348, 36, 119, 23);
		contentPane.add(btnConsultar);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(411, 190, 121, 23);
		contentPane.add(btnExcluir);
		
	
		JLabel lblListar = new JLabel("Checkout");
		lblListar.setHorizontalAlignment(SwingConstants.CENTER);
		lblListar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListar.setBounds(0, 251, 814, 14);
		contentPane.add(lblListar);
		
		JButton btnCheckout = new JButton("Fazer Checkout");
		btnCheckout.setBounds(348, 276, 121, 23);
		contentPane.add(btnCheckout);
		
		//box tabela mostra
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 310, 794, 389);
		contentPane.add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = {"Id", "Nome","Quantidade","Valor","Total Item"};
		model.setColumnIdentifiers(column);
		table.setEnabled(false);
		table.setModel(model);
		
		JFormattedTextField tfquant = new JFormattedTextField();
		tfquant.setToolTipText("Código do produto no formato inteiro");
		tfquant.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfquant.setDropMode(DropMode.INSERT);
		tfquant.setBounds(407, 159, 125, 20);
		contentPane.add(tfquant);
		
		JLabel lblNewProd_Cod_1 = new JLabel("Quantidade");
		lblNewProd_Cod_1.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Cod_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Cod_1.setBounds(407, 146, 125, 14);
		contentPane.add(lblNewProd_Cod_1);

		
		CarrinhoController cartControl = new CarrinhoController(tfquant, tfCodProd, model, tipos, pf, pj, cart, reg, nome);
		
		btnCadTipo.addActionListener(cartControl);
		btnCheckout.addActionListener(cartControl);
		btnExcluir.addActionListener(cartControl);
		btnConsultar.addActionListener(cartControl);
		
	}
	
	public static void verifClient( Fila<ClienteCPF> pf, 
			Fila<ClienteCNPJ> pj, Lista<ListaTipos<Produto>> tipos)
		{	
		
		CsvController CsvController = new CsvController(tipos, null, pj, pf, null, null);
		Pilha<Produto> carrinho;
		String nome;
		String cod = JOptionPane.showInputDialog("Informe o CPF ou CNPJ:");
		if (cod.length() == 11) {
			ClienteCPF cliente = CsvController.localizaClientePF(cod);
			if (cliente == null) return;
			carrinho = cliente.carrinho;
			nome = cliente.nome;
			TelaCart telaCart = new TelaCart(pf, pj, tipos, carrinho,cod,nome);
			telaCart.setVisible(true);
		}
		else if (cod.length() == 14) {
			ClienteCNPJ cliente = CsvController.localizaClientePJ(cod);
			if (cliente == null) return;
			carrinho = cliente.carrinho;
			nome = cliente.nome;
			TelaCart telaCart = new TelaCart(pf, pj, tipos, carrinho,cod,nome);
			telaCart.setVisible(true);
		}
		else {
			JOptionPane.showMessageDialog(null, "Número inválido");
			carrinho = null;
			nome = "";
			return;
		}

		}
	
}
