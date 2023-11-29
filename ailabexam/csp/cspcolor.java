package ailabexam.csp;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Variable {
    String name;
    List<Integer> domain;

    public Variable(String name, List<Integer> domain) {
        this.name = name;
        this.domain = domain;
    }
}

class Constraint {
    Variable var1;
    Variable var2;

    public Constraint(Variable var1, Variable var2) {
        this.var1 = var1;
        this.var2 = var2;
    }

    public boolean isSatisfied(int value1, int value2) {
        return value1 < value2;
    }
}

class CSPSolver {
    List<Variable> variables;
    List<Constraint> constraints;

    public CSPSolver() {
        variables = new ArrayList<>();
        constraints = new ArrayList<>();
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public int[] solve() {
        int[] assignment = new int[variables.size()];
        boolean isSolved = backtrack(assignment, 0);
        if (isSolved) {
            return assignment;
        } else {
            return null;
        }
    }

    private boolean backtrack(int[] assignment, int index) {
        if (index == variables.size()) {
            for (Constraint constraint : constraints) {
                if (!constraint.isSatisfied(assignment[0], assignment[1])) {
                    return false;
                }
            }
            return true;
        }

        Variable var = variables.get(index);
        for (int value : var.domain) {
            assignment[index] = value;
            if (backtrack(assignment, index + 1)) {
                return true;
            }
        }
        return false;
    }
}

public class cspcolor{
    public static void main(String[] args) {
        Variable X = new Variable("X", Arrays.asList(1, 2, 3));
        Variable Y = new Variable("Y", Arrays.asList(1, 2, 3));
        Variable Z = new Variable("Z", Arrays.asList(1, 2, 3));

        Constraint constraint1 = new Constraint(X, Y);
        Constraint constraint2 = new Constraint(Y, Z);

        CSPSolver solver = new CSPSolver();
        solver.addVariable(X);
        solver.addVariable(Y);
        solver.addVariable(Z);
        solver.addConstraint(constraint1);
        solver.addConstraint(constraint2);

        int[] solution = solver.solve();
        if (solution != null) {
            System.out.println("Solution found!");
            System.out.println("X = " + solution[0] + ", Y = " + solution[1] + ", Z = " + solution[2]);
        } else {
            System.out.println("No solution found.");
        }
    }
}
