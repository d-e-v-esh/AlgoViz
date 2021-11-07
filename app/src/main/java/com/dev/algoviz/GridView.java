package com.dev.algoviz;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class GridView extends View {

    private int numberOfColumns;

    private int numberOfRows;


    private boolean showSteps = true;


    private final int cellHeight;
    private final int cellWidth = cellHeight = 20;
    private int size = 25;
    private Paint blackPaint = new Paint();
    private Node node;
    private APathfinding pathfinding;


    public GridView(Context context) {

        this(context, null);

    }

    public GridView(Context context, AttributeSet attrs) {
        super(context, attrs)
        ;
        blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        pathfinding = new APathfinding(this, 5);
    }

    public int getGridHeight() {
        return getHeight();
    }

    public int getGridWidth() {
        return getWidth();

    }

    public boolean showSteps() {
        return showSteps;
    }


    public int getNumberOfColumns() {
        return numberOfColumns;
    }


    public int getNumberOfRows() {
        return numberOfRows;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        Log.d("This is node", node.toString());
        calculateDimensions();
    }


//    private void setNumberOfColumnsAndRows() {
//
//
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        ((Activity) getContext()).getWindowManager()
//                .getDefaultDisplay()
//                .getMetrics(displayMetrics);
//        int height = displayMetrics.heightPixels;
//        int width = displayMetrics.widthPixels;
//
//        this.numberOfColumns = width / 10;
//        this.numberOfRows = height / 10;
//        calculateDimensions();
//    }


    private void calculateDimensions() {
        this.numberOfRows = Math.round((float) getHeight() / cellHeight);
        this.numberOfColumns = Math.round((float) getWidth() / cellWidth);

        if (numberOfColumns < 1 || numberOfRows < 1) {
            return;
        }
        String currentWidth = Integer.toString(getWidth());

        String currentHeight = Integer.toString(getHeight());


        Log.d("currentWidth", currentWidth);
        Log.d("currentHeight", currentHeight);
        Log.d("numberOfRows", Integer.toString(numberOfRows));

        Log.d("numberOfColumns", Integer.toString(numberOfColumns));

//        cellWidth = getWidth() / numberOfColumns;
//        cellHeight = getHeight() / numberOfRows;

        // We need to set cell width and cell height and then find the column and rows.

//        node[numberOfColumns][numberOfRows].setCellChecked(true);
        node = new Node(numberOfColumns, numberOfRows);

        Log.d("This is node 2", String.valueOf(node.getX()));
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawColor(Color.WHITE);

        if (numberOfColumns == 0 || numberOfRows == 0) {
            return;
        }

        int width = getWidth();
        int height = getHeight();

//        for (int i = 0; i < numberOfColumns; i++) {
//            for (int j = 0; j < numberOfRows; j++) {
//                if (node.getCellChecked()) {
//                    ArrayList<Node> bordersList = pathfinding.getBorderList();
////                    if (pathfinding.getBorderList()) {
//
//                    // This draws the rectangles that appear when we click on the grid.
//                    canvas.drawRect(i * cellWidth, j * cellHeight,
//                            (i + 1) * cellWidth, (j + 1) * cellHeight,
//                            blackPaint);
//                }
//            }
//

//        }

        for (int i = 0; i < pathfinding.getBorderList().size(); i++) {
            canvas.drawRect(pathfinding.getBorderList().get(i).getX() * cellWidth, pathfinding.getBorderList().get(i).getY() * cellHeight, (pathfinding.getBorderList().get(i).getX() + 1) * cellWidth, (pathfinding.getBorderList().get(i).getY() + 1) * cellHeight, blackPaint);
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


//            cellChecked[column][row] = !cellChecked[column][row];
            Node newBorder = new Node(column, row);
            pathfinding.addBorder(newBorder);
            Log.d("currentNode", node.toString());
            Log.d("currentColumn", Integer.toString(column));
            Log.d("currentRow", Integer.toString(row));
            node.setCellChecked(!node.getCellChecked());
            invalidate();
        }

        return true;
    }


}
