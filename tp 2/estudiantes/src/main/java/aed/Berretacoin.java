package aed;

import java.util.ArrayList;

public class Berretacoin {

    HEAP<Usuario> maximoTenedor;
    ArrayList<Usuario> posicionUsuariosHeap;
    ArrayList<Bloque> cadena;
    Bloque ultimoBloque;
    public Berretacoin(int n_usuarios){
        maximoTenedor = new HEAP<Usuario>();
        posicionUsuariosHeap = new ArrayList<>(n_usuarios);
        cadena = new ArrayList<>();
        ultimoBloque = null;
        for (int i=1;i<=n_usuarios;i++){
            Usuario u= new Usuario(i,0);
            posicionUsuariosHeap.add(u);
        }

    }

    public void agregarBloque(Transaccion[] transacciones){
        Bloque nuevoBloque = new Bloque(transacciones);
        nuevoBloque .ListaTransacciones = new ListaEnlazada<>();
        nuevoBloque .HeapTransacciones = new HEAP<>();
        for (Transaccion tx: transacciones){
            int id_comp=tx.id_comprador();
            int id_vend=tx.id_vendedor();
            int monto= tx.monto();
            Usuario comprador = posicionUsuariosHeap.get(id_comp-1);
            Usuario vendedor = posicionUsuariosHeap.get(id_vend-1);
            comprador._dinero-=monto;
            vendedor._dinero+=monto;
            maximoTenedor.Encolar(comprador);
            maximoTenedor.Encolar(vendedor);
            nuevoBloque.ListaTransacciones.agregarAtras(tx);
            nuevoBloque.HeapTransacciones.Encolar(tx);
        }
        cadena.add(nuevoBloque );
        ultimoBloque = nuevoBloque;
        }

    public Transaccion txMayorValorUltimoBloque(){
        if (cadena.size()==0){
            return null;
        }
        Bloque ultimoBloque=cadena.get(cadena.size()-1);
        if (ultimoBloque.HeapTransacciones.size() == 0){
            return null;
        }
        return ultimoBloque.HeapTransacciones.Proximo();
    }

    public Transaccion[] txUltimoBloque(){
        if (ultimoBloque == null || ultimoBloque.ListaTransacciones.longitud() == 0) { 
            return new Transaccion[0];
    }
    int longi=ultimoBloque.ListaTransacciones.longitud();
    Transaccion[] copia = new Transaccion[longi];
    for (int i=0; i<longi;i++){
        copia[i]=ultimoBloque.ListaTransacciones.obtener(i);
    }
    return copia;
    }

    public int maximoTenedor(){
        return maximoTenedor.Proximo().id();
    }

    public int montoMedioUltimoBloque(){
        if (cadena.size()==0){
            return 0;
        }
        Bloque ultimo = cadena.get(cadena.size()-1);
        if (ultimo.cantidadTx==0){
            return 0;
        }
        return ultimo.sumaMontos/ultimo.cantidadTx;
    }

    public void hackearTx(){
        throw new UnsupportedOperationException("Implementar!");
    }
}
