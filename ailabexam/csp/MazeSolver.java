package ailabexam.csp;

import java.util.*;

class MazeState {
    int[][] maze;
    int rows;
    int cols;
    int startX;
    int startY;
    int goalX;
    int goalY;

    public MazeState(int[][] maze, int startX, int startY, int goalX, int goalY) {
        this.maze = maze;
        this.rows = maze.length;
        this.cols = maze[0].length;
        this.startX = startX;
        this.startY = startY;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    public List<MazeState> getNextStates() {
        List<MazeState> nextStates = new ArrayList<>();
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        for (int i = 0; i < 4; i++) {
            int nx = startX + dx[i];
            int ny = startY + dy[i];

            if (isValid(nx, ny)) {
                int[][] newMaze = copyMaze();
                newMaze[startX][startY] = 1; // Mark the current position as visited
                newMaze[nx][ny] = 2; // Move to the next position

                nextStates.add(new MazeState(newMaze, nx, ny, goalX, goalY));
            }
        }
        return nextStates;
    }

    private boolean isValid(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != 2;
    }

    private int[][] copyMaze() {
        int[][] newMaze = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newMaze[i][j] = maze[i][j];
            }
        }
        return newMaze;
    }

    public boolean isGoalState() {
        return startX == goalX && startY == goalY;
    }

    public void printState() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == startX && j == startY) {
                    System.out.print("S "); // Start point
                } else if (i == goalX && j == goalY) {
                    System.out.print("G "); // Goal point
                } else {
                    System.out.print(maze[i][j] + " ");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public int calculateHeuristic() {
        // For simplicity, this heuristic returns the Manhattan distance between current and goal points
        return Math.abs(startX - goalX) + Math.abs(startY - goalY);
    }
}

public class MazeSolver {
    public static void bestFirstSearch(MazeState initialState) {
        PriorityQueue<MazeState> queue = new PriorityQueue<>(Comparator.comparingInt(MazeState::calculateHeuristic));
        Set<MazeState> visited = new HashSet<>();
        Map<MazeState, MazeState> parent = new HashMap<>();

        queue.add(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        MazeState goalState = null;

        while (!queue.isEmpty()) {
            MazeState currentState = queue.poll();

            if (currentState.isGoalState()) {
                goalState = currentState;
                break;
            }

            List<MazeState> nextStates = currentState.getNextStates();
            for (MazeState nextState : nextStates) {
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

    public static void printSolutionPath(Map<MazeState, MazeState> parent, MazeState goalState) {
        List<MazeState> path = new ArrayList<>();
        MazeState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);
        for (MazeState state : path) {
            state.printState();
        }
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 1, 0, 0, 0},
                {0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0},
                {0, 1, 1, 1, 0},
                {0, 0, 0, 1, 0}
        };

        int startX = 0;
        int startY = 0;
        int goalX = 4;
        int goalY = 4;

        MazeState initialState = new MazeState(maze, startX, startY, goalX, goalY);

        System.out.println("Initial State:");
        initialState.printState();

        bestFirstSearch(initialState);
    }
}
