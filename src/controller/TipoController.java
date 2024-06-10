package controller;

import lib.*;
import model.*;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class TipoController implements ActionListener
{
	private JFormattedTextField tfConsult_Cod;
	private JFormattedTextField tfDel_Cod;
	private JTextPane tfNew_Desc;
	private JTextField tfNewTipo_Nome;
	private JFormattedTextField tfNewTipo_Cod;
	private DefaultTableModel showItens;
	private Lista<ListaTipos<Produto>> tipos; 
	

	public TipoController(JFormattedTextField tfConsult_Cod, JFormattedTextField tfDel_Cod, JTextPane tfNew_Desc, JTextField tfNewTipo_Nome,
			JFormattedTextField tfNewTipo_Cod, DefaultTableModel showItens, Lista<ListaTipos<Produto>> tipos) {
		super();
		this.tfConsult_Cod = tfConsult_Cod;
		this.tfDel_Cod = tfDel_Cod;
		this.tfNew_Desc = tfNew_Desc;
		this.tfNewTipo_Nome = tfNewTipo_Nome;
		this.tfNewTipo_Cod = tfNewTipo_Cod;
		this.showItens = showItens;
		this.tipos = tipos;
	}

	
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch (cmd)
		{
		case "Consultar":
			try {
				showItens.setNumRows(0);
				int id = Integer.parseInt(tfConsult_Cod.getText());
				consultarTipoPorCod(id);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha o cod corretamente.");
			}
			break;
			
		case "Cadastrar":
			try {
				showItens.setNumRows(0);
				int id = Integer.parseInt(tfNewTipo_Cod.getText());
				String nome = tfNewTipo_Nome.getText();
				String desc = tfNew_Desc.getText();
				adicionarTipo(id, nome, desc);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			break;
		case "Excluir":
			try {
				int id = Integer.parseInt(tfDel_Cod.getText());
				excluirTipoPorCod (id);
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			
			break;
		case "Listar Todos":
			try {
				showItens.setNumRows(0);
				listarTipos ();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Erro ao Listar Tipos.");
			}
			break;
			
		}
	}
	
	
	
	
	//FALTANDO GRAVAR NO CSV
	public void adicionarTipo(int cod, String nome, String desc)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Tipos.csv", true));
			w.write(Integer.toString(cod)); w.write(';'); w.write(nome); w.write(';'); w.write(desc);
			w.newLine();		
			ListaTipos<Produto> listaTipo = 
			new ListaTipos<>(new TipoProduto(cod, nome, desc));
			tipos.addLast(listaTipo);
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar tipo");
		}
	}
	
	public void excluirTipoPorCod(int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean excluido = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<Produto> atual = tipos.get(0);
				tipos.removeFirst();
				TipoProduto tipo = atual.getTipo();
				if (tipo.codigo != cod)
					tipos.addLast(atual);
				else
					excluido = true;
			}
			if (excluido) {
				excluirDoCsv(cod);
				JOptionPane.showMessageDialog(null, "Tipo exclu�do");
			}
			else
				JOptionPane.showMessageDialog(null, "Tipo n�o encontrado");
		} catch (Exception e) {
			System.err.println("Erro ao excluir tipo por c�digo");
		}		
	}

	private void excluirDoCsv(int cod)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Tipos.csv"));
			BufferedWriter w = new BufferedWriter(new FileWriter("tmp.txt"));
			String linha = r.readLine();
			while (linha != null) {
				w.write(linha); w.newLine();
				linha = r.readLine();
			}
			r.close();
			w.close();
			BufferedReader r2 = new BufferedReader(new FileReader("tmp.txt"));
			BufferedWriter w2 = new BufferedWriter(new FileWriter("Tipos.csv"));
			linha = r2.readLine();
			while (linha != null) {
				String[] termos = linha.split(",");
				if (Integer.parseInt(termos[0]) != cod) {
					w2.write(linha); w2.newLine();
				}
				linha = r2.readLine();
			}
			r2.close();
			w2.close();
			File del = new File("tmp.txt");
			del.delete();
		} catch (Exception e) {
			System.err.println("Erro ao excluir tipo do csv");
		}
	}
	
	public void listarTipos()
	{
		try {
			int tamanho = tipos.size();
			if (tamanho == 0)
				System.out.println("Lista vazia");
			else {
				
				for (int i = 0; i < tamanho; i++) {
					ListaTipos<Produto> lista = tipos.get(0);
					TipoProduto tipo = lista.getTipo();
					Object item [] = {
							tipo.codigo
							,tipo.tipo
							,tipo.descricao
							,lista.size()
							};
		
					showItens.addRow(item);	
					tipos.removeFirst();
					tipos.addLast(lista);
				}
			
			}
		} catch (Exception e) {
			System.err.println("Erro na listagem de tipos");
		}
	}
	
	public void consultarTipoPorCod(int cod)
	{
		try {
			int tamanho = tipos.size();
			boolean encontrado = false;
			for (int i = 0; i < tamanho; i++) {
				ListaTipos<Produto> lista = tipos.get(0);
				TipoProduto tipo = lista.getTipo();
				if (cod == tipo.codigo) {
					encontrado = true;
					Object item [] = {
							tipo.codigo
							,tipo.tipo
							,tipo.descricao
							,lista.size()
							};
		
					showItens.addRow(item);	
				}
				tipos.removeFirst();
				tipos.addLast(lista);
			}
			if (!encontrado)
				JOptionPane.showMessageDialog(null, "Tipo n�o encontrado");
		} catch (Exception e) {
			System.err.println("Erro na consulta de tipo por c�digo");
		}
	}
}