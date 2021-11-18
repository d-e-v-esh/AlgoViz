package com.dev.algoviz;

/*
 * NOTES
 * startNode has a distance of 0 and every other node has a distance of infinity.
 * We order all the nodes by distance(weight). That order is called a priority queue.
 * We start from our startNode and look at all the adjacent nodes and update the weights of all those nodes.
 * For example:
 *   We start from S and we need to go to E
 *   The first node near S is A with a weight of 7.
 *   We put A in the priority queue. It becomes the shortest path
 *   Now we check for B, it has a weight of 2 so we set 2 as its weight and put it above A in the priority queue.
 *
 *
 *
 *
 * */

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra implements PathfindingAlgorithm {

    Node startNode, endNode;

    PriorityQueue<Node> priorityQueue = new PriorityQueue<>();
    //    Node[] nodesVisitedOrder = new Node[54 * 89];
    ArrayList<Node> nodesVisitedOrder = new ArrayList<Node>();

    ArrayList<Node> neighbours = new ArrayList<Node>();
    ArrayList<Node> path = new ArrayList<Node>();

    ArrayList<Node> visited = new ArrayList<Node>();
    private Node[][] grid = new Node[54][89];
    Comparator<Node> nodeComparator = new NodeComparator();
    private GridView gridView;
    HashMap<String, ArrayList<Node>> resultHashMap = new HashMap<String, ArrayList<Node>>();//Creating HashMap

    ArrayList<Node> unvisitedNodes = new ArrayList<Node>();

    public Dijkstra(Node startNode, Node endNode, Node[][] grid) {
        this.startNode = startNode;
        this.endNode = endNode;
        this.grid = grid;
        this.startNode.setWeight(0);


    }

    public void print2d(Node[][] grid) {
        // Loop through all rows
        for (int i = 0; i < grid.length; i++) {

            // Loop through all elements of current row
            for (int j = 0; j < grid[i].length; j++) {

                Log.v("", Integer.toString(grid[i][j].getX()) + " " + Integer.toString(grid[i][j].getY()) + " " + Boolean.toString(grid[i][j].isWall()));
//                System.out.println(Integer.toString(grid[i][j].getX()) + " " + Integer.toString(grid[i][j].getY()));

            }

//            System.out.println(" ");
//            Log.v("", " \n ");

        }


    }

    public void printGrid() {
        print2d(grid);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public HashMap<String, ArrayList<Node>> computePath() {
        startNode.setWeight(0);
        PriorityQueue<Node> priorityQueue = new PriorityQueue<>(nodeComparator);

        priorityQueue.add(startNode);

        while (!priorityQueue.isEmpty()) {
            Node closestNode = priorityQueue.poll();

            closestNode.setVisited(true);
            visited.add(closestNode);


            if(!closestNode.equals(startNode) && !closestNode.equals(endNode)){
                nodesVisitedOrder.add(closestNode);
            }

            for(int i = 0; i < closestNode.edges.size(); i++ ){

                Edge currentEdge = closestNode.edges.get(i);
                Node currentNeighbour = currentEdge.endingPoint;
                if(!currentNeighbour.isVisited()){
                    if(currentNeighbour.getWeight() > closestNode.getWeight() + currentEdge.weight){
                        currentNeighbour.setWeight(closestNode.getWeight()+ currentEdge.weight);
                        currentNeighbour.setParent(closestNode);
                        priorityQueue.remove(currentNeighbour);
                        priorityQueue.add(currentNeighbour);
                    }

                }
            }




        }

        Node currentNode = endNode;

        while(currentNode != null ){
            path.add(currentNode);
            currentNode = currentNode.getParent();
        }



        for(int i = 0; i< priorityQueue.size(); i++){
            Log.d( "Q", Integer.toString(priorityQueue.size()));
        }

        resultHashMap.put("nodesVisitedOrder", nodesVisitedOrder);
        resultHashMap.put("path", path);

        resultHashMap.put("visited", visited);






        return resultHashMap;


    }



}
