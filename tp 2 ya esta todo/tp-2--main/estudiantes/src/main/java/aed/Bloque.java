package aed;
public class Bloque implements Comparable<Bloque> {
    ListaEnlazada<Transaccion> ListaTransacciones;
    HEAP<Transaccion> HeapTransacciones;
    int sumaMontos;
    int cantidadTx;

    
    public Bloque(Transaccion[] transacciones) {
    ListaTransacciones = new ListaEnlazada<>();
    HeapTransacciones = new HEAP<>();
    sumaMontos = 0;
    cantidadTx = 0;

    for (Transaccion tx : transacciones) {
        ListaTransacciones.agregarAtras(tx);
        HeapTransacciones.Encolar(tx);
        if (tx.id_comprador() != 0) { // Solo sumar si no es transacción de creación
            sumaMontos += tx.monto();
            cantidadTx++;
        }
    }
}
        /*cantidadTx = 0;
        sumaMontos = 0;
        for (int i = 0; i < transacciones.length; i++) {
            Transaccion tx = transacciones[i];
            ListaTransacciones.agregarAtras(tx);
            HeapTransacciones.Encolar(tx);
            }*/
        /*for (int i = 0; i < ListaTransacciones.longitud(); i++) {
             Transaccion tx = ListaTransacciones.obtener(i);
        if (!esTransaccionCreacion(tx)) {
            cantidadTx++;
            sumaMontos += tx.monto();
            }*/
        
    
    public void recalcularSumaYCantidad() {
        sumaMontos = 0;
        cantidadTx = 0;
        for (int i = 0; i < ListaTransacciones.longitud(); i++) {
            Transaccion tx = ListaTransacciones.obtener(i);
            if (!esTransaccionCreacion(tx)) {
                cantidadTx++;
                sumaMontos += tx.monto();
            }
        }
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
        /*int suma=0;
        for (int i=0;i<lista.longitud();i++){
            suma+=lista.obtener(i).monto();
        }*/
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
