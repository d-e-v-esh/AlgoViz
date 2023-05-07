package com.dev.algoviz.algorithms;

import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Contains functionality that's common to all of the graph search algorithms in this project, to save each one from
 * having to implement this functionality themselves.
 */
public abstract class AbstractSearchAlgorithm implements IGraphSearchAlgorithm {

    // The list of listeners to notify when the algorithm's state changes
    private final List<IAlgorithmListener> listeners = new ArrayList<>();

    // The graph we're searching
    protected final Graph graph;
    // The start and goal nodes
    protected final Node startNode, goalNode;
    /**
     * Keeps track of the paths found by the algorithm.
     * <p>
     * If a and b are nodes, and a maps to b in this map, then it means that the algorithm has identified that
     * a and b are adjacent, and there is a direct path to b from a. We can use this information to build a path
     * from the start to the goal once the algorithm has completed.
     * <p>
     * For example, consider the following: entries:
     * <ol>
     *     <li>goalNode -> c</li>
     *     <li>c -> b</li>
     *     <li>b -> a</li>
     *     <li>a -> startNode</li>
     * </ol>
     * This would mean that there is a path from the start to the goal node as such:
     * <p>
     * startNode -> a -> b -> c -> goalNode.
     * <p>
     * This path is identified and constructed in the {@link #getPath()} method.
     */
    protected Map<Node, Node> cameFrom;
    /**
     * The frontier.
     * <p>
     * The frontier is the collection of nodes from which the algorithm will pick the next node to explore each step.
     */
    protected Queue<Node> frontier;

    /**
     * Creates a new instance.
     *
     * @param graph     the graph we're searching
     * @param startNode the start node
     * @param goalNode  the goal node
     */
    protected AbstractSearchAlgorithm(Graph graph, Node startNode, Node goalNode) {
        this.graph = graph;
        this.startNode = startNode;
        this.goalNode = goalNode;

        reset();
    }

    /**
     * This template method resets the algorithm back to its initial state when the algorithm was first constructed.
     * Calls the {@link #createFrontier()} hook method to create the queue instance to be used as the algorithm's
     * frontier. Also calls the {@link #doReset()} hook method, to allow for any custom algorithm-specific reset
     * functionality.
     */
    @Override
    public final void reset() {
        this.frontier = createFrontier();
        this.frontier.add(startNode); // The first node to explore should be the start node, so let's add it here.
        this.cameFrom = new HashMap<>();
        this.cameFrom.put(startNode, null);

        doReset();

        fireProgressUpdate();
    }

    /**
     * This method is intended to be overridden by derived classes when custom reset functionality is required. It is
     * optional to override this method. By default, this method does nothing.
     */
    protected void doReset() {
    }

    /**
     * Runs the algorithm continuously until it completes.
     */
    @Override
    public final void run() {
        while (!isDone()) {
            step();
        }
    }

    /**
     * This method is a template method. Every time an algorithm iterates, we first check if the algorithm is already
     * complete. If it is, we don't continue any further. Then, the algorithm picks a node in its frontier to explore
     * and updates necessary information according to the {@link #doStep()} hook method. Finally, we notify listeners
     * that an update has occurred, and return a value indicating whether the algorithm is now complete.
     *
     * @return true if the algorithm is now complete, false otherwise.
     */
    @Override
    public final boolean step() {
        if (isDone()) {
            return true;
        }

        doStep();

        fireProgressUpdate();
        return isDone();
    }

    /**
     * When overridden in a derived class, this hook method will perform one iteration of the algorithm.
     */
    protected abstract void doStep();

    /**
     * Returns a value indicating whether the algorithm has completed running (whether or not it actually found a path).
     *
     * @return true if the algorithm has completed, false otherwise.
     */
    @Override
    public boolean isDone() {
        return this.frontier.isEmpty() || isPathFound();
    }

    /**
     * Returns a value indicating whether the algorithm has found a path from the start to the goal.
     *
     * @return true if a path was found, false otherwise.
     */
    @Override
    public boolean isPathFound() {
        return this.cameFrom.containsKey(goalNode);
    }

    /**
     * Gets a collection of all nodes which have been explored while this algorithm has been running.
     *
     * @return a collection of {@link Node} instances.
     */
    @Override
    public final Collection<Node> getReached() {
        return Collections.unmodifiableCollection(cameFrom.keySet());
    }

    /**
     * Gets a collection of all nodes currently in the algorithm's "frontier". The frontier is the set of nodes which
     * from which the algorithm will pick the next node to explore.
     *
     * @return a collection of {@link Node} instances.
     */
    @Override
    public final Collection<Node> getFrontier() {
        return Collections.unmodifiableCollection(frontier);
    }

    /**
     * Builds the path identified by this algorithm from the start to the goal, if any. This is done by starting at the
     * goal and tracing backwards to the start, via the {@link #cameFrom} map.
     * <p>
     * For more information, see the description of {@link #cameFrom}.
     *
     * @return the identified path if any, or an empty list if there is none.
     */
    @Override
    public final List<Node> getPath() {
        List<Node> path = new ArrayList<>();

        if (isPathFound()) {
            Node current = goalNode;
            while (current != null) {
                path.add(current);
                current = cameFrom.get(current);
            }
            Collections.reverse(path);
        }

        return path;
    }

    /**
     * Causes all listeners to be notified that this algorithm's state has been updated.
     */
    protected final void fireProgressUpdate() {
        for (IAlgorithmListener l : listeners) {
            l.progressUpdate(this);
        }
    }

    /**
     * When overridden in a derived class, this method will create the {@link Queue} instance to be used as the frontier
     * for this algorithm. Different algorithms use different types of queues as the frontier (e.g. standard queues
     * versus priority queues).
     * <p>
     * Each time this method is called, a new queue should be created and returned.
     *
     * @return a {@link Queue} instance
     */
    protected abstract Queue<Node> createFrontier();

    /**
     * Adds the given listener to the list of listeners to be notified whenever this algorithm's state changes.
     *
     * @param l the listener to add
     */
    @Override
    public final void addAlgorithmListener(IAlgorithmListener l) {
        this.listeners.add(l);
    }

    /**
     * Removes the given listener from the list of listeners to be notified whenever this algorithm's state changes.
     *
     * @param l the listener to remove
     */
    @Override
    public final void removeAlgorithmListener(IAlgorithmListener l) {
        this.listeners.remove(l);
    }
}
