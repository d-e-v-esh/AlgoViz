package com.dev.algoviz;

import java.util.LinkedList;

public class Node {

    private int x, y, g, h, f, weight;

    private Node parent, previous;
    private boolean isWall, isClosed, isOpen, isPath, isVisited;
    public LinkedList<Edge> edges = new LinkedList<Edge>();

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
        this.setWeight(Integer.MAX_VALUE);
    }


    public void addEdge(Node startingPoint, Node endingPoint, int weight) {
        this.edges.add(new Edge(startingPoint, endingPoint, weight));
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

    public void setWeight(int weight) {
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

    public boolean equals(Node a) {
        if (this.getX() == a.getX() && this.getY() == a.getY()) {
            return true;
        }
        return false;
    }

    public void resetNode() {
        this.parent = null;
        this.previous = null;
        this.edges.clear();
        this.setWeight(Integer.MAX_VALUE);
       
        isWall = false;
        isVisited = false;
        isPath = false;

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

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node previous) {
        this.previous = previous;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

}
