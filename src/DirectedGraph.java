import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author afusco
 * @date 24/02/17
 */
class DirectedGraph {

    /*
     * A map from the vertex to outgoing edge.
     * An outgoing edge is represented as a tuple of vertex and the edge length
     */
    protected final Map<Integer, Map<Integer, Double>> graph;

    /*
     * A map to keep track of the inDegree of a node
     */
    private final Map<Integer, AtomicInteger> inDegree;


    private int size = 0;
    private int edges = 0;

    public DirectedGraph() {
        this.graph = new HashMap<>();
        this.inDegree = new HashMap<>();
    }

    public DirectedGraph(int size) {
        this();
        for (int i = 0; i < size; i++) {
            addVertex(i);
        }
    }

    /**
     * Adds a new vertex to the graph.
     *
     * @param v the vertex to be added
     */
    public void addVertex(int v) {

        if (graph.containsKey(v))
            throw new IllegalArgumentException("Vertex is already contained in graph");

        // Set initial adjacency map
        graph.put(v, new HashMap<>());

        // Increase size of graph
        size++;

        // Set initial inDegree
        inDegree.put(v, new AtomicInteger(0));
    }

    /**
     * Adds an edge from source node to destination node.
     * There can only be a single edge from source to node.
     * Adding additional edge would overwrite the value
     *
     * @param from      first node to be in the edge
     * @param to        the second node to be second node in the edge
     * @param weight    the edge's weight.
     */
    public void addEdge(int from, int to, double weight) {
        if (!graph.containsKey(from) || !graph.containsKey(to)) {
            throw new NoSuchElementException("Source and Destination both should be part of the part of graph");
        }

        graph.get(from).put(to, weight);
        inDegree.get(from).incrementAndGet();

        edges++;
    }

    /**
     * Returns immutable view of the edges
     *
     * @param v     the vertex whose outgoing edge needs to be returned
     * @return An immutable view of edges leaving that vertex
     */
    public Map<Integer, Double> edgesFrom(int v) {

        if (!graph.containsKey(v))
            throw new NoSuchElementException("The vertex must be part of the graph.");

        return Collections.unmodifiableMap(graph.get(v));
    }


    /**
     * Returns an iterator that can traverse the nodes of the graph
     *
     * @return an Iterator.
     */
    public Iterator<Integer> iterator() {
        return graph.keySet().iterator();
    }

    /**
     * Returns the number of edges leaving the vertex
     *
     * @param vertex the vertex whose edges' quantity needs to be returned
     * @return The number of edges leaving the vertex
     */
    public int outDegree(int vertex) {
        return graph.get(vertex).size();
    }

    /**
     * Returns the number of edges entering the vertex
     *
     * @param vertex the vertex whose edges' quantity needs to be returned
     * @return The number of edges leaving the vertex
     */
    public int inDegree(int vertex) {
        return inDegree.get(vertex).get();
    }

    /**
     * Returns the number of vertices in the graph
     */
    public int size() {
        return this.size;
    }

    /**
     * Returns the number of edges in the graph
     */
    public int edges() {
        return this.edges;
    }
}

