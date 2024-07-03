package canal.campominado.modelo;

import java.util.*;

import canal.campominado.excecao.ExplosaoException;

public class Campo {

	private final int linha, coluna;
	private boolean minado = false, livre = false, marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;

	}

	boolean addVizinho(Campo vizinho) {
		boolean linhaDif = linha != vizinho.linha;
		boolean colunaDif = linha != vizinho.coluna;
		boolean diagonal = linhaDif && colunaDif;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {

			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alterMarcacao() {
		if (!livre) {
			marcado = !marcado;
		}
	}

	boolean abrir() {
		if (!livre && !marcado) {
			livre = true;
			if (minado) {
				throw new ExplosaoException();
			}
			if (vizinhancaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {

			return false;
		}
	}

	boolean vizinhancaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		minado = true;
	}

	public boolean isMinado() {
		return minado;
	}

	public boolean isMarcado() {
		return marcado;
	}

	
	public void setLivre(boolean livre) {
		this.livre = livre;
	}

	public boolean isLivre() {
		return livre;
	}

	public boolean isOcupado() {
		return !livre;
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && livre;
		boolean protegido = minado && marcado;
		return desvendado || protegido;
	}

	long minasVizinhas() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		livre = false;
		minado = false;
		marcado = false;
	}

	public String toString() {
		if (marcado) {
			return "x";
		} else if (livre && minado) {
			return "*";
		} else if (livre && minasVizinhas() > 0) {
			return Long.toString(minasVizinhas());
		} else if (livre)

		{
			return " ";
		} else {
			return "?";
		}
	}
}
