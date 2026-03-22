package mycode;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PythonDictTest {
    private PythonDict<String, Integer> dict;
    @Before
    public void setUp() {
        dict = new PythonDict<>(5);
    }
    @Test
    public void testPutAndGet() {
        dict.put("java", 21);
        assertEquals(Integer.valueOf(21), dict.get("java"));
    }

    @Test
    public void testUpdateValue() {
        dict.put("key", 10);
        dict.put("key", 20);
        assertEquals(Integer.valueOf(20), dict.get("key"));
    }

    @Test
    public void testResize() {
        dict.put("1", 1);
        dict.put("2", 2);
        dict.put("3", 3);
        dict.put("4", 4);
        assertNotNull(dict.get("1"));
        assertNotNull(dict.get("4"));
    }

    @Test
    public void testDel() {
        dict.put("test", 100);
        dict.del("test");
        assertNull(dict.get("test"));
    }

    @Test(expected = KeyError.class)
    public void testPopException() {
        dict.pop("non_existent");
    }

    @Test
    public void testPopSuccess() {
        dict.put("popMe", 500);
        Integer val = dict.pop("popMe");
        assertEquals(Integer.valueOf(500), val);
        assertNull(dict.get("popMe"));
    }


    @Test
    public void testClear() {
        dict.put("a", 1);
        dict.clear();
        assertNull(dict.get("a"));
        Object[] keys = dict.keys();
        assertEquals(0, keys.length);
    }

    @Test
    public void testUpdate() {
        PythonDict<String, Integer> other = new PythonDict<>(5);
        other.put("newKey", 99);
        dict.put("oldKey", 1);
        dict.update(other);
        assertEquals(Integer.valueOf(99), dict.get("newKey"));
        assertEquals(Integer.valueOf(1), dict.get("oldKey"));
    }

    @Test
    public void testPopItems() {
        dict.put("first", 1);
        dict.put("last", 2);
        Item<String, Integer> item = dict.popItems();
        assertEquals("last", item.key);
        assertEquals(Integer.valueOf(2), item.value);
    }

    @Test(expected = KeyError.class)
    public void testPopItemsEmpty() {
        dict.popItems();
    }

    @Test
    public void testCopy() {
        dict.put("original", 10);
        PythonDict<String, Integer> clona = dict.copy();
        assertEquals(Integer.valueOf(10), clona.get("original"));
        clona.put("original", 20);
        assertEquals(Integer.valueOf(10), dict.get("original"));
    }

    @Test
    public void testKeysValuesItems() {
        dict.put("k1", 100);
        Object[] keys = dict.keys();
        Object[] values = dict.values();
        Object[] items = dict.items();
        assertEquals(1, keys.length);
        assertEquals(1, values.length);
        assertEquals(1, items.length);
    }
}