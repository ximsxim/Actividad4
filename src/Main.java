/**
 * Autor: Fernanda Ximena Garcia Salazar
 * Fecha: 11/09/2025
 * Descripción: Clase principal que maneja el sistema de gestión de empleados
 * utilizando árboles binarios para optimizar búsquedas y operaciones.
 */
import java.util.*;

public class Main {
    private static ArbolBinario arbolEmpleados = new ArbolBinario();
    private static List<Empleado> listaEmpleados = new ArrayList<>();
    private static final Random RANDOM = new Random();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== SISTEMA DE GESTION DE EMPLEADOS ===");
        
        try {
            System.out.println("Cargando sistema con 1000 empleados...");
            cargarEmpleadosIniciales();
            
            mostrarMenuPrincipal();
            
        } catch (Exception e) {
            System.out.println("Error critico en el sistema: " + e.getMessage());
        } finally {
            arbolEmpleados.cerrarLog();
            scanner.close();
        }
    }

    private static void cargarEmpleadosIniciales() {
        String[] nombres = {
            "Ana", "Carlos", "Elena", "Javier", "Laura", "Miguel", 
            "Patricia", "Ricardo", "Sofia", "Victor", "Isabella", 
            "Fernando", "Gabriela", "Alejandro", "Valentina", "Diego",
            "Camila", "Sebastian", "Natalia", "Andres", "Mariana",
            "Pablo", "Andrea", "Nicolas", "Juliana", "Eduardo"
        };
        
        String[] apellidos = {
            "Garcia", "Rodriguez", "Martinez", "Hernandez", "Lopez",
            "Gonzalez", "Perez", "Sanchez", "Ramirez", "Cruz",
            "Flores", "Torres", "Rivera", "Gomez", "Diaz",
            "Reyes", "Morales", "Jimenez", "Gutierrez", "Ruiz"
        };
        
        Set<Integer> idsUsados = new HashSet<>();
        
        for (int i = 0; i < 1000; i++) {
            int id;
            do {
                id = RANDOM.nextInt(9000) + 1000;
            } while (idsUsados.contains(id));
            idsUsados.add(id);
            
            String nombre = nombres[RANDOM.nextInt(nombres.length)] + " " + 
                           apellidos[RANDOM.nextInt(apellidos.length)];
            
            Empleado empleado = new Empleado(id, nombre);
            arbolEmpleados.insertar(empleado);
            listaEmpleados.add(empleado);
        }
    }

    private static void mostrarMenuPrincipal() {
        int opcion;
        do {
            System.out.println("\n=== MENU PRINCIPAL ===");
            System.out.println("1. Buscar empleado por ID");
            System.out.println("2. Agregar nuevo empleado");
            System.out.println("3. Eliminar empleado por ID");
            System.out.println("4. Mostrar empleados");
            System.out.println("5. Visualizar estructura del arbol");
            System.out.println("6. Comparar eficiencia: Arbol vs Lista");
            System.out.println("7. Mostrar estadisticas del sistema");
            System.out.println("8. Salir del sistema");
            System.out.print("Seleccione una opcion: ");
            
            opcion = leerOpcion();
            
            switch (opcion) {
                case 1:
                    buscarEmpleado();
                    break;
                case 2:
                    agregarEmpleado();
                    break;
                case 3:
                    eliminarEmpleado();
                    break;
                case 4:
                    mostrarRecorridos();
                    break;
                case 5:
                    visualizarArbol();
                    break;
                case 6:
                    compararEficiencia();
                    break;
                case 7:
                    mostrarEstadisticas();
                    break;
                case 8:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
            
            if (opcion != 8) {
                pausar();
            }
            
        } while (opcion != 8);
    }

    private static int leerOpcion() {
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            return opcion;
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un numero valido.");
            scanner.nextLine();
            return -1;
        }
    }

    private static void buscarEmpleado() {
        System.out.println("\n--- BUSCAR EMPLEADO ---");
        System.out.print("Ingrese el ID del empleado: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            long inicioTiempo = System.nanoTime();
            Empleado empleado = arbolEmpleados.buscar(id);
            long finTiempo = System.nanoTime();
            
            double tiempoBusqueda = (finTiempo - inicioTiempo) / 1_000_000.0;
            
            if (empleado != null) {
                System.out.println("\nEMPLEADO ENCONTRADO:");
                System.out.println(empleado);
            } else {
                System.out.println("Empleado con ID " + id + " no encontrado.");
            }
            
            System.out.printf("Tiempo de busqueda: %.4f ms\n", tiempoBusqueda);
            
        } catch (InputMismatchException e) {
            System.out.println("El ID debe ser un numero entero.");
            scanner.nextLine();
        }
    }

    private static void agregarEmpleado() {
        System.out.println("\n--- AGREGAR NUEVO EMPLEADO ---");
        
        try {
            System.out.print("ID del empleado: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            if (arbolEmpleados.buscar(id) != null) {
                System.out.println("Ya existe un empleado con el ID " + id);
                return;
            }
            
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            
            if (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacio.");
                return;
            }
            
            Empleado nuevoEmpleado = new Empleado(id, nombre);
            arbolEmpleados.insertar(nuevoEmpleado);
            listaEmpleados.add(nuevoEmpleado);
            
            System.out.println("Empleado agregado exitosamente:");
            System.out.println(nuevoEmpleado);
            
        } catch (InputMismatchException e) {
            System.out.println("El ID debe ser un numero entero.");
            scanner.nextLine();
        }
    }

    private static void eliminarEmpleado() {
        System.out.println("\n--- ELIMINAR EMPLEADO ---");
        System.out.print("Ingrese el ID del empleado a eliminar: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Empleado empleado = arbolEmpleados.buscar(id);
            if (empleado == null) {
                System.out.println("Empleado con ID " + id + " no encontrado.");
                return;
            }
            
            System.out.println("\nEmpleado a eliminar:");
            System.out.println(empleado);
            
            System.out.print("\nEsta seguro de que desea eliminar este empleado? (S/N): ");
            String confirmacion = scanner.nextLine().trim().toUpperCase();
            
            if (confirmacion.equals("S") || confirmacion.equals("si")) {
                boolean eliminado = arbolEmpleados.eliminar(id);
                if (eliminado) {
                    listaEmpleados.removeIf(emp -> emp.getId() == id);
                    System.out.println("Empleado eliminado exitosamente.");
                } else {
                    System.out.println("Error al eliminar el empleado.");
                }
            } else {
                System.out.println("Eliminacion cancelada.");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("El ID debe ser un numero entero.");
            scanner.nextLine();
        }
    }

    private static void mostrarRecorridos() {
        System.out.println("\n--- RECORRIDOS DEL ARBOL ---");
        System.out.println("1. Preorden");
        System.out.println("2. Inorden");
        System.out.println("3. Postorden");
        System.out.print("Seleccione el tipo de recorrido: ");
        
        try {
            int opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1:
                    arbolEmpleados.recorridoPreorden();
                    break;
                case 2:
                    arbolEmpleados.recorridoInorden();
                    break;
                case 3:
                    arbolEmpleados.recorridoPostorden();
                    break;
                default:
                    System.out.println("Opcion no valida.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Debe ingresar un numero valido.");
            scanner.nextLine();
        }
    }

    private static void visualizarArbol() {
        System.out.println("\n--- VISUALIZACION DEL ARBOL ---");
        arbolEmpleados.visualizarArbol();
    }

    private static void compararEficiencia() {
        System.out.println("\n--- COMPARACION DE EFICIENCIA ---");
        System.out.println("Arbol Binario vs Busqueda Secuencial");
        
        System.out.print("Ingrese el ID a buscar para la comparacion: ");
        
        try {
            int id = scanner.nextInt();
            scanner.nextLine();
            
            if (arbolEmpleados.buscar(id) == null) {
                System.out.println("El ID " + id + " no existe en el sistema.");
                return;
            }
            
            System.out.println("\nIniciando pruebas de rendimiento...");
            
            int repeticiones = 100000;
            System.out.println("Realizando " + repeticiones + " busquedas de cada tipo...");
            
            // Medir tiempo de búsqueda en árbol binario
            long inicioArbol = System.nanoTime();
            for (int i = 0; i < repeticiones; i++) {
                arbolEmpleados.buscar(id);
            }
            long finArbol = System.nanoTime();
            double tiempoArbol = (finArbol - inicioArbol) / 1_000_000.0;
            
            // Medir tiempo de búsqueda secuencial en lista
            long inicioLista = System.nanoTime();
            for (int i = 0; i < repeticiones; i++) {
                buscarEnLista(id);
            }
            long finLista = System.nanoTime();
            double tiempoLista = (finLista - inicioLista) / 1_000_000.0;
            
            mostrarResultadosComparacion(repeticiones, tiempoArbol, tiempoLista, id);
            
        } catch (InputMismatchException e) {
            System.out.println("El ID debe ser un numero entero.");
            scanner.nextLine();
        }
    }

    private static Empleado buscarEnLista(int id) {
        for (Empleado empleado : listaEmpleados) {
            if (empleado.getId() == id) {
                return empleado;
            }
        }
        return null;
    }

    private static void mostrarResultadosComparacion(int repeticiones, double tiempoArbol, 
                                                   double tiempoLista, int id) {
        System.out.println("\n=== RESULTADOS DE LA PRUEBA ===");
        System.out.println("ID buscado: " + id);
        System.out.println("Repeticiones: " + repeticiones);
        System.out.printf("Arbol binario: %.3f ms\n", tiempoArbol);
        System.out.printf("Busqueda secuencial: %.3f ms\n", tiempoLista);
        
        if (tiempoLista > tiempoArbol) {
            double mejora = tiempoLista / tiempoArbol;
            System.out.printf("El arbol es %.1f veces mas rapido\n", mejora);
            System.out.printf("Mejora del rendimiento: %.1f%%\n", ((mejora - 1) * 100));
        } else {
            System.out.println("Resultado inesperado en las mediciones");
        }
    }

    private static void mostrarEstadisticas() {
        System.out.println("\n--- ESTADISTICAS DEL SISTEMA ---");
        System.out.println("Total de empleados: " + arbolEmpleados.contarNodos());
        System.out.println("Altura del arbol: " + arbolEmpleados.altura());
    }

    private static void pausar() {
        System.out.println("\n" + "=".repeat(30));
        System.out.print("Presione ENTER para continuar...");
        scanner.nextLine();
    }
}