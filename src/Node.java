/**
 * Autor: Fernanda Ximena Garcia Salazar
 * Fecha: 11/09/2025
 * Descripción: Clase que representa un nodo del árbol binario de búsqueda para empleados.
 */
public class Node {
    private Empleado data;
    private Node izquierda;
    private Node derecha;

    // Constructor por defecto
    public Node() {
        this.data = null;
        this.izquierda = null;
        this.derecha = null;
    }

    // Constructor con parámetros
    public Node(Empleado data) {
        this.data = data;
        this.izquierda = null;
        this.derecha = null;
    }

    // Getters y Setters
    public Empleado getData() { 
        return data; 
    }
    
    public void setData(Empleado data) { 
        this.data = data; 
    }

    public Node getIzquierda() { 
        return izquierda; 
    }
    
    public void setIzquierda(Node izquierda) { 
        this.izquierda = izquierda; 
    }

    public Node getDerecha() { 
        return derecha; 
    }
    
    public void setDerecha(Node derecha) { 
        this.derecha = derecha; 
    }
}