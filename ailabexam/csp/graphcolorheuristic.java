package ailabexam.csp;


import java.util.*;

class CSPSolver2 {
    private int[][] graph;
    private int numOfVertices;
    private int numOfColors;
    private int[] colors;

    public CSPSolver2(int[][] graph, int numOfColors) {
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

        int nextVertex = selectMostConstrainedVertex();
        if (nextVertex == -1) {
            return false; // No uncolored vertices remaining
        }

        for (int color = 1; color <= numOfColors; color++) {
            if (isColorValid(nextVertex, color)) {
                colors[nextVertex] = color;

                if (backtrack(vertexIndex + 1)) {
                    return true;
                }

                colors[nextVertex] = 0; // Backtrack, reset color to 0
            }
        }
        return false;
    }

    private int selectMostConstrainedVertex() {
        int maxDegree = -1;
        int mostConstrainedVertex = -1;

        for (int i = 0; i < numOfVertices; i++) {
            if (colors[i] == 0) { // Uncolored vertex
                int degree = 0;
                for (int j = 0; j < numOfVertices; j++) {
                    if (graph[i][j] == 1 && colors[j] == 0) {
                        degree++;
                    }
                }
                if (degree > maxDegree) {
                    maxDegree = degree;
                    mostConstrainedVertex = i;
                }
            }
        }
        return mostConstrainedVertex;
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

public class graphcolorheuristic {
    public static void main(String[] args) {
        int[][] graph = {
            {0, 1, 1, 1},
            {1, 0, 1, 0},
            {1, 1, 0, 1},
            {1, 0, 1, 0}
        };

        int numOfColors = 3; // Number of colors available

        CSPSolver2 solver = new CSPSolver2(graph, numOfColors);
        solver.solve();
    }
}
