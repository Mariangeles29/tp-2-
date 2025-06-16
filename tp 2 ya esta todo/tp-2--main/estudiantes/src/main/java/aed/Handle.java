package aed;

import java.util.*;

class Handle<T> {
   private int posicion;

    public Handle(int posicion) {
        this.posicion = posicion;
    }

    public int posicion() {
        return posicion;
    }

    public void setPosicion(int nuevaPos) {
        this.posicion = nuevaPos;
    }
}
