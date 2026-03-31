package mycode;

/**
 * Clase que representa un nodo interno en la estructura del diccionario.
 *
 * @author Ismael Cabrera Cabrera
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Sara Trujillo García
 * @param <K> El tipo de la clave almacenada.
 * @param <V> El tipo del valor asociado.
 */
public class Entry<K, V> {
    K key;
    V value;
    boolean delete;

    Entry<K, V> prev;
    Entry<K, V> next;

    /**
     * Constructor para una nueva entrada. El estado inicial de borrado es siempre.
     *
     * @param key La clave del elemento.
     * @param value El valor del elemento.
     */
    public Entry(K key, V value){
        this.key = key;
        this.value = value;
        this.delete = false;
    }
}
