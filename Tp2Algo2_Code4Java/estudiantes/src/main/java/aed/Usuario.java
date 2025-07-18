package aed;
class Usuario implements Comparable<Usuario> {
    int _id;
    int _dinero;
    Handle<Usuario> handle;

// Constructor con id y cantidad de dinero inicial
    public Usuario(int id, int dinero) {
        this._id = id;
        this._dinero = dinero;
    }

    public int id() {
        return _id;
    }

    public int dinero() {
        return _dinero;
    }
    // asigna un Handle a este usuario
    public void setHandle(Handle<Usuario> h) {
        this.handle = h;
    }

    public Handle<Usuario> getHandle() {
        return this.handle;
    }

    // Compara usuarios por dinero (descendente) y luego por ID (ascendente)
    public int compareTo(Usuario otro) {
        if (this._dinero != otro._dinero) {
            return Integer.compare(otro._dinero, this._dinero); // orden descendente
        } else {
            return Integer.compare(this._id, otro._id); // menor id en caso de empate
        }
    }
}