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
public class TelaProd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNewProd_Nome;
	private Lista<ListaTipos<Produto>> tipos;
	DefaultTableModel model;
	private JTable table;
		

	public TelaProd(Lista<ListaTipos<Produto>> tipos) {
		
		this.tipos = tipos;
		
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
		
		JSeparator separator_1_2_1 = new JSeparator();
		separator_1_2_1.setBounds(0, 180, 814, 2);
		contentPane.add(separator_1_2_1);
		
		JSeparator separator_1_2_2_1 = new JSeparator();
		separator_1_2_2_1.setBounds(0, 360, 814, 2);
		contentPane.add(separator_1_2_2_1);
		
		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(0, 270, 814, 2);
		contentPane.add(separator_1_2_2);
		
		//box CadastraProd
		JLabel lblNewLabel = new JLabel("Cadastrar Produto");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 98, 814, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewProd_Cod = new JLabel("Cod Prod.");
		lblNewProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Cod.setBounds(10, 136, 75, 14);
		contentPane.add(lblNewProd_Cod);
		
		JFormattedTextField tfNewProd_Tipo = new JFormattedTextField(maskInt);
		tfNewProd_Tipo.setToolTipText("Código do Tipo do Produto");
		tfNewProd_Tipo.setBounds(576, 149, 75, 20);
		contentPane.add(tfNewProd_Tipo);
		
		JLabel lblNewProd_nome = new JLabel("Nome");
		lblNewProd_nome.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_nome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_nome.setBounds(95, 136, 115, 14);
		contentPane.add(lblNewProd_nome);
		
		tfNewProd_Nome = new JTextField();
		tfNewProd_Nome.setToolTipText("Nome de Produto");
		tfNewProd_Nome.setBounds(95, 149, 115, 20);
		contentPane.add(tfNewProd_Nome);
		tfNewProd_Nome.setColumns(10);
				
		JLabel lblNewProd_Desc = new JLabel("Descrição");
		lblNewProd_Desc.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Desc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Desc.setBounds(320, 136, 75, 14);
		contentPane.add(lblNewProd_Desc);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(220, 149, 175, 23);
		contentPane.add(scrollPane_1);
		
		JTextPane tfNewProd_Desc = new JTextPane();
		scrollPane_1.setViewportView(tfNewProd_Desc);
		tfNewProd_Desc.setToolTipText("Descrição");
		
		JLabel lblNewProd_Quant = new JLabel("Quantidade");
		lblNewProd_Quant.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Quant.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Quant.setBounds(491, 136, 75, 14);
		contentPane.add(lblNewProd_Quant);
		
		JFormattedTextField tfNewProd_Quant = new JFormattedTextField(maskInt);
		tfNewProd_Quant.setToolTipText("Quantidade do Produto");
		tfNewProd_Quant.setBounds(491, 149, 75, 20);
		contentPane.add(tfNewProd_Quant);
		
		JLabel lblNewProd_Valor = new JLabel("Valor");
		lblNewProd_Valor.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Valor.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Valor.setBounds(405, 136, 75, 14);
		contentPane.add(lblNewProd_Valor);
		
		JFormattedTextField tfNewProd_Valor = new JFormattedTextField();
		/*tfNewProd_Valor.setFormatterFactory(new DefaultFormatterFactory(maskDouble));*/
		tfNewProd_Valor.setToolTipText("Valor do Produto");
		tfNewProd_Valor.setBounds(405, 149, 75, 20);
		contentPane.add(tfNewProd_Valor);
		
		JLabel lblNewProd_Tipo = new JLabel("Cod. Tipo");
		lblNewProd_Tipo.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Tipo.setBounds(576, 136, 75, 14);
		contentPane.add(lblNewProd_Tipo);
		
		JFormattedTextField tfNewProd_Cod = new JFormattedTextField(maskInt);
		tfNewProd_Cod.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfNewProd_Cod.setDropMode(DropMode.INSERT);
		tfNewProd_Cod.setToolTipText("Código do produto no formato inteiro");
		tfNewProd_Cod.setBounds(10, 149, 75, 20);
		contentPane.add(tfNewProd_Cod);
		
		JButton btnCadProduto = new JButton("Cadastrar");
		btnCadProduto.setBounds(661, 146, 121, 23);
		contentPane.add(btnCadProduto);
				
		//Box Consultar Produto
		JLabel titleConsultProd = new JLabel("Consultar Produto");
		titleConsultProd.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleConsultProd.setHorizontalAlignment(SwingConstants.CENTER);
		titleConsultProd.setBounds(0, 11, 814, 14);
		contentPane.add(titleConsultProd);		
	
		JLabel lblConsultProd_Cod = new JLabel("Cod Prod.");
		lblConsultProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblConsultProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultProd_Cod.setBounds(578, 41, 75, 14);
		contentPane.add(lblConsultProd_Cod);
		
		JFormattedTextField tfConsultProd_Cod = new JFormattedTextField(maskInt);
		tfConsultProd_Cod.setToolTipText("Código do produto no formato inteiro");
		tfConsultProd_Cod.setBounds(578, 54, 75, 20);
		contentPane.add(tfConsultProd_Cod);
		
		JButton btnConsultarProduto = new JButton("Consultar");
		btnConsultarProduto.setBounds(663, 51, 119, 23);
		contentPane.add(btnConsultarProduto);
				
		//box deletar produto 
		JLabel titleDelProd = new JLabel("Excluir Produto");
		titleDelProd.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleDelProd.setHorizontalAlignment(SwingConstants.CENTER);
		titleDelProd.setBounds(0, 188, 814, 14);
		contentPane.add(titleDelProd);
		     
		JLabel lbldelProd_Cod = new JLabel("Cod Prod.");
		lbldelProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lbldelProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lbldelProd_Cod.setBounds(576, 227, 75, 14);
		contentPane.add(lbldelProd_Cod);
		
		JFormattedTextField tfDelProd_Cod = new JFormattedTextField(maskInt);
		tfDelProd_Cod.setToolTipText("Código do produto no formato inteiro");
		tfDelProd_Cod.setBounds(576, 240, 75, 20);
		contentPane.add(tfDelProd_Cod);
		
		JButton btnExcluirprod = new JButton("Excluir");
		btnExcluirprod.setBounds(661, 237, 121, 23);
		contentPane.add(btnExcluirprod);
		
		
		//Box Listar Produtos
		JLabel lblListProd_Cod = new JLabel("Cod Prod.");
		lblListProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblListProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblListProd_Cod.setBounds(576, 291, 75, 14);
		contentPane.add(lblListProd_Cod);
		
		JFormattedTextField tfListProd_Cod = new JFormattedTextField(maskInt);
		tfListProd_Cod.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfListProd_Cod.setToolTipText("Código do produto no formato inteiro");
		tfListProd_Cod.setBounds(576, 304, 75, 20);
		contentPane.add(tfListProd_Cod);
		
		JButton btnConsultProdTipo = new JButton("Listar por Tipo");
		btnConsultProdTipo.setBounds(661, 301, 121, 23);
		contentPane.add(btnConsultProdTipo);
		
				
		JLabel lblListarProdutos = new JLabel("Listar Produtos");
		lblListarProdutos.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarProdutos.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListarProdutos.setBounds(0, 276, 814, 14);
		contentPane.add(lblListarProdutos);
		
		JButton btnListarProd = new JButton("Listar Todos");
		btnListarProd.setBounds(661, 329, 121, 23);
		contentPane.add(btnListarProd);
		
		//box tabela mostra
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 384, 794, 315);
		contentPane.add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = {"Id", "Nome","Descrição", "Quantidade", "Valor", "Tipo"};
		//final Object[] row = new Object[0];
		model.setColumnIdentifiers(column);
		table.setEnabled(false);
		table.setModel(model);
		
		
		
		ProdutoController prodcontrol = new ProdutoController(tipos,
				tfNewProd_Cod, tfNewProd_Nome, tfNewProd_Desc,tfNewProd_Quant,tfNewProd_Valor,
				tfNewProd_Tipo,tfDelProd_Cod, tfListProd_Cod,tfConsultProd_Cod,model);

		btnCadProduto.addActionListener(prodcontrol);
		btnConsultProdTipo.addActionListener(prodcontrol);
		btnListarProd.addActionListener(prodcontrol);
		btnExcluirprod.addActionListener(prodcontrol);
		btnConsultarProduto.addActionListener(prodcontrol);
		
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
