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

            Usuario vendedor = usuarios.get(id_vend - 1);
            if (id_comp != 0) {
                Usuario comprador = usuarios.get(id_comp - 1);
                comprador._dinero += monto;
                vendedor._dinero -= monto;
                maximoTenedor.actualizarElemento(comprador.getHandle());
                maximoTenedor.actualizarElemento(vendedor.getHandle());
                ultimoBloque.sumaMontos -= monto;
                ultimoBloque.cantidadTx -= 1;
            } else {
                vendedor._dinero -= monto;
                maximoTenedor.actualizarElemento(vendedor.getHandle());
            }
        }
    }