package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Nodo {

    // un nodo tiene varios vecinos
    private List<Integer> vecinos = new ArrayList<Integer>();

    private int id;
    private int grado;

    private String input;

    private boolean distinguible;

    Nodo(int id, String vecinos) {
        this.id = id;
        this.input = id + vecinos;
        generarListaVecinos(vecinos);
        this.grado = this.vecinos.size();
        distinguible = true;
    }

    private void generarListaVecinos(String nodos) {
        String[] partes = nodos.split(" ");

        for (int i = 0; i < partes.length; i++) {
            vecinos.add(Integer.parseInt(partes[i]));
        }
    }

    public String toString() {
        String ret = "";

        ret += this.input;

        return ret;
    }
}
