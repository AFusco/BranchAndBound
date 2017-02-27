import java.util.ArrayDeque;
import java.util.LinkedList;

/**
 * Solver that tries to find a solution exploring the graph breadth-first
 */
public class BreadthFirstSolver extends GenericGraphSolver implements Solver {
    @Override
    public <A, S> Node<A, S> solve(Problem<A, S> problem) {
        return solve(problem, new ArrayDeque<>());
    }
}
