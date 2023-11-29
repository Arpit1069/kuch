package ailabexam.csp;


import java.util.*;

class MagicSquareState {
    int[][] grid;
    int size;
    int targetSum;

    public MagicSquareState(int[][] grid) {
        this.grid = grid;
        this.size = grid.length;
        this.targetSum = size * (size * size + 1) / 2;
    }

    public List<MagicSquareState> getNextStates() {
        List<MagicSquareState> nextStates = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    if (i > 0) {
                        nextStates.add(getNewState(i, j, i - 1, j));
                    }
                    if (i < size - 1) {
                        nextStates.add(getNewState(i, j, i + 1, j));
                    }
                    if (j > 0) {
                        nextStates.add(getNewState(i, j, i, j - 1));
                    }
                    if (j < size - 1) {
                        nextStates.add(getNewState(i, j, i, j + 1));
                    }
                }
            }
        }
        return nextStates;
    }

    private MagicSquareState getNewState(int x1, int y1, int x2, int y2) {
        int[][] newGrid = new int[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(grid[i], 0, newGrid[i], 0, size);
        }
        int temp = newGrid[x1][y1];
        newGrid[x1][y1] = newGrid[x2][y2];
        newGrid[x2][y2] = temp;
        return new MagicSquareState(newGrid);
    }

    public boolean isGoalState() {
        int diagonalSum1 = 0, diagonalSum2 = 0;
        for (int i = 0; i < size; i++) {
            int rowSum = 0, colSum = 0;
            diagonalSum1 += grid[i][i];
            diagonalSum2 += grid[i][size - 1 - i];
            for (int j = 0; j < size; j++) {
                rowSum += grid[i][j];
                colSum += grid[j][i];
            }
            if (rowSum != targetSum || colSum != targetSum) {
                return false;
            }
        }
        return diagonalSum1 == targetSum && diagonalSum2 == targetSum;
    }

    public void printState() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int calculateHeuristic() {
        int heuristic = 0;
        for (int i = 0; i < size; i++) {
            int rowSum = 0, colSum = 0;
            for (int j = 0; j < size; j++) {
                rowSum += grid[i][j];
                colSum += grid[j][i];
            }
            heuristic += Math.abs(rowSum - targetSum) + Math.abs(colSum - targetSum);
        }
        return heuristic;
    }
}

public class MagicSquareSolverbest {
    public static void bestFirstSearch(MagicSquareState initialState) {
        PriorityQueue<MagicSquareState> queue = new PriorityQueue<>(Comparator.comparingInt(MagicSquareState::calculateHeuristic));
        Set<MagicSquareState> visited = new HashSet<>();
        Map<MagicSquareState, MagicSquareState> parent = new HashMap<>();

        queue.add(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        MagicSquareState goalState = null;

        while (!queue.isEmpty()) {
            MagicSquareState currentState = queue.poll();

            if (currentState.isGoalState()) {
                goalState = currentState;
                break;
            }

            List<MagicSquareState> nextStates = currentState.getNextStates();
            for (MagicSquareState nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                    parent.put(nextState, currentState);
                }
            }
        }

        if (goalState != null) {
            System.out.println("Goal State Found:");
            printSolutionPath(parent, goalState);
        } else {
            System.out.println("Goal state not reachable.");
        }
    }

    public static void printSolutionPath(Map<MagicSquareState, MagicSquareState> parent, MagicSquareState goalState) {
        List<MagicSquareState> path = new ArrayList<>();
        MagicSquareState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);
        for (MagicSquareState state : path) {
            state.printState();
        }
    }

    public static void main(String[] args) {
        int[][] initialGrid = {
                {4, 0, 2},
                {3, 1, 5},
                {7, 6, 8}
        };

        MagicSquareState initialState = new MagicSquareState(initialGrid);

        System.out.println("Initial State:");
        initialState.printState();

        bestFirstSearch(initialState);
    }
}

