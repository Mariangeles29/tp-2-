package aed;

import java.util.ArrayList;

public class Berretacoin {

    HEAP<Usuario> maximoTenedor;
    ArrayList<Usuario> posicionUsuariosHeap;
    ArrayList<Bloque> cadena;
    Bloque ultimoBloque;

    public Berretacoin(int n_usuarios) {
        maximoTenedor = new HEAP<>();
        posicionUsuariosHeap = new ArrayList<>(n_usuarios);
        cadena = new ArrayList<>();
        ultimoBloque = null;

        for (int i = 1; i <= n_usuarios; i++) {
            Usuario u = new Usuario(i, 0);
            posicionUsuariosHeap.add(u);
            maximoTenedor.Encolar(u);
        }
    }

    public void agregarBloque(Transaccion[] transacciones) {
        Bloque nuevoBloque = new Bloque(transacciones);
        /*nuevoBloque.ListaTransacciones = new ListaEnlazada<>();
        nuevoBloque.HeapTransacciones = new HEAP<>();*/

        for (Transaccion tx : transacciones) {
            int id_comp = tx.id_comprador();
            int id_vend = tx.id_vendedor();
            int monto = tx.monto();

            Usuario vendedor = posicionUsuariosHeap.get(id_vend - 1);
            Usuario comprador = (id_comp != 0) ? posicionUsuariosHeap.get(id_comp - 1) : null;

            if (id_comp != 0) {
                comprador._dinero -= monto;
                maximoTenedor.actualizarElemento(comprador.HeapIndice());
            }

            vendedor._dinero += monto;
            maximoTenedor.actualizarElemento(vendedor.HeapIndice());

            /*nuevoBloque.ListaTransacciones.agregarAtras(tx);
            nuevoBloque.HeapTransacciones.Encolar(tx);*/
        }
        /*nuevoBloque.recalcularSumaYCantidad();*/
        cadena.add(nuevoBloque);
        ultimoBloque = nuevoBloque;
    }

    public Transaccion txMayorValorUltimoBloque() {
        if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) {
            return null;
        }
        return ultimoBloque.HeapTransacciones.Proximo();
    }

    public Transaccion[] txUltimoBloque() {
        if (ultimoBloque == null || ultimoBloque.ListaTransacciones.longitud() == 0) {
            return new Transaccion[0];
        }

        int longi = ultimoBloque.ListaTransacciones.longitud();
        Transaccion[] copia = new Transaccion[longi];
        for (int i = 0; i < longi; i++) {
            copia[i] = ultimoBloque.ListaTransacciones.obtener(i);
        }
        return copia;
    }

    public int maximoTenedor() {
        return maximoTenedor.Proximo().id();
    }

    public int montoMedioUltimoBloque() {
        if (ultimoBloque == null || ultimoBloque.cantidadTx == 0) {
            return 0;
        }
        return ultimoBloque.sumaMontos / ultimoBloque.cantidadTx;
    }

    public void hackearTx() {
        if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) return;

        Transaccion tx = ultimoBloque.HeapTransacciones.Proximo();
        ultimoBloque.HeapTransacciones.Desencolar();

        for (int i = 0; i < ultimoBloque.ListaTransacciones.longitud(); i++) {
            if (ultimoBloque.ListaTransacciones.obtener(i).equals(tx)) {
                ultimoBloque.ListaTransacciones.eliminar(i);
                break;
            }
        }

        int id_comp = tx.id_comprador();
        int id_vend = tx.id_vendedor();
        int monto = tx.monto();

        Usuario vendedor = posicionUsuariosHeap.get(id_vend - 1);
        if (id_comp != 0) {
            Usuario comprador = posicionUsuariosHeap.get(id_comp - 1);
            comprador._dinero += monto;
            vendedor._dinero -= monto;
            maximoTenedor.actualizarElemento(comprador.HeapIndice());
            maximoTenedor.actualizarElemento(vendedor.HeapIndice());
            ultimoBloque.sumaMontos -= monto;
            ultimoBloque.cantidadTx -= 1;
        } else {
            vendedor._dinero -= monto;
            maximoTenedor.actualizarElemento(vendedor.HeapIndice());
        }
    }
}
