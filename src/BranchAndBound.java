import java.util.*;


public class BranchAndBound implements Optimizer {

    /*
     * Keep track if the problem is solvable or not.
     */
    private boolean solvable = true;

    /*
     * The problem that must be optimized
     */
    private Problem problem;

    /*
     * The upper bound over which a branch is discarded
     */
    private double bound = Double.POSITIVE_INFINITY;

    /*
     * Number of explored nodes
     */
    private int exploredNodes = 0;

    /*
     * Current local solution, whose total cost is *this.bound*
     */
    Node localSolution = null;


    /**
     * Constructor for a branch and bound optimizer.
     * The initial bound is set through a DepthFirst search, by default.
     *
     * @param problem The problem that must be optimized
     * @param <A>     Action type
     * @param <S>     State type
     */
    public <A, S> BranchAndBound(Problem<A, S> problem) {
        this(problem, new DepthFirstSolver());
    }

    /**
     * Constructor for a branch and bound optimizer.
     * The initial bound is found using the localSolver passed.
     * The number of nodes explored with the local Solver is added to the total.
     *
     * @param problem     The problem that must be optimized
     * @param localSolver The solver used to find the first bound
     * @param <A>         Action type
     * @param <S>         State type
     */
    public <A, S> BranchAndBound(Problem<A, S> problem, Solver localSolver) {
        this(problem, localSolver.solve(problem));
        this.exploredNodes += localSolver.getExploredNodes();
    }

    /**
     * Default constructor for a branch and bound optimizer.
     * It allows to set the initial lower bound.
     *
     * @param problem The problem that must be optimized
     * @param bound   The lower bound used for exploring the tree
     * @param <A>     Action type
     * @param <S>     State type
     */
    public <A, S> BranchAndBound(Problem<A, S> problem, double bound) {
        this.problem = problem;
        this.bound = bound;
    }

    /**
     * Constructor for a branch and bound optimizer, that allows to specify
     * a specific initial local solution. The cost of this local solution will
     * be used as the initial bound.
     *
     * @param problem           The problem that must be optimized
     * @param bestLocalSolution The initial local solution
     * @param <A>               Action type
     * @param <S>               State type
     */
    public <A, S> BranchAndBound(Problem<A, S> problem, Node<A, S> bestLocalSolution) {
        this(problem, bestLocalSolution.getPathCost());
        if (bound == Double.POSITIVE_INFINITY) {
            //Solver could not converge
            this.solvable = false;
        } else {
            this.localSolution = bestLocalSolution;
        }
    }


    /**
     * Method used to solve the problem
     *
     * @param problem The problem that must be solved
     * @param <A>     Action type
     * @param <S>     State type
     * @return The final Node of the path, through which the whole sequence of <Action, State> can be reconstructed
     */
    @Override
    public <A, S> Node<A, S> solve(Problem<A, S> problem) {

        // Check if the problem is solvable. If not, return a path with infinite cost.
        if (!solvable) {
            return new Node<>(problem.getInitialState(), null, null, Double.POSITIVE_INFINITY);
        }

        // Open list, keeps track of the nodes that need to be explored
        Queue<Node<A, S>> fringe = new PriorityQueue<>();

        // Current node being explored.
        Node<A, S> currentNode;

        // The current best solution found.
        Node<A, S> bestSolution = (Node<A, S>) localSolution;

        // The states of the nodes in the graph that have already been visited.
        Set<Node<A, S>> closed = new HashSet<>();

        fringe.add(new Node(problem.getInitialState()));

        while (!fringe.isEmpty()) {


            // Explore first node in the list, and set it as visited.
            // Increment the number of explored nodes.
            currentNode = fringe.remove();
            closed.add(currentNode);
            exploredNodes++;

            // Skip if over bound!
            if (currentNode.getPathCost() >= bound) {
                continue;
            }

            if (problem.isGoal(currentNode.getState())) {
                // If current node is goal, update local best solution solution and lower bound
                // A cost check is made, because the bound may have changed after the nodes had
                // been added to the open list.
                double currentCost = currentNode.getPathCost();
                if (currentCost < bound) {
                    bestSolution = currentNode;
                    bound = currentCost;
                }

            } else {
                //Branch
                for (Node<A, S> n : currentNode.expand(problem)) {
                    //Bound
                    if (n.getPathCost() < bound && !closed.contains(n)) {
                        fringe.add(n);
                    }
                }
            }
        }

        if (bestSolution == null || bound == Double.POSITIVE_INFINITY) {
            return new Node<>(problem.getInitialState(), null, null, Double.POSITIVE_INFINITY);
        } else {
            return bestSolution;
        }
    }

    @Override
    public int getExploredNodes() {
        return exploredNodes;
    }
}
