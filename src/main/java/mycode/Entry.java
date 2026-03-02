package mycode;

public class Entry<K, V> {
    K key;
    V value;
    boolean delete;

    public Entry(K key, V value){
        this.key = key;
        this.value = value;
        this.delete = false;
    }
}
