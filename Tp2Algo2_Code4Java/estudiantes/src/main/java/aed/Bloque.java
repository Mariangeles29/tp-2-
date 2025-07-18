package aed;

import java.util.ArrayList;

public class Bloque implements Comparable<Bloque> {
    ListaEnlazada<Transaccion> ListaTransacciones;  
    HEAP<Transaccion> HeapTransacciones;           
    int sumaMontos;                                
    int cantidadTx;                                

    /*public Bloque(Transaccion[] transacciones) {
        ListaTransacciones = new ListaEnlazada<>();
        HeapTransacciones = new HEAP<>();
        sumaMontos = 0;
        cantidadTx = 0;

        for (Transaccion tx : transacciones) {
            Nodo<Transaccion> nodo = ListaTransacciones.agregarAtrasYDevolverNodo(tx);
            
            Handle<Transaccion> h = HeapTransacciones.Encolar(tx);
            tx.setHandle(h);
            h.setNodoLista(nodo);

            if (tx.id_comprador() != 0) {
                sumaMontos += tx.monto();
                cantidadTx++;
            }
        }
    }  */
    public Bloque(Transaccion[] transacciones) {
    ListaTransacciones = new ListaEnlazada<>();
    HeapTransacciones = new HEAP<>();
    sumaMontos = 0;
    cantidadTx = 0;

    // 1. Agregar todas las transacciones a la lista y preparar para heapify
    ArrayList<Transaccion> transaccionesTemp = new ArrayList<>();
    ArrayList<Handle<Transaccion>> handlesTemp = new ArrayList<>();
    
    for (Transaccion tx : transacciones) {
        Nodo<Transaccion> nodo = ListaTransacciones.agregarAtrasYDevolverNodo(tx);
        
        Handle<Transaccion> h = new Handle<>(transaccionesTemp.size());
        tx.setHandle(h);
        h.setNodoLista(nodo);
        transaccionesTemp.add(tx);
        handlesTemp.add(h);

        if (tx.id_comprador() != 0) {
            sumaMontos += tx.monto();
            cantidadTx++;
        }
    }
    
    // 2. Construir el heap eficientemente con heapify
    HeapTransacciones.buildHeap(transaccionesTemp, handlesTemp);
    } 

    private boolean esTransaccionCreacion(Transaccion tx) {
        return tx.id_comprador() == 0; 
    }

    public int sumaMontos(ListaEnlazada<Transaccion> lista){
        int suma=0;
        for (int i=0;i<lista.longitud();i++){
            Transaccion tx = lista.obtener(i);
            if (!esTransaccionCreacion(tx)) {
                suma += tx.monto();
            }
        }
        return suma;
    }

    @Override
    public int compareTo(Bloque otro) {
        if (this.sumaMontos != otro.sumaMontos) {
             return this.sumaMontos - otro.sumaMontos;
    }   else {
             return this.cantidadTx - otro.cantidadTx;
    }
}
}
