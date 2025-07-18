package aed;

class Transaccion implements Comparable<Transaccion> {
    int id;
    int comprador;
    int vendedor;
    int monto;
    Handle<Transaccion> handle;

    public Transaccion(int id, int comprador, int vendedor, int monto) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.monto = monto;
        this.handle = null;
    }

    public int id() { return id; }
    public int id_comprador() { return comprador; }
    public int id_vendedor() { return vendedor; }
    public int monto() { return monto; }
    public Handle<Transaccion> handle() {return handle;}

    public void setHandle(Handle<Transaccion> h) { this.handle = h; }

    public int compareTo(Transaccion otra) {
       if (this.monto != otra.monto) {
        return Integer.compare(otra.monto, this.monto); 
    } else {
        return Integer.compare(otra.id, this.id);       
    }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; 
        if (o == null || getClass() != o.getClass()) return false;

        Transaccion t = (Transaccion) o;
        if (id != t.id) return false;

        return true;
    }

    public String toString() {
        return "Transaccion{id=" + id + ", comprador=" + comprador + ", vendedor=" + vendedor + ", monto=" + monto + "}";
    }
}