package xyz.vferrari.maze;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Arbol {
	private Maze maze;
	private ArbolNodo raiz;
	private int size;

	private HashMap<Integer, Nodo> m;

	public Arbol(ArbolNodo raiz, Maze maze) {
		m = new HashMap<Integer, Nodo>();
		this.raiz = raiz;
		this.maze = maze;
		m.put(this.raiz.getId(), this.raiz.getElemento());
		++size;
	}

	public Arbol() {
		this.raiz = null;
	}

	public boolean contains(Nodo nodo) {
		return m.containsValue(nodo);
	}

	public boolean contains(ArbolNodo nodo) {
		return m.containsKey(nodo);
	}

	public void insertar(ArbolNodo nodo) {
		m.put(nodo.getId(), nodo.getElemento());
		++size;
	}

	public ArbolNodo getRaiz() {
		return raiz;
	}

	public void setRaiz(ArbolNodo raiz) {
		this.raiz = raiz;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public Maze getMaze() {
		return maze;
	}

	public void setMaze(Maze maze) {
		this.maze = maze;
	}
}
