package aed;

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo<T> _primerNodo;
    private Nodo<T> _ultimoNodo;
    private int longitud;

    public ListaEnlazada() {
        _primerNodo = null;
        _ultimoNodo = null;
        longitud = 0;
    }

    public int longitud() {
        return this.longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (longitud == 0) {
            _primerNodo = _ultimoNodo = nuevo;
        } else {
            nuevo._siguienteNodo = _primerNodo;
            _primerNodo._anteriorNodo = nuevo;
            _primerNodo = nuevo;
        }
        longitud++;
    }

    public void agregarAtras(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (longitud == 0) {
            _primerNodo = _ultimoNodo = nuevo;
        } else {
            nuevo._anteriorNodo = _ultimoNodo;
            _ultimoNodo._siguienteNodo = nuevo;
            _ultimoNodo = nuevo;
        }
        longitud++;
    }

    public Nodo<T> agregarAtrasYDevolverNodo(T elem) {
        Nodo<T> nuevo = new Nodo<>(elem);
        if (longitud == 0) {
            _primerNodo = _ultimoNodo = nuevo;
        } else {
            nuevo._anteriorNodo = _ultimoNodo;
            _ultimoNodo._siguienteNodo = nuevo;
            _ultimoNodo = nuevo;
        }
        longitud++;
        return nuevo;
    }

    public T obtener(int i) {
        Nodo<T> actual = _primerNodo;
        for (int j = 0; j < i; j++) {
            actual = actual._siguienteNodo;
        }
        return actual._valor;
    }

    public void eliminar(int i) {
        if (longitud == 0) return;

        Nodo<T> actual = _primerNodo;

        if (i == 0) {
            _primerNodo = _primerNodo._siguienteNodo;
            if (_primerNodo != null) _primerNodo._anteriorNodo = null;
            else _ultimoNodo = null;
        } else if (i == longitud - 1) {
            _ultimoNodo = _ultimoNodo._anteriorNodo;
            if (_ultimoNodo != null) _ultimoNodo._siguienteNodo = null;
            else _primerNodo = null;
        } else {
            for (int j = 0; j < i; j++) {
                actual = actual._siguienteNodo;
            }
            actual._anteriorNodo._siguienteNodo = actual._siguienteNodo;
            actual._siguienteNodo._anteriorNodo = actual._anteriorNodo;
        }
        longitud--;
    }

    public void eliminarNodo(Nodo<T> nodo) {
        if (nodo == null) return;

        if (nodo._anteriorNodo != null)
            nodo._anteriorNodo._siguienteNodo = nodo._siguienteNodo;
        else
            _primerNodo = nodo._siguienteNodo;

        if (nodo._siguienteNodo != null)
            nodo._siguienteNodo._anteriorNodo = nodo._anteriorNodo;
        else
            _ultimoNodo = nodo._anteriorNodo;

        longitud--;
    }

    public void modificarPosicion(int i, T elem) {
        Nodo<T> actual = _primerNodo;
        for (int j = 0; j < i; j++) {
            actual = actual._siguienteNodo;
        }
        actual._valor = elem;
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        _primerNodo = null;
        _ultimoNodo = null;
        longitud = 0;
        Nodo<T> actual = lista._primerNodo;
        while (actual != null) {
            this.agregarAtras(actual._valor);
            actual = actual._siguienteNodo;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("[");
        Nodo<T> actual = _primerNodo;
        while (actual != null) {
            res.append(actual._valor);
            if (actual._siguienteNodo != null) res.append(", ");
            actual = actual._siguienteNodo;
        }
        res.append("]");
        return res.toString();
    }

    private class ListaIterador implements Iterador<T> {
        private Nodo<T> nodo_actual = _primerNodo;
        private Nodo<T> nodo_anterior = null;

        public boolean haySiguiente() {
            return nodo_actual != null;
        }

        public boolean hayAnterior() {
            return nodo_anterior != null;
        }

        public T siguiente() {
            T valor = nodo_actual._valor;
            nodo_anterior = nodo_actual;
            nodo_actual = nodo_actual._siguienteNodo;
            return valor;
        }

        public T anterior() {
            T valor = nodo_anterior._valor;
            nodo_actual = nodo_anterior;
            nodo_anterior = nodo_anterior._anteriorNodo;
            return valor;
        }
    }

    public Iterador<T> iterador() {
        return new ListaIterador();
    }
}
