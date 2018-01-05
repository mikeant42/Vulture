package engine.base;

import engine.physics.AABB;

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


    private AABB aabb;
    private boolean isSolid = false;


    public Node(String name) {
        this.name = name;
        this.transformation = new Transform();

        aabb = new AABB();

        updateBoundingBox();
    }

    public Node() {
        this("");
    }


    public void setNodeName(String name) {
        this.name = name;
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

    public String getNodeName() {
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


    public boolean isSolid() {
        return isSolid;
    }

    public void remove(Node node) {
        children.remove(node);
    }

    /**
     * The act of setting this to true will register this Node with the physics engine
     * @param solid
     */
    public void setIsSolid(boolean solid) {
        isSolid = solid;
    }

    public AABB getBoundingBox() {
        return aabb;
    }

    public void updateBoundingBox() {
        this.aabb.getMin().x = this.getTransform().getPosition().x;
        this.aabb.getMin().y = this.getTransform().getPosition().y;
        this.aabb.getMax().x = this.getTransform().getPosition().x + this.getTransform().getFullScale().x;
        this.aabb.getMax().y = this.getTransform().getPosition().y + this.getTransform().getFullScale().y;
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

    /**
     * This class is triggered whenever two Nodes collide,
     * @param node, the other node in the collision
     */
    public void handleCollision(Node node) {}
}
