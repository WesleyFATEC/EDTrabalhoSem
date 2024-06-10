package model;

import lib.Pilha;

public class ClienteCNPJ
{
	public String cnpj;
	public String nome;
	public String endereco;
	public String cep;
	public String telefone;
	public String email;
	public Pilha<Produto> carrinho;
	
	public ClienteCNPJ()
	{
		super();
	}
	
	public ClienteCNPJ(String cnpj, String nome, String endereco, String telefone, String email, String cep)
	{
		super();
		this.cnpj = cnpj;
		this.nome = nome;
		this.endereco = endereco;
		this.cep = cep;
		this.telefone = telefone;
		this.email = email;
		carrinho = new Pilha<>();
	}
}
