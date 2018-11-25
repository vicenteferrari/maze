package xyz.vferrari.maze;

import java.util.ArrayList;
import java.util.List;

public class Nodo {
    private List<Nodo> vecinos = new ArrayList<Nodo>();
    private int grado;

    Nodo() {
        this.grado = this.vecinos.size();
    }
}
