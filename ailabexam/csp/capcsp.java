package ailabexam.csp;


import java.util.*;

class CSPSolver10 {
    private Map<Character, Integer> assignment;
    private Set<Character> letters;
    private String[] words;
    private String resultWord;

    public CSPSolver10(String[] words, String resultWord) {
        this.words = words;
        this.resultWord = resultWord;
        this.assignment = new HashMap<>();
        this.letters = new HashSet<>();
        extractLetters();
    }

    private void extractLetters() {
        for (String word : words) {
            for (char c : word.toCharArray()) {
                if (!Character.isDigit(c)) {
                    letters.add(c);
                }
            }
        }
        for (char c : resultWord.toCharArray()) {
            if (!Character.isDigit(c)) {
                letters.add(c);
            }
        }
    }

    public boolean solve() {
        return backtrack(0);
    }

    private boolean backtrack(int index) {
        if (index == letters.size()) {
            return isSolution();
        }

        char[] lettersArray = new char[letters.size()];
        int i = 0;
        for (char letter : letters) {
            lettersArray[i++] = letter;
        }
        char letter = lettersArray[index];

        for (int digit = 0; digit <= 9; digit++) {
            if (!assignment.containsValue(digit)) {
                assignment.put(letter, digit);

                if (backtrack(index + 1)) {
                    return true;
                }

                assignment.remove(letter); // Backtrack
            }
        }
        return false;
    }

    private boolean isSolution() {
        if (assignment.size() != letters.size()) {
            return false;
        }

        int sum = 0;
        for (String word : words) {
            int num = 0;
            for (char c : word.toCharArray()) {
                num = num * 10 + assignment.get(c);
            }
            sum += num;
        }

        int result = 0;
        for (char c : resultWord.toCharArray()) {
            result = result * 10 + assignment.get(c);
        }

        return sum == result;
    }

    public void printSolution() {
        for (Map.Entry<Character, Integer> entry : assignment.entrySet()) {
            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }
}

public class capcsp {
    public static void main(String[] args) {
        String[] words = {"SEND", "MORE"};
        String resultWord = "MONEY";

        CSPSolver10 solver = new CSPSolver10(words, resultWord);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
