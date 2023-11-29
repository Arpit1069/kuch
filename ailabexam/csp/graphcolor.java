package ailabexam.csp;

import java.util.*;

class CSPSolver1 {
    private int[][] graph;
    private int numOfVertices;
    private int numOfColors;
    private int[] colors;

    public CSPSolver1(int[][] graph, int numOfColors) {
        this.graph = graph;
        this.numOfVertices = graph.length;
        this.numOfColors = numOfColors;
        this.colors = new int[numOfVertices];
    }

    public boolean solve() {
        if (!backtrack(0)) {
            System.out.println("No solution found.");
            return false;
        }
        System.out.println("Solution found!");
        printSolution();
        return true;
    }

    private boolean backtrack(int vertexIndex) {
        if (vertexIndex == numOfVertices) {
            return true; // All vertices have been assigned a color
        }

        for (int color = 1; color <= numOfColors; color++) {
            if (isColorValid(vertexIndex, color)) {
                colors[vertexIndex] = color;

                if (backtrack(vertexIndex + 1)) {
                    return true;
                }

                colors[vertexIndex] = 0; // Backtrack, reset color to 0
            }
        }
        return false;
    }

    private boolean isColorValid(int vertexIndex, int color) {
        for (int i = 0; i < numOfVertices; i++) {
            if (graph[vertexIndex][i] == 1 && colors[i] == color) {
                return false; // Connected vertices have the same color
            }
        }
        return true;
    }

    private void printSolution() {
        for (int i = 0; i < numOfVertices; i++) {
            System.out.println("Vertex " + i + " colored with color: " + colors[i]);
        }
    }
}

public class graphcolor{
    public static void main(String[] args) {
        int[][] graph = {
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 0}
        };

        int numOfColors = 3; // Number of colors available

        CSPSolver1 solver = new CSPSolver1(graph, numOfColors);
        solver.solve();
    }
}

