import java.util.Stack;

public class DepthFirstSolver extends GenericGraphSolver {
    @Override
    public <A, S> Node<A, S> solve(Problem<A, S> problem) {
        return solve(problem, new Stack<>());
    }
}
