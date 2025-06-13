package aed;
import java.util.ArrayList;
import java.util.List;

public class HEAP<T extends Comparable<T>> {

    private ArrayList<T> datos_heap;

    public HEAP(){
        datos_heap= new ArrayList<>();
    }
    public void actualizarElemento(int pos) {
        T elemento = datos_heap.get(pos);
        ((Usuario) elemento).heapIndice = pos;
    }

    public T Proximo(){
        return datos_heap.get(0);
    }

    public void Encolar(T elemento){
        datos_heap.add(elemento);
        int posicion_elemento = datos_heap.size() - 1;
        int posicion_padre = (posicion_elemento - 1) / 2;
        while (posicion_elemento > 0 && datos_heap.get(posicion_elemento).compareTo(datos_heap.get(posicion_padre)) > 0){
            T subir = datos_heap.get(posicion_elemento);
            datos_heap.set(posicion_elemento, datos_heap.get(posicion_padre));
            datos_heap.set(posicion_padre, subir);
            
            posicion_elemento= posicion_padre;
            posicion_padre = (posicion_elemento-1)/2;
            }
        }
    
    public void Desencolar(){
        
        if (datos_heap.size() == 0) {
            return;
        }

        T ultimo_elemento = datos_heap.get(datos_heap.size() - 1);
        datos_heap.set(0, ultimo_elemento);
        datos_heap.remove(datos_heap.size() - 1);  

        int posicion = 0;
        int longitud = datos_heap.size();

        int hijo_izquierdo = 2 * posicion + 1;
        int hijo_derecho = 2 * posicion + 2;
            while ((hijo_izquierdo < longitud && datos_heap.get(hijo_izquierdo).compareTo(datos_heap.get(posicion)) > 0) || 
       (hijo_derecho < longitud && datos_heap.get(hijo_derecho).compareTo(datos_heap.get(posicion)) > 0)) {
                int mayor =posicion;
                if (hijo_izquierdo<longitud && datos_heap.get(hijo_izquierdo).compareTo(datos_heap.get(mayor))>0){
                    mayor=hijo_izquierdo;
                }
                if (hijo_derecho<longitud && datos_heap.get(hijo_derecho).compareTo(datos_heap.get(mayor))>0){
                    mayor=hijo_derecho;
                }
                T intercambiar=datos_heap.get(posicion);
                datos_heap.set(posicion,datos_heap.get(mayor));
                datos_heap.set(mayor,intercambiar);

                posicion=mayor;
                hijo_izquierdo=2*posicion+1;
                hijo_derecho=2*posicion+2;
            }
        }
    public int size() {
    return datos_heap.size();
}            
        
        }

    




    




















