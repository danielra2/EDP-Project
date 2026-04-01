package mycode;

/**
 * Excepción que se lanza cuando se intenta acceder
 * a una clave que no existe en el diccionario.
 * Funciona de forma similar al KeyError de Python.
 *
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Ismael Cabrera Cabrera
 * @author Sara Trujillo García
 */
public class KeyError extends RuntimeException {

    /**
     * Crea una nueva excepción indicando la clave
     * que no se ha encontrado.
     *
     * @param key clave que no existe en el diccionario
     */
    public KeyError(Object key) {
        super("Key not found: " + key);
    }
}