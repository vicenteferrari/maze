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

    // la clase maze puede construirse con un arreglo multidimensional de enteros o con un String bien formateado
    Maze(int[][] nodos) {
        generarListaNodos(nodos);
        this.cantidadNodos = this.nodos.size();
    }

    Maze(String nodos) {
        generarListaNodos(nodos);
        this.cantidadNodos = this.nodos.size();
    }

    private void generarListaNodos(int[][] nodos) {
        // transformar arreglo a lista
    }

    private void generarListaNodos(String nodos) {
        // transformar string a lista
    }

    public String solve() {
        String output = "";

        return output;
    }
}
