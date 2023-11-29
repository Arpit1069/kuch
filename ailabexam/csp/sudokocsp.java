package ailabexam.csp;

import java.util.*;

class CSPSolver7 {
    private int[][] board;
    private int boardSize;
    private int subgridSize;

    public CSPSolver7(int[][] initialBoard) {
        this.boardSize = initialBoard.length;
        this.subgridSize = (int) Math.sqrt(boardSize);
        this.board = new int[boardSize][boardSize];
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = initialBoard[i][j];
            }
        }
    }

    public boolean solve() {
        return backtrack();
    }

    private boolean backtrack() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    for (int num = 1; num <= boardSize; num++) {
                        if (isSafe(i, j, num)) {
                            board[i][j] = num;

                            if (backtrack()) {
                                return true;
                            }

                            board[i][j] = 0; // Backtrack
                        }
                    }
                    return false; // No valid number for this cell
                }
            }
        }
        return true; // All cells filled
    }

    private boolean isSafe(int row, int col, int num) {
        // Check if 'num' is not in current row and column
        for (int i = 0; i < boardSize; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }

        // Check if 'num' is not in current subgrid
        int subgridStartRow = row - row % subgridSize;
        int subgridStartCol = col - col % subgridSize;
        for (int i = 0; i < subgridSize; i++) {
            for (int j = 0; j < subgridSize; j++) {
                if (board[subgridStartRow + i][subgridStartCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public void printSolution() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class sudokocsp {
    public static void main(String[] args) {
        int[][] initialBoard = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        CSPSolver7 solver = new CSPSolver7(initialBoard);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
