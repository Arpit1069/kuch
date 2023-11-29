package ailabexam.csp;

import java.util.*;

class CSPSolver {
    private int[] queens;
    private int boardSize;

    public CSPSolver(int boardSize) {
        this.boardSize = boardSize;
        this.queens = new int[boardSize];
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

    private boolean backtrack(int row) {
        if (row == boardSize) {
            return true; // All queens are placed
        }

        List<Integer> feasibleCols = getFeasibleColumns(row);
        Collections.shuffle(feasibleCols); // Shuffle to randomize the order

        for (int col : feasibleCols) {
            queens[row] = col;

            if (backtrack(row + 1)) {
                return true;
            }

            queens[row] = 0; // Backtrack, reset position to 0
        }
        return false;
    }

    private List<Integer> getFeasibleColumns(int row) {
        List<Integer> feasibleCols = new ArrayList<>();
        for (int col = 0; col < boardSize; col++) {
            boolean isFeasible = true;
            for (int i = 0; i < row; i++) {
                if (queens[i] == col || Math.abs(row - i) == Math.abs(col - queens[i])) {
                    isFeasible = false; // Queen can attack another queen
                    break;
                }
            }
            if (isFeasible) {
                feasibleCols.add(col);
            }
        }
        return feasibleCols;
    }

    private void printSolution() {
        for (int i = 0; i < boardSize; i++) {
            System.out.println("Queen at row " + i + ", column " + queens[i]);
        }
    }
}

public class nqueencspheuristic {
    public static void main(String[] args) {
        int boardSize = 8; // Size of the chessboard (8x8)

        CSPSolver solver = new CSPSolver(boardSize);
        solver.solve();
    }
}
