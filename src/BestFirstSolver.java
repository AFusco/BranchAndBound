import java.util.PriorityQueue;

/**
 * Solver implementing BestFirstSearch algorithm, to find a local solution.
 */
public class BestFirstSolver extends GenericGraphSolver implements Solver {
    @Override
    public <A, S> Node<A, S> solve(Problem<A, S> problem) {
        return solve(problem, new PriorityQueue<>());
    }
}
