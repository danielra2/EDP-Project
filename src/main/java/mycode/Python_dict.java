package mycode;

public class Python_dict {
    private Entry[] table;
    private int used;   // Active elements
    private int filled; // Active + Deleted (dummy) elements

    public Python_dict(int capacity){
        table = new Entry[capacity];
        used = 0;
        filled = 0;
    }

    private int index(String key){
        int h = key.hashCode();
        if(h == 0) h = -1; // Specific logic from your skeleton
        return Math.abs(h) % table.length;
    }

    // Resizes the table when it's 2/3 full to maintain performance
    private void resize() {
        Entry[] oldTable = table;
        table = new Entry[oldTable.length * 2];
        used = 0;
        filled = 0;

        for (Entry entry : oldTable) {
            if (entry != null && !entry.delete) {
                put(entry.key, entry.value);
            }
        }
    }

    public void put(String key, String value) {
        // Resize if more than 66% of slots are filled
        if (filled > (table.length * 2) / 3) {
            resize();
        }

        int idx = index(key);
        while (table[idx] != null) {
            // If key exists and is not deleted, update value
            if (table[idx].key.equals(key) && !table[idx].delete) {
                table[idx].value = value;
                return;
            }
            // If key exists but was deleted, reuse the slot
            if (table[idx].key.equals(key) && table[idx].delete) {
                table[idx].value = value;
                table[idx].delete = false;
                used++;
                return;
            }
            idx = (idx + 1) % table.length; // Linear probing
        }

        // Add new entry in an empty slot
        table[idx] = new Entry(key, value);
        used++;
        filled++;
    }

    public String get(String key) {
        int idx = index(key);
        int initialIdx = idx;

        while (table[idx] != null) {
            if (table[idx].key.equals(key) && !table[idx].delete) {
                return table[idx].value;
            }
            idx = (idx + 1) % table.length;
            if (idx == initialIdx) break; // Avoid infinite loop
        }
        throw new KeyError(key);
    }

    public void remove(String key) {
        int idx = index(key);
        int initialIdx = idx;

        while (table[idx] != null) {
            if (table[idx].key.equals(key) && !table[idx].delete) {
                table[idx].delete = true; // Soft delete
                used--;
                return;
            }
            idx = (idx + 1) % table.length;
            if (idx == initialIdx) break;
        }
    }

    public int size() {
        return used;
    }
}