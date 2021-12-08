package com.dev.algoviz.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dev.algoviz.Point;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * For Greedy Best-First-Search, the overall algorithm doesn't differ from {@link BreadthFirstSearch}, hence we can
 * extend it to remove code duplication. The main difference is that GBFS uses a priority queue instead of a standard
 * FIFO queue. For GBFS, the priority queue is ordered according to a heuristic value assigned to each node. The
 * heuristic used in this case for a given node is the estimated cost to reach the goal node from the given node. Lower
 * values are preferred when choosing which nodes to explore next.
 * <p>
 * Essentially, this algorithm attempts to explore nodes first with lower estimated costs to reach the goal.
 */
public class GreedyBestFirstSearch extends BreadthFirstSearch {

    public GreedyBestFirstSearch(Graph graph, Node startNode, Node goalNode) {
        super(graph, startNode, goalNode);
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
     * Orders nodes in the priority queue according to a heuristic that's given by the {@link #heuristic(Node)}
     * method.
     *
     * @param node1 the first node to compare
     * @param node2 the second node to compare
     * @return < 0 if node1 has a lower priority value, > 0 if node2 has a lower priority value, or 0 if the priority
     * values are equal.
     */
    private int comparePriority(Node node1, Node node2) {
        double priority1 = heuristic(node1);
        double priority2 = heuristic(node2);
        return Double.compare(priority1, priority2);
    }

    /**
     * Gets a heuristic value for the given node.
     * <p>
     * In this case, the heuristic value is the straight-line distance between the given node and the goal node. It
     * represents the lowest possible cost to reach the goal from the given node. If there are any obstructions in the
     * way, then the actual cost will be higher, but that's fine. A good heuristic will never overestimate, and this is
     * guaranteed in this case.
     *
     * @param node the node for which the heuristic should be calculated.
     * @return the straight-line distance from the given node to the goal.
     */
    private double heuristic(Node node) {
        Point point1 = (Point) node.getData();
        Point point2 = (Point) goalNode.getData();
        double h = Math.pow(point2.getX() - point1.getX(), 2) + Math.pow(point2.getY() - point1.getY(), 2);
        return Math.sqrt(h);
    }
}
