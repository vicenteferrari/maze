package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
/*
Definimos el grado de un nodo como el numero de vecinos al que ese nodo conecta.
Luego queremos comparar pares de nodos de piezas distinguibles. Inicialmente, dos piezas son distinguibles si su grado
es distinto.

Despues, comparamos pares que no han sido marcados distinguibles, i.e. son de la misma clase de equivalencia.
Suponga que x tiene un vecino A e y tiene un vecino B, tal que A y B han sido marcados distinguibles, i.e. son de clases distintas.
Entonces marque x e y como distinguibles. Repetir hasta que no queden nodos que se pueden marcar distinguibles.
*/

	// el laberinto esta hecho de varios nodos
	private List<Nodo> nodos = new ArrayList<Nodo>();

	// el laberinto tambien tiene una lista variable de clases de equivalencia
	private List<Clase> clases = new ArrayList<Clase>();

	private int cantidadNodos;

	private int gradoMasAlto;
	// luego poner cada nodo en su conjunto con su grado. no se si usar Set o Conjunto...

	// todo: gotta implemet a conjunto class to be able to do proper copy and equals.

	// la clase maze puede construirse con un String bien formado

	Maze(String input) {
		generarNodos(input);
	}

	private void generarNodos(String input) {
		String[] partes = input.split("\n");

		this.cantidadNodos = Integer.parseInt(partes[0]);

		for (int i = 1; i < partes.length; i++) {
			this.nodos.add(new Nodo(this, i));
		}

		for (int i = 1; i < partes.length; i++) {
			String[] partesDePartes = partes[i].split(" ");

			for (int j = 1; j < partesDePartes.length; j++) {
				Nodo n = this.nodos.get(Integer.parseInt(partesDePartes[j]) - 1);
				if (!this.nodos.get(i - 1).getVecinos().contains(n)) {
					this.nodos.get(i - 1).agregarVecino(n);
				}
			}
		}

		this.cantidadNodos = this.nodos.size();
		gradoMasAlto();
	}

	public void resolver() {

		hacerConjuntos();
		primeraPasada();
		System.out.println("Conjuntos despues de la primera pasada: " + clases.toString());
		siguientesPasadas();
		//ultimaPasada();
	}

	private void hacerConjuntos() {
		for (int i = 0; i <= this.gradoMasAlto; i++) {
			this.clases.add(new ArrayList<Nodo>());
		}
	}

	private void eliminarConjuntosVacios() {
		for (int i = 0; i < this.conjuntos.size(); i++) {
			if (this.conjuntos.get(i).size() == 0) {
				this.conjuntos.remove(this.conjuntos.get(i));
				i--;
			}
		}
	}

	private void primeraPasada() {
		for (Nodo n : this.nodos) {
			this.conjuntos.get(n.getGrado()).add(n);
			n.setCurrentSet(this.conjuntos.get(n.getGrado()));
		}

		eliminarConjuntosVacios();
	}

	private void siguientesPasadas() {
		boolean isReady = false;

		List<List<Nodo>> claseAnterior = new ArrayList<List<Nodo>>();

		for (List<Nodo> l : this.conjuntos) {
			claseAnterior.add(new ArrayList<Nodo>());
			for (Nodo n : l) {
				claseAnterior.get(this.conjuntos.indexOf(l)).add(n.clone());
			}
		}

		boolean test = this.conjuntos.equals(claseAnterior);

		while (!isReady) {

			// en este momento tenemos los conjuntos por grados
			// tenemos que comparar los elementos del primer conjunto entre si
			// los conjuntos estan en una lista ordenada.
			// para exhaustar las posibilidades guardaremos el primer conjunto que se intento y lo pondremos al final de
			// la pila. seguiremos intentando con el conjunto mas arriba de la lista y poniendolo al final
			// cuando lleguemos al mismo del principio paramos y hacemos un nuevo conjunto, ya que parabamos el loop si el elemtno cabia en otro conjunto
			// en este ejemplo: {1, 3, 5, 6, 7, 8, 9, 10, 11, 12 ,13}, {2, 4}
			// comparamos 1 con 3, 1 con 5, 1 con 6, 1 con 7, etc.
			// si 1 con 3 es falso y hay mas conjuntos comparamos con esos hasta tratar todos
			// si no se encuentra conjunto donde cabe el 3 se crea uno nuevo



			for(int i = this.conjuntos.size() - 1; i >= 0; i--) {
				List<Nodo> currentSet = this.conjuntos.get(i);

				for (int j = 1; j < currentSet.size(); j++) {
					if (!sonEquivalentes(currentSet.get(0), currentSet.get(j))) {
						Nodo nodoTemp = currentSet.get(j);
						currentSet.remove(nodoTemp);
						j--;
						List<Nodo> listaTemp = currentSet;
						this.conjuntos.remove(listaTemp);
						this.conjuntos.add(0, listaTemp);

						boolean fits = true;
						for (int k = 0; k < this.conjuntos.size(); k++) {
							if(sonEquivalentes(this.conjuntos.get(k).get(0), nodoTemp)) {
								this.conjuntos.get(k).add(nodoTemp);
								break;
							} else if (k == this.conjuntos.size() - 1) {
								fits = false;
							}
						}

						if (!fits) {
							this.conjuntos.add(new ArrayList<Nodo>());
							this.conjuntos.get(this.conjuntos.size() - 1).add(nodoTemp);
						}
					}
				}
			}

			if (claseAnterior.equals(this.conjuntos)) {
				isReady = true;
			}
		}

	}

	private void ultimaPasada() {
		// cuando terminar las pasadas hay que revisar todos los conjuntos que quedan
		// se eliminan los conjuntos con solo 1 elemento
		// luego revisamos todos los que quedan, revisamos todas las combinaciones posibles de estos.
		// es decir si tenemos conjunto {2, 5, 7} debemos revisar (2,5), (2, 7), (5, 7).
		// formamos los arboles de cada elemento del par y revisamos su orden.
		// si el orden de estos no es el mismo, es decir, las clases a las que pertenecen los
		// elementos del arbol cambia, este par no es equivalente
	}

	private boolean sonEquivalentes(Nodo nodo1, Nodo nodo2) {
		if(nodo1.getGrado() != nodo2.getGrado()) return false;
		for (int i = 0; i < nodo1.getVecinos().size(); i++) {
			for (int j = 0; j < nodo2.getVecinos().size(); j++) {
				if (i == j) {
					if (!nodo1.getVecinos().get(i).getCurrentSet().equals(nodo2.getVecinos().get(j).getCurrentSet())) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public String toString() {
		String ret = "";

		for (int i = 0; i < this.conjuntos.size(); i++) {
			for (int j = 0; j < this.conjuntos.get(i).size(); j++) {
				if(j != this.conjuntos.get(i).size() - 1) {
					ret += this.conjuntos.get(i).get(j) + " ";
				} else {
					ret += this.conjuntos.get(i).get(j);
				}
			}

			if (i != this.conjuntos.size() - 1) {
				ret += "\n";
			}
		}

		return ret;
	}

	private List<Nodo[]> removerDuplicados(List<Nodo[]> lista) {
		List<Nodo[]> ret = new ArrayList<Nodo[]>();

		for (Nodo[] n : lista) {
			if (!ret.contains(n)) {
				ret.add(n);
			}
		}

		return ret;
	}

	private void gradoMasAlto() {
		for (Nodo n : this.nodos) {
			if (n.getGrado() > this.gradoMasAlto) {
				this.gradoMasAlto = n.getGrado();
			}
		}
	}
}
