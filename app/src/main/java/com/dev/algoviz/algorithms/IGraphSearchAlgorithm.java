package com.dev.algoviz.algorithms;

import com.dev.algoviz.graph.Node;

import java.util.Collection;
import java.util.List;

/**
 * This interface is to be implemented by classes who want to provide new graph searching algorithms to be used
 * by the rest of this program.
 */
public interface IGraphSearchAlgorithm {

    /**
     * Adds the given listener to the list of listeners to be notified whenever this algorithm's state changes.
     *
     * @param l the listener to add.
     */
    void addAlgorithmListener(IAlgorithmListener l);

    /**
     * Removes the given listener from the list of listeners to be notified whenever this algorithm's state changes.
     *
     * @param l the listener to remove.
     */
    void removeAlgorithmListener(IAlgorithmListener l);

    /**
     * Returns a value indicating whether the algorithm has completed running (whether or not it actually found a path).
     *
     * @return true if the algorithm has completed, false otherwise.
     */
    boolean isDone();

    /**
     * Returns a value indicating whether the algorithm has found a path from the start to the goal.
     *
     * @return true if a path was found, false otherwise.
     */
    boolean isPathFound();

    /**
     * Resets the algorithm back to its initial state when the algorithm was first constructed.
     */
    void reset();

    /**
     * Runs the algorithm continuously until it completes.
     */
    void run();

    /**
     * Runs one iteration of the algorithm. Returns a value indicating whether the algorithm is now complete.
     *
     * @return true if the algorithm is complete, false otherwise.
     */
    boolean step();

    /**
     * Gets a collection of all nodes which have been explored while this algorithm has been running.
     *
     * @return a collection of {@link Node} instances.
     */
    Collection<Node> getReached();

    /**
     * Gets a collection of all nodes currently in the algorithm's "frontier". The frontier is the set of nodes which
     * from which the algorithm will pick the next node to explore.
     *
     * @return a collection of {@link Node} instances.
     */
    Collection<Node> getFrontier();

    /**
     * Gets the path from the start to the goal which has been found by this algorithm, if any.
     *
     * @return a list of {@link Node} instance corresponding to the path found; or an empty list if no path was found.
     */
    List<Node> getPath();

}
