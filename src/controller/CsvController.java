package controller;

import model.*;
import view.Principal;
//import controller.*;
import lib.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CsvController implements ActionListener
{
	private JTextField tfConsultCompra;
	private Fila<ClienteCNPJ> pj;
	private Fila<ClienteCPF> pf;
	private CarrinhoController cc;
	private DefaultTableModel showItens;
	private Lista<ListaTipos<Produto>> tipos;
	
	public CsvController(Lista<ListaTipos<Produto>> tipos,JTextField tfConsultCompra, Fila<ClienteCNPJ> pj, Fila<ClienteCPF> pf,
			CarrinhoController carc, DefaultTableModel model) {
		super();
		this.tipos = tipos;
		this.tfConsultCompra = tfConsultCompra;
		this.pj = pj;
		this.pf = pf;
		this.cc = carc;
		this.showItens = model;
	}

public void actionPerformed(ActionEvent e) {
		
		String cmd = e.getActionCommand();
		switch (cmd)
		{
		case "Consultar":
			Object[] column = {"Nome","Quantidade", "Valor", "Total"};
			showItens.setColumnIdentifiers(column);
			showItens.setRowCount(0);
			int cod = Integer.parseInt(tfConsultCompra.getText());
			consultarCompra(cod);
			break;
		case "Listar Compras":
			Object[] columnListage = {"Nome","Cod da Compra", "Total"};
			showItens.setColumnIdentifiers(columnListage);
			showItens.setRowCount(0);
			listarCompras();
			break;
		}
		}


	public Lista<ListaTipos<Produto>> lerCsvTipos()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("Tipos.csv"));
			Lista<ListaTipos<Produto>> tipos = new Lista<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				TipoProduto t = new TipoProduto(
					Integer.parseInt(termos[0]), termos[1], termos[2]);
				ListaTipos<Produto> lista = new ListaTipos<>(t);
				tipos.addLast(lista);
				linha = br.readLine();
			}
			br.close();
			adicionarProdutosCsv(tipos);
			return tipos;
		} catch (Exception e) {
			//System.err.println("Erro ao ler tipos.csv");
			return new Lista<ListaTipos<Produto>>();
		}
	}

	public void adicionarProdutosCsv(Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Produtos.csv"));
			ProdutoController pc = new ProdutoController();
			String linha = r.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				int indice = pc.indiceDoTipo(tipos, Integer.parseInt(termos[5]));
				TipoProduto t = tipos.get(indice).getTipo();
				Produto p = new Produto(Integer.parseInt(termos[0]), termos[1],
					termos[2], Double.parseDouble(termos[3]), Integer.parseInt(termos[4]), t);
				tipos.get(indice).addLast(p);
				linha = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao adicionar produtos do csv");
		}
	}

	public Fila<ClienteCPF> adicionarClientePF()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("ClientesCPF.csv"));
			Fila<ClienteCPF> pf = new Fila<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				ClienteCPF cl = new ClienteCPF(termos[0], termos[1], termos[2],
					termos[3],termos[4],termos[5]);
				pf.insert(cl);
				linha = br.readLine();
			}
			br.close();
			return pf;
		} catch (Exception e) {
			System.err.println("Erro ao ler clientepf.csv");
			return new Fila<ClienteCPF>();
		}
	}

	public Fila<ClienteCNPJ> adicionarClientePJ()
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader ("ClientesCNPJ.csv"));
			Fila<ClienteCNPJ> pj = new Fila<>();
			String linha = br.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				ClienteCNPJ cl = new ClienteCNPJ(termos[0], termos[1], termos[2],
					termos[3], termos[4], termos[5]);
				pj.insert(cl);
				linha = br.readLine();
			}
			br.close();
			return pj;
		} catch (Exception e) {
			System.err.println("Erro ao ler clientepj.csv");
			return new Fila<ClienteCNPJ>();
		}
	}

	public void adicionarCarrinhos(Fila<ClienteCPF> pf, Fila<ClienteCNPJ> pj,
		CarrinhoController c, Lista<ListaTipos<Produto>> tipos)
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Carrinho.csv"));
			String linha = r.readLine();
			while (linha != null) {
				String[] termos = linha.split(";");
				if (termos[0].length() == 11) {
					ClienteCPF clienteF = localizaClientePF(termos[0]);
					int i = 1;
					while (i < termos.length) {
						Produto p = c.localizarProduto(Integer.parseInt(termos[i++]));
						Produto add = c.copiarProduto(p, Integer.parseInt(termos[i++]));
						clienteF.carrinho.push(add);
					}
				}
				if (termos[0].length() == 14) {
					ClienteCNPJ clienteJ = localizaClientePJ(termos[0]);
					int i = 1;
					while (i < termos.length) {
						Produto p = c.localizarProduto(Integer.parseInt(termos[i++]));
						Produto add = c.copiarProduto(p, Integer.parseInt(termos[i++]));
						clienteJ.carrinho.push(add);
					}
				}
				linha = r.readLine();
			}
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao ler carrinhos csv");
		}
	}

	public void listarCompras()
	{
		try {
			BufferedReader r = new BufferedReader(new FileReader("Compras.csv"));
			String linha = r.readLine();
			String nome;
			int i = 1;
			if (linha == null) {
				JOptionPane.showMessageDialog(null, "Não há Compras");
				r.close();
				return;
			}
			while (linha != null) {
				String[] termos = linha.split(";");
				if (termos[0].length() == 11) {
					nome = localizaClientePF(termos[0]).nome;
				} else {
					nome = localizaClientePJ(termos[0]).nome;
				}
				Object item [] = {	
						nome
						,String.format("%3d", ((termos.length - 2) / 2))
						,String.format("%7.2f", Double.parseDouble(termos[1]))
						};
				showItens.addRow(item);	
							
				linha = r.readLine();
			}
			System.out.println("-----------------------");
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao listar compras");
		}
	}

	public void consultarCompra(int num)
	{
		try {
			
			boolean encontrado = false;
			String nome;
			BufferedReader r = new BufferedReader(new FileReader("Compras.csv"));
			String linha = r.readLine();
			int i = 1;
			while (linha != null && i <= num) {
				if (i == num) {
					String[] termos = linha.split(";");
					if (termos[0].length() == 11) {
						nome = localizaClientePF( termos[0]).nome;
					} else {
						nome = localizaClientePJ( termos[0]).nome;
					}
					System.out.println("Cliente: " + nome);
					System.out.println(String.format("%3d", ((termos.length - 2) / 2)) +
					" itens - Total: R$ " + String.format("%7.2f", Double.parseDouble(termos[1])));
					int tamanho = termos.length;
					int j = 2;
					while (j < tamanho) {
						Produto p = cc.localizarProduto(Integer.parseInt(termos[j++]));
						
						if (p!=null) {Object item [] = {	
								p.nome
								,termos[j]
								,p.valor
								,String.format("%10.2f", p.valor * Integer.parseInt(termos[j++]))
								};
						
						showItens.addRow(item);	
						System.out.println(String.format("%25s", p.nome) +
							" - " + String.format("%3d", Integer.parseInt(termos[j])) +
							" x R$ " + String.format("%7.2f", p.valor) + " = R$ " +
							String.format("%10.2f", p.valor * Integer.parseInt(termos[j++])));
						}
					}
					encontrado = true;
				}
				linha = r.readLine();
				i++;
			}
			if (!encontrado)
				JOptionPane.showMessageDialog(null, "Compra não encontrada");
			r.close();
		} catch (Exception e) {
			System.err.println("Erro ao consultar compra");
		}
	}
	
	public ClienteCPF localizaClientePF(String cpf)
	{
		try {	
			ClienteCPF retorno = null;
			int tamanho = pf.size();
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCPF cliente = pf.remove();
					if (cpf.equals(cliente.cpf)) {
						retorno = cliente;
					}
					pf.insert(cliente);
				}
			}
			if (retorno == null) {
				JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
				return null;
			}
			else return retorno;
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
			return null;
		}
	}

	public ClienteCNPJ localizaClientePJ(String cnpj)
	{
		try {	
			ClienteCNPJ retorno = null;
			int tamanho = pj.size();
			if (tamanho > 0) {
				for (int i = 0; i < tamanho; i++) {
					ClienteCNPJ cliente = pj.remove();
					if (cnpj.equals(cliente.cnpj)) {
						retorno = cliente;
					}
					pj.insert(cliente);
				}
			}
			if (retorno == null) {
				JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
				return null;
			}
			else return retorno;
		} catch (Exception e) {
			System.err.println("Erro ao consultar cliente por cpf");
			return null;
		}
	}



	
}
