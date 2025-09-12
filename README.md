# Árbol Binario de Empleados 

Este proyecto implementa:
- Un **árbol binario** con inserción, eliminación y búsqueda.
- Gestión de **empleados**.
- Diferentes tipos de **recorridos**.

Archivos principales:
- `Main.java`
- `ArbolBinario.java`
- `Node.java`
- `Empleado.java`

---

## Requisitos

- **Java 8 o superior**
- Una **terminal** (CMD/PowerShell en Windows o Bash/Zsh en macOS/Linux)

---

## Estructura del proyecto

/ACTIVIDAD4
/src
Main.java
ArbolBinario.java
Node.java
Empleado.java

## ¿Cómo compilar el programa?

En la terminal, estando en la carpeta del proyecto:

### Windows (CMD/PowerShell)
```bat
cd ruta\de\tu-proyecto
javac -d bin -encoding UTF-8 src\*.java

macOS / Linux
cd /ruta/de/tu-proyecto
mkdir -p bin
javac -d bin -encoding UTF-8 src/*.java

-d bin → manda los compilados (.class) a la carpeta bin.

-encoding UTF-8 → evita errores con acentos o caracteres especiales.

src/*.java → compila todos los archivos .java dentro de src.

## ¿Cómo ejecutar el programa?
Después de compilar, ejecuta:

Windows
java -cp bin Main

macOS / Linux
java -cp bin Main

-cp bin → le dice a Java que busque los .class en la carpeta bin.

Main → es la clase principal del proyecto.


Funcionalidades principales
Al ejecutar el programa desde la terminal, podrás:

Agregar empleado
Registrar un nuevo empleado en el árbol binario.

Buscar empleado
Consultar un empleado por su clave (ejemplo: ID).

Eliminar empleado
Quitar un empleado del árbol binario.

Mostrar recorridos del árbol

In-orden

Pre-orden

Post-orden

Salir del sistema
