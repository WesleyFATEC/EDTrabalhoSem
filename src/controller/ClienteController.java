package controller;

import model.*;
import lib.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class ClienteController implements ActionListener 
{
	private Fila<ClienteCPF>  pf;
	private Fila<ClienteCNPJ>  pj;
	private JFormattedTextField cpf;
	private JFormattedTextField cnpj;
	private JTextField nome;
	private JTextField email;
	private JTextField rua;
	private JTextField num;
	private JTextField comp;
	private JTextField tel;
	private JFormattedTextField cep;
	private JFormattedTextField del_reg;
	private JFormattedTextField tfConsult_reg;
	private DefaultTableModel clientListaShow;
	private ButtonGroup radiochoicePes;
	private CsvController csv = new CsvController(null, cep, pj, pf, null, clientListaShow);
	private ButtonGroup radioPesListTipo;
	
	public ClienteController()
	{
		super();
	}	
	
	 
	public ClienteController(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj, JFormattedTextField cpf,
			JFormattedTextField cnpj, JTextField nome, JTextField email,
			JTextField rua, JTextField num, JTextField comp,
			JTextField tel, JFormattedTextField cep, JFormattedTextField del_reg,
			 JFormattedTextField tfConsult_reg,ButtonGroup radioPesListTipo,
			DefaultTableModel clientShow, ButtonGroup radiochoicePes) {
		super();
		this.pf = pf;
		this.pj = pj;
		this.cpf = cpf;
		this.cnpj = cnpj;
		this.nome = nome;
		this.email = email;
		this.rua = rua;
		this.num = num;
		this.comp = comp;
		this.tel = tel;
		this.cep = cep;
		this.del_reg = del_reg;
		this.tfConsult_reg = tfConsult_reg;
		this.clientListaShow = clientShow;
		this.radiochoicePes = radiochoicePes;
		this.radioPesListTipo = radioPesListTipo;
		this.csv = csv;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch (cmd)
		{
		case "Consultar":
			try {
				String consult= tfConsult_reg.getText();
				int tiporeg = consult.length();
				if (tiporeg == 11) {
					consultaClientePF(consult);
				}
				else if (tiporeg == 14) {
					consultaClientePF(consult);		
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Preencha o cpf/cnpj corretamente.");
				}

			}catch (NumberFormatException ex)
			{
				System.out.println("Erro ao buscar Cliente");
			}
			break;
			
		case "Cadastrar":
			try {
				String tipoPessoa ="";
				for (Enumeration<AbstractButton> buttons = radiochoicePes.getElements(); buttons.hasMoreElements();) {
			            AbstractButton button = buttons.nextElement();

			            if (button.isSelected()) {
			            	tipoPessoa = button.getName();
			            }
				 }
		       if (tipoPessoa.equals("pf")) 
		        	{
		    	   System.out.print(tipoPessoa);
	                adicionarClientePF(cpf.getText(), nome.getText(), rua.getText(),
	                		num.getText(), comp.getText(), cep.getText(), tel.getText(),
	                		email.getText());
	                JOptionPane.showMessageDialog(null, "Cliente Pessoa Física Cadastrado");

		        	} 
	            else if (tipoPessoa.equals("pj")) 
	            	{
	                adicionarClientePJ(cnpj.getText(), nome.getText(), rua.getText(),
	                		num.getText(), comp.getText(), cep.getText(), tel.getText(),
	                		email.getText());
	                JOptionPane.showMessageDialog(null, "Cliente Pessoa Jurídica Cadastrado");
	            	}
	            else {
	                JOptionPane.showMessageDialog(null, "Selecione o tipo de pessoa.");
	            	}
				}
			catch (NumberFormatException ex)
			{
				System.out.println("Erro ao cadastrar cliente");
			}
			break;
			
		case "Excluir":
			try {
				String reg = del_reg.getText();
				int tiporeg = reg.length();
				if (tiporeg == 11) {
					excluiClientePF(reg);
				}
				else if (tiporeg == 14) {
					excluiClientePJ(reg);
				}
				else {
					JOptionPane.showMessageDialog(null, "Preencha o CPF/CNPJ corretamente.");
				}
			}
			catch (NumberFormatException ex)
			{
				System.out.println("erro ao excluir cliente");
			}
			break;
			
		case "Listar por Tipo":
			try {
				
				String tipoPessoa ="";
				
				for (Enumeration<AbstractButton> buttons = radioPesListTipo.getElements(); buttons.hasMoreElements();) {
			            AbstractButton button = buttons.nextElement();

			            if (button.isSelected()) {
			            	tipoPessoa = button.getName();
			            }
				 }
		       if (tipoPessoa.equals("pf")) 
		        	{
		    	   	listarClientesPf();
		        	} 
	            else if (tipoPessoa.equals("pj")) 
	            	{
	            	listarClientesPj();
	            	}
	            else {
	                JOptionPane.showMessageDialog(null, "Selecione o tipo de pessoa.");
	            	}
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Erro ao Listar Clientes por tipo.");
			}
			break;
			
		case "Listar Todos":
			try {
				if (!pf.isEmpty() | !pj.isEmpty())
				{
					listarClientesPf();
					listarClientesPj();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Não Há Clientes Cadastrados");
				}
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Erro ao Listar Todos os clientes.");
			}
			break;
			
		}
	}

	private void adicionarClientePF (String cpf, String nome, String rua, String num, String comp, String cep, String tel, String email)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("ClientesCPF.csv", true));
				String end = rua + " " + num + " " + comp;
				ClienteCPF cl = new ClienteCPF(cpf, nome, end, cep, tel, email);
				pf.insert(cl);
				w.write(cpf); w.write(';'); w.write(nome); w.write(';');w.write(end); w.write(';');
				w.write(cep); w.write(';'); w.write(tel); w.newLine(); w.write(email); w.newLine();
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cliente cpf");
		}
	}

	private void adicionarClientePJ(String cnpj, String nome, String rua, String num, String comp, String cep, String tel, String email)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("ClientesCNPJ.csv", true));
			String end = rua + " " + num + " " + comp;
			ClienteCNPJ cl = new ClienteCNPJ(cnpj, nome, end, cep, tel, email);
				pj.insert(cl);
				w.write(cnpj); w.write(';'); w.write(nome); w.write(';');w.write(end);
				w.write(cep); w.write(';'); w.write(tel); w.write(';');
				w.write(email); w.newLine();
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar cliente cnpj");
		}
	}

	public void listarClientesPf()
	{
		try {
			clientListaShow.setRowCount(0);
			int tamanhoPF = pf.size();
				for (int i = 0; i < tamanhoPF; i++) {
					ClienteCPF cliente = pf.remove();
					
					Object item [] = 
						{	cliente.cpf
							,cliente.nome
							,cliente.endereco
							,cliente.cep
							,cliente.celular
							,cliente.email
						};
									
					clientListaShow.addRow(item);		
					pf.insert(cliente);
				}
		}catch (Exception e) {
			System.err.println("Erro ao listar clientes cpf");
		}
	}
	public void listarClientesPj() {
		try 
		{	clientListaShow.setRowCount(0);		
			int tamanhoPJ = pj.size();

				for (int i = 0; i < tamanhoPJ; i++) {
					ClienteCNPJ cliente = pj.remove();
					
					Object item [] = 
						{	cliente.cnpj
							,cliente.nome
							,cliente.endereco
							,cliente.cep
							,cliente.telefone
							,cliente.email
						};
									
					clientListaShow.addRow(item);		
					pj.insert(cliente);
				}
		}catch (Exception e) {
			System.err.println("Erro ao listar clientes pessoas jurídicas");
		}
	}
		

	private void consultaClientePF(String cpf)
	{
		try {
			clientListaShow.setRowCount(0);
			int tamanho = pf.size();
			boolean encontrado = false;
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCPF cliente = pf.remove();
					if (cpf.equals(cliente.cpf)) {
						clientListaShow.setRowCount(0);
						Object item [] = 
							{	cliente.cpf
								,cliente.nome
								,cliente.endereco
								,cliente.cep
								,cliente.celular
								,cliente.email
							};
										
						clientListaShow.addRow(item);;
						encontrado = true;
					}
					pf.insert(cliente);
				}
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}

	private void consultaClientePJ (String cnpj)
	{
		try {
			clientListaShow.setRowCount(0);
			int tamanho = pj.size();
			boolean encontrado = false;
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCNPJ cliente = pj.remove();
					if (cnpj.equals(cliente.cnpj)) {
						clientListaShow.setRowCount(0);
						Object item [] = 
							{	cliente.cnpj
								,cliente.nome
								,cliente.endereco
								,cliente.cep
								,cliente.telefone
								,cliente.email
							};
						encontrado = true;
					}
					pj.insert(cliente);
				}
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cnpj");
		}
	}

	private void excluiClientePF(String cpf)
	{
		try {	
			int tamanho = pf.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ClienteCPF cliente = pf.remove();
				if (!cpf.equals(cliente.cpf))
					pf.insert(cliente);
				else
					encontrado = true;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
			else {
				JOptionPane.showMessageDialog(null, "cliente removido");
				excluirDoCsvPF(cpf);
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}
	
	private void excluirDoCsvPF(String cpf)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("ClientesCPF.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("ClientesCPF.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (!termos[0].equals(cpf)) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir cliente cpf do csv");
		}
	}

	private void excluiClientePJ(String cnpj)
	{
		try {	
			int tamanho = pj.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ClienteCNPJ cliente = pj.remove();
				if (!cnpj.equals(cliente.cnpj))
					pj.insert(cliente);
				else
					encontrado = true;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
			else {
				JOptionPane.showMessageDialog(null, "cliente removido");
				excluirDoCsvPJ(cnpj);
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
		}
	}

	private void excluirDoCsvPJ(String cnpj)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("ClientesCPF.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("ClientesCPF.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (!termos[0].equals(cnpj)) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir cliente cpf do csv");
		}
	}

	
}
