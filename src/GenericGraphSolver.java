import java.util.*;

public abstract class GenericGraphSolver implements Solver {

    private int exploredNodes = 0;


    public <A, S> Node<A, S> solve(Problem<A, S> problem, Collection<Node<A, S>> fringe) {
        Node<A, S> node;
        Set<S> closed = new HashSet<>();

        fringe.add(new Node<>(problem.getInitialState()));

        while (!fringe.isEmpty()) {
            node = Utils.pop(fringe);
            closed.add(node.getState());
            exploredNodes++;
            if (problem.isGoal(node.getState())) {
                return node;
            }

            for (Node<A, S> n : node.expand(problem)) {
                if (!closed.contains(n.getState())) {
                    fringe.add(n);
                }
            }
        }

        // Path not found, return infinity cost
        return new Node<>(problem.getInitialState(), null, null, Double.POSITIVE_INFINITY);
    }

    public int getExploredNodes() {
        return exploredNodes;
    }
}
