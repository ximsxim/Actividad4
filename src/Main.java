import java.util.*;
import java.util.Locale;

public class Main {
    private static Node<String> root = null;
    private static int hijos, niveles;
    private static boolean izquierda;
    private static final Random R = new Random();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Número de hijos por nodo (1-5): ");
        hijos = sc.nextInt(); if (hijos < 1 || hijos > 5) hijos = 2;

        System.out.print("Número de niveles (1-4): ");
        niveles = sc.nextInt(); if (niveles < 1 || niveles > 4) niveles = 2;

        System.out.println("Dirección:\n1) izquierda a derecha\n2) derecha a izquierda");
        System.out.print("Elige: ");
        izquierda = (sc.nextInt() == 1);

        System.out.print("Dato para la raíz: ");
        root = Create(sc.next());

        mostrarArbol();
        menu(sc);
    }

    public static Node<String> Create(String data) { return new Node<>(data); }

    public static void Add(String data) { addRecursive(root, data, 1); }

    private static void addRecursive(Node<String> node, String data, int nivel) {
        if (nivel >= niveles) return;
        if (node.getChildren().size() < hijos) {
            if (izquierda) node.getChildren().add(new Node<>(data));
            else node.getChildren().add(0, new Node<>(data));
        } else addRecursive(node.getChildren().get(node.getChildren().size() - 1), data, nivel + 1);
    }

    public static void Remove() { removeRecursive(root); }

    private static void removeRecursive(Node<String> node) {
        if (node.getChildren().isEmpty()) return;
        Node<String> last = node.getChildren().get(node.getChildren().size() - 1);
        if (last.getChildren().isEmpty()) node.removeLastChild();
        else removeRecursive(last);
    }

    private static void printTreeByLevel(Node<String> root) {
        if (root == null) return;
        Queue<Node<String>> queue = new LinkedList<>();
        queue.add(root);

        int nivel = 0;
        while (!queue.isEmpty() && nivel < niveles) {
            int size = queue.size();

            printSpaces((niveles - nivel) * 3);

            List<Node<String>> currentLevel = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Node<String> current = queue.poll();
                System.out.print(current.getData() + "   ");
                currentLevel.add(current);
                for (Node<String> child : current.getChildren()) queue.add(child);
            }
            System.out.println();

            if (!queue.isEmpty()) {
                printSpaces((niveles - nivel) * 3);
                for (Node<String> parent : currentLevel) {
                    if (!parent.getChildren().isEmpty()) {
                        for (int i = 0; i < parent.getChildren().size(); i++)
                            System.out.print(i == 0 ? "/ " : (i == parent.getChildren().size() - 1 ? "\\ " : "| "));
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }
            nivel++;
        }
    }

    private static void printSpaces(int n) { for (int i = 0; i < n; i++) System.out.print(" "); }

    private static void mostrarArbol() { System.out.println("\nÁrbol actual:"); printTreeByLevel(root); System.out.println(); }

    private static void menu(Scanner sc) {
        System.out.println("Opciones: ");
        System.out.println("1. Agregar nodo\n2. Eliminar último nodo\n3. Mostrar árbol\n4. Comparar búsqueda (arreglo vs árbol)\n5. Salir");
        System.out.print("Elige: ");
        int opcion = sc.nextInt();

        if (opcion == 1) { System.out.print("Dato a agregar: "); Add(sc.next()); mostrarArbol(); menu(sc); }
        else if (opcion == 2) { Remove(); mostrarArbol(); menu(sc); }
        else if (opcion == 3) { mostrarArbol(); menu(sc); }
        else if (opcion == 4) { compararBusquedas(); menu(sc); }
        else if (opcion == 5) System.out.println("Saliendo...");
        else { System.out.println("Opción no válida."); menu(sc); }
    }

    // Comparativa 
    private static void compararBusquedas() {
        int[] datos = new int[100]; llenarUnicos(datos, 0);
        Node<Integer> bst = construirBST(null, datos, 0);
        int objetivo = datos[R.nextInt(datos.length)];
        System.out.println("Buscando: " + objetivo);

        int rep = 50000;

        long a0 = System.nanoTime(); repeat(rep, () -> buscarLineal(datos, 0, objetivo)); long a1 = System.nanoTime();
        long t0 = System.nanoTime(); repeat(rep, () -> buscarEnBST(bst, objetivo));       long t1 = System.nanoTime();

        System.out.println("Arreglo: " + fmt((a1 - a0) / 1_000_000.0) + " milisegundos");
        System.out.println("Árbol: "   + fmt((t1 - t0) / 1_000_000.0) + " milisegundos");
    }

    private static void repeat(int n, Runnable r) {
        if (n <= 0) return;
        if (n == 1) { r.run(); return; }
        int a = n >> 1, b = n - a;
        repeat(a, r); repeat(b, r);
    }

    // 100 valores
    private static void llenarUnicos(int[] arr, int i) {
        if (i == arr.length) return;
        int v = R.nextInt(1000);
        if (contiene(arr, i, v)) llenarUnicos(arr, i); else { arr[i] = v; llenarUnicos(arr, i + 1); }
    }
    private static boolean contiene(int[] arr, int len, int v) {
        if (len == 0) return false;
        return arr[len - 1] == v || contiene(arr, len - 1, v);
    }

    private static Node<Integer> construirBST(Node<Integer> n, int[] a, int i) {
        if (i == a.length) return n;
        return construirBST(insertarBST(n, a[i]), a, i + 1);
    }
    private static Node<Integer> insertarBST(Node<Integer> n, int v) {
        if (n == null) return new Node<>(v);
        if (v < n.getData()) setHijo(n, 0, insertarBST(hijo(n, 0), v));
        else if (v > n.getData()) setHijo(n, 1, insertarBST(hijo(n, 1), v));
        return n;
    }
    private static boolean buscarEnBST(Node<Integer> n, int v) {
        if (n == null) return false;
        if (v == n.getData()) return true;
        return v < n.getData() ? buscarEnBST(hijo(n, 0), v) : buscarEnBST(hijo(n, 1), v);
    }
    private static Node<Integer> hijo(Node<Integer> n, int i) {
        return n.getChildren().size() > i ? n.getChildren().get(i) : null;
    }
    private static void setHijo(Node<Integer> n, int i, Node<Integer> val) {
        List<Node<Integer>> ch = n.getChildren();
        if (ch.size() == i) ch.add(val);
        else if (ch.size() > i) ch.set(i, val);
        else { rellenar(n, ch.size(), i); ch.add(val); }
    }
    private static void rellenar(Node<Integer> n, int cur, int target) {
        if (cur == target) return;
        n.getChildren().add(null);
        rellenar(n, cur + 1, target);
    }

    private static boolean buscarLineal(int[] arr, int i, int objetivo) {
        if (i == arr.length) return false;
        return arr[i] == objetivo || buscarLineal(arr, i + 1, objetivo);
    }

    private static String fmt(double ms) { return String.format(Locale.US, "%.1f", ms); }
}
