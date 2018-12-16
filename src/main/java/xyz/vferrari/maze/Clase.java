package xyz.vferrari.maze;

import java.util.List;
import java.util.ArrayList;

public class Clase {

	private List<List<Nodo>> conjuntos = new ArrayList<List<Nodo>>();

	Clase(List<Nodo>... conjuntos) {
		for (List<Nodo> l : conjuntos) {
			this.conjuntos.add(new ArrayList<Nodo>(l));
		}
	}

	Clase(Clase clase) {
		this.conjuntos = clase.getConjuntos();
	}

	public List<Nodo> getConjunto(int index) {
		return this.conjuntos.get(index);
	}

	public List<List<Nodo>> getConjuntos() {
		return conjuntos;
	}

	public void setConjuntos(List<List<Nodo>> conjuntos) {
		this.conjuntos = conjuntos;
	}
}
