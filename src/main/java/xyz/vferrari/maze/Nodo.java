package xyz.vferrari.maze;

import java.util.ArrayList;

public class Nodo {

	private Clase clase;

	// un nodo tiene varios vecinos
	private ArrayList<Nodo> vecinos = new ArrayList<Nodo>();

	private int id;
	private int grado;

	private String input;

	Nodo(int id) {
		this.id = id;
	}



	public boolean equivalentes(Nodo nodo2) {
		if(this.getGrado() != nodo2.getGrado()) return false;

		for (int i = 0; i < this.getVecinos().size(); i++) {
			for (int j = 0; j < nodo2.getVecinos().size(); j++) {
				if (i == j) {
					if (!this.getVecinos().get(i).getClase().equals(nodo2.getVecinos().get(j).getClase())) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public Nodo clone() {
		Nodo n = new Nodo(this.id);
		return n;
	}

	public boolean equals(Nodo n) {
		if (n.getId() == this.id) {
			return true;
		} else {
			return false;
		}
	}

	public void agregarVecino(Nodo nodo) {
		this.vecinos.add(nodo);
		this.grado = this.vecinos.size();
	}

	public Clase getClase() {
		return clase;
	}

	public void setClase(Clase clase) {
		this.clase = clase;
	}

	public String toString() {
		String ret = "";

		ret += this.id;

		return ret;
	}

	public ArrayList<Nodo> getVecinos() {
		return vecinos;
	}

	public void setVecinos(ArrayList<Nodo> vecinos) {
		this.vecinos = vecinos;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}
}
