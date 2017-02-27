/**
 * This interface defines a solver for a generic problem.
 */
public interface Solver {

    /**
     * Find the solution of a generic problem
     * @param problem   The problem that must be solved
     * @param <A>       The type that represents the action needed to take a solution
     * @param <S>       The type that represents the state of a partial solution
     */
    <A, S> Node<A, S> solve(Problem<A, S> problem);

    /**
     * Return the number of nodes explored in the expansion graph.
     */
    int getExploredNodes();
}
