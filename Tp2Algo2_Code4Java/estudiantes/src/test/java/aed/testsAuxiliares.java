package aed;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class testsAuxiliares {

    @Test
    public void testTransaccion_ComparacionPorMontoYId() {
        Transaccion t1 = new Transaccion(1, 1, 2, 100);
        Transaccion t2 = new Transaccion(2, 3, 4, 200);
        Transaccion t3 = new Transaccion(3, 5, 6, 100);

        assertTrue(t1.compareTo(t2) > 0); 
        assertTrue(t1.compareTo(t3) > 0);
    }

    @Test
    public void testTransaccion_Equals() {
        Transaccion t1 = new Transaccion(1, 1, 2, 100);
        Transaccion t2 = new Transaccion(1, 1, 2, 100);
        Transaccion t3 = new Transaccion(2, 1, 2, 100);

        assertTrue(t1.equals(t2));
        assertFalse(t1.equals(t3));
    }


    @Test
    public void testUsuario_ComparacionPorDineroYId() {
        Usuario u1 = new Usuario(1, 100);
        Usuario u2 = new Usuario(2, -50);
        Usuario u3 = new Usuario(3, 100); 

        assertTrue(u1.compareTo(u2) < 0);
        assertTrue(u1.compareTo(u3) < 0); 

        u3._dinero = 150; 
        
        assertTrue(u1.compareTo(u3) > 0);
    }

    @Test
    public void testUsuario_Handle() {
        Usuario u = new Usuario(1, 100);
        Handle<Usuario> h1 = new Handle<>(0);
        Handle<Usuario> h2 = new Handle<>(1);
        u.setHandle(h1);
        assertEquals(h1, u.getHandle());
        assertEquals(0, u.getHandle().posicion());

        //cambiar Handle 
        u.setHandle(h2);
        assertEquals(h2,u.getHandle()); 
        assertEquals(1,u.getHandle().posicion());
        assertNotEquals(h1, u.getHandle());
    }

    @Test
    public void testHEAP_EncolarYDesencolar() {
        HEAP<Usuario> heap = new HEAP<>();
        Usuario u1 = new Usuario(1, 100);
        Usuario u2 = new Usuario(2, 200);
        Usuario u3 = new Usuario(3, 50);
        Usuario u4 = new Usuario(4, 200);

        heap.Encolar(u1);
        heap.Encolar(u2);
        heap.Encolar(u3);
        heap.Encolar(u4);
    
        assertEquals(u2, heap.Proximo());
        heap.Desencolar();
        assertEquals(u4, heap.Proximo());
        heap.Desencolar();
        assertEquals(u1, heap.Proximo());
        heap.Desencolar();
        assertEquals(u3, heap.Proximo());
        heap.Desencolar();
    
        // Verificar que el heap queda vacío
        assertTrue(heap.size() == 0);
    }
    @Test
    public void testHEAP_ActualizarElemento() {
        HEAP<Usuario> heap = new HEAP<>();
        Usuario u1 = new Usuario(1, 100);
        Usuario u2 = new Usuario(2, 200);
    
        heap.Encolar(u1);
        heap.Encolar(u2);
        assertEquals(u2, heap.Proximo());
    
        // Cambiar el dinero
        u1._dinero = 200; // Ahora u1 es el mayor, ID u1 < ID u2
        heap.actualizarElemento(u1.getHandle());
        assertEquals(u1, heap.Proximo()); 
        heap.Desencolar();
        assertEquals(u2, heap.Proximo());
    }

    @Test
    public void testBloque_TransaccionesDeCreacion() {
        Transaccion tCreacion = new Transaccion(0, 0, 1, 1);
        Transaccion tNormal = new Transaccion(1, 1, 2, 50);
        Bloque bloque = new Bloque(new Transaccion[]{tCreacion, tNormal});

        assertEquals(1, bloque.cantidadTx); 
        assertEquals(50, bloque.sumaMontos);
    }

    @Test
    public void testBloque_HeapTransacciones() {
        Transaccion t1 = new Transaccion(1, 1, 2, 150);
        Transaccion t2 = new Transaccion(2, 2, 3, 100);
        Transaccion t3 = new Transaccion(3, 3, 4, 100);
        Bloque bloque = new Bloque(new Transaccion[]{t1, t2, t3});

        // Verificar orden descendente por monto, luego por ID
        assertEquals(3, bloque.cantidadTx);
        assertEquals(t1, bloque.HeapTransacciones.Proximo()); 
        bloque.HeapTransacciones.Desencolar();
        assertEquals(t3, bloque.HeapTransacciones.Proximo()); // Igualdad en monto, desempato por ID mayor
        bloque.HeapTransacciones.Desencolar();
        assertEquals(t2, bloque.HeapTransacciones.Proximo()); 

    }


    @Test
    public void testIntegracion_HEAPEnBloque() {
        Transaccion t1 = new Transaccion(1, 1, 2, 50);
        Transaccion t2 = new Transaccion(2, 2, 3, 100);
        Bloque bloque = new Bloque(new Transaccion[]{t1, t2});

        assertEquals(t2, bloque.HeapTransacciones.Proximo());
        // Simular cambio en una transacción
        t1.monto = 150;
        bloque.HeapTransacciones.actualizarElemento(t1.handle());
    
        // Ahora t1 debería ser el primero
        assertEquals(t1, bloque.HeapTransacciones.Proximo());
        bloque.HeapTransacciones.Desencolar();
        assertEquals(t2, bloque.HeapTransacciones.Proximo());
    }
}