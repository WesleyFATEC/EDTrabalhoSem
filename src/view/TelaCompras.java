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
public class TelaCompras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Lista<ListaTipos<Produto>> tipos;
	DefaultTableModel model;
	private JTable table;
	private Fila<ClienteCNPJ> pj;
	private Fila<ClienteCPF> pf;
	private CarrinhoController carc;
		

	public TelaCompras ( Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj,
			CarrinhoController carc, Lista<ListaTipos<Produto>> tipos) {
		
		this.tipos = tipos;
		this.pf=pf;
		this.pj=pj;
		this.carc = carc;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 830, 738);
		contentPane = new JPanel();
		contentPane.setToolTipText("int");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//restringir campo field//
		NumberFormatter maskInt = FormatNumber.formatint();
				
		//alerta se campo não preenchido
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JSeparator separator_1_2 = new JSeparator();
		separator_1_2.setBounds(0, 90, 814, 2);
		contentPane.add(separator_1_2);
		
		JSeparator separator_1_2_2_1 = new JSeparator();
		separator_1_2_2_1.setBounds(0, 166, 814, 2);
		contentPane.add(separator_1_2_2_1);
		
		//box ListaCompras
		JLabel lblNewLabel = new JLabel("Listar Compras");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 98, 814, 14);
		contentPane.add(lblNewLabel);
		
		JButton btnListarCompras = new JButton("Listar Compras");
		btnListarCompras.setBounds(663, 123, 121, 23);
		contentPane.add(btnListarCompras);
		
		//Box Consultar Produto
		JLabel titleConsultCompra = new JLabel("Consultar Compra");
		titleConsultCompra.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleConsultCompra.setHorizontalAlignment(SwingConstants.CENTER);
		titleConsultCompra.setBounds(0, 11, 814, 14);
		contentPane.add(titleConsultCompra);		
	
		JLabel lblConsultCompra = new JLabel("Cod Compra");
		lblConsultCompra.setVerticalAlignment(SwingConstants.BOTTOM);
		lblConsultCompra.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultCompra.setBounds(578, 41, 75, 14);
		contentPane.add(lblConsultCompra);
		
		JTextField tfConsultCompra = new JTextField();
		tfConsultCompra.setToolTipText("Código da compra");
		tfConsultCompra.setBounds(578, 54, 75, 20);
		contentPane.add(tfConsultCompra);
		
		JButton btnConsultarCompras = new JButton("Consultar");
		btnConsultarCompras.setBounds(663, 51, 119, 23);
		contentPane.add(btnConsultarCompras);
		
		
		
		//box tabela mostra
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 191, 794, 508);
		contentPane.add(scrollPane_2);
		table = new JTable();
		scrollPane_2.setViewportView(table);
		model = new DefaultTableModel();
		table.setEnabled(false);
		table.setModel(model);
		
		CsvController csvControl = new CsvController(tipos, tfConsultCompra, pj, pf, carc, model);
		btnListarCompras.addActionListener(csvControl);
		btnConsultarCompras.addActionListener(csvControl);
		
	}
	


	private static boolean VerifPreenchido (String campo) {
		if (campo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para a ação");
			return false;
		}
		else {
			return true;
		}
	}
}
