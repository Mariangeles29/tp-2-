package aed;

import java.util.ArrayList;

public class Berretacoin {
    private HEAP<Usuario> montoUsuario;   // Heap para usuarios ordenados por dinero (O(1) para maximoTenedor)
    private ArrayList<Usuario> usuarios;  // Lista de usuarios (O(1) acceso por id)
    private Bloque ultimoBloque;         // Referencia al último bloque

    // O(P)
    public Berretacoin(int n_usuarios) {
        montoUsuario = new HEAP<>();
        usuarios = new ArrayList<>(n_usuarios);
        
        // Inicializar usuarios con saldo 0
        for (int i = 1; i <= n_usuarios; i++) {
            Usuario u = new Usuario(i, 0);
            Handle<Usuario> h = montoUsuario.Encolar(u); // O(1) por usuario (total O(P))
            u.setHandle(h);
            usuarios.add(u);
        }
    }

    // O(nb * log P)
    /*public void agregarBloque(Transaccion[] transacciones) {
        Bloque nuevoBloque = new Bloque(transacciones); // O(nb) para crear lista, O(nb log nb) para heap
        
        for (Transaccion tx : transacciones) { // O(nb) iteraciones
            // Procesar transacción
            int id_comp = tx.id_comprador();
            int id_vend = tx.id_vendedor();
            int monto = tx.monto();

            Usuario vendedor = usuarios.get(id_vend - 1); // O(1)
            Usuario comprador = (id_comp != 0) ? usuarios.get(id_comp - 1) : null;

            if (comprador != null) {
                comprador._dinero -= monto;
                montoUsuario.actualizarElemento(comprador.getHandle()); // O(log P)
            }

            vendedor._dinero += monto;
            montoUsuario.actualizarElemento(vendedor.getHandle()); // O(log P)
        }
        
        ultimoBloque = nuevoBloque;
    }*/
    // O(nb * log P)
    public void agregarBloque(Transaccion[] transacciones) {
    // 1. Crear el bloque usando heapify - O(nb)
    Bloque nuevoBloque = new Bloque(transacciones);
    
    // 2. Procesar transacciones - O(nb * log P)
    for (Transaccion tx : transacciones) {
        int id_comp = tx.id_comprador();
        int id_vend = tx.id_vendedor();
        int monto = tx.monto();

        Usuario vendedor = usuarios.get(id_vend - 1);
        Usuario comprador = (id_comp != 0) ? usuarios.get(id_comp - 1) : null;

        if (comprador != null) {
            comprador._dinero -= monto;
            montoUsuario.actualizarElemento(comprador.getHandle());
        }

        vendedor._dinero += monto;
        montoUsuario.actualizarElemento(vendedor.getHandle());
    }
    
    ultimoBloque = nuevoBloque;
    }
    // O(1)
    public Transaccion txMayorValorUltimoBloque() {
        if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) {
            return null;
        }
        return ultimoBloque.HeapTransacciones.Proximo(); // O(1)
    }

    // O(nb)
   public Transaccion[] txUltimoBloque() {
    if (ultimoBloque == null) {
        return new Transaccion[0];  // Si no hay bloques retornar arreglo vacio
    }

    // Obtenemos la lista enlazada de transacciones del ultimo bloque
    ListaEnlazada<Transaccion> listaTransacciones = ultimoBloque.ListaTransacciones;

    // Creamos un arreglo del tamaño de la lista
    Transaccion[] copia = new Transaccion[listaTransacciones.longitud()];

    // Usamos el iterador para recorrer la lista eficientemente (O(n), donde n = transacciones en el bloque)
    Iterador<Transaccion> iterador = listaTransacciones.iterador();
    int i = 0;
    while (iterador.haySiguiente()) {
        copia[i++] = iterador.siguiente();
    }

    return copia;
}
    // O(1)
    public int maximoTenedor() {
        return montoUsuario.Proximo().id(); // O(1)
    }

    // O(1)
    public int montoMedioUltimoBloque() {
        if (ultimoBloque == null || ultimoBloque.cantidadTx == 0) {
            return 0;
        }
        return ultimoBloque.sumaMontos / ultimoBloque.cantidadTx;
    }

    // O(log nb + log P)
    public void hackearTx() {
        if (ultimoBloque == null || ultimoBloque.HeapTransacciones.size() == 0) return;

        // Extraer transacción del heap en O(log nb)
        Transaccion tx = ultimoBloque.HeapTransacciones.Proximo();
        ultimoBloque.HeapTransacciones.Desencolar();

        // Eliminar de la lista usando el nodo del handle en O(1)
        Nodo<Transaccion> nodo = (Nodo<Transaccion>) tx.handle.nodoLista();
        ultimoBloque.ListaTransacciones.eliminarNodo(nodo);

        // Revertir transacción en O(log P)
        int id_comp = tx.id_comprador();
        int id_vend = tx.id_vendedor();
        int monto = tx.monto();

        Usuario vendedor = usuarios.get(id_vend - 1);
        vendedor._dinero -= monto;
        montoUsuario.actualizarElemento(vendedor.getHandle());

        if (id_comp != 0) {
            Usuario comprador = usuarios.get(id_comp - 1);
            comprador._dinero += monto;
            montoUsuario.actualizarElemento(comprador.getHandle());
            ultimoBloque.sumaMontos -= monto;
            ultimoBloque.cantidadTx--;
        }
    }
}