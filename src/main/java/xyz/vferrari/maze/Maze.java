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

	// el laberinto tambien tiene una lista variable de equivalencias de equivalencia
	private ArrayList<Equivalencia> equivalencias = new ArrayList<Equivalencia>();

	// luego poner cada nodo en su conjunto con su grado. no se si usar Set o Clase...

	// todo: gotta implemet a conjunto class to be able to do proper copy and equals.

	// la clase maze puede construirse con un String bien formado

	Maze(String input) {
		generarNodos(input);
		gradoMasAlto();
		init();
		resolver();
	}

	private void generarNodos(String input) {
		String[] partes = input.split("\n");

		this.cantidadNodos = Integer.parseInt(partes[0]);

		for (int i = 1; i < partes.length; i++) {
			this.nodos.add(new Nodo(i));
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
	}

	public void resolver() {
		generarEquivalencia0();
		this.equivalencias.get(0).eliminarClasesVacias();
		System.out.println("Equivalencia 0: " + this.toString());
		siguientesPasadas();
		//ultimaPasada();
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
			Queremos generar nuevas equivalencias partiendo de la clase 0.
			Terminamos cuando la clase n es igual a la nclase n-1
		 */

		int index = 0;

		while (true) {
			Equivalencia current = this.equivalencias.get(index);

			// siguiente equivalencia
			this.equivalencias.add(current.reducir());

			if (index > 0)
				if (this.equivalencias.get(index).equals(this.equivalencias.get(index - 1)))
					break;

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

	private void ultimaPasada() {
		// cuando terminar las pasadas hay que revisar todos los conjuntos que quedan
		// se eliminan los conjuntos con solo 1 elemento
		// luego revisamos todos los que quedan, revisamos todas las combinaciones posibles de estos.
		// es decir si tenemos conjunto {2, 5, 7} debemos revisar (2,5), (2, 7), (5, 7).
		// formamos los arboles de cada elemento del par y revisamos su orden.
		// si el orden de estos no es el mismo, es decir, las equivalencias a las que pertenecen los
		// elementos del arbol cambia, este par no es equivalente
	}



	public String toString() {
		String ret = "";

		for (Equivalencia e : equivalencias) {
			for (Clase c : e.getClases()) {
				ret += c.toString();
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

	private void gradoMasAlto() {
		for (Nodo n : this.nodos) {
			if (n.getGrado() > this.gradoMasAlto) {
				this.gradoMasAlto = n.getGrado();
			}
		}
	}
}
