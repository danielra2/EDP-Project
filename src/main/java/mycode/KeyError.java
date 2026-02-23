package mycode;

/**
 * Custom exception used when a key is not found in the dictionary.
 */
public class KeyError extends RuntimeException {
    public KeyError(String key) {
        super("Key not found: " + key);
    }
}