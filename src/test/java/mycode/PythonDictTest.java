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
}