    package aed;

    import java.util.ArrayList;

    public class Berretacoin {
        HEAP<Usuario> maximoTenedor;
        
        ArrayList<Usuario> usuarios; // Ya no guarda Handle, guarda Usuario
        ArrayList<Bloque> cadena;
        Bloque ultimoBloque;

        public Berretacoin(int n_usuarios) {
            maximoTenedor = new HEAP<>();
            usuarios = new ArrayList<>(n_usuarios);
            cadena = new ArrayList<>();
            ultimoBloque = null;

            for (int i = 1; i <= n_usuarios; i++) {
                Usuario u = new Usuario(i, 0);
                Handle<Usuario> h = maximoTenedor.Encolar(u);
                u.setHandle(h);
                usuarios.add(u);
            }
        }

        public void agregarBloque(Transaccion[] transacciones) {
            Bloque nuevoBloque = new Bloque(transacciones);

            for (Transaccion tx : transacciones) {
                int id_comp = tx.id_comprador();
                int id_vend = tx.id_vendedor();
                int monto = tx.monto();

                Usuario vendedor = usuarios.get(id_vend - 1);
                Usuario comprador = (id_comp != 0) ? usuarios.get(id_comp - 1) : null;

                if (id_comp != 0) {
                    comprador._dinero -= monto;
                    maximoTenedor.actualizarElemento(comprador.getHandle());
                }

                vendedor._dinero += monto;
                maximoTenedor.actualizarElemento(vendedor.getHandle());
            }

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
    return ultimoBloque.sumaMontos / ultimoBloque.cantidadTx; // División entera
}

    public void hackearTx() {
    if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) return;

    Transaccion tx = ultimoBloque.HeapTransacciones.Proximo();
    ultimoBloque.HeapTransacciones.Desencolar();

    // Buscar y eliminar la transacción de la lista
    boolean encontrada = false;
    for (int i = 0; i < ultimoBloque.ListaTransacciones.longitud(); i++) {
        Transaccion txActual = ultimoBloque.ListaTransacciones.obtener(i);
        if (txActual.id() == tx.id() && 
            txActual.id_comprador() == tx.id_comprador() && 
            txActual.id_vendedor() == tx.id_vendedor() && 
            txActual.monto() == tx.monto()) {
            ultimoBloque.ListaTransacciones.eliminar(i);
            encontrada = true;
            break;
        }
    }

    if (!encontrada) return; // No debería pasar, pero por seguridad

    // Actualizar saldos
    int id_comp = tx.id_comprador();
    int id_vend = tx.id_vendedor();
    int monto = tx.monto();

    Usuario vendedor = usuarios.get(id_vend - 1);
    vendedor._dinero -= monto;
    maximoTenedor.actualizarElemento(vendedor.getHandle());

    if (id_comp != 0) {
        Usuario comprador = usuarios.get(id_comp - 1);
        comprador._dinero += monto;
        maximoTenedor.actualizarElemento(comprador.getHandle());
        ultimoBloque.sumaMontos -= monto;
        ultimoBloque.cantidadTx--;
    }
}

    }