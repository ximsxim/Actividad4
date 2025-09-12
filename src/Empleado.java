/**
 * Autor: Fernanda Ximena Garcia Salazar
 * Fecha: 11/09/2025
 * Descripción: Clase que representa un empleado del sistema con ID único y nombre.
 */
public class Empleado {
    private int id;
    private String nombre;

    // Constructor por defecto: crea un empleado vacío
    public Empleado() {
        this.id = 0;
        this.nombre = "";
    }

    // Constructor con parámetros: crea un empleado con datos
    public Empleado(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Devuelve el ID del empleado
    public int getId() { 
        return id; 
    }
    
    // Asigna un nuevo ID
    public void setId(int id) { 
        this.id = id; 
    }

    // Devuelve el nombre del empleado
    public String getNombre() { 
        return nombre; 
    }
    
    // Asigna un nuevo nombre
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    // Representación en texto del empleado
    @Override
    public String toString() {
        return String.format("ID: %-6d | Nombre: %s", id, nombre);
    }

    // Compara empleados por su ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Empleado empleado = (Empleado) obj;
        return id == empleado.id;
    }

    // Genera un código hash basado en el ID
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}
