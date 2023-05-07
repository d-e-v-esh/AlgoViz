package com.dev.algoviz;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class DrawGrid {

    public static final int cellWidth = 50, cellHeight = 50;
    public static Paint greenPaint = new Paint();
    public static Paint redPaint = new Paint();
    public static Paint whitePaint = new Paint();

    public static void drawForeground(Grid grid, Canvas canvas) {
        greenPaint.setColor(Color.GREEN);
        redPaint.setColor(Color.RED);
        whitePaint.setColor(Color.WHITE);

        Point startPoint = grid.getStartPoint();
        Point endPoint = grid.getGoalPoint();

        // Drawing start and end node
        canvas.drawRect(startPoint.getX() * cellWidth, startPoint.getY() * cellWidth, (startPoint.getX() + 1) * cellWidth, (startPoint.getY() + 1) * cellWidth, greenPaint);
        canvas.drawRect(endPoint.getX() * cellWidth, endPoint.getY() * cellWidth, (endPoint.getX() + 1) * cellWidth, (endPoint.getY() + 1) * cellWidth, redPaint);
    }

    public static void drawGridBackground(Grid grid, Canvas canvas) {

        paintTerrain(grid, canvas);


    }


    /**
     * Paints the grid lines of the given maze.
     *
     * @param grid   the maze whose grid lines should be painted.
     * @param canvas the graphics object to use to paint the maze.
     */
    public static void paintGridlines(Grid grid, Canvas canvas) {

        Paint blackPaint = new Paint();
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Here line is being drawn for columns
        for (int i = 1; i < grid.getWidth(); i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, cellHeight * grid.getHeight(), blackPaint);
        }

//        // Here line is being drawn for rows
        for (int i = 1; i < grid.getHeight(); i++) {
            canvas.drawLine(0, i * cellHeight, cellWidth * grid.getWidth(), i * cellHeight, blackPaint);
        }


        // Creates outline
        blackPaint.setStyle(Paint.Style.STROKE);
        canvas.drawRect(0, 0, cellWidth * grid.getWidth(), cellHeight * grid.getHeight(), blackPaint);

    }


    /**
     * Paints the terrain tiles of the given maze.
     *
     * @param grid   the maze whose terrain should be painted.
     * @param canvas the graphics object to use to paint the maze.
     */
    private static void paintTerrain(Grid grid, Canvas canvas) {
//        Paint blackPaint = new Paint();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {

                TileTypes tile = grid.getTile(new Point(x, y));

                Paint currentColor = new Paint();
                if (tile == TileTypes.Blank) {
                    currentColor.setColor(Color.WHITE);

                } else if (tile == TileTypes.Wall) {
                    currentColor.setColor(Color.BLACK);
                }

                canvas.drawRect(x * cellWidth, y * cellHeight, (x + 1) * cellWidth, (y + 1) * cellHeight, currentColor);


            }
        }
    }

}
