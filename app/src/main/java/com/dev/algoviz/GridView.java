package com.dev.algoviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class GridView extends View {
    private boolean showSteps = true;
    private ArrayList<Node> borders, open, closed, path, visited;
    private Button resetButton;
    private final int cellHeight;
    private final int cellWidth = cellHeight = 20;
    private int size = 25;
    public Paint blackPaint = new Paint();
    public Paint greenPaint = new Paint();
    public Paint redPaint = new Paint();

    public Paint yellowPaint = new Paint();
    public Paint magentaPaint = new Paint();
    private final int numberOfRows = 89;
    private int numberOfColumns = 54;
    private Node[][] grid = new Node[numberOfColumns][numberOfRows];
    int startX, startY, endX, endY;
    Node startNode, endNode;


    PriorityQueue<Node> priorityQueue = new PriorityQueue<>();

    HashMap<String, ArrayList<Node>> resultHashMap = new HashMap<String, ArrayList<Node>>();//Creating HashMap
    ArrayList<Node> nodesVisitedOrder = new ArrayList<Node>();
    Comparator<Node> nodeComparator = new NodeComparator();


    private String currentAlgorithm;

    final Handler algorithmHandler = new Handler(Looper.getMainLooper());

    public GridView(Context context) {
        this(context, null);
    }

    public GridView(Context context, AttributeSet attrs) {


        super(context, attrs);

        startX = 30;
        startY = 30;
        endX = 30;
        endY = 40;

        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {
                grid[i][j] = new Node(i, j);
            }
        }

        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        greenPaint.setColor(Color.GREEN);
        magentaPaint.setColor(Color.MAGENTA);

        yellowPaint.setColor(Color.YELLOW);
        redPaint.setColor(Color.RED);
//        startNode = new Node(30, 30);
//        endNode = new Node(30, 60);

        startNode = grid[startX][startY];
        endNode = grid[endX][endY];
        borders = new ArrayList<Node>();
        open = new ArrayList<Node>();
        closed = new ArrayList<Node>();
        path = new ArrayList<Node>();
        visited = new ArrayList<Node>();

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
            if (node.getX() == startNode.getX() && node.getY() == startNode.getY()) {
                return true;
            }

            if (node.getX() == endNode.getX() && node.getY() == endNode.getY()) {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void visualizeDijkstra() {
        joinAdjacentNodes();
        startNode.setWeight(0);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(nodeComparator);

        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node closestNode = priorityQueue.poll();

            closestNode.setVisited(true);
            visited.add(closestNode);


            if (!closestNode.equals(startNode) && !closestNode.equals(endNode)) {
                nodesVisitedOrder.add(closestNode);
            }

            for (int i = 0; i < closestNode.edges.size(); i++) {

                Edge currentEdge = closestNode.edges.get(i);
                Node currentNeighbour = currentEdge.endingPoint;
                if (!currentNeighbour.isVisited()) {
                    if (currentNeighbour.getWeight() > closestNode.getWeight() + currentEdge.weight) {
                        currentNeighbour.setWeight(closestNode.getWeight() + currentEdge.weight);
                        currentNeighbour.setParent(closestNode);
                        priorityQueue.remove(currentNeighbour);
                        priorityQueue.add(currentNeighbour);
                    }

                }
            }

        }

        Node currentNode = endNode;

        while (currentNode != null) {
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }


        for (int i = 0; i < priorityQueue.size(); i++) {
            Log.d("Q", Integer.toString(priorityQueue.size()));
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void joinAdjacentNodes() {
//
        for (int i = 0; i < numberOfColumns; i++) {
            for (int j = 0; j < numberOfRows; j++) {

                //  If the current node is a wall then we continue
                if (grid[i][j].isWall()) {
                    continue;
                }

                int topX, topY, bottomX, bottomY, rightX, rightY, leftX, leftY;

                rightX = i;
                rightY = j + 1;

                leftX = i;
                leftY = j - 1;

                topX = i - 1;
                topY = j;

                bottomX = i + 1;
                bottomY = j;
                // If current node is not a wall then we add edges to that node.
                // Checking if node to the right is a wall
                if (rightX >= 0 && rightX < numberOfColumns && rightY >= 0 && rightY < numberOfRows) {
                    if (!grid[rightX][rightY].isWall()) {

                        grid[i][j].addEdge(grid[i][j], grid[rightX][rightY], 1);
                    }
                }


                // Checking if node to the left is a wall

                if (leftX >= 0 && leftX < numberOfColumns && leftY >= 0 && leftY < numberOfRows) {
                    if (!grid[i][j - 1].isWall()) {
                        grid[i][j].addEdge(grid[i][j], grid[leftX][leftY], 1);
                    }
                }

                // Checking if node to the top is a wall
                if (topX >= 0 && topX < numberOfColumns && topY >= 0 && topY < numberOfRows) {
                    if (!grid[i - 1][j].isWall()) {
                        grid[i][j].addEdge(grid[i][j], grid[topX][topY], 1);
                    }
                }

                // Checking if node to the bottom is a wall

                if (bottomX >= 0 && bottomX < numberOfColumns && bottomY >= 0 && bottomY < numberOfRows) {
                    if (!grid[i + 1][j].isWall()) {
                        grid[i][j].addEdge(grid[i][j], grid[bottomX][bottomY], 1);
                    }
                }
            }
        }

        invalidate();
    }


    public void resetGrid() {
        borders.clear();
        path.clear();
        open.clear();
        closed.clear();
        algorithmHandler.removeCallbacksAndMessages(null);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);

        // Drawing start and end node
        canvas.drawRect(startNode.getX() * cellWidth, startNode.getY() * cellWidth, (startNode.getX() + 1) * cellWidth, (startNode.getY() + 1) * cellWidth, greenPaint);
        canvas.drawRect(endNode.getX() * cellWidth, endNode.getY() * cellWidth, (endNode.getX() + 1) * cellWidth, (endNode.getY() + 1) * cellWidth, redPaint);

        if (numberOfColumns == 0 || numberOfRows == 0) {
            return;
        }

        // We can manipulate this one to get what we want
        int width = getWidth();
        int height = getHeight();

        // Drawing the borders
        for (int i = 0; i < borders.size(); i++) {
            canvas.drawRect(
                    borders.get(i).getX() * cellWidth,
                    borders.get(i).getY() * cellHeight,
                    (borders.get(i).getX() + 1) * cellWidth,
                    (borders.get(i).getY() + 1) * cellHeight,
                    blackPaint);

        }


        for (int i = 0; i < visited.size(); i++) {
            canvas.drawRect(
                    visited.get(i).getX() * cellWidth,
                    visited.get(i).getY() * cellHeight,
                    (visited.get(i).getX() + 1) * cellWidth,
                    (visited.get(i).getY() + 1) * cellHeight,
                    greenPaint);

        }


        // Drawing the path

        for (int i = 0; i < path.size(); i++) {
            canvas.drawRect(
                    path.get(i).getX() * cellWidth,
                    path.get(i).getY() * cellHeight,
                    (path.get(i).getX() + 1) * cellWidth,
                    (path.get(i).getY() + 1) * cellHeight,
                    magentaPaint);

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

            if (column < numberOfColumns && row < numberOfRows) {
                addBorder(grid[column][row]);
                grid[column][row].setWall(true);
            }


            invalidate();
        }

        if (event.getAction() == DragEvent.ACTION_DRAG_LOCATION) {
            int column = (int) (event.getX() / cellWidth);
            int row = (int) (event.getY() / cellHeight);
            if (column < numberOfColumns && row < numberOfRows) {
                addBorder(grid[column][row]);
                grid[column][row].setWall(true);
            }

            invalidate();
        }
        return true;
    }
}
