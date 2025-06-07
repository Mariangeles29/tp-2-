
public class Usuario {
    int _id;
    int _dinero;

    public Usuario(int id, int dinero) {
        this._id=id;
        this._dinero=dinero;
    }

    public int id() {
        return _id;
    }

    public int dinero() {
        return _dinero;
    }

}