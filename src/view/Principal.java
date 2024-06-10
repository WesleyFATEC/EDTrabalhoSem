package view;

import javax.swing.JOptionPane;

import controller.CarrinhoController;
import controller.ClienteController;
import controller.CsvController;
import controller.TipoController;
import lib.Fila;
import lib.Lista;
import lib.ListaTipos;
import lib.Pilha;
import model.ClienteCNPJ;
import model.ClienteCPF;
import model.Produto;

public class Principal {

	public static void main(String[] args) {
		CsvController csvc = new CsvController(null, null, null, null, null, null);
		TipoController tc = new TipoController(null, null, null, null, null, null, null);
		Lista<ListaTipos<Produto>> tipos = csvc.lerCsvTipos();
		ClienteController cc = new ClienteController();
		CarrinhoController carc = new CarrinhoController(null, null, null, tipos, null, null, null, null, null);
		Fila<ClienteCNPJ> pj = csvc.adicionarClientePJ();
		Fila<ClienteCPF> pf = csvc.adicionarClientePF();
		
		
		
		
		String[] opcs = {"BackOffice", "Cliente", "sair"};
		
		 while (true) {
	            int op = JOptionPane.showOptionDialog(
	                    null, "Selecione o tipo de usuário",
	                    null, JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
	                    opcs, opcs[0]);
	            if (op == 0) {
	                Tela mainTela = new Tela(tipos, pf, pj, carc);
	                mainTela.setVisible(true);
	                break;
	            } else if (op == 1) {
	                TelaCart.verifClient(pf, pj, tipos);
	                break;
	            } else {
	                System.exit(0);
	            }
	        }
	}
	
	
}
