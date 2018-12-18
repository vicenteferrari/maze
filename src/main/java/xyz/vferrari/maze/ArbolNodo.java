package xyz.vferrari.maze;

import java.util.ArrayList;

public class ArbolNodo {
	private Nodo elemento;
	private int id;
	private Arbol arbol;
	private Nodo padre;
	private ArrayList<ArbolNodo> hijos = new ArrayList<ArbolNodo>();

	ArbolNodo(Nodo elemento) {
		this.elemento = elemento;
		this.id = elemento.getId();
	}

	ArbolNodo(Nodo elemento, Arbol arbol) {
		this.arbol = arbol;
		this.elemento = elemento;
		this.id = elemento.getId();
	}

	public void insertarHijo(ArbolNodo nodo) {
		this.hijos.add(nodo);
		this.arbol.insertar(nodo);
	}

	public void generarArbol() {
		for (int i = 0; i < this.elemento.getVecinos().size(); ++i) {
			ArbolNodo nuevo = new ArbolNodo(this.elemento.getVecinos().get(i), this.arbol);
			this.arbol.getRaiz().insertarHijo(nuevo);
			generarArbol(nuevo, this.arbol.getRaiz().getElemento().getVecinos().get(i).getVecinos().indexOf(this.arbol.getRaiz().getElemento()));
		}
	}

	public void generarArbol(ArbolNodo nodo, int index) {
		// queremos insertarlos en orden.
		// si entro a 1 desde 3 a la izquierda tengo 5 y a la der tengo 4.
		// si entro a 1 desde 4, a la izq tengo 3 y a la der tengo 5.

		for (int i = index; i < nodo.getElemento().getVecinos().size(); ++i) {
			if (!this.arbol.contains(nodo.getElemento().getVecinos().get(i))) {
				ArbolNodo nuevo = new ArbolNodo(nodo.getElemento().getVecinos().get(i), this.arbol);
				nodo.insertarHijo(nuevo);
				generarArbol(nuevo, nodo.getElemento().getVecinos().get(i).getVecinos().indexOf(nodo.getElemento()));
			}
		}

		for (int i = 0; i <= index; ++i) {
			if (!this.arbol.contains(nodo.getElemento().getVecinos().get(i))) {
				ArbolNodo nuevo = new ArbolNodo(nodo.getElemento().getVecinos().get(i), this.arbol);
				nodo.insertarHijo(nuevo);
				generarArbol(nuevo, nodo.getElemento().getVecinos().get(i).getVecinos().indexOf(nodo.getElemento()));
			}
		}

	}

	public Nodo getElemento() {
		return elemento;
	}

	public void setElemento(Nodo elemento) {
		this.elemento = elemento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Arbol getArbol() {
		return arbol;
	}

	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}

	public Nodo getPadre() {
		return padre;
	}

	public void setPadre(Nodo padre) {
		this.padre = padre;
	}

	public ArrayList<ArbolNodo> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<ArbolNodo> hijos) {
		this.hijos = hijos;
	}
}
