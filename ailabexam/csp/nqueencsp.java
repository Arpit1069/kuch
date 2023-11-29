package ailabexam.csp;

import java.util.*;

class CSPSolver5 {
    private int[] queens;
    private int boardSize;

    public CSPSolver5(int boardSize) {
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

        for (int col = 0; col < boardSize; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;

                if (backtrack(row + 1)) {
                    return true;
                }

                queens[row] = 0; // Backtrack, reset position to 0
            }
        }
        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int i = 0; i < row; i++) {
            if (queens[i] == col || Math.abs(row - i) == Math.abs(col - queens[i])) {
                return false; // Queen can attack another queen
            }
        }
        return true;
    }

    private void printSolution() {
        for (int i = 0; i < boardSize; i++) {
            System.out.println("Queen at row " + i + ", column " + queens[i]);
        }
    }
}

public class nqueencsp {
    public static void main(String[] args) {
        int boardSize = 8; // Size of the chessboard (8x8)

        CSPSolver5 solver = new CSPSolver5(boardSize);
        solver.solve();
    }
}

