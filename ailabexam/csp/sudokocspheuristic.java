package ailabexam.csp;


import java.util.*;

class CSPSolver9 {
    private int[][] board;
    private int boardSize;
    private int subgridSize;

    public CSPSolver9(int[][] initialBoard) {
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
        int[] nextCell = getNextCell(); // Heuristic MRV
        int row = nextCell[0];
        int col = nextCell[1];

        if (row == -1 && col == -1) {
            return true; // All cells filled
        }

        List<Integer> feasibleValues = getFeasibleValues(row, col);
        Collections.sort(feasibleValues, (a, b) -> {
            return getLCV(row, col, a) - getLCV(row, col, b); // Heuristic LCV
        });

        for (int num : feasibleValues) {
            board[row][col] = num;

            if (backtrack()) {
                return true;
            }

            board[row][col] = 0; // Backtrack
        }
        return false;
    }

    private int[] getNextCell() {
        int minRemaining = Integer.MAX_VALUE;
        int[] nextCell = {-1, -1};

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (board[i][j] == 0) {
                    int remainingValues = getFeasibleValues(i, j).size();
                    if (remainingValues < minRemaining) {
                        minRemaining = remainingValues;
                        nextCell[0] = i;
                        nextCell[1] = j;
                    }
                }
            }
        }
        return nextCell;
    }

    private List<Integer> getFeasibleValues(int row, int col) {
        List<Integer> feasibleValues = new ArrayList<>();
        boolean[] used = new boolean[boardSize + 1];

        // Check row and column
        for (int i = 0; i < boardSize; i++) {
            used[board[row][i]] = true;
            used[board[i][col]] = true;
        }

        // Check subgrid
        int startRow = row - row % subgridSize;
        int startCol = col - col % subgridSize;
        for (int i = startRow; i < startRow + subgridSize; i++) {
            for (int j = startCol; j < startCol + subgridSize; j++) {
                used[board[i][j]] = true;
            }
        }

        for (int num = 1; num <= boardSize; num++) {
            if (!used[num]) {
                feasibleValues.add(num);
            }
        }
        return feasibleValues;
    }

    private int getLCV(int row, int col, int num) {
        int count = 0;
        board[row][col] = num;

        for (int i = 0; i < boardSize; i++) {
            if (i != col && board[row][i] == 0) {
                count += getFeasibleValues(row, i).size();
            }
            if (i != row && board[i][col] == 0) {
                count += getFeasibleValues(i, col).size();
            }
        }

        int startRow = row - row % subgridSize;
        int startCol = col - col % subgridSize;
        for (int i = startRow; i < startRow + subgridSize; i++) {
            for (int j = startCol; j < startCol + subgridSize; j++) {
                if (i != row && j != col && board[i][j] == 0) {
                    count += getFeasibleValues(i, j).size();
                }
            }
        }

        board[row][col] = 0;
        return count;
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

public class sudokocspheuristic {
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

        CSPSolver9 solver = new CSPSolver9(initialBoard);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
