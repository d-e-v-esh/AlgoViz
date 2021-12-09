package com.dev.algoviz;

import android.content.Context;

public class CanvasAdapter {

    Grid grid;
    GridView gridView;

    public CanvasAdapter(Context context) {

        grid = new Grid(20, 20);
        gridView = new GridView(context, null);
        gridView.setGrid(grid);
    }


    public Grid getGrid() {
        return grid;
    }

    public GridView getGridView() {
        return gridView;
    }
}
