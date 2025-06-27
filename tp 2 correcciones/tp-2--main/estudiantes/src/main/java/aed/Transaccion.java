package aed;

class Transaccion implements Comparable<Transaccion> {
    int id;
    int comprador;
    int vendedor;
    int monto;
    
// Constructor
    public Transaccion(int id, int comprador, int vendedor, int monto) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.monto = monto;
    }
// Getters
    public int id() { return id; }
    public int id_comprador() { return comprador; }
    public int id_vendedor() { return vendedor; }
    public int monto() { return monto; }

// Compara por monto (descendente) y luego por ID (descendente)
    public int compareTo(Transaccion otra) {
       if (this.monto != otra.monto) {
        return Integer.compare(otra.monto, this.monto); // mayor monto primero
    } else {
        return Integer.compare(otra.id, this.id);       // mayor id primero
    }
    }

    // Para comparar transacciones
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Transaccion)) return false;
        Transaccion t = (Transaccion) o;
        return id == t.id && comprador == t.comprador && vendedor == t.vendedor && monto == t.monto;
    }

    // Representacion como string
    public String toString() {
        return "Transaccion{id=" + id + ", comprador=" + comprador + ", vendedor=" + vendedor + ", monto=" + monto + "}";
    }
}