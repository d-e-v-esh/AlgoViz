package com.dev.algoviz.graph;

import java.util.Objects;

/**
 * Represents a node in a graph. Nodes have a cost, as well as data which can be any object. Both the cost and the data
 * may be examined by various algorithms.
 */
public class Node {

    private final Object data;
    private final double cost;

    public Node(Object data) {
        this(data, 0);
    }

    public Node(Object data, int cost) {
        this.data = data;
        this.cost = cost;
    }

    /**
     * Gets this node's data.
     *
     * @return an object.
     */
    public Object getData() {
        return data;
    }

    /**
     * Gets this node's cost.
     *
     * @return the node's cost, as an integer.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets a value indicating whether this object is equal to the given object. The given object is considered equal
     * if it is also a Node, with the same data and cost.
     *
     * @param o the other object to compare.
     * @return true if o is equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return data.equals(node.data) &&
                cost == node.cost;
    }

    /**
     * Returns a hash of this node, so that it can be used as a key in a hashtable such as a HashMap or HashSet.
     * <p>
     * Note: Two doubles which are "close enough" to be equal but differ slightly due to rounding errors will likely
     * produce different hash values. That could be a bug. I should think of a better way of representing costs. Perhaps
     * just represent costs as integers, but multiplied by 1000 or 10000 or so, so that things like "root 2" could still
     * be used as costs with reasonable precision?
     *
     * @return a hashcode.
     */
    @Override
    public int hashCode() {
        return Objects.hash(data, cost);
    }
}
