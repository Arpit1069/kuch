package ailabexam.games;


import java.util.*;

class MonkeyBananaState {
    int monkeyX;
    int monkeyY;
    int chairX;
    int chairY;
    int bananaX;
    int bananaY;

    public MonkeyBananaState(int monkeyX, int monkeyY, int chairX, int chairY, int bananaX, int bananaY) {
        this.monkeyX = monkeyX;
        this.monkeyY = monkeyY;
        this.chairX = chairX;
        this.chairY = chairY;
        this.bananaX = bananaX;
        this.bananaY = bananaY;
    }

    public List<MonkeyBananaState> getNextStates() {
        List<MonkeyBananaState> nextStates = new ArrayList<>();

        // Monkey moves
        nextStates.add(new MonkeyBananaState(monkeyX + 1, monkeyY, chairX, chairY, bananaX, bananaY)); // Move right
        nextStates.add(new MonkeyBananaState(monkeyX - 1, monkeyY, chairX, chairY, bananaX, bananaY)); // Move left
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY + 1, chairX, chairY, bananaX, bananaY)); // Move up
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY - 1, chairX, chairY, bananaX, bananaY)); // Move down

        // Chair moves
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY, chairX + 1, chairY, bananaX, bananaY)); // Move chair right
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY, chairX - 1, chairY, bananaX, bananaY)); // Move chair left
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY, chairX, chairY + 1, bananaX, bananaY)); // Move chair up
        nextStates.add(new MonkeyBananaState(monkeyX, monkeyY, chairX, chairY - 1, bananaX, bananaY)); // Move chair down

        return nextStates;
    }

    public boolean isGoalState() {
        return monkeyX == bananaX && monkeyY == bananaY;
    }

    public void printState() {
        System.out.println("Monkey: (" + monkeyX + ", " + monkeyY + ") | Chair: (" + chairX + ", " + chairY + ") | Banana: (" + bananaX + ", " + bananaY + ")");
    }

    public int calculateHeuristic() {
        return Math.abs(monkeyX - bananaX) + Math.abs(monkeyY - bananaY);
    }
}

public class MonkeyBananaSolverbfs {
    public static void bestFirstSearch(MonkeyBananaState initialState) {
        PriorityQueue<MonkeyBananaState> queue = new PriorityQueue<>(Comparator.comparingInt(MonkeyBananaState::calculateHeuristic));
        Set<MonkeyBananaState> visited = new HashSet<>();
        Map<MonkeyBananaState, MonkeyBananaState> parent = new HashMap<>();

        queue.add(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        MonkeyBananaState goalState = null;

        while (!queue.isEmpty()) {
            MonkeyBananaState currentState = queue.poll();

            if (currentState.isGoalState()) {
                goalState = currentState;
                break;
            }

            List<MonkeyBananaState> nextStates = currentState.getNextStates();
            for (MonkeyBananaState nextState : nextStates) {
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

    public static void printSolutionPath(Map<MonkeyBananaState, MonkeyBananaState> parent, MonkeyBananaState goalState) {
        List<MonkeyBananaState> path = new ArrayList<>();
        MonkeyBananaState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);
        for (MonkeyBananaState state : path) {
            state.printState();
        }
    }

    
    public static void main(String[] args) {
        MonkeyBananaState initialState = new MonkeyBananaState(1, 1, 2, 2, 4, 4);

        System.out.println("Initial State:");
        initialState.printState();

        bestFirstSearch(initialState);
    }
}
