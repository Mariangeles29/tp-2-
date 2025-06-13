import aed.Transaccion;

public class Bloque {
    ListaEnlazada<Transaccion> ListaTransacciones;
    HEAP<Transaccion> HeapTransacciones;

    public int sumaMontos(ListaEnlazada<Transaccion> lista){
        int suma=0;
        for (int i=0;0<=i<lista.length;i++){
            suma+=lista[i].monto;
        }
        return suma;
    }
}
