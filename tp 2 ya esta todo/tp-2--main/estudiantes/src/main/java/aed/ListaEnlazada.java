package aed;

import java.util.*;

public class ListaEnlazada<T> implements Secuencia<T> {

    private Nodo _primerNodo;
    private Nodo _ultimoNodo;

    private int longitud;    

    private class Nodo {
        T _valor;
        Nodo _siguienteNodo;
        Nodo _anteriorNodo;
        
        Nodo(T _v) {_valor = _v;};
    }

    public ListaEnlazada() {
        _primerNodo = null;
        _ultimoNodo =null;
    }

    public int longitud() {
        return this.longitud;
    }

    public void agregarAdelante(T elem) {
        Nodo _nuevoPrimerNodo = new Nodo(elem);
        if (longitud==0){
            _primerNodo=_nuevoPrimerNodo;
            int longitudNueva= longitud+1;
            _ultimoNodo=_nuevoPrimerNodo;
            longitud=longitudNueva;
        }
        else{
            _nuevoPrimerNodo._siguienteNodo=_primerNodo;
            _primerNodo._anteriorNodo= _nuevoPrimerNodo;               
            _primerNodo=_nuevoPrimerNodo;
            int nuevaLongitud=longitud+1;
            longitud=nuevaLongitud;
        }
    }

    public void agregarAtras(T elem) {
        Nodo _nuevoUltimoNodo= new Nodo(elem);
        if (longitud==0){
            _nuevoUltimoNodo._anteriorNodo=null;
            _nuevoUltimoNodo._siguienteNodo=null;
            _primerNodo=_nuevoUltimoNodo;
            _ultimoNodo=_nuevoUltimoNodo;
            int longitudNueva= longitud+1;
            longitud=longitudNueva;
        }
        else {
            _nuevoUltimoNodo._anteriorNodo=_ultimoNodo;
            _ultimoNodo._siguienteNodo=_nuevoUltimoNodo;
            
            _nuevoUltimoNodo._siguienteNodo=null;
            _ultimoNodo=_nuevoUltimoNodo;
            int nuevaLongitud=longitud+1;
            longitud=nuevaLongitud;
        }
    }

    public T obtener(int i) {
        Nodo nodoActual =_primerNodo;
        
        if (i==0){
            return nodoActual._valor;
        }
        else{
            for (int j=1;j<=i;j++){
                nodoActual=nodoActual._siguienteNodo;                               
            }
        return nodoActual._valor;
        }
    }

    public void eliminar(int i) {
        Nodo nodoActual =_primerNodo;
        Nodo nodoAnterior =_primerNodo;
        if (longitud==1){
            _primerNodo=null;
            _ultimoNodo=null;
            longitud=0;
        }
        else if (i==0){
            nodoActual=_primerNodo._siguienteNodo;
            nodoActual._anteriorNodo=null;
            _primerNodo=nodoActual;
            longitud=longitud-1;
        }
        else if (i==longitud-1){
            nodoActual=_ultimoNodo._anteriorNodo;
            nodoActual._siguienteNodo=null;
            _ultimoNodo=nodoActual;
            longitud=longitud-1;
        }
        else {
            for (int j=1;j<=i;j++){
                nodoAnterior=nodoActual;
                nodoActual=nodoActual._siguienteNodo;                
            }
            
            nodoAnterior._siguienteNodo=nodoActual._siguienteNodo;
            nodoActual._siguienteNodo._anteriorNodo=nodoAnterior;
            longitud=longitud-1;
        }
    }

    public void modificarPosicion(int indice, T elem) {
            Nodo nodoActual = _primerNodo;
            for (int i = 0; i < indice; i++) {
                nodoActual = nodoActual._siguienteNodo;
            }
            nodoActual._valor = elem;
        }
        /*Nodo nuevoNodo= new Nodo(elem);
        
        if (indice==0){
            _primerNodo._siguienteNodo=nuevoNodo._siguienteNodo; 
            _primerNodo._siguienteNodo._anteriorNodo = nuevoNodo;
            _primerNodo = nuevoNodo;
        }
        else if (indice==longitud-1){
            _ultimoNodo._anteriorNodo=nuevoNodo._anteriorNodo;
            _ultimoNodo=nuevoNodo;
        }
        else{
            Nodo nodoActual=_primerNodo;
            for (int j=1; j<=indice;j++){
               nodoActual= nodoActual._siguienteNodo;
               }
            nuevoNodo._anteriorNodo = nodoActual._anteriorNodo;
            nuevoNodo._siguienteNodo=nodoActual._siguienteNodo;   
            nodoActual._anteriorNodo._siguienteNodo = nuevoNodo;
            nodoActual._siguienteNodo._anteriorNodo = nuevoNodo;*/

        
    public ListaEnlazada(ListaEnlazada<T> lista) {
        _primerNodo = null;
        _ultimoNodo = null;
        longitud = 0;
    
        Nodo actual = lista._primerNodo;
        while (actual != null) {
            this.agregarAtras(actual._valor);
            actual = actual._siguienteNodo;
        }
    }
    
    @Override
    public String toString() {
        String res= "[";
        Nodo actual= _primerNodo;
        while (actual!=null){
            if (actual == _ultimoNodo){
                
                res+=actual._valor+"]";
            }
            else{
              
                res+=actual._valor+","+" ";
            }
            actual=actual._siguienteNodo;
        }
        return res;
    }

    private class ListaIterador implements Iterador<T> {
    	private Nodo nodo_actual;
        private Nodo nodo_anterior;

        public boolean haySiguiente() {
            return nodo_actual!=null;
        }
        
        public boolean hayAnterior() {
	        return nodo_anterior!=null;
        }

        public T siguiente() {
            T nodo_siguiente = nodo_actual._valor;
            nodo_anterior = nodo_actual;
            nodo_actual = nodo_actual._siguienteNodo;
            return nodo_siguiente;
	        
        }
        

        public T anterior() {
            T nodoAnterior = nodo_anterior._valor;
            nodo_actual = nodo_anterior;
            nodo_anterior = nodo_anterior._anteriorNodo;
            return nodoAnterior;
        }
    }

    public Iterador<T> iterador() {
        ListaIterador iterador = new ListaIterador();
        iterador.nodo_actual = _primerNodo;
        iterador.nodo_anterior = null;
        return iterador;
    }

}
