package aed;

public class Nodo<T> {
    public T _valor;
    public Nodo<T> _siguienteNodo;
    public Nodo<T> _anteriorNodo;

    public Nodo(T valor) {
        this._valor = valor;
    }
}