package aed;

import java.util.*;
class HEAP<T extends Comparable<T>> {
    private ArrayList<T> datos; // Elementos del heap O(1)
    private ArrayList<Handle<T>> handles; // Handles correspondientes O(1)

    public HEAP() { //O(1)
        datos = new ArrayList<>();
        handles = new ArrayList<>();
    }

// Agrega un elemento al heap y devuelve su Handle O(log n)
    public Handle<T> Encolar(T elem) {
        datos.add(elem);// O(1)
        Handle<T> h = new Handle<>(datos.size() - 1);
        handles.add(h);// O(1)

        // Si es Usuario, asigno handle
        if (elem instanceof Usuario) {
            ((Usuario) elem).setHandle((Handle<Usuario>) h);// O(1)
        }

        flotar(datos.size() - 1); // Reordena el heap
        return h;
    }

// O(log n) 
// Actualiza la posicion de un elemento despues de cambiar su valor
    public void actualizarElemento(Handle<T> h) {
        int pos = h.posicion();
        flotar(pos);// Intenta mover hacia arriba O(log n) 
        hundir(pos);// Intenta mover hacia abajo O(log n) 
    }

// Devuelve el elemento de mayor prioridad (raiz)
//O(1)
    public T Proximo() {
        return datos.get(0);
    }

// O(log n)
// Elimina el elemento de mayor prioridad
    public void Desencolar() {
        int ultimo = datos.size() - 1;
        swap(0, ultimo);// Mueve el ultimo a la raiz
        datos.remove(ultimo);// Elimina el ultimo
        handles.remove(ultimo);
        if (!datos.isEmpty()) hundir(0); // Reordena el heap
    }

    //O(1)
    public int size() {
        return datos.size();
    }


// Mueve un elemento hacia arriba mientras sea necesario
    private void flotar(int i) {  // O(log n)
        while (i > 0 && datos.get(i).compareTo(datos.get((i - 1) / 2)) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }
// Mueve un elemento hacia abajo mientras sea necesario O(log n)
    private void hundir(int i) {
        int tam = datos.size();
        while (2 * i + 1 < tam) {
            int h = 2 * i + 1;// Hijo izquierdo
            if (h + 1 < tam && datos.get(h + 1).compareTo(datos.get(h)) < 0) h++;// Hijo derecho si es menor
            if (datos.get(i).compareTo(datos.get(h)) <= 0) break;// Si ya esta en orden, termina
            swap(i, h); // Intercambia con el hijo menor
            i = h;
        }
    }
// Intercambia dos elementos y actualiza sus Handles 
    private void swap(int i, int j) {
        // Intercambia elementos
        T tmp = datos.get(i);
        datos.set(i, datos.get(j));
        datos.set(j, tmp);

        // Intercambia Handles
        Handle<T> hi = handles.get(i);
        Handle<T> hj = handles.get(j);
        handles.set(i, hj);
        handles.set(j, hi);

        // Actualiza posiciones en los Handles
        hi.setPosicion(j);
        hj.setPosicion(i);
    }
}
