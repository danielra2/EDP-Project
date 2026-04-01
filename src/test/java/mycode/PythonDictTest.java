package mycode;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de pruebas unitarias (JUnit) sobre el PythonDict.
 * Comprueba que el diccionario funciona correctamente, asi como
 * sus estructuras de datos.
 *
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Ismael Cabrera Cabrera
 * @author Sara Trujillo García
 */
public class PythonDictTest {
    private PythonDict<String, Integer> dict;

    /**
     * Configuración inicial que se ejecuta antes de cada test.
     * Pone un diccicionario vacío con capacidad 5.
     */
    @Before
    public void setUp() {
        dict = new PythonDict<>(5);
    }

    /**
     * Verifica que un par clave-valor se inserta correctamente y que
     * puede ser recuperado por su clave.
     */
    @Test
    public void testPutAndGet() {
        dict.put("java", 21);
        assertEquals(Integer.valueOf(21), dict.get("java"));
    }

    /**
     * Comprueba el comportamiento de actualización del diccionario.
     * Si se inserta un valor en una clave ya existente, el valor antiguo
     * debe ser sobrescrito por el nuevo.
     */
    @Test
    public void testUpdateValue() {
        dict.put("key", 10);
        dict.put("key", 20);
        assertEquals(Integer.valueOf(20), dict.get("key"));
    }

    /**
     * Evalúa si el redimensionamiento funciona correctamente.
     */
    @Test
    public void testResize() {
        dict.put("1", 1);
        dict.put("2", 2);
        dict.put("3", 3);
        dict.put("4", 4);
        assertNotNull(dict.get("1"));
        assertNotNull(dict.get("4"));
    }

    /**
     * Verifica la operación de borrado.
     * Tras eliminar una clave, intentar recuperarla debe devolver null.
     */
    @Test
    public void testDel() {
        dict.put("test", 100);
        dict.del("test");
        assertNull(dict.get("test"));
    }

    /**
     * Comprueba si al extraer una clave que no existe se devuelve KeyError.
     */
    @Test(expected = KeyError.class)
    public void testPopException() {
        dict.pop("non_existent");
    }

    /**
     * Verifica que el método pop funciona y devuelve el valor que
     * tenía asociado justo antes de ser eliminado.
     */
    @Test
    public void testPopSuccess() {
        dict.put("popMe", 500);
        Integer val = dict.pop("popMe");
        assertEquals(Integer.valueOf(500), val);
        assertNull(dict.get("popMe"));
    }


    /**
     * Evalúa el vaciado completo de la estructura. No debe tener elementos después.
     */
    @Test
    public void testClear() {
        dict.put("a", 1);
        dict.clear();
        assertNull(dict.get("a"));
        Object[] keys = dict.keys();
        assertEquals(0, keys.length);
    }

    /**
     * Comprueba que el método update añade los elementos
     * de otro diccionario sin eliminar los que ya existen.
     */
    @Test
    public void testUpdate() {
        PythonDict<String, Integer> other = new PythonDict<>(5);
        other.put("newKey", 99);
        dict.put("oldKey", 1);
        dict.update(other);
        assertEquals(Integer.valueOf(99), dict.get("newKey"));
        assertEquals(Integer.valueOf(1), dict.get("oldKey"));
    }

    /**
     * Comprueba que popItems devuelve y elimina
     * el último elemento insertado.
     */
    @Test
    public void testPopItems() {
        dict.put("first", 1);
        dict.put("last", 2);
        Item<String, Integer> item = dict.popItems();
        assertEquals("last", item.key);
        assertEquals(Integer.valueOf(2), item.value);
    }

    /**
     * Verifica que popItems lanza una excepción
     * si el diccionario está vacío.
     */
    @Test(expected = KeyError.class)
    public void testPopItemsEmpty() {
        dict.popItems();
    }

    /**
     * Comprueba que copy crea una copia del diccionario
     * y que los cambios en la copia no afectan al original.
     */
    @Test
    public void testCopy() {
        dict.put("original", 10);
        PythonDict<String, Integer> clona = dict.copy();
        assertEquals(Integer.valueOf(10), clona.get("original"));
        clona.put("original", 20);
        assertEquals(Integer.valueOf(10), dict.get("original"));
    }

    /**
     * Verifica que keys, values e items devuelven
     * el número correcto de elementos.
     */
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