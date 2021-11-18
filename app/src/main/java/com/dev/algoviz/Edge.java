package com.dev.algoviz;

public class Edge {
    public Node startingPoint;
    public Node endingPoint;
    public int weight;

    public Edge(Node startingPoint, Node endingPoint, int weight) {
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.weight = weight;
    }
}
