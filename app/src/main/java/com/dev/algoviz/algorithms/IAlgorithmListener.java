package com.dev.algoviz.algorithms;

/**
 * This interface is to be implemented by classes who are interested in responding to state changes in
 * {@link IGraphSearchAlgorithm} instances.
 */
public interface IAlgorithmListener {

    /**
     * This method will be called by any {@link IGraphSearchAlgorithm} instances this object is listening to,
     * whenever those algorithms' state changes.
     *
     * @param algorithm the {@link IGraphSearchAlgorithm} whose state changed.
     */
    void progressUpdate(IGraphSearchAlgorithm algorithm);
}
