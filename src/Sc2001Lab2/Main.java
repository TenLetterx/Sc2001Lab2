package Sc2001Lab2;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {

		int vertices, edges, count = 0;
		long startTime, timeTaken;

		Random random = new Random();

		FileWriter csvWriter = new FileWriter("results.csv", false);

		csvWriter
				.append("Number of Vertices, Number of Edges, Matrix & Array PQ(ns), List & Minimizing Heap PQ(ns),\n");

		Scanner scanner = new Scanner(System.in);

		System.out.println("Enter minimum number of vertices: ");
		int min = scanner.nextInt(); // Fixed value of S

		System.out.println("Enter maximum number of vertices: ");
		int max = scanner.nextInt(); // Fixed value of S

		System.out.println("Enter amount of data to make: ");
		int numData = scanner.nextInt(); // Fixed value of S

		double factor;

		factor = calculateFactor(min, max, numData);
		// System.out.println(factor);
		if (factor <= 1) {
			factor = 1.0001;
		}

		if (min == max) {
			for (int i = 0; i < numData; i++) {
				vertices = min;

				// Adjacency Matrix Graph
				MatrixGraph matrixGraph = new MatrixGraph(vertices);

				// Adjacency List Graph
				ListGraph listGraph = new ListGraph(vertices);

				edges = completeGraph(vertices, matrixGraph, listGraph, count);
				count++;

				csvWriter.append(vertices + "," + edges + ",");

				int src = random.nextInt(vertices);

				startTime = System.nanoTime();
				matrixGraph.dijkstraArrayPQ(src);
				timeTaken = (System.nanoTime() - startTime);
				csvWriter.append(timeTaken + ",");

				startTime = System.nanoTime();
				listGraph.dijkstraMinHeapPQ(src);
				timeTaken = (System.nanoTime() - startTime);
				csvWriter.append(timeTaken + "\n");

			}
		} else {
			for (double i = min; i <= max; i *= factor) {
				vertices = (int) Math.ceil(i);

				// Adjacency Matrix Graph
				MatrixGraph matrixGraph = new MatrixGraph(vertices);

				// Adjacency List Graph
				ListGraph listGraph = new ListGraph(vertices);

				edges = completeGraph(vertices, matrixGraph, listGraph, count);
				count++;

				csvWriter.append(vertices + "," + edges + ",");

				int src = random.nextInt(vertices);

				startTime = System.nanoTime();
				matrixGraph.dijkstraArrayPQ(src);
				timeTaken = (System.nanoTime() - startTime);
				csvWriter.append(timeTaken + ",");

				startTime = System.nanoTime();
				listGraph.dijkstraMinHeapPQ(src);
				timeTaken = (System.nanoTime() - startTime);
				csvWriter.append(timeTaken + "\n");

			}
		}

		csvWriter.close();
		System.out.println("Completed");

	}

	public static int completeGraph(int V, MatrixGraph matrixGraph, ListGraph listGraph, int count) throws IOException {
		int edges = 0, v1, v2, weight, maxEdges, wtlimit = 10;
		Random random = new Random();
		int[][] tempMatrix = new int[V][V];

		// Create FileWriter for CSV (overwrite mode)
		FileWriter csvWriter2 = new FileWriter("graph" + (count + 1) + ".csv", false);

		// Write the header row (vertex labels)
		csvWriter2.append("Graph " + (count + 1) + ",");
		for (int i = 0; i < V; i++) {
			csvWriter2.append(String.valueOf(i));
			if (i < V - 1) {
				csvWriter2.append(",");
			}
		}
		csvWriter2.append("\n");

		// In a directed graph, the maximum number of edges is V * (V - 1)
		maxEdges = V * (V - 1);
		// Create a complete directed graph
        for (v1 = 0; v1 < V; v1++) {
            v2 = (v1 + 1) % V; // Connect each vertex to the next, forming a cycle

            weight = random.nextInt(wtlimit) + 1; // Random weight between 1 and wtlimit

            // Add the directed edge v1 -> v2
            tempMatrix[v1][v2] = weight;
            matrixGraph.addEdge(v1, v2, weight); // Directed edge in matrix representation
            listGraph.addEdge(v1, v2, weight);   // Directed edge in list representation

            edges++;
        }

		// Adding additional random edges (up to maxEdges)
		int additionalEdges;

		if (V != 3) {
			//additionalEdges = random.nextInt(maxEdges - edges) + edges + 1; // Random number of additional edges
			//additionalEdges = maxEdges;
			//additionalEdges = 0;
			additionalEdges = 20;

			while (edges < additionalEdges) {
				// Pick random vertices for additional edges
				v1 = random.nextInt(V);
				v2 = random.nextInt(V);

				// Ensure that the vertices are not the same and no duplicate edge exists
				if (v1 != v2 && tempMatrix[v1][v2] == 0) {
					weight = random.nextInt(10) + 1;

					// Add the additional edge v1 -> v2
					tempMatrix[v1][v2] = weight;
					matrixGraph.addEdge(v1, v2, weight); // Directed edge in matrix
					listGraph.addEdge(v1, v2, weight); // Directed edge in list

					edges++; // Increment edge count
				}
			}
		}

		// Write the adjacency matrix to the CSV file
		for (int i = 0; i < V; i++) {
			csvWriter2.append(String.valueOf(i)).append(","); // Write row label
			for (int j = 0; j < V; j++) {
				csvWriter2.append(String.valueOf(tempMatrix[i][j]));
				if (j < V - 1) {
					csvWriter2.append(",");
				}
			}
			csvWriter2.append("\n"); // Move to the next row
		}

		// Close CSV Writer
		csvWriter2.close();

		return edges;
	}

	public static double calculateFactor(double min, double max, int numData) {
		return Math.pow(max / min, 1.0 / numData);
	}
}