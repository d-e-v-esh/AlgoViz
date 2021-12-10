package com.dev.algoviz;

/**
 * This interface is to be implemented by classes who want to be informed when a {@link Grid}'s state changes.
 */
public interface IGridListener {

    /**
     * Called by an observed {@link Grid} when its state changes.
     *
     * @param grid the maze that changed.
     */
    void gridChanged(Grid grid);
}

