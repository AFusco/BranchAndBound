/**
 * This class defines a node in the expanded tree of a problem.
 * It allows to backtrack all the steps required to get to the current state.
 *
 * @param <Action> Type for describing an action to get to a new node
 * @param <State>  Type for describing a local state in a problem
 */
public class Node<Action, State> implements Comparable<Node<Action, State>> {

    /*
     * Keep track of the parent node for backtracking..
     */
    protected Node<Action, State> parent;

    /*
     * Current state
     */
    protected State state;

    /*
     * Action required to get to current state
     */
    protected Action action;

    /*
     * Distance from parent node
     */
    protected int depth;

    /*
     * Total path cost to get to current node.
     */
    protected double pathCost;

    /**
     * Constructor for a node.
     *
     * @param state    The state held in the node.
     * @param parent   The parent node.
     * @param action   The action required to get to current node.
     * @param pathCost The total cost of the path required to get to current node from root.
     */
    public Node(State state, Node parent, Action action, double pathCost) {
        this.state = state;
        this.parent = parent;
        this.action = action;
        this.pathCost = pathCost;

        if (this.parent != null) {
            this.depth = parent.depth + 1;
        } else {
            this.depth = 0;
        }
    }

    public Node(State state, Node parent, Action action) {
        this(state, parent, action, 0);
    }

    public Node(State state, Node parent) {
        this(state, parent, null, 0);
    }

    public Node(State state) {
        this(state, null, null, 0);
    }

    /**
     * The path required to get to current node from the root node.
     *
     * @return An array containing all the nodes in the path, from the last to the first.
     */
    public Node<Action, State>[] path() {
        Node<Action, State>[] path = (Node<Action, State>[]) new Node[depth + 1];
        Node node = this;

        int i = 0;

        path[i++] = this;
        while (node.parent != null) {
            path[i++] = node.parent;
            node = node.parent;
        }

        return path;
    }

    /**
     * Get the nodes that are adjacent to the current node.
     *
     * @param problem
     * @return An array of the adjacent nodes.
     */
    public Node<Action, State>[] expand(Problem<Action, State> problem) {
        Pair<Action, State>[] nextStates = problem.expand(this.state);
        Node<Action, State>[] nextNodes = (Node<Action, State>[]) new Node[nextStates.length];

        for (int i = 0; i < nextStates.length; i++) {
            Action action = nextStates[i].x;
            State nextState = nextStates[i].y;

            nextNodes[i] = new Node<>(nextState, this, action,
                    problem.pathCost(this.pathCost, this.state, action, nextState));
        }

        return nextNodes;
    }

    /**
     * Get the state associated to this node.
     *
     * @return
     */
    public State getState() {
        return state;
    }

    /**
     * Get the last action required to get to this node.
     *
     * @return
     */
    public Action getAction() {
        return action;
    }

    /**
     * Get the distance from the root node.
     *
     * @return
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Get the total path cost required to get to this node from the root node.
     *
     * @return
     */
    public double getPathCost() {
        return pathCost;
    }

    /**
     * Return a string representing the path
     */
    public String pathString() {

        Node<Action, State>[] path = this.path();

        String out = path[path.length-1].state.toString();
        for (int i = path.length - 2; i >= 0; i--) {
            out += " -> " + path[i].state;
        }

        return out;
    }

    public String toString() {
        return String.format("cost: % 8.2f\tdepth: % 2d",
                getPathCost(),
                getDepth());
    }

    public String fullPathString() {
        return toString() + "\t" + pathString();

    }

    public void printPath() {
        System.out.println(pathString());
    }

    @Override
    public int compareTo(Node<Action, State> o) {
        int path_cmp = Double.compare(this.getPathCost(), o.getPathCost());
        return path_cmp != 0 ? path_cmp : Integer.compare(this.getDepth(), o.getDepth());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node<?, ?> node = (Node<?, ?>) o;

        if (depth != node.depth) return false;
        if (Double.compare(node.pathCost, pathCost) != 0) return false;
        return state != null ? state.equals(node.state) : node.state == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = state != null ? state.hashCode() : 0;
        result = 31 * result + depth;
        temp = Double.doubleToLongBits(pathCost);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
