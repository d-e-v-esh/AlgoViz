<h1 align="center">
    <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher.png" alt="Logo" width="140" height="140"><br/>
  AlgoViz
</h1>

<h3 align="center">Pathfinding Algorithm Visualizer</h3>

AlgoViz is an educational Android application I developed this app as part of my portfolio to demonstrate my understanding of various pathfinding algorithms by visualizing them in a grid-based environment. AlgoViz is designed to provide a clear, interactive visualization of how these algorithms work on a grid.

## Supported Pathfinding Algorithms
AlgoViz currently supports visualization of the following pathfinding algorithms:
1. **Breadth-First Search (BFS)**: BFS starts at the initial node and explores its immediate neighbors before moving on to the next level of neighbors. In the context of a grid, BFS first visits all the cells immediately adjacent to the starting cell (up, down, left, and right, and optionally diagonals), then the cells adjacent to those cells, and so on. This process continues until the target cell is found or all reachable cells have been visited. BFS is guaranteed to find the shortest path, but it may not be the most efficient algorithm, especially for larger grids or grids with obstacles.
2. **Dijkstra's Search**: Dijkstra's algorithm is a weighted graph search algorithm, meaning it takes into consideration the cost of moving from one node to another. In the context of a grid, this cost is usually represented by the distance between cells, assuming uniform cost for all movements. Dijkstra's algorithm starts at the initial cell and explores its neighbors, assigning each of them a tentative cost (the cost of reaching that cell from the starting cell). It then selects the cell with the lowest cost and explores its neighbors, updating their tentative costs if a lower-cost path is found. This process continues until the target cell is found or all reachable cells have been visited. Dijkstra's algorithm is guaranteed to find the shortest path and is generally more efficient than BFS.
3. **Greedy Best First Search**: This algorithm uses a heuristic function to estimate the cost of reaching the target cell from the current cell. The heuristic function typically calculates the straight-line distance between the current cell and the target cell. The algorithm starts at the initial cell and selects the neighbor with the lowest heuristic value. It then continues exploring the grid by always selecting the unvisited cell with the lowest heuristic value among the cells it has already encountered. Greedy Best First Search is faster than BFS and Dijkstra's algorithm, but it's not guaranteed to find the shortest path, as it can get stuck in local minima.
4. *A Search*: A* Search combines the ideas of Dijkstra's algorithm and Greedy Best First Search. It calculates the total cost **`f(n)`** of each cell as the sum of two components: the actual cost from the starting cell to the current cell **`g(n)`** (like Dijkstra's algorithm) and the heuristic cost from the current cell to the target cell **`h(n)`** (like Greedy Best First Search). The algorithm starts at the initial cell and explores its neighbors, updating their total costs if a lower-cost path is found. It then selects the cell with the lowest total cost and explores its neighbors. This process continues until the target cell is found or all reachable cells have been visited. A* Search is guaranteed to find the shortest path, provided that the heuristic function used is admissible (never overestimates the true cost) and consistent (satisfies the triangle inequality), and is typically more efficient than both BFS and Dijkstra's algorithm, as it balances exploration and exploitation using the heuristic function.



## Key Features

1. **Diagonal and Non-Diagonal Searches**: In this project, I implemented both diagonal and non-diagonal searches, allowing you to explore how each algorithm behaves with different movement constraints.
2. **Speed Control**: The speed of the algorithm visualization can be adjusted in real-time while the algorithm is running, making it easy to follow the search process at a comfortable pace.
3. **Step-by-Step Exploration**: With the help of the STEP button, you can go through the algorithm's path one step at a time, providing a clear understanding of how the search progresses and how each algorithm makes its decisions.

AlgoViz serves as an excellent portfolio project, demonstrating not only my understanding of pathfinding algorithms but also my ability to effectively implement and visualize them in an Android application. 

I hope you find AlgoViz useful in gaining a deeper understanding of these pathfinding algorithms, and I'm looking forward to your feedback and suggestions on how I can further improve and expand this project.

### Screenshots

<div>

<img src="https://user-images.githubusercontent.com/59534570/164244354-f7f8294f-82f2-4ea2-96c9-101263e25271.jpg" width="200" />
<img src="https://user-images.githubusercontent.com/59534570/164244359-48c95854-234b-4557-b40c-72c39930f9e2.jpg" width="200" />
<img src="https://user-images.githubusercontent.com/59534570/164244334-b67822e4-bad5-4c37-a903-df0a63461a80.jpg" width="200" />
<img src="https://user-images.githubusercontent.com/59534570/164244348-4a8ba7ea-f061-41ca-9c66-fbe3d9077781.jpg" width="200" />


</div>


<h3 align="center">
    It will be soon available on Play Store
</h3>
