package com.dev.algoviz.graph;

import java.util.Objects;

/**
 * Represents an edge in a graph. Edges have a direction, from one node to another. Edges also have a cost. These
 * properties may be used by various algorithms.
 */
public class Edge {

    private final Node from, to;
    private final double cost;

    public Edge(Node from, Node to, double cost) {
        this.from = from;
        this.to = to;
        this.cost = cost;
    }

    public Edge(Node from, Node to) {
        this(from, to, 1);
    }

    /**
     * Gets the origin node for this edge.
     *
     * @return a {@link Node}
     */
    public Node getFrom() {
        return from;
    }

    /**
     * Gets the destination node for this edge.
     *
     * @return a {@link Node}
     */
    public Node getTo() {
        return to;
    }

    /**
     * Gets the cost of this edge.
     *
     * @return the edge's cost, as an integer.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Gets a value indicating whether this edge is equal to another object. The other object is considered equal if it
     * is also an edge, with the same origin, destination, and cost.
     *
     * @param o the other object to compare.
     * @return true if the other object is equal to this instance, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return cost == edge.cost &&
                from.equals(edge.from) &&
                to.equals(edge.to);
    }

    /**
     * Returns a hash of this edge, so that it can be used as a key in a hashtable such as a HashMap or HashSet.
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
        return Objects.hash(from, to, cost);
    }
}
