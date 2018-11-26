package xyz.vferrari.maze;

import java.util.*;

public class Maze {
    // Define the degree of a room to be the number of rooms to which it connects. You will want to compare pairs of
    // distinct rooms. Initially, two rooms are distinguishable if their degrees are different. Now on the second iteration,
    // look at rooms x, y that you have not marked as distinguishable. Suppose that x has a neighbor A and y neighbor B,
    // such that A and B have been marked distinguishable. Then mark x and y as distinguishable. Repeat until no more
    // nodes can be marked as distinguishable.

    // el laberinto esta hecho de varios nodos
    private List<Nodo> nodos = new ArrayList<Nodo>();

    private int cantidadNodos;

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

        while (isReady){
            //if () implementar algoritmo principal
        }

        return output;
    }

    private void primeraPasada() {
        for (int i = 0; i < this.nodos.size(); i++) {
            // separar laberinto a disitntos grupos basados en sus grados.
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
}
