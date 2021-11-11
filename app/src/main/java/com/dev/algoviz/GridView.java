package com.dev.algoviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class GridView extends View {
    private boolean showSteps = true;
    private ArrayList<Node> borders, open, closed, path;
    private Button resetButton;
    private final int cellHeight;
    private final int cellWidth = cellHeight = 20;
    private int size = 25;
    public Paint blackPaint = new Paint();
    public Paint greenPaint = new Paint();
    public Paint redPaint = new Paint();
    private final int numberOfRows = 89;
    private int numberOfColumns = 54;
    private final Node[][] node = new Node[numberOfColumns][numberOfRows];
    private Node startNode, endNode, par, currentNode;

    public GridView(Context context) {
        this(context, null);
    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        greenPaint.setColor(Color.GREEN);

        redPaint.setColor(Color.RED);
        startNode = new Node(30, 30);
        endNode = new Node(30, 60);

        borders = new ArrayList<Node>();
        open = new ArrayList<Node>();
        closed = new ArrayList<Node>();
        path = new ArrayList<Node>();
    }

    public void addBorder(Node node) {
        if (borders.size() == 0) {
            borders.add(node);
        } else if (!checkBorderDuplicate(node)) {
            borders.add(node);
        }
    }

    public void addOpen(Node node) {
        if (open.size() == 0) {
            open.add(node);
        } else if (!checkOpenDuplicate(node)) {
            open.add(node);
        }
    }

    public void addClosed(Node node) {
        if (closed.size() == 0) {
            closed.add(node);
        } else if (!checkClosedDuplicate(node)) {
            closed.add(node);
        }
    }

    public boolean checkBorderDuplicate(Node node) {
        for (int i = 0; i < borders.size(); i++) {
            if (node.getX() == borders.get(i).getX() && node.getY() == borders.get(i).getY()) {
                return true;
            }
            if(node.getX()== startNode.getX() && node.getY() == startNode.getY()){
                return true;
            }

            if(node.getX()== endNode.getX() && node.getY() == endNode.getY()){
                return true;
            }
        }
        return false;
    }

    public boolean checkOpenDuplicate(Node node) {
        for (int i = 0; i < open.size(); i++) {
            if (node.getX() == open.get(i).getX() && node.getY() == open.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkClosedDuplicate(Node node) {
        for (int i = 0; i < closed.size(); i++) {
            if (node.getX() == closed.get(i).getX() && node.getY() == closed.get(i).getY()) {
                return true;
            }
        }
        return false;
    }

    public void addPath(Node node) {
        if (path.size() == 0) {
            path.add(node);
        } else {
            path.add(node);
        }
    }

    public void removePath(int location) {
        path.remove(location);
    }

    public void removeBorder(int location) {
        borders.remove(location);
    }

    public void removeOpen(int location) {
        open.remove(location);
    }

    public void removeOpen(Node node) {
        for (int i = 0; i < open.size(); i++) {
            if (node.getX() == open.get(i).getX() && node.getY() == open.get(i).getY()) {
                open.remove(i);
            }
        }
    }

    public void removeClosed(int location) {
        closed.remove(location);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.d("This is node", node.toString());
        calculateDimensions();
    }

    public ArrayList<Node> getBorderList() {
        return borders;
    }

    public ArrayList<Node> getOpenList() {
        return open;
    }

    public Node getOpen(int location) {
        return open.get(location);
    }

    public ArrayList<Node> getClosedList() {
        return closed;
    }

    public ArrayList<Node> getPathList() {
        return path;
    }

    private void calculateDimensions() {

        Log.d("z", Integer.toString(Math.round((float) getHeight() / cellHeight)));
        Log.d("x", Integer.toString(Math.round((float) getWidth() / cellWidth)));

        if (numberOfColumns < 1 || numberOfRows < 1) {
            return;
        }
        String currentWidth = Integer.toString(getWidth());
        String currentHeight = Integer.toString(getHeight());

        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                node[i][j] = new Node(i, j);
            }
        }

        /* To fill up the array lists, we can run a for loop and put conditionals to divide them in array lists
         *
         * if(node[x][y].isWall){
         *   borders.add(node[x][y])
         * }
         * Then we can draw it on the canvas
         *
         * */
        invalidate();
    }

    public void resetGrid() {
        Log.d("resetGrid", "Reset Functions Ran");
        borders.clear();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        // Drawing start and end node
        canvas.drawRect(startNode.getX() * cellWidth, startNode.getY() * cellWidth, (startNode.getX() + 1) * cellWidth, (startNode.getY() + 1) * cellWidth, greenPaint);
        canvas.drawRect(endNode.getX() * cellWidth, endNode.getY() * cellWidth, (endNode.getX() + 1) * cellWidth, (endNode.getY() + 1) * cellWidth, redPaint);

        Log.d("Borders in onDraw: ", Integer.toString(borders.size()));
        if (numberOfColumns == 0 || numberOfRows == 0) {
            return;
        }

        // We can manipulate this one to get what we want
        int width = getWidth();
        int height = getHeight();

        for (int i = 0; i < borders.size(); i++) {
            canvas.drawRect(
                    borders.get(i).getX() * cellWidth,
                    borders.get(i).getY() * cellHeight,
                    (borders.get(i).getX() + 1) * cellWidth,
                    (borders.get(i).getY() + 1) * cellHeight,
                    blackPaint);

        }

        // Here line is being drawn for columns
        for (int i = 1; i < numberOfColumns; i++) {
            canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
        }

//        // Here line is being drawn for rows
        for (int i = 1; i < numberOfRows; i++) {
            canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);
            currentNode = new Node(column, row);

//            currentNode.setWall(true);

            addBorder(currentNode);
            invalidate();
        }

        if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION) {
            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);
            currentNode = new Node(column, row);
//            currentNode.setWall(true);

            addBorder(currentNode);
            invalidate();
        }
        return true;
    }
}
