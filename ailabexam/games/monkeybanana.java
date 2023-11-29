package ailabexam.games;
import java.util.*;

class MonkeyBananaState {
    boolean monkeyAtBox;
    boolean bananaPickedUp;

    public MonkeyBananaState(boolean monkeyAtBox, boolean bananaPickedUp) {
        this.monkeyAtBox = monkeyAtBox;
        this.bananaPickedUp = bananaPickedUp;
    }

    public boolean isGoalState() {
        return bananaPickedUp;
    }

    public List<MonkeyBananaState> getNextStates() {
        List<MonkeyBananaState> nextStates = new ArrayList<>();
        if (!bananaPickedUp && !monkeyAtBox) {
            nextStates.add(new MonkeyBananaState(true, false)); // Monkey pushes the box
        } else if (!bananaPickedUp && monkeyAtBox) {
            nextStates.add(new MonkeyBananaState(false, false)); // Monkey climbs the box
        } else if (bananaPickedUp && monkeyAtBox) {
            nextStates.add(new MonkeyBananaState(false, true)); // Monkey picks up the banana
        }
        return nextStates;
    }
}

public class monkeybanana {
    public static void bfs(MonkeyBananaState initialState) {
        Queue<MonkeyBananaState> queue = new LinkedList<>();
        Set<MonkeyBananaState> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            MonkeyBananaState currentState = queue.poll();

            if (currentState.isGoalState()) {
                System.out.println("BFS: Monkey reached the banana!");
                return;
            }

            List<MonkeyBananaState> nextStates = currentState.getNextStates();
            for (MonkeyBananaState nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }
        System.out.println("BFS: Banana is unreachable!");
    }

    public static void dfs(MonkeyBananaState currentState, Set<MonkeyBananaState> visited) {
        if (currentState.isGoalState()) {
            System.out.println("DFS: Monkey reached the banana!");
            return;
        }

        List<MonkeyBananaState> nextStates = currentState.getNextStates();
        for (MonkeyBananaState nextState : nextStates) {
            if (!visited.contains(nextState)) {
                visited.add(nextState);
                dfs(nextState, visited);
            }
        }
    }

    public static void main(String[] args) {
        MonkeyBananaState initialState = new MonkeyBananaState(false, false);

        System.out.println("Initial State:");

        bfs(initialState);

        // Reset visited set for DFS
        Set<MonkeyBananaState> visitedForDFS = new HashSet<>();

        System.out.println("\nInitial State:");

        bfs(initialState);
    }
}
