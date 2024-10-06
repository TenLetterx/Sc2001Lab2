package Sc2001Lab2;

import java.util.Arrays;

public class MatrixGraph {
	private int[][] adjacencyMatrix;
	private int numVertices;

	public MatrixGraph(int numVertices) {
		this.numVertices = numVertices;
		adjacencyMatrix = new int[numVertices][numVertices];
	}

	public void addEdge(int src, int dest, int weight) {
		adjacencyMatrix[src][dest] = weight;
	}

	public void dijkstraArrayPQ(int startVertex) {
		int[] distances = new int[numVertices];
		boolean[] visited = new boolean[numVertices];
		int[] predecessors = new int[numVertices];

		// Initialise distances and visited arrays
		Arrays.fill(distances, Integer.MAX_VALUE);
		Arrays.fill(visited, false);

		distances[startVertex] = 0;
		predecessors[startVertex] = -1;

		ArrayPriorityQueue pq = new ArrayPriorityQueue(numVertices);
		pq.insertElement(startVertex, distances[startVertex]);

		while (!pq.isEmpty()) {
			int u = pq.extractMin();
			visited[u] = true;

			for (int v = 0; v < numVertices; v++) {
				if (adjacencyMatrix[u][v] != 0 && !visited[v] && distances[u] != Integer.MAX_VALUE
						&& distances[u] + adjacencyMatrix[u][v] < distances[v]) {
					distances[v] = distances[u] + adjacencyMatrix[u][v];
					predecessors[v] = u;
					if (!visited[v]) {
						pq.insertElement(v, distances[v]);
					}
					pq.updatePriority(v, distances[v]);
				}
			}
		}
		//System.out.println("Array Priority Queue");
		//printSolution(startVertex, distances, predecessors);

	}

	private void printSolution(int startVertex, int[] distances, int[] predecessors) {
		System.out.println("Vertex\t\t Distance\tPath");
		for (int vertexIndex = 0; vertexIndex < numVertices; vertexIndex++) {
			if (vertexIndex != startVertex) {
				System.out.print(startVertex + " -> ");
				System.out.print(vertexIndex + " \t\t ");
				if (distances[vertexIndex] == Integer.MAX_VALUE) {
					System.out.println("Infinity \t No path");
				} else {
					System.out.print(distances[vertexIndex] + "\t\t");
					printPath(vertexIndex, predecessors);
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
