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

    private List<List<Nodo>> conjuntosPorGrado = new ArrayList<List<Nodo>>();

    private int cantidadNodos;
    private int gradoMasAlto; // TODO: implementar - luego hacer conjuntos para todos los grados hasta el mas alto
                              // luego poner cada nodo en su conjunto con su grado. no se si usar Set o Conjunto...
    private String solution;

    // la clase maze puede construirse con un String bien formateado

    Maze(String nodos) {
        generarListaNodos(nodos);
        this.cantidadNodos = this.nodos.size();

        solve();
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
        gradoMasAlto();
        hacerConjuntos();
        primeraPasada();
        System.out.println("Conjuntos despues de la primera pasada: " + this.conjuntosPorGrado.toString());

        boolean isReady = false;

        while (!isReady) {
            //if () implementar algoritmo principal
        }

        return output;
    }

    private void hacerConjuntos() {
        for (int i = 0; i <= this.gradoMasAlto; i++) {
            this.conjuntosPorGrado.add(new ArrayList<Nodo>());
        }
    }

    private void primeraPasada() {
        for (Nodo n : this.nodos) {
            this.conjuntosPorGrado.get(n.getGrado()).add(n);
        }
    }

    private void siguientesPasadas() {
        List<Nodo[]> pares = new ArrayList<Nodo[]>();
        for (Nodo n1 : this.nodos) {
            for (Nodo n2 : this.nodos) {
                if (n1 != n2) {
                    Nodo[] par = new Nodo[2];
                    par[0] = n1;
                    par[1] = n2;
                    pares.add(par);
                }
            }
        }

        pares = removerDuplicados(pares);

        for (Nodo[] n : pares) {
            if (n[0].getGrado() != n[1].getGrado()) {

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

    public List<Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(List<Nodo> nodos) {
        this.nodos = nodos;
    }

    public List<List<Nodo>> getConjuntosPorGrado() {
        return conjuntosPorGrado;
    }

    public void setConjuntosPorGrado(List<List<Nodo>> conjuntosPorGrado) {
        this.conjuntosPorGrado = conjuntosPorGrado;
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
