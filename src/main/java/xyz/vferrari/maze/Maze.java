package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
/*
	Definimos el grado de un nodo como el numero de vecinos al que ese nodo conecta.
	Luego queremos comparar pares de nodos de piezas distinguibles. Inicialmente, dos piezas son distinguibles si su grado
	es distinto.

	Despues, comparamos pares que no han sido marcados distinguibles, i.e. son de la misma clase de equivalencia.
	Suponga que x tiene un vecino A e y tiene un vecino B, tal que A y B han sido marcados distinguibles, i.e. son de equivalencias distintas.
	Entonces marque x e y como distinguibles. Repetir hasta que no queden nodos que se pueden marcar distinguibles.
*/

	// el laberinto esta hecho de varios nodos
	private ArrayList<Nodo> nodos = new ArrayList<Nodo>();
	private int cantidadNodos;
	private int gradoMasAlto;
	private String input;

	// el laberinto tambien tiene una lista variable de equivalencias
	private ArrayList<Equivalencia> equivalencias = new ArrayList<Equivalencia>();


	// la clase maze puede construirse con un String bien formado

	Maze(String input) {
		this.input = input;
		generarNodos(input);
		gradoMasAlto();
		init();
		resolver();
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

		for (int i = 0; i < this.nodos.size(); ++i) {
			this.nodos.get(i).generarArbol();
		}
	}

	public void resolver() {
		generarEquivalencia0();
		this.equivalencias.get(0).eliminarClasesVacias();
		siguientesPasadas();
	}

	private void generarEquivalencia0() {
		for (Nodo n : this.nodos) {
			this.equivalencias.get(0).getClase(n.getGrado()).add(n);
			n.setClase(this.equivalencias.get(0).getClase(n.getGrado()));
		}
	}

	private void init() {
		this.equivalencias.add(new Equivalencia());
		for (int i = 0; i <= this.gradoMasAlto; i++) {
			this.equivalencias.get(0).addClase(new Clase());
		}
	}

	private void siguientesPasadas() {

		/*
			Queremos generar nuevas equivalencias partiendo de la equivalencia 0.
			Terminamos cuando la equivalencia n es igual a la equivalencia n-1
		 */

		int index = 0;

		while (true) {
			Equivalencia current = this.equivalencias.get(index);

			// siguiente equivalencia
			this.equivalencias.add(current.reducir());

			if (index > 0)
				if (!compareEquivalencias(this.equivalencias.get(index), this.equivalencias.get(index - 1))) {
					ultimaPasada(this.equivalencias.get(this.equivalencias.size() - 1));
					break;
				}

			++index;
		}

	}

	private void generarSiguienteClase() {
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


	}

	private void ultimaPasada(Equivalencia equivalencia) {
		//primero nos aseguramos que los nodos tengan las clases correctas.

		Equivalencia ultima = this.equivalencias.get(this.equivalencias.size() - 1);

		for (int i = 0; i < ultima.size(); ++i) {
			Clase clase = ultima.getClase(i);

			for (int j = 0; j < clase.size(); ++j) {
				clase.get(j).setClase(clase);
			}
		}

		// cuando terminar las pasadas hay que revisar todos los conjuntos que quedan
		// se eliminan los conjuntos con solo 1 elemento
		// luego revisamos todos los que quedan, revisamos todas las combinaciones posibles de estos.
		// es decir si tenemos conjunto {2, 5, 7} debemos revisar (2,5), (2, 7), (5, 7).
		// formamos los arboles de cada elemento del par y revisamos su orden.
		// si el orden de estos no es el mismo, es decir, las equivalencias a las que pertenecen los
		// elementos del arbol cambia, este par no es equivalente

		removerClasesSolas(equivalencia);

		// luego de remover las clases con 1 solo elemento revisamos las restante, generando los arboles de todos los nodos
		// y comparando estos arboles buscando diferencias en el orden de las clases
		for (int i = 0; i < equivalencia.size(); ++i) {
			for (int j = 0; j < equivalencia.getClase(i).realSize(); ++j) {
				for (int k = 0; k < equivalencia.getClase(i).realSize(); ++k) {
					if (j != k) {
						ArbolNodo nodo1 = equivalencia.getClase(i).get(j).getArbol().getRaiz();
						ArbolNodo nodo2 = equivalencia.getClase(i).get(k).getArbol().getRaiz();
						if (!compareTrees(nodo1, nodo2)) {
							equivalencia.getClase(i).remove(nodo2.getElemento());
						}
					}
				}
			}
		}

		removerClasesSolas(equivalencia);

	}

	private void removerClasesSolas(Equivalencia equivalencia) {
		for (int i = 0; i < equivalencia.size(); ++i) {
			Clase currentClase = equivalencia.getClase(i);

			if (currentClase.realSize() == 1) {
				equivalencia.remove(i);
				--i;
			}
		}
	}

	private boolean compareTrees(ArbolNodo nodo1, ArbolNodo nodo2) {
		if (!nodo1.getElemento().getClase().equals(nodo2.getElemento().getClase())) return false;

		if (nodo1.getHijos().size() != 0 && nodo2.getHijos().size() != 0) {
			for (int i = 0; i < nodo1.getHijos().size(); ++i) {
				return compareTrees(nodo1.getHijos().get(i), nodo2.getHijos().get(i));
			}
		}


		return true;
	}

	private boolean compareEquivalencias(Equivalencia e1, Equivalencia e2) {
		for (int i = 0; i < e1.getClases().size(); ++i) {
			for (int j = 0; j < e2.getClases().size(); ++j) {
				if(!compareClases(e1.getClase(i), e2.getClase(j))) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean compareClases(Clase c1, Clase c2) {
		for (int i = 0; i < c1.realSize(); ++i) {
			for (int j = 0; j < c2.realSize(); ++j) {
				if (!c1.get(i).equals(c2.get(j))) {
					return false;
				}
			}
		}

		return true;
	}

	public String toString() {
		String ret = "";

		Equivalencia ultima = this.equivalencias.get(this.equivalencias.size() - 1);

		if (ultima.size() == 0) {
			ret += "none";
		} else {
			for (int i = 0; i < ultima.getClases().size(); ++i) {
				ret += ultima.getClase(i).toString();
				ret += "\n";
			}
		}

		return ret;

//		Equivalencia ultimaEquivalencia = this.equivalencias.get(this.equivalencias.size() - 1);
//
//		for (int i = 0; i < ultimaEquivalencia.getClases().size(); i++) {
//			Clase clase = ultimaEquivalencia.getClase(i);
//
//			for (int j = 0; j < clase.size(); j++) {
//				if(j != clase.size() - 1) {
//					ret += clase.get(j) + " ";
//				} else {
//					ret += clase.get(j);
//				}
//			}
//
//			if(i++ == clase.size() - 1){
//				ret += "\n";
//			}
//		}
//
//		return ret;
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

	public int getCantidadNodos() {
		return cantidadNodos;
	}

	public void setCantidadNodos(int cantidadNodos) {
		this.cantidadNodos = cantidadNodos;
	}

	public Nodo[] getNodos() {
		Nodo[] nodos = new Nodo[this.cantidadNodos];

		for (int i = 0; i < this.nodos.size(); ++i) {
			nodos[i] = this.nodos.get(i);
		}

		return nodos;
	}

	public void setNodos(ArrayList<Nodo> nodos) {
		this.nodos = nodos;
	}

	private void gradoMasAlto() {
		for (Nodo n : this.nodos) {
			if (n.getGrado() > this.gradoMasAlto) {
				this.gradoMasAlto = n.getGrado();
			}
		}
	}
}
