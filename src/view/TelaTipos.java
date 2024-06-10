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
public class TelaTipos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNewTipo_Nome;
	private Lista<ListaTipos<Produto>> tipos;
	DefaultTableModel model;
	private JTable table;
		

	public TelaTipos(Lista<ListaTipos<Produto>> tipos) {
		
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
		JLabel lblNewLabel = new JLabel("Cadastrar Tipo");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 98, 814, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewProd_Cod = new JLabel("Cod Tipo");
		lblNewProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_Cod.setBounds(59, 136, 75, 14);
		contentPane.add(lblNewProd_Cod);
		
		JLabel lblNewProd_nome = new JLabel("Nome");
		lblNewProd_nome.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewProd_nome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewProd_nome.setBounds(163, 136, 212, 14);
		contentPane.add(lblNewProd_nome);
		
		tfNewTipo_Nome = new JTextField();
		tfNewTipo_Nome.setToolTipText("Nome de Produto");
		tfNewTipo_Nome.setBounds(163, 149, 212, 20);
		contentPane.add(tfNewTipo_Nome);
		tfNewTipo_Nome.setColumns(10);
				
		JLabel lblNew_Desc = new JLabel("Descrição");
		lblNew_Desc.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNew_Desc.setHorizontalAlignment(SwingConstants.CENTER);
		lblNew_Desc.setBounds(513, 136, 75, 14);
		contentPane.add(lblNew_Desc);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane_1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane_1.setBounds(385, 149, 260, 23);
		contentPane.add(scrollPane_1);
		
		JTextPane tfNew_Desc = new JTextPane();
		scrollPane_1.setViewportView(tfNew_Desc);
		tfNew_Desc.setToolTipText("Descrição");
		
		JFormattedTextField tfNewTipo_Cod = new JFormattedTextField(maskInt);
		tfNewTipo_Cod.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfNewTipo_Cod.setDropMode(DropMode.INSERT);
		tfNewTipo_Cod.setToolTipText("Código do produto no formato inteiro");
		tfNewTipo_Cod.setBounds(59, 149, 75, 20);
		contentPane.add(tfNewTipo_Cod);
		
		JButton btnCadTipo = new JButton("Cadastrar");
		btnCadTipo.setBounds(661, 146, 121, 23);
		contentPane.add(btnCadTipo);
				
		//Box Consultar Produto
		JLabel titleConsultProd = new JLabel("Consultar Tipo");
		titleConsultProd.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleConsultProd.setHorizontalAlignment(SwingConstants.CENTER);
		titleConsultProd.setBounds(0, 11, 814, 14);
		contentPane.add(titleConsultProd);		
	
		JLabel lblConsultProd_Cod = new JLabel("Cod Tipo");
		lblConsultProd_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lblConsultProd_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultProd_Cod.setBounds(578, 41, 75, 14);
		contentPane.add(lblConsultProd_Cod);
		
		JFormattedTextField tfConsult_Cod = new JFormattedTextField(maskInt);
		tfConsult_Cod.setToolTipText("Código do tipo");
		tfConsult_Cod.setBounds(578, 54, 75, 20);
		contentPane.add(tfConsult_Cod);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.setBounds(663, 51, 119, 23);
		contentPane.add(btnConsultar);
				
		//box deletar produto 
		JLabel titleDel = new JLabel("Excluir Tipo");
		titleDel.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleDel.setHorizontalAlignment(SwingConstants.CENTER);
		titleDel.setBounds(0, 188, 814, 14);
		contentPane.add(titleDel);
		     
		JLabel lbldel_Cod = new JLabel("Cod Tipo");
		lbldel_Cod.setVerticalAlignment(SwingConstants.BOTTOM);
		lbldel_Cod.setHorizontalAlignment(SwingConstants.CENTER);
		lbldel_Cod.setBounds(576, 227, 75, 14);
		contentPane.add(lbldel_Cod);
		
		JFormattedTextField tfDel_Cod = new JFormattedTextField(maskInt);
		tfDel_Cod.setToolTipText("Código do tipo");
		tfDel_Cod.setBounds(576, 240, 75, 20);
		contentPane.add(tfDel_Cod);
		
		JButton btnExcluir = new JButton("Excluir");
		btnExcluir.setBounds(661, 237, 121, 23);
		contentPane.add(btnExcluir);
		
	//listar
		JLabel lblListar = new JLabel("Listar Tipos");
		lblListar.setHorizontalAlignment(SwingConstants.CENTER);
		lblListar.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListar.setBounds(0, 276, 814, 14);
		contentPane.add(lblListar);
		
		JButton btnListarTodos = new JButton("Listar Todos");
		btnListarTodos.setBounds(661, 303, 121, 23);
		contentPane.add(btnListarTodos);
		
		//box tabela mostra
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 384, 794, 315);
		contentPane.add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = {"Id", "Nome","Descrição"};
		model.setColumnIdentifiers(column);
		table.setEnabled(false);
		table.setModel(model);

		TipoController tpctrl= new TipoController(tfConsult_Cod,tfDel_Cod,tfNew_Desc,tfNewTipo_Nome,
				tfNewTipo_Cod,model,tipos);
		btnCadTipo.addActionListener(tpctrl);
		btnListarTodos.addActionListener(tpctrl);
		btnExcluir.addActionListener(tpctrl);
		btnConsultar.addActionListener(tpctrl);
		
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
