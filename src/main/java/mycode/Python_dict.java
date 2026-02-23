package mycode;

public class Python_dict {
    private Entry[] table;
    private int used;
    private int filled;

    public Python_dict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
    }

    private int index(String key){
        int h = key.hashCode();
        if(h==0){
            h = -h;
        }
        return h % table.length;
    }

}
