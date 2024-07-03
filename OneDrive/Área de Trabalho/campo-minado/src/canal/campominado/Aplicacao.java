package canal.campominado;

import canal.campominado.modelo.Tabuleiro;
import canal.campominado.visao.TabuleiroConsole;

public class Aplicacao {
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(6,6,6);
		
		new TabuleiroConsole(tabuleiro);
		
		
	}
}
