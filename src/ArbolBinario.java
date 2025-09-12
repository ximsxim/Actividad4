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
    private boolean enableLogging = true; // controla si se escribe en el log o no

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

    // Permite encender o apagar el log
    public void setEnableLogging(boolean enableLogging) {
        this.enableLogging = enableLogging;
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

    // Método para insertar un empleado en el árbol
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
        // Si el ID es igual, no se inserta para evitar duplicados
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
        // Si el ID es menor, busca a la izquierda, si no, a la derecha
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
            // Caso 1: nodo sin hijos
            if (nodo.getIzquierda() == null && nodo.getDerecha() == null) {
                return null;
            }
            // Caso 2: nodo con un hijo
            if (nodo.getIzquierda() == null) {
                return nodo.getDerecha();
            } else if (nodo.getDerecha() == null) {
                return nodo.getIzquierda();
            }
            // Caso 3: nodo con dos hijos
            Empleado sucesor = encontrarMinimo(nodo.getDerecha());
            nodo.setData(sucesor);
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

    // Métodos de recorrido: visitan todos los nodos en distintos órdenes
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
            System.out.println(String.format("%-36s", nodo.getData()));
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
            System.out.println(String.format("%-36s", nodo.getData()));
        }
    }

    // Visualización del árbol en forma gráfica con ramas
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

    // Calcula la altura del árbol (número máximo de niveles)
    public int altura() {
        return alturaRec(raiz);
    }

    private int alturaRec(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + Math.max(alturaRec(nodo.getIzquierda()), alturaRec(nodo.getDerecha()));
    }

    // Cuenta el número total de nodos en el árbol
    public int contarNodos() {
        return contarNodosRec(raiz);
    }

    private int contarNodosRec(Node nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contarNodosRec(nodo.getIzquierda()) + contarNodosRec(nodo.getDerecha());
    }

    // Verifica si el árbol está vacío
    public boolean estaVacio() {
        return raiz == null;
    }

    // Muestra estadísticas generales del árbol
    public void mostrarEstadisticas() {
        System.out.println("\n=== ESTADISTICAS DEL ARBOL ===");
        System.out.println("Total de empleados: " + contarNodos());
        System.out.println("Altura del arbol: " + altura());
        System.out.println("Estado: " + (estaVacio() ? "Vacio" : "Con datos"));
        System.out.println("==============================");
    }

    // Escribe un mensaje en el log con fecha y hora
    private void log(String mensaje) {
        if (!enableLogging) return; // se puede desactivar para medir rendimiento
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

    // Cierra el archivo de log
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
