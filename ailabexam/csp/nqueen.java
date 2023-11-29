package ailabexam.csp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CSP {
    private int N;
    private List<Integer> assignment;

    public CSP(int N) {
        this.N = N;
        assignment = new ArrayList<>();
    }

    private boolean isSafe(int row, int col) {
        for (int prevRow = 0; prevRow < row; prevRow++) {
            int prevCol = assignment.get(prevRow);
            if (prevCol == col || prevCol - prevRow == col - row || prevCol + prevRow == col + row) {
                return false;
            }
        }
        return true;
    }

    private boolean solve(int row) {
        if (row == N) {
            return true;
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                assignment.add(col);
                if (solve(row + 1)) {
                    return true;
                }
                assignment.remove(assignment.size() - 1);
            }
        }
        return false;
    }

    public void solve() {
        if (solve(0)) {
            System.out.println("Solution found:");
            for (int row = 0; row < N; row++) {
                for (int col = 0; col < N; col++) {
                    if (assignment.get(row) == col) {
                        System.out.print("Q ");
                    } else {
                        System.out.print(". ");
                    }
                }
                System.out.println();
            }
        } else {
            System.out.println("No solution found.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens: ");
        int N = scanner.nextInt();
        scanner.close();

        CSP csp = new CSP(N);
        csp.solve();
    }
}