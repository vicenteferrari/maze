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
        this.input = id + " " + vecinos;
        generarListaVecinos(vecinos);
        this.grado = this.vecinos.size();
        distinguible = true;
    }

    private void generarListaVecinos(String nodos) {
        String[] partes = nodos.split(" ");

        for (int i = 1; i < partes.length; i++) {
            vecinos.add(Integer.parseInt(partes[i]));
        }
    }

    public String toString() {
        String ret = "";

        ret += this.vecinos;

        return ret;
    }

    public List<Integer> getVecinos() {
        return vecinos;
    }

    public void setVecinos(List<Integer> vecinos) {
        this.vecinos = vecinos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public boolean isDistinguible() {
        return distinguible;
    }

    public void setDistinguible(boolean distinguible) {
        this.distinguible = distinguible;
    }
}
