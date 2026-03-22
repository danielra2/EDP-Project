package mycode;

public class PythonDict<K, V> {
    private Entry<K, V>[] table;  //storage Array (added <K, V> to support any data type)
    private int used;       //number of used positions (not deleted)
    private int filled;     //number of positions filled (deleted)

    private Entry<K, V> head;
    private Entry<K, V> tail;

    //constructor
    public PythonDict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
        head = null;
        tail = null;
    }

    //converts the key into an array index
    private int index(K key){
        int h = key.hashCode(); //obtain the hash code
        //avoid negatives
        h = Math.abs(h);
        // size adjustment
        return h % table.length;
    }
    // method that expands the size of the table
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

    //search the value by the key given
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

    //delete the key by marking it as deleted
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

    //delete the key from the dict and return de value
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
    // delete all the key-value pairs
    public void clear(){
        table = new Entry[table.length];
        used = 0;
        filled = 0;
        head = null;
        tail = null;

    }

    public PythonDict<K, V> copy() {
        PythonDict<K, V> newDict = new PythonDict<>(table.length);

        Entry<K, V> current = head;
        while (current != null) {
            newDict.put(current.key, current.value);
            current = current.next;
        }
        return newDict;
    }

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

    //insert a new key value pair into the dict
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

    private void linkLast(Entry<K, V> e) {
        if (tail == null) {
            head = tail = e;
        } else {
            tail.next = e;
            e.prev = tail;
            tail = e;
        }
    }

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