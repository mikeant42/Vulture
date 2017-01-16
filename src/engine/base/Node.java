package engine.base;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anarchist on 8/28/16.
 */
public class Node {
    private Transform transformation;
    private String name;
    private List<Node> children = new ArrayList<>();
    private Node parent;

    public Node(String name) {
        this.name = name;
        this.transformation = new Transform();
    }

    public Node() {
        this("");
    }

    public void addChild(Node node) {
        children.add(node);
        node.setParent(this);
    }

    public void addChildren(List<Node> nodes) {
        children.addAll(nodes);
        for (Node node : nodes) {
            node.setParent(this);
        }
    }

    private void setParent(Node parent) {
        this.parent = parent;
    }

    public Transform getTransform() {
        return transformation;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }

    /**
     * Returns all relatives of the selected node, not just the nodes directly related.
     * @return allChildren
     */
    protected List<Node> getAllChildren() {
        List<Node> allChildren = new ArrayList<>();

        for (Node node : children) {
            allChildren.add(node);
            for (Node node1 : node.getChildren()) {
                allChildren.add(node1);
                allChildren.addAll(node1.getChildren());
            }
        }

        return allChildren;
    }

    public boolean hasChildren() {
        return children.size() > 0;
    }

    public Node getParent() {
        return parent;
    }

    @Override
    public String toString() {
        return name;
    }

    public void render() {}
    public void input() {}
    public void update() {}
}
