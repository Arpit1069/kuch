package ailabexam.csp;


import java.util.*;

class CSPSolver11 {
    private Map<Character, Integer> assignment;
    private Set<Character> letters;
    private String[] words;
    private String resultWord;

    public CSPSolver11(String[] words, String resultWord) {
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
        List<Character> orderedLetters = new ArrayList<>(letters);
        orderedLetters.sort(Comparator.comparingInt(o -> -getOccurrenceInWords(o)));

        return backtrack(0, orderedLetters);
    }

    private int getOccurrenceInWords(char c) {
        int count = 0;
        for (String word : words) {
            count += word.chars().filter(ch -> ch == c).count();
        }
        return count;
    }

    private boolean backtrack(int index, List<Character> orderedLetters) {
        if (index == orderedLetters.size()) {
            return isSolution();
        }

        char letter = orderedLetters.get(index);

        for (int digit = 9; digit >= 0; digit--) {
            if (!assignment.containsValue(digit)) {
                assignment.put(letter, digit);

                if (backtrack(index + 1, orderedLetters)) {
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

public class capcspheuristic{
    public static void main(String[] args) {
        String[] words = {"SEND", "MORE"};
        String resultWord = "MONEY";

        CSPSolver11 solver = new CSPSolver11(words, resultWord);
        if (solver.solve()) {
            System.out.println("Solution found:");
            solver.printSolution();
        } else {
            System.out.println("No solution found.");
        }
    }
}
