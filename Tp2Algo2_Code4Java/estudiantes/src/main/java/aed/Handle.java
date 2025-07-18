package aed;

class Handle<T> {
   private int posicion;
   private Object nodoLista;
// Constructor que recibe la posición inicial del elemento en el HEAP
    public Handle(int posicion) {
        this.posicion = posicion;
        this.nodoLista = null;
    }
// consigo la posición
    public int posicion() { //-> posicion: almacena el indice donde se encuentra el elemento en el array del HEAP
        return posicion;
    }
// setea para actualizar la posición
    public void setPosicion(int nuevaPos) { //->setPosicion: actualiza la posicion cuando el elemento se mueve en el HEAP
        this.posicion = nuevaPos;
    }
// Métodos para manejar la referencia al nodo de lista
    public Object nodoLista() {
        return nodoLista;
    }

    public void setNodoLista(Object nodo) {
        this.nodoLista = nodo;
    }
    
}
