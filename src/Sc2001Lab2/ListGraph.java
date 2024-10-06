package Sc2001Lab2;
import java.util.*;

public class ListGraph {
    private int V; // Number of vertices
    private List<List<Node>> adj; // Adjacency list representation

    // Node class to represent the neighbor and the weight of the edge
    class Node implements Comparable<Node> {
        int vertex, weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight); // Min-heap based on weight
        }
    }

    // Constructor
    public ListGraph(int V) {
        this.V = V;
        adj = new ArrayList<>(V);

        // Initialize each adjacency list for each vertex
        for (int i = 0; i < V; i++) {
            adj.add(new LinkedList<>());
        }
    }

    // Function to add an edge to the graph
    public void addEdge(int u, int v, int weight) {
        adj.get(u).add(new Node(v, weight));
    }

    // Dijkstra's algorithm using a min-heap priority queue
    public void dijkstraMinHeapPQ(int src) {
        // Distance array. dist[i] will hold the shortest distance from src to i
        int[] dist = new int[V];
        int[] pre = new int[V];
        // Set all distances to infinity, except the source
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(pre, -1);
        dist[src] = 0;
        pre[src] = -1;

        // Priority queue to store the vertices and their current known distances
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(src, 0)); // Add the source node with distance 0

        // Boolean array to mark visited nodes
        boolean[] visited = new boolean[V];

        while (!pq.isEmpty()) {
            // Extract the vertex with the minimum distance
            Node current = pq.poll();
            int u = current.vertex;

            // If already visited, skip processing
            if (visited[u]) continue;
            visited[u] = true;

            // Process each neighbor of u
            for (Node neighbor : adj.get(u)) {
                int v = neighbor.vertex;
                int weight = neighbor.weight;

                // Relaxation step: If a shorter path is found, update dist[v]
                if (!visited[v] && dist[u] + weight < dist[v]) {
                    dist[v] = dist[u] + weight;
                    pre[v] = u;
                    pq.add(new Node(v, dist[v]));
                }
            }
        }

        // Print the shortest distances from the source
        //System.out.println("Minimizing Heap Priority Queue");
        //printSolution(src, dist, pre);
    }
    
	private void printSolution(int startVertex, int[] dist, int[] pre) {
		System.out.println("Vertex\t\t Distance\tPath");
		for (int vertexIndex = 0; vertexIndex < V; vertexIndex++) {
			if (vertexIndex != startVertex) {
				System.out.print(startVertex + " -> ");
				System.out.print(vertexIndex + " \t\t ");
				if (dist[vertexIndex] == Integer.MAX_VALUE) {
					System.out.println("Infinity \t No path");
				} else {
					System.out.print(dist[vertexIndex] + "\t\t");
					printPath(vertexIndex, pre);
				}
				System.out.println();
			}
		}
		System.out.println();
	}

	private void printPath(int currentVertex, int[] predecessors) {
		if (currentVertex == -1) {
			return;
		}
		printPath(predecessors[currentVertex], predecessors);
		System.out.print(currentVertex + " ");
	}
}

