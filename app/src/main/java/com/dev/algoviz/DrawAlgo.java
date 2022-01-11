package com.dev.algoviz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.dev.algoviz.algorithms.IGraphSearchAlgorithm;
import com.dev.algoviz.graph.Node;

import java.util.Collection;
import java.util.List;

/**
 * The responsibility for drawing the search algorithm has been delegated from {@link GridView} to this class,
 * to reduce the amount of code in that class.
 */
public class DrawAlgo {
    // Constants. Change these to alter the look of the painted algorithm.
    public static Paint FRONTIER_COLOR = new Paint();
    public static Paint REACHED_COLOR = new Paint();
    public static Paint PATH_COLOR = new Paint();
    public static int radius = 0;


    /**
     * Paints the given algorithm. This method will paint the algorithm's reached (explored) nodes, frontier, and, if
     * a path has been found, will also paint the path.
     *
     * @param algorithm the algorithm to paint.
     * @param canvas    the graphics object to use to paint the algorithm.
     */
    public static void paintAlgorithm(IGraphSearchAlgorithm algorithm, Canvas canvas) {
        FRONTIER_COLOR.setColor(Color.CYAN);
        REACHED_COLOR.setColor(Color.YELLOW);
        PATH_COLOR.setColor(Color.MAGENTA);

        paintNodes(algorithm.getReached(), REACHED_COLOR, canvas);
        paintNodes(algorithm.getFrontier(), FRONTIER_COLOR, canvas);


        if (algorithm.isPathFound()) {
            paintPath(algorithm.getPath(), canvas);
        }
    }

    /**
     * Paints the given graph nodes in the given color.
     *
     * @param nodes  the nodes to paint.
     * @param paint  the color to paint the nodes.
     * @param canvas the graphics object to use to paint the nodes.
     */
    private static void paintNodes(Collection<Node> nodes, Paint paint, Canvas canvas) {

        // I will probably need to create a new function for each node that will be responsible
        // to animate each node.


        for (Node node : nodes) {
            Point p = (Point) node.getData();
            canvas.drawRect(p.getX() * DrawGrid.cellWidth,
                    p.getY() * DrawGrid.cellHeight,
                    (p.getX() + 1) * DrawGrid.cellWidth,
                    (p.getY() + 1) * DrawGrid.cellHeight,
                    paint);
        }
    }

    /**
     * @param path   the nodes in the path to paint.
     * @param canvas the graphics object to use to paint the path.
     */
    private static void paintPath(List<Node> path, Canvas canvas) {
        for (int i = 0; i < path.size(); i++) {
            Point p = (Point) path.get(i).getData();

            canvas.drawRect(p.getX() * DrawGrid.cellWidth, p.getY() * DrawGrid.cellWidth, (p.getX() + 1) * DrawGrid.cellWidth, (p.getY() + 1) * DrawGrid.cellWidth, PATH_COLOR);

        }
    }
}
