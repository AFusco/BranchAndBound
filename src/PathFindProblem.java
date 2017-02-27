import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author afusco
 * @date 27/02/17
 */
public class PathFindProblem extends Problem<Integer, Integer> {

    private DirectedGraph g;

    public PathFindProblem(DirectedGraph g, int from, int to) {
        super(from, to);
        this.g = g;
    }

    @Override
    public Pair<Integer, Integer>[] expand(Integer integer) {

        List<Pair<Integer, Integer>> next = new ArrayList<>();
        for (Map.Entry<Integer, Double> e : g.edgesFrom(integer).entrySet()) {
            next.add(new Pair<>(null, e.getKey()));
        }

        return next.toArray(new Pair[0]);
    }

    @Override
    public double pathCost(double previousCost, Integer from, Integer integer, Integer to) {
        try {
            return g.edgesFrom(from).get(to) + previousCost;
        } catch (Exception e) {
            throw new RuntimeException("Invalid from-to");
        }
    }
}
