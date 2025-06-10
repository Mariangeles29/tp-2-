package aed;

public class Usuario implements Comparable<Usuario> {

    int _id;
    int _dinero;
    int heapIndice;

    public Usuario(int id, int dinero) {
        this._id=id;
        this._dinero=dinero;
        heapIndice=-1;
    }

    public int id() {
        return _id;
    }

    public int dinero() {
        return _dinero;
    }
@Override
    public int compareTo(Usuario otro) {
        if (this._dinero != otro._dinero) {
            return this._dinero - otro._dinero;  
        } else {
            return otro._id - this._id;
        }

    }
    public void setearHeapIndice(int indice) {
        this.heapIndice = indice;
    }
    public int HeapIndice() {
        return heapIndice;
    }
}