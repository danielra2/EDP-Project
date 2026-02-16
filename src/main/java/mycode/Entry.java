package mycode;

public class Entry {
    String key;
    String value;
    boolean delete;

    public Entry(String key, String value){
        this.key = key;
        this.value = value;
        this.delete = false;
    }
}
