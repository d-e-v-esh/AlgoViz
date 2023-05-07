package com.dev.algoviz;


import com.dev.algoviz.graph.Edge;
import com.dev.algoviz.graph.Graph;
import com.dev.algoviz.graph.Node;

/**
 * This class handles the responsibility of converting a {@link Grid} into a {@link Graph} which can be searched.
 */
public class GraphFactory {

    private static final double ROOT_2 = Math.sqrt(2);

    public static Graph fromMaze(Grid grid) {
        return fromMaze(grid, false);
    }

    /**
     * Creates a {@link Graph}, from a {@link Grid}.
     * <p>
     * Each tile in the maze, except wall tiles, corresponds to a node in the graph. Each tile node is linked to its
     * (up to) four neighbours, (or up to eight neighbours if diagonal movement is allowed) by an edge.
     * <p>
     * Each Node's {@link Node#getData()} method will return the {@link Point} detailing the corresponding tile's
     * position in maze coordinate space.
     *
     * @param grid                  the maze to convert.
     * @param allowDiagonalMovement true if diagonal movement is allowed, false otherwise.
     * @return a {@link Graph}.
     */
    public static Graph fromMaze(Grid grid, boolean allowDiagonalMovement) {
        Graph g = new Graph();
        Node[][] nodes = createNodes(grid, g);
        createEdges(g, nodes, allowDiagonalMovement);
        return g;
    }

    /**
     * Creates all {@link Node} instances from the given {@link Grid}. Adds the nodes to the given graph, and returns
     * them in a 2D array for ease of referencing by coordinates at a later point.
     *
     * @param maze the maze to convert.
     * @param g    the graph to which new nodes should be added.
     * @return the created nodes, in a 2D array.
     */
    private static Node[][] createNodes(Grid maze, Graph g) {
        Node[][] nodes = new Node[maze.getWidth()][maze.getHeight()];
        for (int x = 0; x < maze.getWidth(); x++) {
            for (int y = 0; y < maze.getHeight(); y++) {
                TileTypes tile = maze.getTile(new Point(x, y));
                if (tile == TileTypes.Blank) {
                    nodes[x][y] = new Node(new Point(x, y));
                    g.addNode(nodes[x][y]);
                }
            }
        }
        return nodes;
    }

    /**
     * For each node in the graph, creates up to either four or eight edges from that node to its neighbours, depending
     * on whether or not diagonal movement is allowed. Edges are not created into wall nodes, or outside the boundaries
     * of the maze.
     *
     * @param g                     the graph we're currently building.
     * @param nodes                 the nodes which were previously created, in a 2D array so we can easily identify each node's
     *                              neighbours.
     * @param allowDiagonalMovement true if diagonal movement is allowed, false otherwise.
     */
    private static void createEdges(Graph g, Node[][] nodes, boolean allowDiagonalMovement) {
        for (int x = 0; x < nodes.length; x++) {
            for (int y = 0; y < nodes[0].length; y++) {
                Point p = new Point(x, y);

                addStraightEdges(g, p, nodes);

                if (allowDiagonalMovement) {
                    addDiagonalEdges(g, p, nodes);
                }
            }
        }
    }

    /**
     * Adds all straight edges from the given node. That is, adds edges from that node to its north, south, east, and
     * west neighbours.
     *
     * @param graph      the graph we're currently building.
     * @param fromCoords the coordinates of the node whose edges we're creating.
     * @param allNodes   all nodes, in a 2D array so the node's neighbours can be easily identified.
     */
    private static void addStraightEdges(Graph graph, Point fromCoords, Node[][] allNodes) {
        Node current = allNodes[fromCoords.getX()][fromCoords.getY()];
        if (current != null) {
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() - 1, fromCoords.getY()), allNodes, 1);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() + 1, fromCoords.getY()), allNodes, 1);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX(), fromCoords.getY() - 1), allNodes, 1);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX(), fromCoords.getY() + 1), allNodes, 1);
        }
    }

    /**
     * Adds all diagonal edges from the given node. That is, adds edges from that node to its northwest, northeast,
     * southwest, and southeast neighbours.
     *
     * @param graph      the graph we're currently building.
     * @param fromCoords the coordinates of the node whose edges we're creating.
     * @param allNodes   all nodes, in a 2D array so the node's neighbours can be easily identified.
     */
    private static void addDiagonalEdges(Graph graph, Point fromCoords, Node[][] allNodes) {
        Node current = allNodes[fromCoords.getX()][fromCoords.getY()];
        if (current != null) {
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() - 1, fromCoords.getY() - 1), allNodes, ROOT_2);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() - 1, fromCoords.getY() + 1), allNodes, ROOT_2);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() + 1, fromCoords.getY() - 1), allNodes, ROOT_2);
            addEdgeToNodeIfExists(graph, current, new Point(fromCoords.getX() + 1, fromCoords.getY() + 1), allNodes, ROOT_2);
        }
    }

    /**
     * Adds an edge from the given node to the node at the given coordinates, with the given cost. Only adds the node if
     * the destination node actually exists.
     *
     * @param graph    the graph we're currently building.
     * @param from     the node from which an edge should be created.
     * @param toCoords the coordinates of the node to which an edge should be created.
     * @param allNodes all nodes, organized as a 2D array so we can easily check coordinate boundaries.
     * @param cost     the cost of the edge to create.
     */
    private static void addEdgeToNodeIfExists(Graph graph, Node from, Point toCoords, Node[][] allNodes, double cost) {
        if (toCoords.getX() >= 0 && toCoords.getX() < allNodes.length
                && toCoords.getY() >= 0 && toCoords.getY() < allNodes[0].length) {

            Node to = allNodes[toCoords.getX()][toCoords.getY()];
            if (to != null) {
                graph.addEdge(new Edge(from, to, cost));
            }
        }
    }

}
