package mycode;

public class Python_dict {
    private Entry[] table;  //storage Array
    private int used;       //number of used positions (not deleted)
    private int filled;     //number of positions filled (deleted)

    //constructor
    public Python_dict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
    }

    //converts the key into an array index
    private int index(String key){
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
        Entry[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        used = 0;
        filled = 0;

        for(int i=0; i<oldTable.length; i++){
            if(oldTable[i] != null && !oldTable[i].delete){
                put(oldTable[i].key, oldTable[i].value);
            }
        }
    }

    //delete the key by marking it as deleted
    public void remove(String key){
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

    //insert a new key value pair into the dict
    public void put(String key, String value){
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
                table[index] = new Entry(key, value);
                used++;
                filled++;
                return;
            }

            // if pos was deleted, is reused
            if(table[index].delete){
                table[index] = new Entry(key, value);
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
