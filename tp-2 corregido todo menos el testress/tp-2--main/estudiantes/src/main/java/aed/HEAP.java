package aed;

import java.util.*;
class HEAP<T extends Comparable<T>> {
    private ArrayList<T> datos;
    private ArrayList<Handle<T>> handles;

    public HEAP() {
        datos = new ArrayList<>();
        handles = new ArrayList<>();
    }

    public Handle<T> Encolar(T elem) {
        datos.add(elem);
        Handle<T> h = new Handle<>(datos.size() - 1);
        handles.add(h);

        // Si es Usuario, asigno handle
        if (elem instanceof Usuario) {
            ((Usuario) elem).setHandle((Handle<Usuario>) h);
        }

        flotar(datos.size() - 1);
        return h;
    }

    public void actualizarElemento(Handle<T> h) {
        int pos = h.posicion();
        flotar(pos);
        hundir(pos);
    }

    public T Proximo() {
        return datos.get(0);
    }

    public void Desencolar() {
        int ultimo = datos.size() - 1;
        swap(0, ultimo);
        datos.remove(ultimo);
        handles.remove(ultimo);
        if (!datos.isEmpty()) hundir(0);
    }

    public int size() {
        return datos.size();
    }

    private void flotar(int i) {
        while (i > 0 && datos.get(i).compareTo(datos.get((i - 1) / 2)) < 0) {
            swap(i, (i - 1) / 2);
            i = (i - 1) / 2;
        }
    }

    private void hundir(int i) {
        int tam = datos.size();
        while (2 * i + 1 < tam) {
            int h = 2 * i + 1;
            if (h + 1 < tam && datos.get(h + 1).compareTo(datos.get(h)) < 0) h++;
            if (datos.get(i).compareTo(datos.get(h)) <= 0) break;
            swap(i, h);
            i = h;
        }
    }

    private void swap(int i, int j) {
        T tmp = datos.get(i);
        datos.set(i, datos.get(j));
        datos.set(j, tmp);

        Handle<T> hi = handles.get(i);
        Handle<T> hj = handles.get(j);
        handles.set(i, hj);
        handles.set(j, hi);
        hi.setPosicion(j);
        hj.setPosicion(i);
    }
}
