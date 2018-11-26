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

    private int cantidadNodos;
    private int gradoMasAlto; // TODO: implementar - luego hacer conjuntos para todos los grados hasta el mas alto
                              // luego poner cada nodo en su conjunto con su grado. no se si usar Set o Conjunto...

    // la clase maze puede construirse con un String bien formateado

    Maze(String nodos) {
        generarListaNodos(nodos);
        this.cantidadNodos = this.nodos.size();
    }

    private void generarListaNodos(String nodos) {
        String[] partes = nodos.split("\n");

        this.cantidadNodos = Integer.parseInt(partes[0]);

        for (int i = 1; i < partes.length; i++) {
            this.nodos.add(new Nodo(i, partes[i]));
        }
    }

    public String solve() {
        String output = "";

        boolean isReady = false;

        while (isReady) {
            //if () implementar algoritmo principal
        }

        return output;
    }

    private void primeraPasada() {
        List<Nodo[]> pares = new ArrayList<Nodo[]>();
        for (Nodo item1 : this.nodos) {
            for (Nodo item2 : this.nodos) {
                if (item1 != item2) {
                    Nodo[] par = new Nodo[2];
                    par[0] = item1;
                    par[1] = item2;
                    pares.add(par);
                }
            }
        }

        pares = removerDuplicados(pares);

        for (Nodo[] i : pares) {
            if (i[0].getGrado() != i[1].getGrado()) {

            }
        }
    }

    public String toString() {
        String ret = "";

        for (int i = 0; i < this.nodos.size(); i++) {
            ret += this.nodos.get(i).toString();
            ret += "\n";
        }

        return ret;
    }

    private List<Nodo[]> removerDuplicados(List<Nodo[]> lista) {
        List<Nodo[]> ret = new ArrayList<Nodo[]>();

        for (Nodo[] i : lista) {
            if (!ret.contains(i)) {
                ret.add(i);
            }
        }

        return ret;
    }
}
