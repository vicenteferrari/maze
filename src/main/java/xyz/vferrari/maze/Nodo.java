package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

	// es parte de un laberinto
	private Maze maze;
	private List<Nodo> currentSet;

	// un nodo tiene varios vecinos
	private List<Nodo> vecinos = new ArrayList<Nodo>();

	private int id;
	private int grado;

	private String input;

	Nodo(Maze maze, int id) {
		this.maze = maze;
		this.id = id;
	}

	Nodo(int id) {
		this.id = id;
	}

	public Nodo clone() {
		return new Nodo(this.maze, this.id);
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

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}

	public List<Nodo> getCurrentSet() {
		return currentSet;
	}

	public void setCurrentSet(List<Nodo> currentSet) {
		this.currentSet = currentSet;
	}

	public String toString() {
		String ret = "";

		ret += this.id;

		return ret;
	}

	public List<Nodo> getVecinos() {
		return vecinos;
	}

	public void setVecinos(List<Nodo> vecinos) {
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
