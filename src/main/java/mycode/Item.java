package mycode;

/**
 * Representa un par clave-valor para devoler reusltado al usuario.
 *
 * @author Ismael Cabrera Cabrera
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Sara Trujillo García
 * @param <K> El tipo de la clave.
 * @param <V> El tipo del valor.
 */
public class Item<K, V> {
    public K key;
    public V value;

    /**
     * Crea un nuevo Item con la clave y el valor proporcionados.
     *
     * @param key Clave del par.
     * @param value Valor del par.
     */
    public Item(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
