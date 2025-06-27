    package aed;

    import java.util.ArrayList;

    public class Berretacoin {
        HEAP<Usuario> montoUsuario;// Heap para encontrar usuario con mas dinero   
        ArrayList<Usuario> usuarios; // Todos los usuarios
        // ArrayList<Bloque> cadena;// Cadena de bloques
        Bloque ultimoBloque;// Referencia al ultimo bloque

        // Constructor con numero de usuarios
        public Berretacoin(int n_usuarios) {
            montoUsuario = new HEAP<>();
            usuarios = new ArrayList<>(n_usuarios);
            // cadena = new ArrayList<>();
            ultimoBloque = null;

            // Crea usuarios iniciales con saldo 0
            for (int i = 1; i <= n_usuarios; i++) {
                Usuario u = new Usuario(i, 0);
                Handle<Usuario> h = montoUsuario.Encolar(u);
                u.setHandle(h);
                usuarios.add(u);
            }
        }

        // Agrega un bloque de transacciones
        public void agregarBloque(Transaccion[] transacciones) {
            Bloque nuevoBloque = new Bloque(transacciones);

            // Procesa cada transaccion
            for (Transaccion tx : transacciones) {
                int id_comp = tx.id_comprador();
                int id_vend = tx.id_vendedor();
                int monto = tx.monto();

                Usuario vendedor = usuarios.get(id_vend - 1);
                Usuario comprador=null;

                // Actualiza saldos
                if (id_comp != 0){
                    comprador= usuarios.get(id_comp - 1);
                }
                 
                if (id_comp != 0) {
                    comprador._dinero -= monto;
                    montoUsuario.actualizarElemento(comprador.getHandle());
                }

                vendedor._dinero += monto;
                montoUsuario.actualizarElemento(vendedor.getHandle());
            }

            // cadena.add(nuevoBloque);
            ultimoBloque = nuevoBloque;
        }

        // Obtiene la transaccion de mayor valor del ultimo bloque
        public Transaccion txMayorValorUltimoBloque() {
            if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) {
                return null;
            }
            return ultimoBloque.HeapTransacciones.Proximo();
        }

        // Obtiene todas las transacciones del último bloque
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

        // Obtiene el ID del usuario con mas dinero
        public int maximoTenedor() {
            return montoUsuario.Proximo().id();
        }
 // Calcula el monto medio del ultimo bloque
        public int montoMedioUltimoBloque() {
    if (ultimoBloque == null || ultimoBloque.cantidadTx == 0) {
        return 0;
    }
    return ultimoBloque.sumaMontos / ultimoBloque.cantidadTx; // Division entera
}

 // Elimina la transaccion de mayor valor del ultimo bloque
    public void hackearTx() {
    if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) return; // O (1)

    Transaccion tx = ultimoBloque.HeapTransacciones.Proximo(); // O(1)
    ultimoBloque.HeapTransacciones.Desencolar();// O(log t)

    // Buscar y eliminar la transacción de la lista
     // Busca y elimina de la lista
    boolean encontrada = false; // O(1)
    for (int i = 0; i < ultimoBloque.ListaTransacciones.longitud(); i++) { // O(t)
        Transaccion txActual = ultimoBloque.ListaTransacciones.obtener(i); // O(1)
        if (txActual.id() == tx.id() && // O(1)
            txActual.id_comprador() == tx.id_comprador() && // O(1)
            txActual.id_vendedor() == tx.id_vendedor() && // O(1)
            txActual.monto() == tx.monto()) {// O(1) 
            ultimoBloque.ListaTransacciones.eliminar(i);// O(1) vemos
            encontrada = true;// O(1)
            break; // O(1) 
        }
    }

    if (!encontrada) return; // No deberia pasar, pero por seguridad // O(1)

    // Actualizar saldos
    int id_comp = tx.id_comprador();// O(1)
    int id_vend = tx.id_vendedor();// O(1)
    int monto = tx.monto();// O(1)

    Usuario vendedor = usuarios.get(id_vend - 1);// O(1)
    vendedor._dinero -= monto;// O(1)
    montoUsuario.actualizarElemento(vendedor.getHandle()); //O(log n) 

    if (id_comp != 0) { // O(1)
        Usuario comprador = usuarios.get(id_comp - 1);
        comprador._dinero += monto;
        montoUsuario.actualizarElemento(comprador.getHandle()); //O(log n) 
        ultimoBloque.sumaMontos -= monto; // O(1)
        ultimoBloque.cantidadTx--;// O(1)
    }
}

    }