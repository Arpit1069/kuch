package ailabexam.lab1;

import java.util.Scanner;

public class TicTacToenonAI {
    private char[][] board;
    private char currentPlayer;

    public TicTacToenonAI() {
        board = new char[3][3];
        currentPlayer = 'X';
        initializeBoard();
    }

    public void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) {
                    System.out.print(" | ");
                }
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---------");
            }
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean makeMove(int row, int col) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            return true;
        }
        return false;
    }

    public boolean checkWin(char player) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true; // Row win
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true; // Column win
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true; // Diagonal win (top-left to bottom-right)
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true; // Diagonal win (top-right to bottom-left)
        }
        return false;
    }

    public boolean checkWin() {
        return checkWin(currentPlayer);
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public boolean isMoveAvailable(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    public boolean makeAdvancedMove() {
        // Try to win
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isMoveAvailable(i, j)) {
                    board[i][j] = currentPlayer;
                    if (checkWin()) {
                        return true;
                    }
                    board[i][j] = ' '; // Reset the move
                }
            }
        }

        // Try to block the opponent from winning
        char opponent = (currentPlayer == 'X') ? 'O' : 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (isMoveAvailable(i, j)) {
                    board[i][j] = opponent;
                    if (checkWin(opponent)) {
                        board[i][j] = currentPlayer;
                        return true;
                    }
                    board[i][j] = ' '; // Reset the move
                }
            }
        }

        // Make a random move if no winning or blocking move is available
        int row, col;
        do {
            row = (int) (Math.random() * 3);
            col = (int) (Math.random() * 3);
        } while (!isMoveAvailable(row, col));

        board[row][col] = currentPlayer;
        return true;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Tic-Tac-Toe!");
        printBoard();

        while (true) {
            System.out.println("Player " + currentPlayer + "'s turn.");
            
            if (currentPlayer == 'X') {
                System.out.println("Enter row (0-2) and column (0-2) separated by a space:");
                int row = scanner.nextInt();
                int col = scanner.nextInt();

                if (makeMove(row, col)) {
                    printBoard();
                } else {
                    System.out.println("Invalid move. Try again.");
                    continue;
                }
            } else {
                makeAdvancedMove();
                printBoard();
            }

            if (checkWin()) {
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            switchPlayer();
        }

        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToenonAI game = new TicTacToenonAI();
        game.play();
    }
}
