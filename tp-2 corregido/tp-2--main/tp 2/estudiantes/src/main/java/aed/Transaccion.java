package aed;

public class Transaccion implements Comparable<Transaccion> {
    private int id;
    private int id_comprador;
    private int id_vendedor;
    private int monto;

    public Transaccion(int id, int id_comprador, int id_vendedor, int monto) {
        this.id = id;
        this.id_comprador = id_comprador;
        this.id_vendedor = id_vendedor;
        this.monto = monto;
    }

    @Override
    public int compareTo(Transaccion otro) {
        if (this.monto != otro.monto) {
        return otro.monto - this.monto; 
    } else {
        return this.id - otro.id; 
    }
    }

    @Override
    public boolean equals(Object otro){
        boolean otroEsNull=(otro==null);
        boolean claseDistinta=otro.getClass()!=this.getClass();

        if (otroEsNull || claseDistinta) {
            return false;
            }

        Transaccion Transaccion_otro=(Transaccion) otro; //casteo

        int id_otro=Transaccion_otro.id();
        int id_comprador_otro=Transaccion_otro.id_comprador();
        int id_vendedor_otro=Transaccion_otro.id_vendedor();
        int monto_otro=Transaccion_otro.monto();

        return (id_otro==id && id_comprador_otro==id_comprador && id_vendedor_otro==id_vendedor && monto_otro==monto);
    }

    public int id() {
        return id;
    }

    public int monto() {
        return monto;
    }

    public int id_comprador() {
        return id_comprador;
    }
    
    public int id_vendedor() {
        return id_vendedor;
    }
}