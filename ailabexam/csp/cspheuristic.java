package ailabexam.csp;

import java.util.*;

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
    Map<Variable, List<Integer>> domainValues;

    public CSPSolver() {
        variables = new ArrayList<>();
        constraints = new ArrayList<>();
        domainValues = new HashMap<>();
    }

    public void addVariable(Variable variable) {
        variables.add(variable);
        domainValues.put(variable, new ArrayList<>(variable.domain));
    }

    public void addConstraint(Constraint constraint) {
        constraints.add(constraint);
    }

    public int[] solve() {
        int[] assignment = new int[variables.size()];
        boolean isSolved = backtrack(assignment);
        if (isSolved) {
            return assignment;
        } else {
            return null;
        }
    }

    private boolean backtrack(int[] assignment) {
        if (isComplete(assignment)) {
            return true;
        }

        Variable var = selectUnassignedVariable(assignment);
        List<Integer> orderedValues = orderDomainValues(var, assignment);

        for (int value : orderedValues) {
            if (isValueConsistent(var, value, assignment)) {
                assignment[variables.indexOf(var)] = value;
                if (backtrack(assignment)) {
                    return true;
                }
                assignment[variables.indexOf(var)] = 0;
            }
        }
        return false;
    }

    private boolean isComplete(int[] assignment) {
        for (int value : assignment) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    private Variable selectUnassignedVariable(int[] assignment) {
        Variable minVar = null;
        int minRemainingValues = Integer.MAX_VALUE;

        for (Variable var : variables) {
            if (assignment[variables.indexOf(var)] == 0) {
                int remainingValues = domainValues.get(var).size();
                if (remainingValues < minRemainingValues) {
                    minRemainingValues = remainingValues;
                    minVar = var;
                }
            }
        }
        return minVar;
    }

    private List<Integer> orderDomainValues(Variable var, int[] assignment) {
        List<Integer> values = domainValues.get(var);

        values.sort((v1, v2) -> {
            int count1 = countLeastConstrainingValues(var, v1, assignment);
            int count2 = countLeastConstrainingValues(var, v2, assignment);
            return Integer.compare(count1, count2);
        });

        return values;
    }

    private int countLeastConstrainingValues(Variable var, int value, int[] assignment) {
        int count = 0;
        assignment[variables.indexOf(var)] = value;

        for (Constraint constraint : constraints) {
            Variable otherVar = (constraint.var1 == var) ? constraint.var2 : constraint.var1;
            if (assignment[variables.indexOf(otherVar)] == 0) {
                for (int val : domainValues.get(otherVar)) {
                    if (isValueConsistent(otherVar, val, assignment)) {
                        count++;
                    }
                }
            }
        }
        assignment[variables.indexOf(var)] = 0;
        return count;
    }

    private boolean isValueConsistent(Variable var, int value, int[] assignment) {
        assignment[variables.indexOf(var)] = value;

        for (Constraint constraint : constraints) {
            Variable otherVar = (constraint.var1 == var) ? constraint.var2 : constraint.var1;
            if (assignment[variables.indexOf(otherVar)] != 0 &&
                    !constraint.isSatisfied(value, assignment[variables.indexOf(otherVar)])) {
                assignment[variables.indexOf(var)] = 0;
                return false;
            }
        }
        assignment[variables.indexOf(var)] = 0;
        return true;
    }
}

public class cspheuristic {
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

