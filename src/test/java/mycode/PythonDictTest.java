package mycode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

class PythonDictTest {
    private Python_dict<String, Integer> dict;

    @BeforeEach
    void setUp() {
        dict = new Python_dict<>(5);
    }

    @Test
    void testPut() {
        assertDoesNotThrow(() -> dict.put("java", 21));
    }

    @Test
    void testUpdateValue() {
        dict.put("key", 10);
        dict.put("key", 20);
        assertDoesNotThrow(() -> dict.put("key", 30));
    }

    @Test
    void testCollisions() {
        dict.put("A", 1);
        dict.put("B", 2);
        dict.put("C", 3);
        assertDoesNotThrow(() -> dict.put("D", 4));
    }

    @Test
    void testResize() {
        dict.put("1", 1);
        dict.put("2", 2);
        dict.put("3", 3);
        dict.put("4", 4);
        assertDoesNotThrow(() -> dict.put("5", 5));
    }

    @Test
    void testRemoveAndReuse() {
        dict.put("test", 100);
        dict.del("test");
        assertDoesNotThrow(() -> dict.put("test", 200));
        assertDoesNotThrow(() -> dict.put("other", 300));
    }
    @Test
    void testRemoveNonExistentKey() {
        assertDoesNotThrow(() -> dict.del("none"));
    }
    @Test
    void testMocking() {
        Python_dict dictMock = Mockito.mock(Python_dict.class);
        dictMock.put("k", "v");
        Mockito.verify(dictMock).put("k", "v");
    }
    @Test
    void testGet() {
        dict.put("cheie1", 100);
        assertEquals(100, dict.get("cheie1"));
        assertNull(dict.get("inexistent"));
    }

    @Test
    void testPop() {
        dict.put("popMe", 500);
        Integer value = dict.pop("popMe");
        assertEquals(500, value);
        assertNull(dict.get("popMe")); // Verificăm că a fost șters
    }

    @Test
    void testPopException() {
        // Metoda pop aruncă KeyError dacă cheia nu există
        assertThrows(KeyError.class, () -> dict.pop("missing"));
    }

    @Test
    void testClear() {
        dict.put("a", 1);
        dict.put("b", 2);
        dict.clear();
        assertNull(dict.get("a"));
        assertNull(dict.get("b"));
        Object[] keys = dict.keys();
        assertEquals(0, keys.length);
    }

    @Test
    void testCopy() {
        dict.put("original", 10);
        Python_dict<String, Integer> clona = dict.copy();

        assertEquals(10, clona.get("original"));

        // Verificăm independența: modificarea clonei nu afectează originalul
        clona.put("original", 20);
        assertEquals(10, dict.get("original"));
        assertEquals(20, clona.get("original"));
    }

    @Test
    void testKeys() {
        dict.put("k1", 1);
        dict.put("k2", 2);
        Object[] keys = dict.keys();
        assertEquals(2, keys.length);
        // Putem verifica prezența (ordinea poate varia din cauza hashing-ului)
        boolean foundK1 = false;
        for(Object k : keys) if(k.equals("k1")) foundK1 = true;
        assertTrue(foundK1);
    }

    @Test
    void testValues() {
        dict.put("k1", 100);
        dict.put("k2", 200);
        Object[] values = dict.values();
        assertEquals(2, values.length);
    }

    @Test
    void testItems() {
        dict.put("itemKey", 99);
        Item<String, Integer>[] items = dict.items();
        assertEquals(1, items.length);
        assertEquals("itemKey", items[0].key);
        assertEquals(99, items[0].value);
    }
}
