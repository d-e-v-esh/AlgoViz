package com.dev.algoviz;


public class Dijkstra {

    Node startNode, endNode;
    Node[][] grid;


    public Dijkstra(Node startNode, Node endNode, Node[][] grid){
        this.startNode = startNode;
        this.endNode = endNode;
        this.grid = grid;

    }
}
