package com.dev.algoviz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

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

//        if (algorithm.isPathFound()) {
//            paintPath(algorithm.getPath(), canvas);
//        }
    }

    /**
     * Paints the given graph nodes in the given color.
     *
     * @param nodes  the nodes to paint.
     * @param paint  the color to paint the nodes.
     * @param canvas the graphics object to use to paint the nodes.
     */
    private static void paintNodes(Collection<Node> nodes, Paint paint, Canvas canvas) {
        Point test = (Point) nodes.iterator().next().getData();
        Log.d("algoX", Integer.toString(test.getX()));
        Log.d("algoY", Integer.toString(test.getY()));

        for (Node node : nodes) {
            Point p = (Point) node.getData();
            /// Start from here and start to replace this with android canvas drawRect
            canvas.drawRect(p.getX() * DrawGrid.cellWidth, p.getY() * DrawGrid.cellHeight, (p.getX() + 1) * DrawGrid.cellWidth, (p.getY() + 1) * DrawGrid.cellHeight, paint);
        }
    }

    /**
     * Paints a polyline of n points, n being the size of the given path. Each point in the polyline will be centered
     * on the corresponding node.
     *
     * @param path   the nodes in the path to paint.
     * @param canvas the graphics object to use to paint the path.
     */
    private static void paintPath(List<Node> path, Canvas canvas) {

        int[] xs = new int[path.size()];
        int[] ys = new int[path.size()];
        float[] pts = new float[path.size()];

        for (int i = 0; i < path.size(); i++) {
            Point p = (Point) path.get(i).getData();

            xs[i] = p.getX() * DrawGrid.cellWidth + DrawGrid.cellWidth / 2;
            ys[i] = p.getY() * DrawGrid.cellHeight + DrawGrid.cellHeight / 2;
        }

//        g.setColor(PATH_COLOR);
//        g.setStroke(new BasicStroke(3));
//        g.drawPolyline(xs, ys, path.size());
        canvas.drawLines(pts, 0, path.size(), PATH_COLOR);

    }
}
