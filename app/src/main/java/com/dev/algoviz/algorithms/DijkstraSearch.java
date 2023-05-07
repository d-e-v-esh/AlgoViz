package com.dev.algoviz.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dev.algoviz.graph.Edge;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Dijkstra's algorithm, or "Uniform Cost Search", uses a priority queue instead of a standard FIFO queue for its
 * frontier. When picking nodes to explore from the frontier, this algorithm will prefer to explore nodes which have
 * the lowest cost from the start point to the node in question.
 */
public class DijkstraSearch extends AbstractSearchAlgorithm {

    /**
     * Keeps track of the costs to reach nodes from the start node.
     * <p>
     * If this map has a key "a" which maps to the value "42", this means that the algorithm has identified that the
     * lowest cost it has found so far to reach node a from the start point, is 42.
     */
    protected Map<Node, Double> costSoFar;

    public DijkstraSearch(Graph graph, Node startNode, Node goalNode) {
        super(graph, startNode, goalNode);
    }

    /**
     * Resets the {@link #costSoFar} map by adding an initial entry - the start node will obviously always have a cost
     * of 0 to reach itself!
     */
    @Override
    protected void doReset() {
        this.costSoFar = new HashMap<>();
        this.costSoFar.put(startNode, 0.0);
    }

    /**
     * Explores a single node by removing it from the head of the frontier queue and locating its neighbours in the
     * graph. For each of those neighbours, they will only be added to the frontier if they have never been found
     * before, or if the newly discovered cost to reach them is lower than the previously discovered cost to reach
     * them.
     */
    @Override
    protected void doStep() {

        Node current = this.frontier.remove();

        Set<Edge> edges = this.graph.getEdgesFrom(current);
        for (Edge e : edges) {

            Node next = e.getTo();
            double newCost = this.costSoFar.get(current) + e.getCost() + next.getCost();

            if (!this.costSoFar.containsKey(next) || newCost < this.costSoFar.get(next)) {
                this.costSoFar.put(next, newCost);
                this.frontier.add(next);
                this.cameFrom.put(next, current);
            }
        }
    }

    /**
     * The frontier for this algorithm is backed by a priority queue. Priorities in the queue are set
     * by the {@link #comparePriority(Node, Node)} method.
     *
     * @return a new instance of {@link PriorityQueue}.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected Queue<Node> createFrontier() {
        return new PriorityQueue<>(this::comparePriority);
    }

    /**
     * This method is used by the {@link PriorityQueue} to determine the ordering of nodes in the queue. Nodes with
     * the lower values as calculated in this method, will be closer to the head of the queue and thus will be explored
     * first by the algorithm.
     * <p>
     * For this algorithm, the priority values for nodes are calculated solely from the identified cost to reach those
     * nodes from the starting node.
     *
     * @param node1 the first node to compare.
     * @param node2 the second node to compare.
     * @return < 0 if node1 has a lower priority value, > 0 if node2 has a lower priority value, or 0 if the priority
     * values are equal.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    protected int comparePriority(Node node1, Node node2) {
        double priority1 = this.costSoFar.getOrDefault(node1, 0.0);
        double priority2 = this.costSoFar.getOrDefault(node2, 0.0);
        return Double.compare(priority1, priority2);
    }
}
