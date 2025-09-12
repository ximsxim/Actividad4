/**
 * Autor: Fernanda Ximena Garcia Salazar
 * Fecha: 11/09/2025
 * Descripción: Clase que representa un empleado del sistema con ID único y nombre.
 */
public class Empleado {
    private int id;
    private String nombre;

    // Constructor por defecto
    public Empleado() {
        this.id = 0;
        this.nombre = "";
    }

    // Constructor con parámetros
    public Empleado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id; 
    }

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    @Override
    public String toString() {
        return String.format("ID: %-6d | Nombre: %s", id, nombre);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Empleado empleado = (Empleado) obj;
        return id == empleado.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}