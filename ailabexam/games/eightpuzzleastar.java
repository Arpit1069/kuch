package ailabexam.games;
import java.util.*;

class EightPuzzleState1 {
    int[][] board;
    int zeroRow, zeroCol;
    int gCost;
    int hCost;

    public EightPuzzleState1(int[][] board, int gCost, int hCost) {
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
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public List<EightPuzzleState1> getNextStates() {
        List<EightPuzzleState1> nextStates = new ArrayList<>();
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
                nextStates.add(new EightPuzzleState1(newBoard, gCost + 1, calculateHeuristic(newBoard)));
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

    public int calculateHeuristic(int[][] currentBoard) {
        int heuristic = 0;
        int goalRow, goalCol;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (currentBoard[i][j] != 0) {
                    goalRow = (currentBoard[i][j] - 1) / 3;
                    goalCol = (currentBoard[i][j] - 1) % 3;
                    heuristic += Math.abs(goalRow - i) + Math.abs(goalCol - j);
                }
            }
        }
        return heuristic;
    }

    public int getTotalCost() {
        return gCost + hCost;
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

public class eightpuzzleastar {
    public static void aStarSearch(EightPuzzleState1 initialState) {
        PriorityQueue<EightPuzzleState1> queue = new PriorityQueue<>(Comparator.comparingInt(EightPuzzleState1::getTotalCost));
        Set<EightPuzzleState1> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            EightPuzzleState1 currentState = queue.poll();

            if (currentState.isGoalState()) {
                System.out.println("Goal State Found:");
                currentState.printState();
                return;
            }

            List<EightPuzzleState1> nextStates = currentState.getNextStates();
            for (EightPuzzleState1 nextState : nextStates) {
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
            {1, 3, 6},
            {4, 0, 2},
            {7, 5, 8}
        };

        EightPuzzleState1 initialState = new EightPuzzleState1(initialBoard, 0, 0);
        System.out.println("Initial State:");
        initialState.printState();

        aStarSearch(initialState);
    }
}
