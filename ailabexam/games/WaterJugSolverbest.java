package ailabexam.games;

import java.util.*;

class WaterJugState {
    int jugA;
    int jugB;

    public WaterJugState(int jugA, int jugB) {
        this.jugA = jugA;
        this.jugB = jugB;
    }

    public List<WaterJugState> getNextStates(int jugACapacity, int jugBCapacity) {
        List<WaterJugState> nextStates = new ArrayList<>();
        
        // Fill Jug A
        nextStates.add(new WaterJugState(jugACapacity, jugB));
        // Fill Jug B
        nextStates.add(new WaterJugState(jugA, jugBCapacity));
        // Empty Jug A
        nextStates.add(new WaterJugState(0, jugB));
        // Empty Jug B
        nextStates.add(new WaterJugState(jugA, 0));
        // Pour from A to B
        int aToB = Math.min(jugA, jugBCapacity - jugB);
        nextStates.add(new WaterJugState(jugA - aToB, jugB + aToB));
        // Pour from B to A
        int bToA = Math.min(jugB, jugACapacity - jugA);
        nextStates.add(new WaterJugState(jugA + bToA, jugB - bToA));
        
        return nextStates;
    }

    public boolean isGoalState(int targetAmount) {
        return jugA == targetAmount || jugB == targetAmount;
    }

    public void printState() {
        System.out.println("Jug A: " + jugA + " | Jug B: " + jugB);
    }

    public int calculateHeuristic(int targetAmount) {
        return Math.abs(jugA - targetAmount) + Math.abs(jugB - targetAmount);
    }
}

public class WaterJugSolverbest {
    public static void bestFirstSearch(WaterJugState initialState, int targetAmount, int jugACapacity, int jugBCapacity) {
        PriorityQueue<WaterJugState> queue = new PriorityQueue<>(Comparator.comparingInt(state -> state.calculateHeuristic(targetAmount)));
        Set<WaterJugState> visited = new HashSet<>();
        Map<WaterJugState, WaterJugState> parent = new HashMap<>();

        queue.add(initialState);
        visited.add(initialState);
        parent.put(initialState, null);

        WaterJugState goalState = null;

        while (!queue.isEmpty()) {
            WaterJugState currentState = queue.poll();

            if (currentState.isGoalState(targetAmount)) {
                goalState = currentState;
                break;
            }

            List<WaterJugState> nextStates = currentState.getNextStates(jugACapacity, jugBCapacity);
            for (WaterJugState nextState : nextStates) {
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

    public static void printSolutionPath(Map<WaterJugState, WaterJugState> parent, WaterJugState goalState) {
        List<WaterJugState> path = new ArrayList<>();
        WaterJugState currentState = goalState;

        while (currentState != null) {
            path.add(currentState);
            currentState = parent.get(currentState);
        }

        Collections.reverse(path);
        for (WaterJugState state : path) {
            state.printState();
        }
    }

    public static void main(String[] args) {
        int jugACapacity = 4;
        int jugBCapacity = 3;
        int targetAmount = 2; // Desired amount of water

        WaterJugState initialState = new WaterJugState(0, 0); // Initially both jugs are empty
        System.out.println("Initial State:");
        initialState.printState();

        bestFirstSearch(initialState, targetAmount, jugACapacity, jugBCapacity);
    }
}
