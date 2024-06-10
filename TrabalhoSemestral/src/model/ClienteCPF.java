package model;

import lib.Pilha;

public class ClienteCPF
{
	public String cpf;
	public String nome;
	public String endereco;
	public String celular;
	public String cep;
	public String email;
	public Pilha<Produto> carrinho;
	
	public ClienteCPF()
	{
		super();
	}
	
	public ClienteCPF(String cpf, String nome, String endereco, String celular, String email, String cep)
	{
		super();
		this.cpf = cpf;
		this.nome = nome;
		this.endereco = endereco;
		this.celular = celular;
		this.cep = cep;
		this.email =  email;
		carrinho = new Pilha<>();
	}
}
