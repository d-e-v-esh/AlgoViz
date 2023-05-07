package com.dev.algoviz.algorithms;

import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

import java.lang.reflect.Constructor;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A static class which lists the names of all the algorithms in this project, along with a method to create new
 * instances of these algorithms based on the name, through reflection.
 */
public class GraphSearchAlgorithmFactory {

    /**
     * Maintains a collection of {@link Class<IGraphSearchAlgorithm>} instances, each one referring to one of the
     * algorithms implemented in this program. Each of these classes are mapped by a human-readable name.
     */
    private static final Map<String, Class<? extends IGraphSearchAlgorithm>> classes = new LinkedHashMap<>();

    // Populates the {@link #classes} map.
    static {
        classes.put("Breadth-First Search", BreadthFirstSearch.class);
        classes.put("Dijkstra's Search", DijkstraSearch.class);
        classes.put("Greedy Best First Search", GreedyBestFirstSearch.class);
        classes.put("A* Search", AStarSearch.class);
    }

    /**
     * Gets an array of all of the human-readable algorithm names.
     *
     * @return an array of Strings.
     */
    public static String[] getAlgorithmNames() {
        return classes.keySet().toArray(new String[0]);
    }

    /**
     * Creates a new instance of {@link IGraphSearchAlgorithm}, based on the information given.
     *
     * @param name      the human-readable name of the algorithm to create. This should match one of the names in the
     *                  {@link #classes} keyset.
     * @param graph     the graph to search.
     * @param startNode the start node in the graph to search.
     * @param goalNode  the goal node in the graph to search.
     * @return a new instance of a class which implements the {@link IGraphSearchAlgorithm} interface.
     */
    public static IGraphSearchAlgorithm createAlgorithm(String name, Graph graph, Node startNode, Node goalNode) {
        Class<? extends IGraphSearchAlgorithm> clazz = classes.get(name);
        try {
            Constructor<? extends IGraphSearchAlgorithm> ctor = clazz.getConstructor(graph.getClass(), startNode.getClass(), goalNode.getClass());
            return ctor.newInstance(graph, startNode, goalNode);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}