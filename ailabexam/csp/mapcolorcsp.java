package ailabexam.csp;

import java.util.*;

class CSPSolver3 {
    private Map<String, List<String>> map;
    private Map<String, Integer> colors;
    private List<String> regions;
    private int numOfColors;

    public CSPSolver3(Map<String, List<String>> map, int numOfColors) {
        this.map = map;
        this.colors = new HashMap<>();
        this.regions = new ArrayList<>(map.keySet());
        this.numOfColors = numOfColors;
    }

    public boolean solve() {
        if (!backtrack(0)) {
            System.out.println("No solution found.");
            return false;
        }
        System.out.println("Solution found!");
        printSolution();
        return true;
    }

    private boolean backtrack(int regionIndex) {
        if (regionIndex == regions.size()) {
            return true; // All regions have been assigned a color
        }

        String region = regions.get(regionIndex);

        for (int color = 1; color <= numOfColors; color++) {
            if (isColorValid(region, color)) {
                colors.put(region, color);

                if (backtrack(regionIndex + 1)) {
                    return true;
                }

                colors.remove(region); // Backtrack, remove color assignment
            }
        }
        return false;
    }

    private boolean isColorValid(String region, int color) {
        List<String> neighbors = map.get(region);
        if (neighbors == null) {
            return true; // No neighbors, any color is valid
        }

        for (String neighbor : neighbors) {
            Integer neighborColor = colors.get(neighbor);
            if (neighborColor != null && neighborColor == color) {
                return false; // Neighbor has the same color
            }
        }
        return true;
    }

    private void printSolution() {
        for (Map.Entry<String, Integer> entry : colors.entrySet()) {
            System.out.println(entry.getKey() + " colored with color: " + entry.getValue());
        }
    }
}

public class mapcolorcsp {
    public static void main(String[] args) {
        // Define the map with regions and their neighbors
        Map<String, List<String>> map = new HashMap<>();
        map.put("A", Arrays.asList("B", "C"));
        map.put("B", Arrays.asList("A", "C", "D"));
        map.put("C", Arrays.asList("A", "B", "D"));
        map.put("D", Arrays.asList("B", "C"));

        int numOfColors = 3; // Number of colors available

        CSPSolver3 solver = new CSPSolver3(map, numOfColors);
        solver.solve();
    }
}

