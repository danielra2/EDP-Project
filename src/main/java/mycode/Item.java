package mycode;

public class Item<K, V> {
    public K key;
    public V value;

    public Item(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
