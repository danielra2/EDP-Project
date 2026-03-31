package mycode;

/**
 * Implementación de un diccionario inspirado en las estructuras de datos de Python.
 *
 * La clase utiliza una tabla Hash con direccionamiento abierto para la resolución de colisiones.
 *
 * @author Ismael Cabrera Cabrera
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Sara Trujillo García
 * @param <K> El tipo de las claves mantenidas por este diccionario.
 * @param <V> El tipo de los valores mapeados.
 */
public class PythonDict<K, V> {
    private Entry<K, V>[] table;  //storage Array (added <K, V> to support any data type)
    private int used;       //number of used positions (not deleted)
    private int filled;     //number of positions filled (deleted)

    private Entry<K, V> head;
    private Entry<K, V> tail;

    /**
     * Constructor que inicializa el diccionario con una capacidad inicial.
     *
     * @param capacity Tamaño inicial de la tabla de hash.
     */
    public PythonDict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
        head = null;
        tail = null;
    }

    /**
     * Calcula el índice de la tabla para una clave dada mediante su hashCode.
     *
     * @param key Clave a procesar.
     * @return Índice dentro del rango del array actual.
     */
    private int index(K key){
        int h = key.hashCode(); //obtain the hash code
        //avoid negatives
        h = Math.abs(h);
        // size adjustment
        return h % table.length;
    }

    /**
     * Duplica el tamaño de la tabla interna y reubica todos los elementos.
     */
    private void resize(){
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        used = 0;
        filled = 0;
        head = null;
        tail = null;

        for(int i=0; i<oldTable.length; i++){
            if(oldTable[i] != null && !oldTable[i].delete){
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }

    /**
     * Recupera el valor asociado a una clave.
     *
     * @param key La clave cuyo valor se desea obtener.
     * @return El valor asociado, o null si la clave no existe.
     */
    public V get(K key){
        int hash = index(key);
        int pos = 0;

        while(pos < table.length){
            int idx = (hash + pos * pos) % table.length;

            if(table[idx] == null){
                return null;
            }
            // if the value is not delete and the key matches with the given, return value
            if(!table[idx].delete && table[idx].key.equals(key)){
                return table[idx].value;
            }
            pos++;
        }
        return null;
    }

    /**
     * Elimina un par clave-valor del diccionario marcándolo como borrado.
     *
     * @param key Clave a eliminar.
     */
    public void del(K key){
        int hash = index(key);
        int pos = 0;

        while(pos < table.length){
            int idx = (hash + pos * pos) % table.length;

            if(table[idx] == null){
                return;
            }

            //if the key is in the dict, mark it as deleted
            if(!table[idx].delete && table[idx].key.equals(key)){
                table[idx].delete = true;
                used--;

                unlink(table[idx]);
                return;
            }
            pos++;
        }
    }

    /**
     * Elimina la clave y devuelve su valor asociado.
     *
     * @param key Clave a eliminar.
     * @return El valor que estaba asociado a la clave.
     * @throws KeyError si la clave no se encuentra en el diccionario.
     */
    public V pop(K key){
        int hash = index(key);
        int pos = 0;

        while(pos < table.length){
            int idx = (hash + pos * pos) % table.length;

            if(table[idx] == null){
                throw new KeyError(key);
            }

            if(!table[idx].delete && table[idx].key.equals(key)){
                V value = table[idx].value;
                table[idx].delete = true;
                used--;

                unlink(table[idx]);
                return value;

            }

            pos++;
        }
        throw new KeyError(key);
    }

    /**
     * Elimina todos los elementos del diccionario.
     * Reinicializa la tabla de hash y las referencias de la lista enlazada,
     */
    public void clear(){
        table = new Entry[table.length];
        used = 0;
        filled = 0;
        head = null;
        tail = null;

    }

    /**
     * Crea y devuelve una copia superficial del diccionario.
     * Los nuevos pares clave-valor se insertan siguiendo el orden de la lista original.
     *
     *  @return Un nuevo objeto con el mismo contenido y capacidad.
     */
    public PythonDict<K, V> copy() {
        PythonDict<K, V> newDict = new PythonDict<>(table.length);

        Entry<K, V> current = head;
        while (current != null) {
            newDict.put(current.key, current.value);
            current = current.next;
        }
        return newDict;
    }

    /**
     * Devuelve un array que contiene todas las claves presentes en el diccionario.
     *
     * @return Un array con las claves de tipo K.
     */
    public K[] keys(){
        K[] result = (K[]) new Object[used];
        int i = 0;

        Entry<K, V> current = head;
        while (current != null) {
            result[i++] = current.key;
            current = current.next;
        }
        return result;
    }

    /**
     * Devuelve un array que contiene todos los valores presentes en el diccionario.
     *
     * @return Un array con los valores de tipo V.
     */
    public V[] values() {
        V[] result = (V[]) new Object[used];
        int i = 0;

        Entry<K, V> current = head;
        while (current != null) {
            result[i++] = current.value;
            current = current.next;
        }
        return result;
    }

    /**
     * Devuelve un array de objetos que representan los pares clave-valor.
     *
     * @return Un array con los elementos (items) del diccionario.
     */
    public Item<K, V>[] items() {
        Item<K, V>[] result = (Item<K, V>[]) new Item[used];
        int i = 0;

        // MODIFICADO
        Entry<K, V> current = head;
        while (current != null) {
            result[i++] = new Item<>(current.key, current.value);
            current = current.next;
        }
        return result;
    }

    /**
     * Inserta un nuevo par clave-valor o actualiza el valor de una clave existente.
     *
     * @param key Clave con la que asociar el valor.
     * @param value Valor que se almacenará.
     */
    public void put(K key, V value){
        //if the table is more than 2/3 full, we make it bigger
        if((double) filled / table.length > 0.66){
            resize();
        }

        //first index
        int hashIdx = index(key);
        //current position
        int pos = 0;

        while(pos < table.length){
            int index = (hashIdx + pos * pos) % table.length;

            // append a new key value pair if the pos is empty(null)
            if(table[index] == null){
                table[index] = new Entry<>(key, value);
                used++;
                filled++;

                linkLast(table[index]);
                return;
            }

            // if pos was deleted, is reused
            if(table[index].delete){
                table[index] = new Entry<>(key, value);
                used++;

                linkLast(table[index]);
                return;
            }

            // if the key exists, update the value
            if(table[index].key.equals(key)){
                table[index].value = value;
                return;
            }
            //incrementing position
            pos++;
        }


    }

    /**
     * Elimina y devuelve el último par clave-valor insertado en el diccionario.
     *
     * @return El objeto eliminado.
     * @throws KeyError si el diccionario está vacío.
     */
    public Item<K, V> popItems(){
        if(head==null){
            throw new KeyError("Dictionary is empty");
        }

        Entry<K, V> last = tail;
        last.delete = true;
        used--;

        unlink(last);
        return new Item<>(last.key, last.value);
    }

    /**
     * Enlaza una nueva entrada al final de la lista doblemente enlazada.
     * Mantiene las referencias head y tail actualizadas.
     *
     * @param e Entrada a añadir.
     */
    private void linkLast(Entry<K, V> e) {
        if (tail == null) {
            head = tail = e;
        } else {
            tail.next = e;
            e.prev = tail;
            tail = e;
        }
    }

    /**
     * Extrae una entrada de la lista doblemente enlazada, ajustando los punteros
     * de los elementos anterior y siguiente.
     *
     * @param e Entrada a desvincular.
     */
    private void unlink(Entry<K, V> e) {
        if (e.prev != null) {
            e.prev.next = e.next;
        } else {
            head = e.next;
        }

        if (e.next != null) {
            e.next.prev = e.prev;
        } else {
            tail = e.prev;
        }

        e.prev = null;
        e.next = null;
    }

    /**
     * Actualiza el diccionario actual con los pares clave-valor de otro diccionario.
     * Si una clave ya existe, su valor es sobrescrito por el del nuevo diccionario.
     *
     * @param other El diccionario del cual se copiarán los datos.
     */
    public void update(PythonDict<K, V> other) {

        Entry<K, V> current = other.head;

        while (current != null) {
            if (!current.delete) {
                this.put(current.key, current.value);
            }
            current = current.next;
        }
    }
}