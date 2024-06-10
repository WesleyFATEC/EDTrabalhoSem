package controller;

import lib.*;
import model.*;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import java.io.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProdutoController implements ActionListener
{	private Lista<ListaTipos<Produto>> tipos;
	private JFormattedTextField tfNewProd_Cod;
	private JTextField tfNewProd_Nome;
	private JTextPane tfNewProd_Desc;
	private JFormattedTextField tfNewProd_Quant;
	private JFormattedTextField tfNewProd_Valor;
	private JFormattedTextField tfNewProd_Tipo;
	private JFormattedTextField tfDelProd_Cod;
	private JFormattedTextField tfListProd_Cod;
	private JFormattedTextField consultCod;
	private DefaultTableModel prodListaShow;
	private CsvController csv = new CsvController(tipos, consultCod, null, null, null, null);
	
	ProdutoController(){
		super();
	}
	public ProdutoController(Lista<ListaTipos<Produto>> tipos,JFormattedTextField tfNewProd_Cod, JTextField tfNewProd_Nome, JTextPane tfNewProd_Desc,
			JFormattedTextField tfNewProd_Quant, JFormattedTextField tfNewProd_Valor,
			JFormattedTextField tfNewProd_Tipo, JFormattedTextField tfDelProd_Cod, JFormattedTextField tfListProd_Cod,
			JFormattedTextField tfConsultProd_Cod, DefaultTableModel model) {
		super();
		this.tipos = tipos;
		this.tfNewProd_Cod = tfNewProd_Cod;
		this.tfNewProd_Nome = tfNewProd_Nome;
		this.tfNewProd_Desc = tfNewProd_Desc;
		this.tfNewProd_Quant = tfNewProd_Quant;
		this.tfNewProd_Valor = tfNewProd_Valor;
		this.tfNewProd_Tipo = tfNewProd_Tipo;
		this.tfDelProd_Cod = tfDelProd_Cod;
		this.tfListProd_Cod = tfListProd_Cod;
		this.consultCod = tfConsultProd_Cod;
		this.prodListaShow = model;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch (cmd)
		{
		case "Consultar":
			try {
			int id = Integer.parseInt(consultCod.getText());
			consultarProdutoPorId(id);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha o cod corretamente.");
			}
			break;
			
		case "Cadastrar":
			try {
			int id = Integer.parseInt(tfNewProd_Cod.getText());
			String nome = tfNewProd_Nome.getText();
			String desc = tfNewProd_Desc.getText();
			int quant = Integer.parseInt(tfNewProd_Quant.getText());
			double valor = Double.parseDouble(tfNewProd_Valor.getText());
			int tipo = Integer.parseInt(tfNewProd_Tipo.getText());
			adicionarProduto(tipo,id,nome,desc,quant,valor);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			break;
		case "Excluir":
			try {
				int id = Integer.parseInt(tfDelProd_Cod.getText());
				excluirProdutoPorId (id);
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			
			break;
		case "Listar por Tipo":
			try {
				int tipo = Integer.parseInt(tfListProd_Cod.getText());
				consultarProdutosDoTipo (tipo);
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			break;
		case "Listar Todos":
			try {
				listarTodosProdutos ();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Erro ao Listar Produtos.");
			}
			break;
			
		}
	}
	
	public void adicionarProduto( int tipo, int id, String nome, String desc, int quant,double valor)
	{		
		int indice = indiceDoTipo(tipos, tipo);
		if (indice == -1)
			JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		else {
			try {
				Produto p = criarProduto(indice,id,nome,desc,quant,valor);
				tipos.get(indice).addLast(p);
				escreverProdutosNoCsv(tipos);
				JOptionPane.showMessageDialog(null,p.nome+" criado com exito");
			} catch (Exception e) {
				System.err.println("Erro ao adicionar produto");
			}
		}
	}

	
	public void listarTodosProdutos()
	{
		int tamanhoTipos = tipos.size();
		int tamanhoLista;
		if (tamanhoTipos == 0)
			JOptionPane.showMessageDialog(null,"Não há produtos!");
		try {
			prodListaShow.setRowCount(0);
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				System.out.println(lista.getTipo().tipo);
				tamanhoLista = lista.size();
				if (tamanhoLista != 0)
				{	
					for (int j = 0; j < tamanhoLista; j++) {
						Produto p = lista.get(j);
						
						Object item [] = {	p.id
											,p.nome
											,p.descricao
											,p.qtd
											,p.valor
											,p.tipo};
						
						prodListaShow.addRow(item);	
					}
				}
				else {
					System.out.println("Não há produtos para esse tipo");
					}
				}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro ao consultar todos os produtos");
		}
	}

	public void consultarProdutosDoTipo(int cod)
	{
		int indice = indiceDoTipo(tipos, cod);
		if (indice == -1)
			JOptionPane.showMessageDialog(null, "Tipo não encontrado");
		else {
			try {
				ListaTipos<Produto> lista = tipos.get(indice);
				int tamanho = lista.size();
				if (tamanho != 0) {
					prodListaShow.setRowCount(0);
					for (int i = 0; i < tamanho; i++) {
						Produto p = lista.get(0);
						Object item [] = {	p.id
								,p.nome
								,p.descricao
								,p.qtd
								,p.valor
								,p.tipo};
			
						prodListaShow.addRow(item);
						
						lista.removeFirst();
						lista.addLast(p);
					}
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Não há produtos para esse tipo");
				}
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null,"Erro ao consultar produtos de um tipo");
			}

		}
	}

	public void consultarProdutoPorId(int id)
	{
		try {	
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			boolean encontrado = false;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == id) {
						prodListaShow.setRowCount(0);
						Object item [] = {	p.id
								,p.nome
								,p.descricao
								,p.qtd
								,p.valor
								,p.tipo};
			
						prodListaShow.addRow(item);
						encontrado = true;
						break;
					}
				}
				if (encontrado) break;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null,"Produto não encontrado");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro ao consultar produto por id");
		}
	}

	public void excluirProdutoPorId( int id)
	{
		try {	
			
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			boolean encontrado = false;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == id) {
						lista.remove(j);
						escreverProdutosNoCsv(tipos); 
						encontrado = true;
						JOptionPane.showMessageDialog(null,p.nome+" excluído com exito");
						
						 for (int row = 0; row < prodListaShow.getRowCount(); row++) {
		                        if (Integer.parseInt(prodListaShow.getValueAt(row, 0).toString()) == id) {
		                            prodListaShow.removeRow(row);
		                            break;
		                        }
		                    }
						break;
					}
				}
				if (encontrado) break;
			}
			if (!encontrado) JOptionPane.showMessageDialog(null, "Produto não encontrado");
		} catch (Exception e) {
			System.err.println("Erro ao consultar produto por id");
		}

	}

	private Produto criarProduto( int indice, int id, String nome, String desc, int qtd, double valor)
	{
		try {
			TipoProduto tipo = tipos.get(indice).getTipo();
			Produto p = new Produto(id, nome, desc, valor, qtd, tipo);
			return p;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null,"Erro na criação de produto");
			return null;
		}
	}

	public void escreverProdutosNoCsv(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Produtos.csv"));
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					w.write(Integer.toString(p.id)); w.write(';'); w.write(p.nome); w.write(';');
					w.write(p.descricao); w.write(';'); w.write(Double.toString(p.valor)); w.write(';');
					w.write(Integer.toString(p.qtd)); w.write(';'); w.write(Integer.toString(p.tipo.codigo));
					w.newLine();
				}
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao escrever produtos no csv");
		}
	}

	public int indiceDoTipo(Lista<ListaTipos<Produto>> tipos, int cod)
	{
		int tamanho = tipos.size();
		if (tamanho == 0)
			return -1;
		ListaTipos<Produto> atual;
		TipoProduto tipo;
		try {
			for (int i = 0; i < tamanho; i++) {
				atual = tipos.get(i);
				tipo = atual.getTipo();
				if (tipo.codigo == cod)
					return i;
		}
		return -1;
		} catch (Exception e) {
			return -1;
		}
	}
	
}
