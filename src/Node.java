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

    // Getter que devuelve el empleado guardado en el nodo
    public Empleado getData() { 
        return data; 
    }
    
    // Setter que asigna un empleado al nodo
    public void setData(Empleado data) { 
        this.data = data; 
    }

    // Devuelve el hijo izquierdo
    public Node getIzquierda() { 
        return izquierda; 
    }
    
    // Asigna un hijo izquierdo
    public void setIzquierda(Node izquierda) { 
        this.izquierda = izquierda; 
    }

    // Devuelve el hijo derecho
    public Node getDerecha() { 
        return derecha; 
    }
    
    // Asigna un hijo derecho
    public void setDerecha(Node derecha) { 
        this.derecha = derecha; 
    }
}
