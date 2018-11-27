package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Maze {
    // Define the degree of a room to be the number of rooms to which it connects. You will want to compare pairs of
    // distinct rooms. Initially, two rooms are distinguishable if their degrees are different. Now on the second iteration,
    // look at rooms x, y that you have not marked as distinguishable. Suppose that x has a neighbor A and y neighbor B,
    // such that A and B have been marked distinguishable. Then mark x and y as distinguishable. Repeat until no more
    // nodes can be marked as distinguishable.

    // el laberinto esta hecho de varios nodos
    private List<Nodo> nodos = new ArrayList<Nodo>();
    private String input;

    private List<List<Nodo>> conjuntos = new ArrayList<List<Nodo>>();
    private List<List<Nodo>> conjuntosAnteriores = new ArrayList<List<Nodo>>();

    private int cantidadNodos;
    private int gradoMasAlto = 0; // TODO: implementar - luego hacer conjuntos para todos los grados hasta el mas alto
                              // luego poner cada nodo en su conjunto con su grado. no se si usar Set o Conjunto...
    private String solution;

    // la clase maze puede construirse con un String bien formateado

    Maze(String input) {
        this.input = input;
        generarNodos();
        this.cantidadNodos = this.nodos.size();

        solve();
    }

    private void generarNodos() {
        String[] partes = this.input.split("\n");

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
    }

    public void solve() {
        String output = "";

        gradoMasAlto();
        hacerConjuntos();
        primeraPasada();
        System.out.println("Conjuntos despues de la primera pasada: " + this.conjuntos.toString());
        siguientesPasadas();
    }

    private void hacerConjuntos() {
        for (int i = 0; i <= this.gradoMasAlto; i++) {
            this.conjuntos.add(new ArrayList<Nodo>());
        }
    }

    private void primeraPasada() {
        for (Nodo n : this.nodos) {
            this.conjuntos.get(n.getGrado()).add(n);
            n.setCurrentSet(this.conjuntos.get(n.getGrado()));
        }
    }

    private void siguientesPasadas() {
        eliminarConjuntosVacios();
        System.out.println("Conjuntos despues de eliminar vacios: " + this.conjuntos.toString());

        boolean isReady = false;

        while (!isReady) {
            this.conjuntosAnteriores = this.conjuntos;

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
                    if (areNodesEquivalent(currentSet.get(0), currentSet.get(j))) {
                        continue;
                    } else {
                        Nodo nodoTemp = currentSet.get(j);
                        currentSet.remove(nodoTemp);
                        j--;
                        List<Nodo> listaTemp = currentSet;
                        this.conjuntos.remove(listaTemp);
                        this.conjuntos.add(0, listaTemp);

                        boolean fits = true;
                        for (int k = 0; k < this.conjuntos.size(); k++) {
                            if(areNodesEquivalent(this.conjuntos.get(k).get(0), nodoTemp)) {
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

            if (this.conjuntosAnteriores.equals(this.conjuntos)) {
                isReady = true;
            }
        }

    }

//    public String getSolucion() {}

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

    private void eliminarConjuntosVacios() {
        for (int i = 0; i < this.conjuntos.size(); i++) {
            if (this.conjuntos.get(i).size() == 0) {
                this.conjuntos.remove(this.conjuntos.get(i));
                i--;
            }
        }
    }

    private boolean areNodesEquivalent (Nodo nodo1, Nodo nodo2) {
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

    private void gradoMasAlto() {
        for (Nodo n : this.nodos) {
            if (n.getGrado() > this.gradoMasAlto) {
                this.gradoMasAlto = n.getGrado();
            }
        }
    }

    public List<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    public int getCantidadNodos() {
        return cantidadNodos;
    }

    public void setCantidadNodos(int cantidadNodos) {
        this.cantidadNodos = cantidadNodos;
    }

    public int getGradoMasAlto() {
        return gradoMasAlto;
    }

    public void setGradoMasAlto(int gradoMasAlto) {
        this.gradoMasAlto = gradoMasAlto;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }
}
