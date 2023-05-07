package com.dev.algoviz.algorithms;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Set;

/**
 * The most basic search algorithm included in this project. When a node is explored, all its neighbours are added
 * to a standard queue. This frontier is explored in a FIFO (standard queue) order. Path costs and estimates are not
 * considered in this algorithm.
 */
public class BreadthFirstSearch extends AbstractSearchAlgorithm {

    public BreadthFirstSearch(Graph graph, Node startNode, Node goalNode) {
        super(graph, startNode, goalNode);
    }

    /**
     * Explores a single node by removing it from the head of the frontier queue, locating its neighbours in the graph,
     * and adding those neighbours to the frontier. We will also record the path from the start node to each of those
     * neighbours by adding them to the {@link AbstractSearchAlgorithm#cameFrom} map.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void doStep() {

        Node current = this.frontier.remove();

        Set<Node> neighbors = this.graph.getNeighbours(current);
        for (Node next : neighbors) {
            if (!cameFrom.containsKey(next)) {
                frontier.add(next);
                cameFrom.put(next, current);
            }
        }
    }

    /**
     * The BFS algorithm uses a standard FIFO queue.
     *
     * @return a new instance of {@link ArrayDeque}.
     */
    @Override
    protected Queue<Node> createFrontier() {
        return new ArrayDeque<>();
    }
}
