package aed;
import java.util.ArrayList;
import java.util.List;

public class HEAP<T extends Comparable<T>> {

    private ArrayList<T> datos_heap;

    public HEAP(){
        datos_heap= new ArrayList<>();
    }
    public void actualizarElemento(int pos) {
        heapifyUp(pos);
        heapifyDown(pos);
    }

    private void heapifyUp(int pos) {
    while (pos > 0) {
        int padre = (pos - 1) / 2;
        T actual = datos_heap.get(pos);
        T padreElem = datos_heap.get(padre);

        if (actual.compareTo(padreElem) > 0) {
            
            datos_heap.set(pos, padreElem);
            datos_heap.set(padre, actual);

            if (actual instanceof Usuario) {
                ((Usuario) actual).setearHeapIndice(padre);
                ((Usuario) padreElem).setearHeapIndice(pos);
            }

            pos = padre;
        } else {
            break;
        }
    }
    }
    private void heapifyDown(int pos) {
    int n = datos_heap.size();

    while (true) {
        int izq = 2 * pos + 1;
        int der = 2 * pos + 2;
        int mayor = pos;

        if (izq < n && datos_heap.get(izq).compareTo(datos_heap.get(mayor)) > 0) {
            mayor = izq;
        }
        if (der < n && datos_heap.get(der).compareTo(datos_heap.get(mayor)) > 0) {
            mayor = der;
        }

        if (mayor == pos) break;

        T actual = datos_heap.get(pos);
        T hijoMayor = datos_heap.get(mayor);

        datos_heap.set(pos, hijoMayor);
        datos_heap.set(mayor, actual);

        if (actual instanceof Usuario) {
            ((Usuario) actual).setearHeapIndice(mayor);
            ((Usuario) hijoMayor).setearHeapIndice(pos);
        }

        pos = mayor;
    }
    }

    public T Proximo(){
        return datos_heap.get(0);
    }



    public void Encolar(T elemento) {
    if (elemento instanceof Usuario) {
        Usuario u = (Usuario) elemento;
        if (u.HeapIndice() != -1) {
            actualizarElemento(u.HeapIndice());
            return;
        }
        u.setearHeapIndice(datos_heap.size());
    }

    datos_heap.add(elemento);
    heapifyUp(datos_heap.size() - 1);
    }
    
    public void Desencolar() {
    if (datos_heap.size() == 0) return;

    T ultimo = datos_heap.get(datos_heap.size() - 1);
    T primero = datos_heap.get(0);

    datos_heap.set(0, ultimo);
    datos_heap.remove(datos_heap.size() - 1);

    if (primero instanceof Usuario) ((Usuario) primero).setearHeapIndice(-1);
    if (ultimo instanceof Usuario) ((Usuario) ultimo).setearHeapIndice(0);

    heapifyDown(0);
}
    public int size() {
    return datos_heap.size();
    }
           
        
}

