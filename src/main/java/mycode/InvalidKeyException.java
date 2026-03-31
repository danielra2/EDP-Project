package mycode;

/**
 * Excepción lanzada cuando se intenta realizar una operación con una clave no válida.
 *
 * @author Ismael Cabrera Cabrera
 * @author Víctor Jesús García Abad
 * @author Rodrigo Andrés Irigoyen Barrios
 * @author Daniel Radoi
 * @author Sara Trujillo García
 */
public class InvalidKeyException extends RuntimeException {

    /**
     * Construye una nueva excepción con el mensaje de error especificado.
     * @param message El detalle del motivo por el cual la clave es inválida.
     */
    public InvalidKeyException(String message) {
        super(message);
    }
}