#  Sistema de Gestión de Empleados con Árbol Binario

Este proyecto implementa un sistema en consola en **Java** para la gestión de empleados.  
Se utiliza un **Árbol Binario de Búsqueda (ABB)** como estructura principal para optimizar las operaciones de inserción, búsqueda y eliminación de empleados.  

---

##  ¿Qué funciones se pueden realizar?

1. **Buscar empleado por ID**  
   - El usuario ingresa un número de identificación.  
   - El sistema realiza una búsqueda eficiente en el árbol binario y muestra si el empleado existe o no.  

2. **Agregar nuevo empleado**  
   - El usuario ingresa el ID y nombre del empleado.  
   - El sistema lo inserta en el árbol binario manteniendo el orden.  

3. **Eliminar empleado por ID**  
   - Se ingresa un ID y el sistema elimina al empleado del árbol si existe.  
   - Se manejan los tres casos clásicos: nodo hoja, nodo con un hijo, nodo con dos hijos.  

4. **Mostrar empleados**  
   - Se muestran los empleados en diferentes tipos de recorridos:  
     - Preorden  
     - Inorden  
     - Postorden  

5. **Visualizar estructura del árbol**  
   - Se imprime en consola una representación jerárquica del árbol binario.  

6. **Comparar eficiencia: Árbol vs Lista**  
   - Se compara el tiempo de búsqueda en el árbol binario contra una lista secuencial.  
   - Se mide con **System.nanoTime()** y se muestran los resultados en milisegundos.  

7. **Mostrar estadísticas del sistema**  
   - Total de empleados registrados.  
   - Altura del árbol binario.  

8. **Salir del sistema**  
   - El usuario puede cerrar el programa en cualquier momento.  

---

##  ¿Qué técnicas se implementaron en este proyecto?

- **Árbol Binario de Búsqueda (ABB):**  
  Para organizar a los empleados por su ID de forma eficiente.  

- **Recorridos de árboles (DFS):**  
  Implementación de Preorden, Inorden y Postorden.  

- **Visualización de árbol:**  
  Representación en consola con ramas y jerarquía.  

- **Comparación de algoritmos:**  
  - **Búsqueda en Árbol Binario** 
  - **Búsqueda Secuencial en Lista**  
  - La comparación muestra claramente las ventajas del ABB cuando se manejan **muchos empleados (100000)**.  

- **Manejo de archivos (Logging):**  
  Registro automático de las operaciones en un archivo `log_empleados.txt`.  
  El log puede desactivarse para evitar interferencia en las pruebas de eficiencia.  

- **Buenas prácticas de POO:**  
  Encapsulamiento, uso de constructores, métodos con única responsabilidad y comentarios descriptivos en cada clase.  

---

##  ¿Cómo compilar el programa?

Ubicarse en la carpeta raíz del proyecto y ejecutar:

### Windows
```bash
javac -d bin -encoding UTF-8 src\*.java
macOS / Linux

javac -d bin -encoding UTF-8 src/*.java

Esto compilará todos los archivos .java y generará los .class en la carpeta bin/.

¿Cómo ejecutar el programa?
Dado que la clase principal es Main, la ejecución es:

java -cp bin Main
