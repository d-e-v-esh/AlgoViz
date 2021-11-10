package com.dev.algoviz;

public class Node {

    private int x, y, g, h, f, weight;

    private Node parent;
    private boolean isWall, isClosed, isOpen, isPath;


    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }



    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight){
        this.weight = weight;
    }



    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public int getF() {
        return f;
    }


    public void setG(int g) {
        this.g = g;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setF(int f) {
        this.f = f;
    }

    public Node getNode() {
        return parent;
    }

    public Node getParent() {
        return parent;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public void setParent(Node parent) {
        this.parent = parent;
    }

    public static boolean isEqual(Node s, Node e) {
        if (s.getX() == e.getX() && s.getY() == e.getY()) {
            return true;
        }
        return false;
    }


    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isPath() {
        return isPath;
    }

    public void setPath(boolean path) {
        isPath = path;
    }
}
