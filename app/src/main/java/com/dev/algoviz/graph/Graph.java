
package com.dev.algoviz.graph;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a graph. Graphs have nodes and edges. Each node and each edge has an associated cost which may be used
 * by certain algorithms.
 */
public class Graph {

    // A set of all nodes in the graph. Each node can only be added once, hence this is a set.
    private final Set<Node> nodes = new HashSet<>();
    // A set of all edges in the graph. Each edge can only be added once, hence this is a set.
    private final Set<Edge> edges = new HashSet<>();
    // All edges in the graph, grouped by the nodes from which those edges emanate.
    private final Map<Node, Set<Edge>> edgesByNode = new HashMap<>();

    /**
     * Gets all nodes in the graph.
     *
     * @return an unmodifiable {@link Set} of all nodes.
     */
    public Set<Node> getNodes() {
        return Collections.unmodifiableSet(nodes);
    }

    /**
     * Gets all edges in the graph.
     *
     * @return an unmodifiable {@link Set} of all edges.
     */
    public Set<Edge> getEdges() {
        return Collections.unmodifiableSet(edges);
    }

    /**
     * Gets an unmodifiable set of all edges emanating from the given node.
     *
     * @param node the node to query.
     * @return all edges emanating from that node, or an empty list if the node has no edges.
     */
    public Set<Edge> getEdgesFrom(Node node) {
        return Collections.unmodifiableSet(edgesByNode.get(node));
    }

    /**
     * Gets all neighbours of the given node. A node a's neighbours are all nodes b for which there is a an edge from
     * a to b.
     * <p>
     * For beginners: the syntax of this method uses advanced Java language features such as the streaming API and
     * method references. Google search terms "Java stream API" and "Java method references" for assistance
     * understanding this syntax.
     *
     * @param node the node to query.
     * @return all neighbours of the given node, or an empty list if the node has no neighbours.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Set<Node> getNeighbours(Node node) {
        return edgesByNode.get(node).stream().map(Edge::getTo).collect(Collectors.toSet());
    }

    /**
     * Adds the given node to the graph. When called with a node already in this graph, this method does nothing.
     *
     * @param node the node to add.
     */
    public void addNode(Node node) {
        boolean success = nodes.add(node);
        if (success) {
            edgesByNode.put(node, new HashSet<>());
        }
    }

    /**
     * Finds the node in the graph with the matching data (that is, the value returned by {@link Node#getData()}).
     *
     * @param data the data to search for
     * @return the node with the matching data, or null if no such node exists in this graph
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Node findNode(Object data) {
        return nodes.stream().filter(node -> node.getData().equals(data)).findFirst().orElse(null);
    }

    /**
     * Adds the given edge to the set of edges in this graph. Adding an edge which already exists will do nothing.
     *
     * @param edge the edge to add.
     */
    public void addEdge(Edge edge) {
        boolean success = edges.add(edge);
        if (success) {
            edgesByNode.get(edge.getFrom()).add(edge);
        }
    }
}
