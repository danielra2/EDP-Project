package mycode;

public class Python_dict<K, V> {
    private Entry<K, V>[] table;  //storage Array (added <K, V> to support any data type)
    private int used;       //number of used positions (not deleted)
    private int filled;     //number of positions filled (deleted)

    //constructor
    public Python_dict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
    }

    //converts the key into an array index
    private int index(K key){
        int h = key.hashCode(); //obtain the hash code
        //avoid negatives
        if(h<0){
            h = -h;
        }
        // size adjustment
        return h % table.length;
    }
    // method that expands the size of the table
    private void resize(){
        Entry<K, V>[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        used = 0;
        filled = 0;

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

        while(true){
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
    }

    //delete the key by marking it as deleted
    public void del(K key){
        int hash = index(key);
        int pos = 0;

        while(true){
            int idx = (hash + pos * pos) % table.length;

            if(table[idx] == null){
                return;
            }

            //if the key is in the dict, mark it as deleted
            if(!table[idx].delete && table[idx].key.equals(key)){
                table[idx].delete = true;
                used--;
                return;
            }

            pos++;
        }
    }

    //delete the key from the dict and return de value
    public V pop(K key){
        int hash = index(key);
        int pos = 0;

        while(true){
            int idx = (hash + pos * pos) % table.length;

            if(table[idx] == null){
                throw new KeyError((String) key);
            }

            if(!table[idx].delete && table[idx].key.equals(key)){
                V value = table[idx].value;
                table[idx].delete = true;
                used--;
                return value;

            }

            pos++;
        }
    }
    // delete all the key-value pairs
    public void clear(){
        table = new Entry[table.length];
        used = 0;
        filled = 0;
    }

    public Python_dict<K, V> copy(){
        Python_dict<K, V> newDict = new Python_dict<>(table.length);

        for(int i=0; i<table.length;i++){
            if(table[i] != null && !table[i].delete){
                newDict.put(table[i].key, table[i].value);
            }
        }
        return newDict;
    }

    public  K[] keys(){
        K[] result = (K[]) new Object[used];

        int j=0;
        for(int i=0;i<table.length;i++){
            if(table[i] != null && !table[i].delete){
                result[j] = table[i].key;
                j++;
            }
        }
        return result;
    }

    public V[] values(){
        V[] result = (V[]) new Object[used];

        int j=0;
        for(int i=0;i<table.length;i++){
            if(table[i] != null && !table[i].delete){
                result[j] = table[i].value;
                j++;
            }
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

        while(true){
            int index = (hashIdx + pos * pos) % table.length;

            // append a new key value pair if the pos is empty(null)
            if(table[index] == null){
                table[index] = new Entry<>(key, value);
                used++;
                filled++;
                return;
            }

            // if pos was deleted, is reused
            if(table[index].delete){
                table[index] = new Entry<>(key, value);
                used++;
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

}
