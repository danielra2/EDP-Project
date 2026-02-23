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


}
