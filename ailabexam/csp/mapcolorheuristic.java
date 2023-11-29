package ailabexam.csp;


import java.util.*;

class CSPSolver4 {
    private Map<String, List<String>> map;
    private Map<String, Integer> colors;
    private List<String> regions;
    private int numOfColors;

    public CSPSolver4(Map<String, List<String>> map, int numOfColors) {
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

        String nextRegion = selectMostConstrainedRegion();
        if (nextRegion == null) {
            return false; // No uncolored regions remaining
        }

        for (int color = 1; color <= numOfColors; color++) {
            if (isColorValid(nextRegion, color)) {
                colors.put(nextRegion, color);

                if (backtrack(regionIndex + 1)) {
                    return true;
                }

                colors.remove(nextRegion); // Backtrack, remove color assignment
            }
        }
        return false;
    }

    private String selectMostConstrainedRegion() {
        int maxNeighbors = -1;
        String mostConstrainedRegion = null;

        for (String region : regions) {
            if (!colors.containsKey(region)) { // Uncolored region
                int numNeighbors = map.containsKey(region) ? map.get(region).size() : 0;
                if (numNeighbors > maxNeighbors) {
                    maxNeighbors = numNeighbors;
                    mostConstrainedRegion = region;
                }
            }
        }
        return mostConstrainedRegion;
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

public class mapcolorheuristic {
    public static void main(String[] args) {
        // Define the map with regions and their neighbors
        Map<String, List<String>> map = new HashMap<>();
        map.put("A", Arrays.asList("B", "C"));
        map.put("B", Arrays.asList("A", "C", "D"));
        map.put("C", Arrays.asList("A", "B", "D"));
        map.put("D", Arrays.asList("B", "C"));

        int numOfColors = 3; // Number of colors available

        CSPSolver4 solver = new CSPSolver4(map, numOfColors);
        solver.solve();
    }
}
