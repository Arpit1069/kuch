package ailabexam.games;

import java.util.*;

class EightPuzzleState {
    int[][] board;
    int zeroRow, zeroCol;

    public EightPuzzleState(int[][] board) {
        this.board = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
                if (board[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
    }

    public List<EightPuzzleState> getNextStates() {
        List<EightPuzzleState> nextStates = new ArrayList<>();
        int dRow[] = {0, 0, -1, 1};
        int dCol[] = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int newRow = zeroRow + dRow[i];
            int newCol = zeroCol + dCol[i];

            if (newRow < 3 && newRow >= 0 && newCol < 3 && newCol >= 0) {
                int[][] newBoard = new int[3][3];
                for (int r = 0; r < 3; r++) {
                    newBoard[r] = Arrays.copyOf(board[r], 3);
                }
                newBoard[zeroRow][zeroCol] = newBoard[newRow][newCol];
                newBoard[newRow][newCol] = 0;
                nextStates.add(new EightPuzzleState(newBoard));
            }
        }
        return nextStates;
    }

    public boolean isGoalState() {
        int count = 1;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != count % 9) {
                    return false;
                }
                count++;
            }
        }
        return true;
    }

    public int calculateHeuristic() {
        int heuristic = 0;
        int goalRow, goalCol;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    goalRow = (board[i][j] - 1) / 3;
                    goalCol = (board[i][j] - 1) % 3;
                    heuristic += Math.abs(goalRow - i) + Math.abs(goalCol - j);
                }
            }
        }
        return heuristic;
    }

     public int calculateHeuristic1() {
        int misplacedTiles = 0;
        int count = 1;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0 && cell != count % 9) {
                    misplacedTiles++;
                }
                count++;
            }
        }
        return misplacedTiles;
    }

    public int calculateHeuristic2() {
        int heuristic = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] != 0) {
                    int goalRow = (board[i][j] - 1) / 3;
                    int goalCol = (board[i][j] - 1) % 3;
                    heuristic += Math.sqrt((goalRow - i) * (goalRow - i) + (goalCol - j) * (goalCol - j));
                }
            }
        }
        return heuristic;
    }
    public void printState() {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
        System.out.println();
    }
}

public class eightpuzzlebest {
    public static void bestFirstSearch(EightPuzzleState initialState) {
        PriorityQueue<EightPuzzleState> queue = new PriorityQueue<>(Comparator.comparingInt(EightPuzzleState::calculateHeuristic2));
        Set<EightPuzzleState> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            EightPuzzleState currentState = queue.poll();

            if (currentState.isGoalState()) {
                System.out.println("Goal State Found:");
                currentState.printState();
                return;
            }

            List<EightPuzzleState> nextStates = currentState.getNextStates();
            for (EightPuzzleState nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }
        System.out.println("Goal state not reachable.");
    }

    public static void main(String[] args) {
        int[][] initialBoard = {
            {1, 2,3},
            {4, 5,6},
            { 0,7, 8}
        };

        EightPuzzleState initialState = new EightPuzzleState(initialBoard);
        System.out.println("Initial State:");
        initialState.printState();

        bestFirstSearch(initialState);
    }
}
