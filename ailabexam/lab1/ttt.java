package ailabexam.lab1;

import java.util.Scanner;

class TicTacToe1 {
    private char[][] board;
    private char currentPlayer;
    private String playerName; // New field to store the user's name

    public TicTacToe1(String playerName) {
        board = new char[3][3];
        currentPlayer = 'X';
        this.playerName = playerName;
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = '-';
            }
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public void displayPlayerName() {
        System.out.println("Player's Name: " + playerName);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkWin(char player) {

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }

        return false;
    }

    private void updateHeuristic(int row, int col) {
        int[][] manhattanDistance = {
                { 0, 1, 2 },
                { 1, 2, 1 },
                { 2, 1, 0 }
        };

        int scoreO = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'O') {
                    scoreO += manhattanDistance[i][j];
                }
            }
        }
        int aiScore = evaluateManhattan();
        aiScore -= scoreO;
    }

    private int evaluateManhattan() {
        int scoreX = 0;
        int scoreO = 0;
        int[][] manhattanDistance = {
                { 0, 1, 2 },
                { 1, 2, 1 },
                { 2, 1, 0 }
        };
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 'X') {
                    scoreX += manhattanDistance[i][j];
                } else if (board[i][j] == 'O') {
                    scoreO += manhattanDistance[i][j];
                }
            }
        }

        return scoreX - scoreO;
    }

    private int minimax(int depth, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth;
        } else if (checkWin('O')) {
            return depth - 10;
        } else if (isBoardFull()) {
            return 0;
        }

        int bestScore;
        if (isMaximizing) {
            bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minimax(depth + 1, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
        } else {
            bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        int score = minimax(depth + 1, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
        }
        if (bestScore == Integer.MIN_VALUE || bestScore == Integer.MAX_VALUE) {
            return 0;
        }

        return bestScore;
    }

    private int minimax(int depth, int alpha, int beta, boolean isMaximizing) {
        if (checkWin('X')) {
            return 10 - depth;
        } else if (checkWin('O')) {
            return depth - 10;
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'X';
                        int score = minimax(depth + 1, alpha, beta, false);
                        board[i][j] = '-';
                        bestScore = Math.max(score, bestScore);
                        alpha = Math.max(alpha, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == '-') {
                        board[i][j] = 'O';
                        int score = minimax(depth + 1, alpha, beta, true);
                        board[i][j] = '-';
                        bestScore = Math.min(score, bestScore);
                        beta = Math.min(beta, bestScore);
                        if (beta <= alpha) {
                            break;
                        }
                    }
                }
            }
            return bestScore;
        }
    }
    private void makeMove1() {
    int bestScore = Integer.MIN_VALUE;
    int bestRow = -1;
    int bestCol = -1;
    int alpha = Integer.MIN_VALUE;
    int beta = Integer.MAX_VALUE;

    for (int i = 0; i < 3; i++) {
        for (int j = 0; j < 3; j++) {
            if (board[i][j] == '-') {
                board[i][j] = 'X';
                int score = minimax(0, alpha, beta, false);
                board[i][j] = '-';
                if (score > bestScore) {
                    bestScore = score;
                    bestRow = i;
                    bestCol = j;
                }
            }
        }
    }

    board[bestRow][bestCol] = 'X';
}
    private void makeMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestRow = -1;
        int bestCol = -1;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '-') {
                    board[i][j] = 'X';
                    int score = minimax(0, false);
                    board[i][j] = '-';

                    if (score > bestScore) {
                        bestScore = score;
                        bestRow = i;
                        bestCol = j;
                    }
                }
            }
        }

        board[bestRow][bestCol] = 'X';
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Tic Tac Toe - You are 'O', and I am 'X'");
        System.out.println("Here's the initial empty board:");
        printBoard();

        while (true) {
            if (checkWin('X')) {
                System.out.println("AI won. Better luck next time!");
                break;
            } else if (checkWin('O')) {
                System.out.println("Congratulations, " + getPlayerName() + "! You won!");
                break;
            } else if (isBoardFull()) {
                System.out.println("It's a draw! Good game!");
                break;
            }

            System.out.println("Your turn (row [0-2] and column [0-2]):");
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != '-') {
                System.out.println("Invalid move! Try again.");
                continue;
            }

            board[row][col] = 'O';
            printBoard();
            updateHeuristic(row, col);

            if (!checkWin('O') && !isBoardFull()) {
                makeMove();
                System.out.println("AI's move:");
                printBoard();
                int aiScore = evaluateManhattan();
                System.out.println("AI's Heuristic Score: " + aiScore);
            }
        }
    }

    private void printBoard() {
        System.out.println("-------------");
        for (int i = 0; i < 3; i++) {
            System.out.print("| ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j] + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
    }
}

public class ttt {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String playerName = scanner.nextLine();

        TicTacToe1 game = new TicTacToe1(playerName);
        game.displayPlayerName();
        game.play();
    }
}
