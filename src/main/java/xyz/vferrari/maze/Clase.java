package xyz.vferrari.maze;

import java.util.Arrays;
import java.util.Iterator;

public class Clase implements Iterable<Nodo>{
	//Size of list
	private int size = 0;

	//Default capacity of list is 10
	private static final int DEFAULT_CAPACITY = 10;

	//This array will store all elements added to list
	private Nodo elements[];

	//Default constructor
	public Clase() {
		elements = new Nodo[DEFAULT_CAPACITY];
	}

	public Clase(Clase clase) {
		this.elements = new Nodo[DEFAULT_CAPACITY];

		for (int i = 0; i < clase.elements.length; ++i) {
			add(clase.elements[i]);
		}
		removeNull();
	}

	//Add method
	public void add(Nodo e) {
		if (size == elements.length) {
			ensureCapacity();
		}
		elements[size++] = e;
	}

	private void removeNull() {
		for (int i = 0; i < elements.length; ++i) {
			if (elements[i] == null) remove(i);
		}
		makeSmaller();
	}

	//Get method
	@SuppressWarnings("unchecked")
	public Nodo get(int i) {
		if (i >= size || i < 0) {
			System.out.println(this + " WITH INDEX: " +i);
			System.out.println(this.elements[0].getClase());
			throw new IndexOutOfBoundsException("Index: " + i + ", Size " + i);
		}
		return elements[i];
	}

	public Nodo[] getElements() {
		return elements;
	}

	public Nodo remove(int index) {
		Nodo oldValue = elements[index];

		int numMoved = size - index - 1;
		if (numMoved > 0)
			System.arraycopy(elements, index+1, elements, index, numMoved);

		elements[--size] = null; // Let gc do its work
//		removeNull();

		return oldValue;
	}

	public boolean remove(Nodo o) {
		if (o == null) {
			for (int index = 0; index < size; index++)
				if (elements[index] == null) {
					remove(index);
					return true;
				}
		} else {
			for (int index = 0; index < size; index++)
				if (o.equals(elements[index])) {
					remove(index);
					return true;
				}
		}

		return false;
	}

	//Get Size of list
	public int size() {
		return size;
	}

	public int realSize() {
		int i = 0;

		for (int j = 0; j < elements.length; ++j) {
			if (elements[j] != null) i++;
		}

		return i;
	}

	//Print method
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
//		sb.append('[');
		for(int i = 0; i < size ;i++) {
			if (elements[i] != null) {
				sb.append(elements[i].toString());
				if(i<size-1) {
					sb.append(" ");
				}
			}
		}
//		sb.append(']');
		return sb.toString();
	}

	public int indexOf(Nodo o) {

		if (o == null) {

			for (int i = 0; i < size; i++)

				if (elements[i]==null)

					return i;

		} else {

			for (int i = 0; i < size; i++)

				if (o.equals(elements[i]))

					return i;

		}

		return -1;

	}

//	public Clase clone() {
//		Clase ret = new Clase();
//
//		for (int i = 0; i < this.elements.length; i++) {
//			ret.add(this.elements[i]);
//		}
//
//		return ret;
//	}

	@SuppressWarnings("unchecked")
	public Iterator<Nodo> iterator() {
		return (Iterator<Nodo>) Arrays.asList(this.elements);
	}

	private void ensureCapacity() {
		int newSize = elements.length + 1;
		elements = Arrays.copyOf(elements, newSize);
	}

	private void makeSmaller() {
		int rs = realSize();
		if (realSize() < size())
		elements = Arrays.copyOf(elements, rs);
	}
}