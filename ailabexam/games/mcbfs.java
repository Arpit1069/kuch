package ailabexam.games;


import java.util.*;

class State {
    int missionariesLeft;
    int cannibalsLeft;
    int missionariesRight;
    int cannibalsRight;
    boolean boatOnLeft;
    State parent;

    public State(int missionariesLeft, int cannibalsLeft, int missionariesRight, int cannibalsRight, boolean boatOnLeft,State parent) {
        this.missionariesLeft = missionariesLeft;
        this.cannibalsLeft = cannibalsLeft;
        this.missionariesRight = missionariesRight;
        this.cannibalsRight = cannibalsRight;
        this.boatOnLeft = boatOnLeft;
        this.parent = parent;
    }

    public boolean isValid() {
        // Check if the number of missionaries and cannibals is within the valid range on both sides
        return missionariesLeft >= 0 && cannibalsLeft >= 0 && missionariesRight >= 0 && cannibalsRight >= 0
                && (missionariesLeft == 0 || missionariesLeft >= cannibalsLeft)
                && (missionariesRight == 0 || missionariesRight >= cannibalsRight);
    }

    public boolean isGoal() {
        return missionariesLeft == 0 && cannibalsLeft == 0;
    }

    public List<State> getNextStates() {
        List<State> nextStates = new ArrayList<>();

        int direction = boatOnLeft ? 1 : -1;

        // Generate possible moves (1 or 2 missionaries/cannibals)
        for (int m = 0; m <= 2; m++) {
            for (int c = 0; c <= 2; c++) {
                if (m + c >= 1 && m + c <= 2) {
                    int newML = missionariesLeft - direction * m;
                    int newCL = cannibalsLeft - direction * c;
                    int newMR = missionariesRight + direction * m;
                    int newCR = cannibalsRight + direction * c;

                    State newState = new State(newML, newCL, newMR, newCR, !boatOnLeft,this);

                    if (newState.isValid()) {
                        nextStates.add(newState);
                    }
                }
            }
        }

        return nextStates;
    }

    public void printState() {
        System.out.println(
                "Left: " + missionariesLeft + "M " + cannibalsLeft + "C | Right: " + missionariesRight + "M " + cannibalsRight + "C | Boat: " + (boatOnLeft ? "Left" : "Right"));
    }
    public List<State> getPath() {
        List<State> path = new ArrayList<>();
        State current = this;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }
        Collections.reverse(path);
        return path;
    }
}

public class mcbfs{
    public static void dfs(State initialState) {
        Stack<State> stack = new Stack<>();
        Set<State> visited = new HashSet<>();

        stack.push(initialState);
        visited.add(initialState);

        while (!stack.isEmpty()) {
            State currentState = stack.pop();

            if (currentState.isGoal()) {
                System.out.println("Goal State Found:");
                currentState.printState();
                return;
            }

            List<State> nextStates = currentState.getNextStates();
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
                    visited.add(nextState);
                }
            }
        }
        System.out.println("Goal state not reachable.");
    }
    public static void bfs(State initialState) {
        Queue<State> queue = new LinkedList<>();
        Set<State> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            
            if (currentState.isGoal()) {
                System.out.println("Goal State Found:");
                List<State> path = currentState.getPath();
                for (State state : path) {
                    state.printState();
                }
                return;
            }

            List<State> nextStates = currentState.getNextStates();
            for (State nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    queue.add(nextState);
                    visited.add(nextState);
                }
            }
        }
        System.out.println("Goal state not reachable.");
    }

    public static void main(String[] args) {
        State initialState = new State(3, 3, 0, 0, true,null);

        System.out.println("Initial State:");
        initialState.printState();

        bfs(initialState);
    }
}
