/**
 * @author afusco
 * @date 23/02/17
 */

/**
 * This is the abstract class that needs to be extended to define a combinatorial problem.
 * A problem has an initial state and a goal state.
 * Each possible state can lead to other states through an action.
 * While the state is important to define each node in the tree, the actions can be ignored if not needed.
 * @param <Action>  The type representing an action.
 * @param <State>   The type representing a state.
 */
public abstract class Problem<Action, State> {

    protected State goal;
    protected State start;

    public Problem(State start, State goal) {
        this.start = start;
        this.goal = goal;
    }

    /**
     * Return true if the state is the final goal.
     * @param state
     * @return
     */
    public boolean isGoal(State state) {
        return state.equals(goal);
    }

    /**
     * Return a collection of Pair<Action, State>, containing all the adjacent states of the specified state.
     * @param state
     * @return
     */
    public abstract Pair<Action, State>[] expand(State state);

    /**
     * Return the total cost of the path between two specified states, through a certain action, given an initial cost
     * of previousCost
     * @param previousCost  The cost needed to get to state "from"
     * @param from          The first state
     * @param action        The action required to go from state "from" to state "to"
     * @param to            The final state
     * @return the total cost of the path.
     */
    public abstract double pathCost(double previousCost, State from, Action action, State to);

    /**
     * Return the initial state of the problem
     */
    public State getInitialState() {
        return this.start;
    }

}
