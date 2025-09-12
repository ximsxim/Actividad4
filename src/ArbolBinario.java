/**
 * Autor: Fernanda Ximena Garcia Salazar
 * Fecha: 11/09/2025
 * Descripción: Clase que implementa un árbol binario de búsqueda para gestión de empleados
 * con operaciones de inserción, eliminación, búsqueda y recorridos.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ArbolBinario {
    private Node raiz;
    private PrintWriter logWriter;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String LOG_FILE_PATH = "log_empleados.txt";

    // Constructor por defecto
    public ArbolBinario() {
        raiz = null;
        inicializarLog();
    }

    // Constructor con parámetros
    public ArbolBinario(String logFilePath) {
        raiz = null;
        inicializarLog(logFilePath);
    }

    private void inicializarLog() {
        inicializarLog(LOG_FILE_PATH);
    }

    private void inicializarLog(String logFilePath) {
        try {
            logWriter = new PrintWriter(new FileWriter(logFilePath, true));
            log("Sistema de gestión de empleados iniciado");
        } catch (IOException e) {
            System.out.println("Error al inicializar el archivo de log: " + e.getMessage());
        }
    }

    // Método para insertar un empleado
    public void insertar(Empleado empleado) {
        if (empleado == null) {
            log("Error: Intento de insertar empleado nulo");
            return;
        }
        
        try {
            raiz = insertarRec(raiz, empleado);
            log("Empleado insertado exitosamente: " + empleado.getId() + " - " + empleado.getNombre());
        } catch (Exception e) {
            log("Error al insertar empleado: " + e.getMessage());
        }
    }

    private Node insertarRec(Node nodo, Empleado empleado) {
        if (nodo == null) {
            return new Node(empleado);
        }
        
        if (empleado.getId() < nodo.getData().getId()) {
            nodo.setIzquierda(insertarRec(nodo.getIzquierda(), empleado));
        } else if (empleado.getId() > nodo.getData().getId()) {
            nodo.setDerecha(insertarRec(nodo.getDerecha(), empleado));
        }
        // Si los IDs son iguales, no insertamos 
        
        return nodo;
    }

    // Método para buscar un empleado por ID
    public Empleado buscar(int id) {
        try {
            Empleado resultado = buscarRec(raiz, id);
            if (resultado != null) {
                log("Búsqueda de empleado ID " + id + ": ENCONTRADO - " + resultado.getNombre());
            } else {
                log("Búsqueda de empleado ID " + id + ": NO ENCONTRADO");
            }
            return resultado;
        } catch (Exception e) {
            log("Error en búsqueda del empleado ID " + id + ": " + e.getMessage());
            return null;
        }
    }

    private Empleado buscarRec(Node nodo, int id) {
        if (nodo == null) {
            return null;
        }
        
        if (nodo.getData().getId() == id) {
            return nodo.getData();
        }
        
        return id < nodo.getData().getId() 
            ? buscarRec(nodo.getIzquierda(), id) 
            : buscarRec(nodo.getDerecha(), id);
    }

    // Método para eliminar un empleado por ID
    public boolean eliminar(int id) {
        try {
            if (buscar(id) == null) {
                log("Intento de eliminar empleado inexistente: ID " + id);
                return false;
            }
            
            raiz = eliminarRec(raiz, id);
            log("Empleado eliminado exitosamente: ID " + id);
            return true;
        } catch (Exception e) {
            log("Error al eliminar empleado ID " + id + ": " + e.getMessage());
            return false;
        }
    }

    private Node eliminarRec(Node nodo, int id) {
        if (nodo == null) {
            return nodo;
        }

        if (id < nodo.getData().getId()) {
            nodo.setIzquierda(eliminarRec(nodo.getIzquierda(), id));
        } else if (id > nodo.getData().getId()) {
            nodo.setDerecha(eliminarRec(nodo.getDerecha(), id));
        } else {
            // Nodo encontrado para eliminar
            
            // Caso 1: Nodo sin hijos
            if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
                return null;
            }
            
            // Caso 2: Nodo con un solo hijo
            if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }

            // Caso 3: Nodo con dos hijos
            // Encontrar el sucesor inorden 
            Empleado sucesor = encontrarMinimo(nodo.getDerecha());
            nodo.setData(sucesor);
            
            // Eliminar el sucesor
            nodo.setDerecha(eliminarRec(nodo.getDerecha(), sucesor.getId()));
        }
        
        return nodo;
    }

    private Empleado encontrarMinimo(Node nodo) {
        while (nodo.getIzquierda() != null) {
            nodo = nodo.getIzquierda();
        }
        return nodo.getData();
    }

    // Métodos de recorrido
    public void recorridoPreorden() {
        log("Recorrido Preorden iniciado");
        System.out.println("== RECORRIDO PREORDEN ==");
        
        if (raiz == null) {
            System.out.println(" El árbol está vacío ");
        } else {
            preordenRec(raiz);
        }
        
    }

    private void preordenRec(Node nodo) {
        if (nodo != null) {
            System.out.println( String.format("%-36s", nodo.getData()));
            preordenRec(nodo.getIzquierda());
            preordenRec(nodo.getDerecha());
        }
    }

    public void recorridoInorden() {
        log("Recorrido Inorden iniciado");
        System.out.println("== RECORRIDO INORDEN ==");
        
        if (raiz == null) {
            System.out.println("El árbol está vacío");
        } else {
            inordenRec(raiz);
        }
        
    }

    private void inordenRec(Node nodo) {
        if (nodo != null) {
            inordenRec(nodo.getIzquierda());
            System.out.println("║ " + String.format("%-36s", nodo.getData()) + " ║");
            inordenRec(nodo.getDerecha());
        }
    }

    public void recorridoPostorden() {
        log("Recorrido Postorden iniciado");
        System.out.println("== RECORRIDO POSTORDEN ==");
        
        if (raiz == null) {
            System.out.println("El árbol está vacío");
        } else {
            postordenRec(raiz);
        }
        
    }

    private void postordenRec(Node nodo) {
        if (nodo != null) {
            postordenRec(nodo.getIzquierda());
            postordenRec(nodo.getDerecha());
            System.out.println( String.format("%-36s", nodo.getData()));
        }
    }

    // Método para visualizar el árbol de forma gráfica
    public void visualizarArbol() {
        log("Visualización del árbol iniciada");
        System.out.println("== VISUALIZACIÓN DEL ÁRBOL ==");
        
        if (raiz == null) {
            System.out.println("    El árbol está vacío");
            return;
        }
        
        imprimirArbolVisual(raiz, "", true);
    }

    private void imprimirArbolVisual(Node nodo, String prefijo, boolean esUltimo) {
        if (nodo != null) {
            System.out.println(prefijo + (esUltimo ? "└── " : "├── ") + 
                             "ID:" + nodo.getData().getId() + " (" + nodo.getData().getNombre() + ")");
            
            // Contar hijos
            boolean tieneIzquierda = nodo.getIzquierda() != null;
            boolean tieneDerecha = nodo.getDerecha() != null;
            
            String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
            
            if (tieneIzquierda) {
                imprimirArbolVisual(nodo.getIzquierda(), nuevoPrefijo, !tieneDerecha);
            }
            
            if (tieneDerecha) {
                imprimirArbolVisual(nodo.getDerecha(), nuevoPrefijo, true);
            }
        }
    }

    // Método auxiliar para calcular la altura del árbol
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(alturaRec(nodo.getIzquierda()), alturaRec(nodo.getDerecha()));
    }

    // Método para contar nodos
    public int contarNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodosRec(nodo.getIzquierda()) + contarNodosRec(nodo.getDerecha());
    }

    // Método para verificar si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // Método para obtener estadísticas del árbol
    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADISTICAS DEL ARBOL ===");
        System.out.println("Total de empleados: " + contarNodos());
        System.out.println("Altura del arbol: " + altura());
        System.out.println("Estado: " + (estaVacio() ? "Vacio" : "Con datos"));
        System.out.println("==============================");
    }

    // Método para registrar en el log con fecha y hora
    private void log(String mensaje) {
        if (logWriter != null) {
            try {
                String fechaHora = LocalDateTime.now().format(formatter);
                logWriter.println("[" + fechaHora + "] " + mensaje);
                logWriter.flush();
            } catch (Exception e) {
                System.out.println("Error al escribir en el log: " + e.getMessage());
            }
        }
    }

    // Cerrar log
    public void cerrarLog() {
        if (logWriter != null) {
            try {
                log("Sistema de gestión de empleados finalizado");
                logWriter.close();
                logWriter = null;
            } catch (Exception e) {
                System.out.println("Error al cerrar el log: " + e.getMessage());
            }
        }
    }
}