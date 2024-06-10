package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
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
import java.text.ParseException;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JScrollBar;
import javax.swing.ButtonGroup;
import javax.swing.DropMode;
import controller.*;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;
import model.ClienteCNPJ;
import model.ClienteCPF;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JRadioButton;



public class TelaClient extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNewClient_Nome;
	DefaultTableModel model;
	private JTable table;
	private JTextField tfNewClient_Rua;
	private JTextField tfNewClient_email;
	private Fila<ClienteCNPJ> pj;
	private Fila<ClienteCPF> pf;
	private JTextField tfNewClient_comp;
	private JTextField tfNewClient_num;
	
	
	/**
	 * Launch the application.
	 */

	public TelaClient(Fila<ClienteCNPJ> pj, Fila<ClienteCPF> pf) {
		super();
		this.pj = pj;
		this.pf = pf;
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 830, 738);
		contentPane = new JPanel();
		contentPane.setToolTipText("int");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//restringir campo field//
		NumberFormatter maskInt = FormatNumber.formatint();
		MaskFormatter maskCnpj = FormatNumber.cnpjformat();
		MaskFormatter maskCpf = FormatNumber.cpfformat();
		MaskFormatter maskCep = FormatNumber.cepformat();
		MaskFormatter maskTel = FormatNumber.telformat();
	     
		
	        
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
		separator_1_2_1.setBounds(0, 220, 814, 2);
		contentPane.add(separator_1_2_1);
		
		JSeparator separator_1_2_2_1 = new JSeparator();
		separator_1_2_2_1.setBounds(0, 384, 814, 2);
		contentPane.add(separator_1_2_2_1);
		
		JSeparator separator_1_2_2 = new JSeparator();
		separator_1_2_2.setBounds(0, 284, 814, 2);
		contentPane.add(separator_1_2_2);
		
		//cadastro cliente box
		JLabel lblNewLabel = new JLabel("Cadastrar Cliente");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 98, 814, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lbltipoCliente = new JLabel("Tipo de Cliente: ");
		lbltipoCliente.setBounds(10, 111, 94, 14);
		contentPane.add(lbltipoCliente);
		
		JLabel lblNewClient_Cpf = new JLabel("CPF");
		lblNewClient_Cpf.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Cpf.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Cpf.setBounds(10, 136, 104, 14);
		contentPane.add(lblNewClient_Cpf);
		
		JFormattedTextField tfNewClient_Cnpj = new JFormattedTextField(maskCnpj);
		tfNewClient_Cnpj.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfNewClient_Cnpj.setDropMode(DropMode.INSERT);
		tfNewClient_Cnpj.setToolTipText("CPF insira apenas Números");
		tfNewClient_Cnpj.setBounds(10, 149, 104, 20);
		contentPane.add(tfNewClient_Cnpj);
		
		JFormattedTextField tfNewClient_Cpf = new JFormattedTextField(maskCpf);
		tfNewClient_Cpf.setFocusLostBehavior(JFormattedTextField.PERSIST);
		tfNewClient_Cpf.setDropMode(DropMode.INSERT);
		tfNewClient_Cpf.setToolTipText("CNPJ insira apenas Números");
		tfNewClient_Cpf.setBounds(10, 149, 104, 20);
		contentPane.add(tfNewClient_Cpf);
		
		JRadioButton  check_cpf = new JRadioButton ("Pessoa Física");
		check_cpf.setBounds(106, 106, 114, 23);
		check_cpf.setName("pf");
		contentPane.add(check_cpf);
		check_cpf.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (check_cpf.isSelected()) {
					lblNewClient_Cpf.setText("CPF");
					tfNewClient_Cnpj.setVisible(false);
					tfNewClient_Cpf.setVisible(true);
				}
			};
			
		});
		
		JRadioButton  check_cnpj = new JRadioButton ("Pessoa Júridica");
		check_cnpj.setBounds(217, 106, 134, 23);
		check_cnpj.setName("pj");
		contentPane.add(check_cnpj);
		check_cnpj.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (check_cnpj.isSelected()) {
					lblNewClient_Cpf.setText("CNPJ");
					tfNewClient_Cnpj.setVisible(true);
					tfNewClient_Cpf.setVisible(false);
				}
			};
			
		});
		
		ButtonGroup radiochoicePes = new ButtonGroup();
		radiochoicePes.add(check_cnpj);
		radiochoicePes.add(check_cpf);
				
		
		
		
		
		
		JFormattedTextField tfNewClient_Cep = new JFormattedTextField(maskCep);
		tfNewClient_Cep.setToolTipText("cep");
		tfNewClient_Cep.setBounds(576, 182, 75, 20);
		contentPane.add(tfNewClient_Cep);
		
		JLabel lblNewClient_Nome = new JLabel("Nome");
		lblNewClient_Nome.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Nome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Nome.setBounds(124, 136, 125, 14);
		contentPane.add(lblNewClient_Nome);
		
		tfNewClient_Nome = new JTextField();
		tfNewClient_Nome.setToolTipText("Nome do cliente");
		tfNewClient_Nome.setBounds(124, 148, 125, 20);
		contentPane.add(tfNewClient_Nome);
		tfNewClient_Nome.setColumns(10);
				
		JLabel lblNewClient_Rua = new JLabel("Logradouro");
		lblNewClient_Rua.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Rua.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Rua.setBounds(10, 169, 374, 14);
		contentPane.add(lblNewClient_Rua);
		
		tfNewClient_Rua = new JTextField();
		tfNewClient_Rua.setToolTipText("rua");
		tfNewClient_Rua.setColumns(10);
		tfNewClient_Rua.setBounds(10, 182, 374, 20);
		contentPane.add(tfNewClient_Rua);
		
		JLabel lblNewClient_Comp = new JLabel("Complemento");
		lblNewClient_Comp.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Comp.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Comp.setBounds(491, 169, 75, 14);
		contentPane.add(lblNewClient_Comp);
		
		tfNewClient_comp = new JTextField();
		tfNewClient_comp.setToolTipText("Complemento");
		tfNewClient_comp.setBounds(491, 182, 75, 20);
		contentPane.add(tfNewClient_comp);
		
		JLabel lblNewClient_Num = new JLabel("Número");
		lblNewClient_Num.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Num.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Num.setBounds(394, 169, 86, 14);
		contentPane.add(lblNewClient_Num);
		
		tfNewClient_num = new JTextField();
		tfNewClient_num.setToolTipText("número da casa");
		tfNewClient_num.setBounds(394, 182, 86, 20);
		contentPane.add(tfNewClient_num);
		
		JLabel lblNewClient_Cep = new JLabel("CEP");
		lblNewClient_Cep.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Cep.setBounds(576, 169, 75, 14);
		contentPane.add(lblNewClient_Cep);
		
		
		JLabel lblNewClient_Email = new JLabel("Email");
		lblNewClient_Email.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Email.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Email.setBounds(259, 136, 125, 14);
		contentPane.add(lblNewClient_Email);
		
		tfNewClient_email = new JTextField();
		tfNewClient_email.setToolTipText("Nome do cliente");
		tfNewClient_email.setColumns(10);
		tfNewClient_email.setBounds(259, 148, 125, 20);
		contentPane.add(tfNewClient_email);
		
		JFormattedTextField tfNewClient_tel = new JFormattedTextField(maskTel);
		tfNewClient_tel.setToolTipText("número do telefone");
		tfNewClient_tel.setBounds(394, 148, 86, 20);
		contentPane.add(tfNewClient_tel);
		
		JLabel lblNewClient_Tel = new JLabel("Telefone");
		lblNewClient_Tel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewClient_Tel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewClient_Tel.setBounds(394, 135, 86, 14);
		contentPane.add(lblNewClient_Tel);
		
		if (check_cnpj.isSelected()) {
			tfNewClient_Cpf.setVisible(false);
			tfNewClient_Cnpj.setVisible(true);
			lblNewClient_Cpf.setText("CNPJ");
		}
		else {
			tfNewClient_Cpf.setVisible(true);
			tfNewClient_Cnpj.setVisible(false);
		}
		
		JButton btnCadClient = new JButton("Cadastrar");
		btnCadClient.setBounds(661, 179, 121, 23);
		contentPane.add(btnCadClient);	
		
		
		
		
				
		//Box Consultar 
		JLabel titleConsultClient = new JLabel("Consultar Cliente");
		titleConsultClient.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleConsultClient.setHorizontalAlignment(SwingConstants.CENTER);
		titleConsultClient.setBounds(0, 11, 814, 14);
		contentPane.add(titleConsultClient);		
	
		JLabel lblConsultClient = new JLabel("CPF/ CNPJ");
		lblConsultClient.setVerticalAlignment(SwingConstants.BOTTOM);
		lblConsultClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblConsultClient.setBounds(491, 41, 162, 14);
		contentPane.add(lblConsultClient);
		
		JFormattedTextField tfConsultClient_reg = new JFormattedTextField();
		tfConsultClient_reg.setToolTipText("CPF ou CNPJ insira apenas Números");
		tfConsultClient_reg.setBounds(491, 54, 162, 20);
		contentPane.add(tfConsultClient_reg);
		
		JButton btnConsultarClient = new JButton("Consultar");
		btnConsultarClient.setBounds(663, 51, 119, 23);
		contentPane.add(btnConsultarClient);
		
		
		//Excluir cliente box
		     
		JLabel lbldelClient_reg = new JLabel("CPF/ CNPJ");
		lbldelClient_reg.setVerticalAlignment(SwingConstants.BOTTOM);
		lbldelClient_reg.setHorizontalAlignment(SwingConstants.CENTER);
		lbldelClient_reg.setBounds(491, 240, 160, 14);
		contentPane.add(lbldelClient_reg);
		
		JFormattedTextField tfDelClient_reg = new JFormattedTextField();
		tfDelClient_reg.setToolTipText("Código do produto no formato inteiro");
		tfDelClient_reg.setToolTipText("CPF ou CNPJ insira apenas Números");
		tfDelClient_reg.setBounds(491, 253, 160, 20);
		contentPane.add(tfDelClient_reg);
		
		JButton btnExcluirClient = new JButton("Excluir");
		btnExcluirClient.setBounds(661, 250, 121, 23);
		contentPane.add(btnExcluirClient);
		
		
		// List por tipo
		JLabel lblListarClient = new JLabel("Listar Clientes");
		lblListarClient.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarClient.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblListarClient.setBounds(0, 297, 814, 14);
		contentPane.add(lblListarClient);
		
		JButton btnConsultClientTipo = new JButton("Listar por Tipo");
		btnConsultClientTipo.setBounds(661, 322, 121, 23);
		contentPane.add(btnConsultClientTipo);
			
		//listar todos		
				
		JButton btnListarClient = new JButton("Listar Todos");
		btnListarClient.setBounds(661, 350, 121, 23);
		contentPane.add(btnListarClient);
		
						
		//box tabela mostra
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(10, 397, 794, 302);
		contentPane.add(scrollPane_2);
		
		table = new JTable();
		scrollPane_2.setViewportView(table);
		model = new DefaultTableModel();
		Object[] column = {"CPF/CNPJ","NOME","ENDEREÇO","CEP","EMAIL","TELEFONE"};
		model.setColumnIdentifiers(column);
		table.setEnabled(false);
		table.setModel(model);
					
		//box CadastraProd
		
		
		//box deletar client
		JLabel titleDelClient = new JLabel("Excluir Cliente");
		titleDelClient.setFont(new Font("Tahoma", Font.BOLD, 11));
		titleDelClient.setHorizontalAlignment(SwingConstants.CENTER);
		titleDelClient.setBounds(0, 227, 814, 14);
		contentPane.add(titleDelClient);
		
		
		
		//radio list
		JRadioButton check_cnpjList = new JRadioButton("Pessoa Júridica");
		check_cnpjList.setBounds(517, 322, 134, 23);
		check_cnpjList.setName("pj");
		contentPane.add(check_cnpjList);
		
		JRadioButton check_cpfList = new JRadioButton("Pessoa Física");
		check_cpfList.setBounds(406, 322, 114, 23);
		check_cpfList.setName("pf");
		contentPane.add(check_cpfList);
		
		ButtonGroup radioPesListTipo = new ButtonGroup();
		radioPesListTipo.add(check_cnpjList);
		radioPesListTipo.add(check_cpfList);
		
		JLabel lbltipoClienteList = new JLabel("Tipo de Cliente: ");
		lbltipoClienteList.setBounds(310, 327, 94, 14);
		contentPane.add(lbltipoClienteList);		
		
		
		
		
		
		ClienteController cc = new ClienteController(pf, pj, tfNewClient_Cpf,tfNewClient_Cnpj, tfNewClient_Nome, tfNewClient_email,
				 tfNewClient_Rua,  tfNewClient_num, tfNewClient_comp, tfNewClient_tel,  tfNewClient_Cep,  tfDelClient_reg,
				 tfConsultClient_reg, radioPesListTipo, model, radiochoicePes);
		btnConsultarClient.addActionListener(cc);
		btnListarClient.addActionListener(cc);
		btnConsultClientTipo.addActionListener(cc);
		btnExcluirClient.addActionListener(cc);
		btnCadClient.addActionListener(cc);
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
