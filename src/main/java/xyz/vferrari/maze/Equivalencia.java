package xyz.vferrari.maze;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Equivalencia {

	private ArrayList<Clase> clases;
	private int size = 0;

	Equivalencia() {
		clases = new ArrayList<Clase>();
	}

	Equivalencia(Equivalencia equivalencia) {
		this.clases = new ArrayList<Clase>();

		for (Clase c : equivalencia.getClases()) {
			this.clases.add(new Clase(c));
		}

		this.size = this.clases.size();
	}

	Equivalencia(ArrayList<Clase> clases) {
		this.clases = clases;
	}

//	Equivalencia(Clase... clases) {
//		this.clases = new ArrayList<Clase>();
//		for (Clase c : clases) {
//			this.clases.add(new Clase());
//		}
//	}

	public Equivalencia reducir() {

		Equivalencia siguiente = new Equivalencia(this);

		for (int i = 0; i < siguiente.getClases().size(); ++i) {
			Clase currentClase = siguiente.get(i);
			Nodo nodoBase = currentClase.get(0);

			for (int j =1; j < currentClase.size(); ++j) {
				// we loop through all elements of class, comparing against the very first one.
				Nodo nodoTemp = currentClase.get(j);

				boolean sonEquivalentes = nodoBase.equivalentes(nodoTemp);

				if (!sonEquivalentes) {
					currentClase.remove(nodoTemp);
					--j;

					boolean fits = true;

					for (int k = 0; k < siguiente.getClases().size(); k++) {
						Nodo nodoPivote = siguiente.getClases().get(k).get(0);

						if(nodoPivote.equivalentes(nodoTemp)) {
							siguiente.getClases().get(k).add(nodoTemp);
							break;
						} else if (k == siguiente.getClases().size() - 1) {
							fits = false;
						}
					}

					if (!fits) {
						siguiente.addClase(new Clase());
						siguiente.get(siguiente.size() - 1).add(nodoTemp);
					}
				}
			}

//			for (int j = 1; j < currentClase.size(); ++j) {
//				if (!currentClase.get(0).equivalentes(currentClase.get(j))) {
//					Nodo nodoTemp = currentClase.get(j);
//					currentClase.remove(nodoTemp);
//					--j;
//					Clase claseTemp = new Clase(currentClase);
//					siguiente.remove(claseTemp);
//					siguiente.addClase(0, claseTemp);
//
//					boolean fits = true;
//					for (int k = 0; k < this.clases.size(); k++) {
//						if(this.clases.get(k).get(0).equivalentes(nodoTemp)) {
//							this.clases.get(k).add(nodoTemp);
//							break;
//						} else if (k == this.clases.size() - 1) {
//							fits = false;
//						}
//					}
//
//					if (!fits) {
//						siguiente.addClase(new Clase());
//						siguiente.get(siguiente.size() - 1).add(nodoTemp);
//					}
//				}
//			}
		}

//		for(int i = siguiente.size() - 1; i >= 0; i--) {
//			Clase currentClase = siguiente.get(i);
//
//			for (int j = 1; j < currentClase.size(); j++) {
//				if (!currentClase.get(0).equivalentes(currentClase.get(j))) {
//					Nodo nodoTemp = currentClase.get(j);
//					currentClase.remove(nodoTemp);
//					j--;
//					Clase claseTemp = new Clase(currentClase);
//					siguiente.remove(claseTemp);
//					siguiente.addClase(0, claseTemp);
//
//					boolean fits = true;
//					for (int k = 0; k < this.clases.size(); k++) {
//						if(this.clases.get(k).get(0).equivalentes(nodoTemp)) {
//							this.clases.get(k).add(nodoTemp);
//							break;
//						} else if (k == this.clases.size() - 1) {
//							fits = false;
//						}
//					}
//
//					if (!fits) {
//						siguiente.addClase(new Clase());
//						siguiente.get(siguiente.size() - 1).add(nodoTemp);
//					}
//				}
//			}
//		}

		return siguiente;
	}

	public Clase getClase(int index) {
		return this.clases.get(index);
	}

	public void addClase(Clase clase) {
		this.clases.add(clase);
		++size;
	}

	public void addClase(int index, Clase clase) {
		this.clases.add(index, clase);
		++size;
	}

	public void eliminarClasesVacias() {
		Iterator<Clase> iter = this.clases.iterator();

		while(iter.hasNext()) {
			Clase clase = iter.next();

			if(clase.size() == 0) {
				iter.remove();
			}
		}

//		for (Clase c : this.clases) {
//			if (c.size() == 0) {
//				this.clases.remove(this.clases.indexOf(c));
//			}
//		}

//		for (int i = 0; i < this.clases.size(); i++) {
//			if (this.clases.get(i).size() == 0) {
//				this.clases.remove(this.clases.get(i));
//				i--;
//			}
//		}

		this.size = this.clases.size();
	}

	public ArrayList<Clase> getClases() {
		return clases;
	}

	public void setClases(ArrayList<Clase> clases) {
		this.clases = clases;
	}

	public Clase get(int index) {
		return this.clases.get(index);

	}

	public Clase remove(int index) {
		Clase ret = this.clases.remove(index);
		this.size = this.clases.size();
		return ret;

	}

//	public Clase remove(int index) {
//		Clase oldValue = this.clases.get(index);
//
//		int numMoved = size - index - 1;
//		if (numMoved > 0)
//			System.arraycopy(this.clases.toArray(), index+1, this.clases.to, index, numMoved);
//
//		this.clases.set(--size, null); // Let gc do its work
//
//		return oldValue;
//	}

	public boolean remove(Clase o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (clases.get(index) == null) {
					remove(index);
					this.size = this.clases.size();
					return true;
				}
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(clases.get(index))) {
					remove(index);
					this.size = this.clases.size();
					return true;
				}
		}

		return false;
	}

	public String toString() {
		String ret = "";

		for (int i = 0; i < this.getClases().size(); i++) {
			Clase clase = this.getClase(i);

			for (int j = 0; j < clase.size(); j++) {
				if(j != clase.size() - 1) {
					ret += clase.get(j) + " ";
				} else {
					ret += clase.get(j);
				}
			}

			if(i++ == clase.size() - 1){
				ret += "\n";
			}
		}

		return ret;
	}

//	public Equivalencia clone() {
//		ArrayList<Clase> temp = new ArrayList<Clase>();
//
//		for(Clase c : this.getClases()) {
//			temp.add(new Clase(c));
//		}
//
//		Equivalencia c = new Equivalencia(temp);
//		return c;
//	}

	public int size() {
		return this.clases.size();

	}
}
