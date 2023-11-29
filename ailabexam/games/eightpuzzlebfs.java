package ailabexam.games;
import java.util.*;

class eightpuzzlestate {
    int[][] board;
    int zeroRow, zeroCol;

    public eightpuzzlestate(int[][] board) {
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

    public List<eightpuzzlestate> getNextStates(){
        List<eightpuzzlestate> nextstates = new ArrayList<>();
        int drow[] ={0,0,-1,1};
        int dcol[] = {1,-1,0,0};

        for (int i =0;i<4;i++){
            int nrow = drow[i] + zeroRow;
            int ncol = dcol[i] + zeroCol;

            if(nrow<3 && nrow >= 0 && ncol <3 && ncol>=0){
                int newboard[][ ] = new int[3][3];
                for (int r = 0; r < 3; r++) {
                    for (int c = 0; c < 3; c++) {
                        newboard[r][c] = board[r][c];
                    }
                }
                newboard[zeroRow][zeroCol] = newboard [nrow][ncol];
                newboard[nrow][ncol] = 0;
                 nextstates.add(new eightpuzzlestate(newboard));
            }
        }

        return nextstates;

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

public class eightpuzzlebfs {
    public static void bfs( eightpuzzlestate initialState){
        Queue<eightpuzzlestate> queue = new  LinkedList<>();
        Set<eightpuzzlestate> visited = new HashSet<>();

        queue.add(initialState);
        visited.add(initialState);
        while(!queue.isEmpty()){

            eightpuzzlestate currentState = queue.poll();

            if(currentState.isGoalState()){
                System.out.println("goal state found");
                currentState.printState();
                return;
            }

            List<eightpuzzlestate> nextStates = currentState.getNextStates();
            for(eightpuzzlestate next : nextStates){
                if(!visited.contains(next)){
                    queue.add(next);
                    visited.add(next);

                }
            }

        }
        System.out.println("goal state not found");
    }
    public static void dfs(eightpuzzlestate initialState) {
        Stack<eightpuzzlestate> stack = new Stack<>();
        Set<eightpuzzlestate> visited = new HashSet<>();

        stack.push(initialState);
        visited.add(initialState);

        while (!stack.isEmpty()) {
            eightpuzzlestate currentState = stack.pop();

            if (currentState.isGoalState()) {
                System.out.println("Goal State Found:");
                currentState.printState();
                return;
            }

            List<eightpuzzlestate> nextStates = currentState.getNextStates();
            for (eightpuzzlestate nextState : nextStates) {
                if (!visited.contains(nextState)) {
                    stack.push(nextState);
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

        eightpuzzlestate initailState = new eightpuzzlestate(initialBoard);
        System.out.println("Initail State:");
        initailState.printState();

        dfs(initailState);
    }
}
