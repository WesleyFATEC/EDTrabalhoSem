package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import lib.*;
import model.*;

public class CarrinhoController implements ActionListener
{
	private JFormattedTextField tfquant;
	private JFormattedTextField tfCodProd;
	private JFormattedTextField tfConsult_Cod;
	private DefaultTableModel showItens;
	private Lista<ListaTipos<Produto>> tipos;
	private Fila<ClienteCPF>  pf;
	private Fila<ClienteCNPJ>  pj;
	private Pilha<Produto> cart;
	private String reg;
	private String nome;
	


	public CarrinhoController(JFormattedTextField tfquant, JFormattedTextField tfCodProd,
			DefaultTableModel showItens, Lista<ListaTipos<Produto>> tipos, Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj,
			Pilha<Produto> cart, String reg, String nome) {
		super();
		this.tfquant = tfquant;
		this.tfCodProd = tfCodProd;
		this.tfConsult_Cod = tfConsult_Cod;
		this.showItens = showItens;
		this.tipos = tipos;
		this.pf = pf;
		this.pj = pj;
		this.cart = cart;
		this.reg = reg;
		this.nome =nome;
	}

	public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch (cmd)
		{
		case "Consultar":
			try {
				showItens.setNumRows(0);
				consultarCarrinho(nome);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha o cod corretamente.");
			}
			break;
			
		case "Adicionar":
			try {
				int cod = Integer.parseInt(tfCodProd.getText());
				addicionarAoCarrinho(cod);
			}catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			break;
			
		case "Excluir":
			try {
				excluirProduto ();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Preencha todos os campos corretamente.");
			}
			
			break;
		case "Fazer Checkout":
			try {
				menuCheckout();
			}
			catch (NumberFormatException ex)
			{
				JOptionPane.showMessageDialog(null, "Erro ao Listar Tipos.");
			}
			break;
			
		}
	}

	public double addicionarAoCarrinho(int cod)
	{		
		Produto p = localizarProduto(cod);
		if (p == null) {
			JOptionPane.showMessageDialog(null, "Produto não encontrado");
			return 0;
		} else {
			int quant = Integer.parseInt(tfquant.getText());
			Double totItem = p.valor * quant;
			cart.push(copiarProduto(p, quant));
			Object item [] = {	p.id
					,p.nome
					,quant
					,p.valor
					,totItem
					};

			showItens.addRow(item);
			return totItem;
		}
	}

	public void consultarCarrinho(String nome)
	{
		try {
			double total = 0;
			Pilha<Produto> aux = new Pilha<>();
			int tamanho = cart.size();
			System.out.println(nome + ":");
			if (tamanho == 0)
				System.out.println("\tNão há produtos no carrinho");
			else {
				for (int i = 0; i < tamanho; i++) {
					aux.push(cart.pop());
				}
				for (int i = 0; i < tamanho; i++) {
					Produto p = aux.pop();
					total += p.valor * p.qtd;
					Object item [] = {	p.id
							,p.nome
							,p.qtd
							,p.valor
							,total
							};
		
					showItens.addRow(item);	

					cart.push(p);
				}
				
			}
		} catch (Exception e) {
			System.err.println("Erro ao consultar carrinho");
		}
	}

	public void excluirProduto()
	{
		try {	
			int item = Integer.parseInt(
				JOptionPane.showInputDialog("Selecione o item que deseja excluir"));
			int tamanho = cart.size();
			if (item < 1 || item > tamanho)
				JOptionPane.showMessageDialog(null, "Item inválido");
			else {
				Pilha<Produto> aux = new Pilha<>();
				for (int i = 0; i < tamanho; i++) {
					aux.push(cart.pop());
				}
				for (int i = 0; i < tamanho; i++) {
					if (item != i + 1)
						cart.push(aux.pop());
					else
						aux.pop();
						
				}
				JOptionPane.showMessageDialog(null, "Produto excluído");
				showItens.setNumRows(0);
				consultarCarrinho(nome);
			}
		} catch (Exception e) {
			System.err.println("Erro ao excluir produto do carrinho");
		}
	}

	public void escreverNoCsv()
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Carrinho.csv"));
			int tamanhoPf = pf.size();
			int tamanhoPj = pj.size();
			for (int i = 0; i < tamanhoPf; i++) {
				ClienteCPF cliente = pf.remove();
				w.write(cliente.cpf);
				Pilha<Produto> carrinho = cliente.carrinho;
				Pilha<Produto> aux = new Pilha<>();
				int tamanhoCarrinho = carrinho.size();
				for (int j = 0; j < tamanhoCarrinho; j++) {
					aux.push(carrinho.pop());
				}
				for (int j = 0; j < tamanhoCarrinho; j++) {
					Produto p = aux.pop();
					w.write(';'); w.write(Integer.toString(p.id));
					w.write(';'); w.write(Integer.toString(p.qtd));
					carrinho.push(p);
				}
				w.newLine();
				pf.insert(cliente);
			}
			for (int i = 0; i < tamanhoPj; i++) {
				ClienteCNPJ clienteJ = pj.remove();
				w.write(clienteJ.cnpj);
				Pilha<Produto> carrinho = clienteJ.carrinho;
				Pilha<Produto> aux = new Pilha<>();
				int tamanhoCarrinho = carrinho.size();
				for (int j = 0; j < tamanhoCarrinho; j++) {
					aux.push(carrinho.pop());
				}
				for (int j = 0; j < tamanhoCarrinho; j++) {
					Produto p = aux.pop();
					w.write(';'); w.write(Integer.toString(p.id));
					w.write(';'); w.write(Integer.toString(p.qtd));
					carrinho.push(p);
				}
				w.newLine();
				pj.insert(clienteJ);
			}
			w.close();
		} catch (Exception e) {
			System.err.println("Erro ao escrever carrinhos no csv");
		}
	}

	public Produto copiarProduto(Produto p, int qtd)
	{
		Produto copia = new Produto(p.id, p.nome, p.descricao,
			 p.valor, qtd, p.tipo);
		return copia;
	}

	public Produto localizarProduto(int cod)
	{
		try {	
			int tamanhoTipos = tipos.size();
			int tamanhoLista;
			for (int i = 0; i < tamanhoTipos; i++) {
				ListaTipos<Produto> lista = tipos.get(i);
				tamanhoLista = lista.size();
				for (int j = 0; j < tamanhoLista; j++) {
					Produto p = lista.get(j);
					if (p.id == cod)
						return p;
				}
			}
			return null;
		} catch (Exception e) {
			System.err.println("Erro ao localizar produto");
			return null;
		}
	}

	public void menuCheckout()
	{
		try {	
			int tamanho = cart.size();
			Fila<Produto> caixa = new Fila<>();
			if (tamanho == 0) {
				JOptionPane.showMessageDialog(null, "Carrinho vazio");
				return;
			}
			double total = 0;
			int itens = 0;
			for (int i = 0; i < tamanho; i++) {
				Produto p = cart.pop();
				Produto est = localizarProduto(p.id);
				if (p.qtd <= est.qtd) {
					caixa.insert(p);
					total += p.qtd * p.valor;
					itens++;
					System.out.println((i + 1) + ": " + String.format("%25s", p.nome) + 
						" - qtd: " + String.format("%4d", p.qtd) + " - Valor unit: R$ " +
						String.format("%7.2f", p.valor) + " - Valor total: R$ " +
						String.format("%9.2f", p.valor * p.qtd));
				} else {
					p.qtd = est.qtd;
					caixa.insert(p);
					total += p.qtd * p.valor;
					itens++;
					System.out.println((i + 1) + ": " + String.format("%25s", p.nome) + 
						" - qtd: " + String.format("%4d", p.qtd) + " - Valor unit: R$ " +
						String.format("%7.2f", p.valor) + " - Valor total: R$ " +
						String.format("%9.2f", p.valor * p.qtd) + " * Não há disponibilidade total");
				}
			}
			System.out.println("Total: R$ " + String.format("%.2f", total));
			String[] opcs = {"Registrar", "Cancelar"};
			int op = JOptionPane.showOptionDialog(
				null, itens + " itens\nTotal: R$ " + String.format("%.2f", total) +
				"\n\nSelecione a opção desejada", "Sistema",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
				opcs, opcs[1]);
			if (op == 1) {
				Pilha<Produto> aux = new Pilha<>();
				for (int i = 0; i < tamanho; i++) {
					aux.push(caixa.remove());
				}
				for (int i = 0; i < tamanho; i++) {
					cart.push(aux.pop());
				}
			}
			else
				checkout(caixa, total);
		} catch (Exception e) {
			System.err.println("Erro no menu do chekout");
		}
	}

	private void checkout(Fila<Produto> caixa, double total)
	{
		try {
			BufferedWriter w = new BufferedWriter(new FileWriter("Compras.csv", true));
			w.write(reg); w.write(";");
			w.write(Double.toString(total));
			int tamanho = caixa.size();
			for (int i = 0; i < tamanho; i++) {
				Produto p = caixa.remove();
				Produto est = localizarProduto(p.id);
				est.qtd -= p.qtd;
				if (est.qtd < 0)
					est.qtd = 0;
				w.write(";"); w.write(Integer.toString(p.id));
				w.write(";"); w.write(Integer.toString(p.qtd));
			}
			w.newLine();
			w.close();
			new ProdutoController().escreverProdutosNoCsv(tipos);
		} catch (Exception e) {
			System.err.println("Erro no checkout");
		}
	}
}
