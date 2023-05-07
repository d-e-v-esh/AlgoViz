package com.dev.algoviz.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dev.algoviz.Point;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

/**
 * For A* Search, the overall algorithm doesn't differ from {@link DijkstraSearch}, hence we can
 * extend it to remove code duplication. The main difference is that A* uses a current-to-goal heuristic in addition
 * to the total cost so far, when deciding which nodes to explore next. A*, like Dijkstra, is guaranteed to find the
 * shortest path to the goal if one exists, as long as its heuristic doesn't overestimate. And, it will usually do this
 * while exploring fewer total nodes than Dijkstra.
 */
public class AStarSearch extends DijkstraSearch {

    public AStarSearch(Graph graph, Node startNode, Node goalNode) {
        super(graph, startNode, goalNode);
    }

    /**
     * Combines the priority comparisons from {@link DijkstraSearch} and {@link GreedyBestFirstSearch}. Each node's
     * priority is given by its cost to be reached from the start, plus its estimated cost to reach the goal from that
     * node.
     *
     * @param node1 the first node to compare.
     * @param node2 the second node to compare.
     * @return < 0 if node1 has a lower priority value, > 0 if node2 has a lower priority value, or 0 if the priority
     * values are equal.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected int comparePriority(Node node1, Node node2) {
        double priority1 = this.costSoFar.getOrDefault(node1, 0.0);
        double priority2 = this.costSoFar.getOrDefault(node2, 0.0);
        priority1 += heuristic(node1);
        priority2 += heuristic(node2);
        return Double.compare(priority1, priority2);
    }

    /**
     * The same heuristic function as {@link GreedyBestFirstSearch}'s heuristic.
     * <p>
     * The heuristic value is the straight-line distance between the given node and the goal node. It represents the
     * lowest possible cost to reach the goal from the given node. If there are any obstructions in the way, then the
     * actual cost will be higher, but that's fine. A good heuristic will never overestimate, and this is guaranteed in
     * this case.
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
