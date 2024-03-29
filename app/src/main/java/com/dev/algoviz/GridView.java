package com.dev.algoviz;


import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.dev.algoviz.algorithms.IAlgorithmListener;
import com.dev.algoviz.algorithms.IGraphSearchAlgorithm;


public class GridView extends View {
    private Grid grid;
    private IGraphSearchAlgorithm algorithm;
    private TouchOperation touchOperation = TouchOperation.None;
    int blockType = 0;

    public GridView(Context context) {
        super(context);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Sets the maze to render. Registers a listener to respond to changes in the maze, so we know when a re-render
     * needs to occur.
     *
     * @param grid the maze to display.
     */
    public void setGrid(Grid grid) {
        if (this.grid != null) {
            this.grid.removeMazeListener(gridListener);
        }
        this.grid = grid;
        if (this.grid != null) {
            this.grid.addMazeListener(gridListener);
        }
        invalidate();
    }

    /**
     * Sets the algorithm to render. Registers a listener to respond to changes in the algorithm, so we know when a
     * re-render needs to occur.
     *
     * @param algorithm the algorithm to display.
     */
    public void setAlgorithm(IGraphSearchAlgorithm algorithm) {
        if (this.algorithm != null) {
            this.algorithm.removeAlgorithmListener(algListener);
        }
        this.algorithm = algorithm;
        if (this.algorithm != null) {
            this.algorithm.addAlgorithmListener(algListener);
        }
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
    }


    private Point getOffsets() {
//        int gridWidthPx = this.grid.getWidth() * DrawGrid.cellWidth;
//        int gridHeightPx = this.grid.getHeight() * DrawGrid.cellHeight;
//        this.getWidth() and this.getHeight() gives you the resolution of the canvas.
//        int xOffs = (this.getWidth() - gridWidthPx) / 2;
//        int yOffs = (this.getHeight() - gridHeightPx) / 2;

        int xOffs = 0;
        int yOffs = 0;
        return new Point(xOffs, yOffs);

    }


    private Point getClickedBlock(MotionEvent event) {
        if (this.grid != null) {
            Point offsets = getOffsets();

            int clickedTileX = (int) (event.getX() - offsets.getX()) / DrawGrid.cellWidth;
            int clickedTileY = (int) (event.getY() - offsets.getY()) / DrawGrid.cellHeight;

            if (clickedTileX >= 0 && clickedTileY >= 0 && clickedTileX < this.grid.getWidth() && clickedTileY < this.grid.getHeight()) {
                return new Point(clickedTileX, clickedTileY);
            }
        }
        return null;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (grid != null) {
            DrawGrid.drawGridBackground(this.grid, canvas);
            if (algorithm != null) {
                DrawAlgo.paintAlgorithm(this.algorithm, canvas);
            }
            DrawGrid.paintGridlines(this.grid, canvas);
            DrawGrid.drawForeground(this.grid, canvas);
        }
    }


    private void doTouchOperation(Point clickedBlock) {
        if (clickedBlock != null) {
            switch (this.touchOperation) {
                case PlaceWall:
                    this.grid.setTile(clickedBlock, TileTypes.Wall);
                    break;
                case DeleteWall:
                    this.grid.setTile(clickedBlock, TileTypes.Blank);

                    break;
                case MoveStart:
                    this.grid.setStartPoint(clickedBlock);
                    break;
                case MoveGoal:
                    this.grid.setGoalPoint(clickedBlock);
                    break;
            }
        }
    }


    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point clickedBlock = getClickedBlock(event);
        switch (event.getAction()) {
            // TAP and DRAG are merged under one case for now
            case MotionEvent.ACTION_DOWN:
            case DragEvent.ACTION_DRAG_LOCATION:
                if (clickedBlock == null) {
                    touchOperation = TouchOperation.None;
                } else if (blockType == 2) {
                    touchOperation = TouchOperation.MoveStart;
                } else if (blockType == 3) {
                    touchOperation = TouchOperation.MoveGoal;
                } else if (blockType == 0) {
                    touchOperation = TouchOperation.PlaceWall;
                } else if (blockType == 1) {
                    touchOperation = TouchOperation.DeleteWall;
                }
                break;
        }
        doTouchOperation(clickedBlock);
        invalidate();
        return true;

    }


    // A listener which responds to maze change events by causing a repaint of this panel.
    private final IGridListener gridListener = grid -> invalidate();

    // A listener which responds to algorithm change events by causing a repaint of this panel.
    private final IAlgorithmListener algListener = alg -> invalidate();

    private enum TouchOperation {
        None, PlaceWall, DeleteWall, MoveStart, MoveGoal
    }

}
