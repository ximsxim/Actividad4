import java.util.*;

public class Main {
    private static Node<String> root = null;
    private static int hijos;
    private static int niveles;
    private static boolean izquierda;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Número de hijos por nodo (1-5): ");
        hijos = sc.nextInt();
        if (hijos < 1 || hijos > 5) hijos = 2;

        System.out.print("Número de niveles (1-4): ");
        niveles = sc.nextInt();
        if (niveles < 1 || niveles > 4) niveles = 2;

        System.out.println("Dirección:");
        System.out.println("1) izquierda a derecha");
        System.out.println("2) derecha a izquierda");
        System.out.print("Elige: ");
        int dir = sc.nextInt();
        izquierda = (dir == 1);

        System.out.print("Dato para la raíz: ");
        root = Create(sc.next());

        mostrarArbol();
        menu(sc);
    }
    //Create
    public static Node<String> Create(String data) {
        return new Node<>(data);
    }
    //Add
    public static void Add(String data) {
        addRecursive(root, data, 1);
    }

    private static void addRecursive(Node<String> node, String data, int nivel) {
        if (nivel >= niveles) return;

        if (node.getChildren().size() < hijos) {
            if (izquierda) node.getChildren().add(new Node<>(data));
            else node.getChildren().add(0, new Node<>(data));
        } else {
            addRecursive(node.getChildren().get(node.getChildren().size() - 1), data, nivel + 1);
        }
    }

    // Remove
    public static void Remove() {
        removeRecursive(root);
    }

    private static void removeRecursive(Node<String> node) {
        if (node.getChildren().isEmpty()) return;

        Node<String> last = node.getChildren().get(node.getChildren().size() - 1);
        if (last.getChildren().isEmpty()) {
            node.removeLastChild();
        } else {
            removeRecursive(last);
        }
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

                for (Node<String> child : current.getChildren()) {
                    queue.add(child);
                }
            }
            System.out.println();

            if (!queue.isEmpty()) {
                printSpaces((niveles - nivel) * 3);
                for (Node<String> parent : currentLevel) {
                    if (!parent.getChildren().isEmpty()) {
                        for (int i = 0; i < parent.getChildren().size(); i++) {
                            if (i == 0) System.out.print("/ ");
                            else if (i == parent.getChildren().size() - 1) System.out.print("\\ ");
                            else System.out.print("| ");
                        }
                        System.out.print("  ");
                    }
                }
                System.out.println();
            }

            nivel++;
        }
    }

    private static void printSpaces(int n) {
        for (int i = 0; i < n; i++) System.out.print(" ");
    }

    private static void mostrarArbol() {
        System.out.println("\nÁrbol actual:");
        printTreeByLevel(root);
        System.out.println();
    }

    private static void menu(Scanner sc) {
        System.out.println("Opciones: ");
        System.out.println("1. Agregar nodo");
        System.out.println("2. Eliminar último nodo");
        System.out.println("3. Salir");

        System.out.print("Elige: ");
        int opcion = sc.nextInt();

        if (opcion == 1) {
            System.out.print("Dato a agregar: ");
            String data = sc.next();
            Add(data);
            mostrarArbol();
            menu(sc);
        } else if (opcion == 2) {
            Remove();
            mostrarArbol();
            menu(sc);
        } else if (opcion == 3) {
            System.out.println("Saliendo...");
        } else {
            System.out.println("Opción no válida.");
            menu(sc);
        }
    }
}