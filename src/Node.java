import java.util.ArrayList;
import java.util.List;

public class Node<T> {
    private T data;
    private List<Node<T>> children;

    public Node(T data) {
        this.data = data;
        this.children = new ArrayList<>();
    }

    public T getData() { return data; }
    public void setData(T data) { this.data = data; }

    public List<Node<T>> getChildren() { return children; }

    public void addChild(Node<T> child) {
        children.add(child);
    }

    public void removeLastChild() {
        if (!children.isEmpty()) {
            children.remove(children.size() - 1);
        }
    }
}