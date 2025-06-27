package aed;
public class Bloque implements Comparable<Bloque> {
    ListaEnlazada<Transaccion> ListaTransacciones;  // O(n)
    HEAP<Transaccion> HeapTransacciones; // O(log n)
    int sumaMontos;// O(1)
    int cantidadTx;// O(1)

     // Constructor que procesa un array de transacciones
     // O(m log m) m->numero de transacciones
    public Bloque(Transaccion[] transacciones) {
    ListaTransacciones = new ListaEnlazada<>();
    HeapTransacciones = new HEAP<>();
    sumaMontos = 0;
    cantidadTx = 0;

    // O(m log m) 
    for (Transaccion tx : transacciones) {
        ListaTransacciones.agregarAtras(tx);// Agrega a lista
        HeapTransacciones.Encolar(tx);// Agrega al heap   O(log m)
        if (tx.id_comprador() != 0) { // Solo sumar si no es transaccion de creacion
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
        

    // Verifica si es transacción de creación (comprador = 0) O(1)
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

    // Compara bloques por sumaMontos y cantidadTx
    @Override
    public int compareTo(Bloque otro) {
        if (this.sumaMontos != otro.sumaMontos) {
             return this.sumaMontos - otro.sumaMontos;
    }   else {
             return this.cantidadTx - otro.cantidadTx;
    }
}
}
