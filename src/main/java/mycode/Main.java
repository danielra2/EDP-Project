package mycode;

/**
 * Clase principal para demostrar el uso completo de la estructura PythonDict.
 * Muestra el funcionamiento de todos los métodos públicos del diccionario
 * simulando la gestión del inventario de una tienda.
 */
public class Main {
    public static void main(String[] args) {

        System.out.println("=== SISTEMA DE GESTIÓN DE INVENTARIO ===\n");

        // 1. Creación diccionario (put, get)
        System.out.println("--- 1. Carga de datos inicial ---");
        PythonDict<String, Integer> inventario = new PythonDict<>(5);
        inventario.put("Portatil", 10);
        inventario.put("Raton", 50);
        inventario.put("Teclado", 30);
        inventario.put("Monitor", 15);

        System.out.println("Stock de Ratones inicial: " + inventario.get("Raton"));
        inventario.put("Raton", 45); // Actualizamos el valor
        System.out.println("Stock de Ratones tras una venta: " + inventario.get("Raton"));

        // 2. Elementos del diccionario (keys, values, items)
        System.out.println("\n--- 2. Listados del inventario ---");

        System.out.print("Claves (Productos): ");
        Object[] productos = inventario.keys();
        for (int i = 0; i < productos.length; i++) System.out.print(productos[i] + " | ");

        System.out.print("\nValores (Cantidades): ");
        Object[] cantidades = inventario.values();
        for (int i = 0; i < cantidades.length; i++) System.out.print(cantidades[i] + " | ");

        System.out.println("\n\nInventario completo (Items):");
        Item<String, Integer>[] listaItems = inventario.items();
        for (int i = 0; i < listaItems.length; i++) {
            System.out.println(" -> " + listaItems[i].key + ": " + listaItems[i].value + " uds.");
        }

        // 3. Copia de seguridad (copy)
        System.out.println("\n--- 3. Creando copia de seguridad ---");
        PythonDict<String, Integer> backup = inventario.copy();
        Object[] backupKeys = backup.keys();
        System.out.println("Copia creada. Productos en backup: " + backupKeys.length);

        // 4. Actualización inventario
        System.out.println("\n--- 4. Recibiendo nuevo cargamento ---");
        PythonDict<String, Integer> nuevoCargamento = new PythonDict<>(5);
        nuevoCargamento.put("Auriculares", 20); // Producto nuevo
        nuevoCargamento.put("Portatil", 12);    // Sobrescribe el valor del Portatil (de 10 a 12)

        inventario.update(nuevoCargamento);
        System.out.println("Cargamento procesado.");
        System.out.println("Nuevo stock de Portatiles: " + inventario.get("Portatil"));
        System.out.println("Stock de Auriculares: " + inventario.get("Auriculares"));

        // 5. Borrado y extracción (del, pop, popItems)
        System.out.println("\n--- 5. Eliminación de productos ---");

        inventario.del("Teclado");
        System.out.println("Teclado descatalogado (del). ¿Existe?: " + (inventario.get("Teclado") != null));

        try {
            Integer stockMonitor = inventario.pop("Monitor");
            System.out.println("Monitor extraido (pop). Stock que quedaba: " + stockMonitor);
            // Intentamos hacer pop de algo que no existe para ver la excepción
            inventario.pop("ProductoFalso");
        } catch (KeyError e) {
            System.out.println("Excepcion KeyError capturada correctamente: " + e.getMessage());
        }

        try {
            Item<String, Integer> ultimo = inventario.popItems();
            System.out.println("Ultimo producto extraido (popItems): " + ultimo.key + " con " + ultimo.value + " uds.");
        } catch (KeyError e) {
            System.out.println("Error: " + e.getMessage());
        }

        // 6. Clear
        System.out.println("\n--- 6. Vaciado del almacén (clear) ---");
        inventario.clear();
        Object[] invKeys = inventario.keys();
        System.out.println("Almacén vaciado. Total de productos ahora: " + invKeys.length);

        // Comprobamos que el backup sigue intacto
        Object[] finalBackupKeys = backup.keys();
        System.out.println("Comprobando que el backup no se vio afectado... Total en backup: " + finalBackupKeys.length);

        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
}