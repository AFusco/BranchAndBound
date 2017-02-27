import java.util.Random;

/**
 * @author Alessandro Fusco
 * @author Eduardo Ortega
 */
public class Main {

    private final static Random random = new Random(System.currentTimeMillis());

    public static void main(String[] args) {
        DirectedGraph g = new DirectedGraph(8);

        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 3);
        g.addEdge(0, 3, 2);
        g.addEdge(1, 4, 5);
        g.addEdge(1, 6, 3);
        g.addEdge(2, 4, 4);
        g.addEdge(2, 5, 3);
        g.addEdge(3, 5, 2);
        g.addEdge(3, 6, 7);
        g.addEdge(4, 7, 4);
        g.addEdge(5, 7, 1);
        g.addEdge(6, 7, 1);

        PathFindProblem p = new PathFindProblem(g, 0, 7);


        Solver[] c = new Solver[]{
                new BestFirstSolver(),
                new DepthFirstSolver(),
                new BreadthFirstSolver(),
                new BranchAndBound(p)
        };


        System.out.format("%-20s %-18s %-13s %-12s     %s\n",
                "solving_strategy",
                "explored_paths",
                "path_depth",
                "path_weight",
                "result_path");

        System.out.println("");

        for (Solver cl : c) {
            new Thread(new TestRun(cl, p)).start();
        }
    }

    static class TestRun implements Runnable {

        Problem p;
        Solver s;

        public <T extends Solver> TestRun(T s, Problem p) {
            this.s = s;
            this.p = p;
        }

        @Override
        public void run() {
            Node x = s.solve(p);

            System.out.format("%-20s % 10d % 16d % 10.2f               %s\n",
                    s.getClass().getName(),
                    s.getExploredNodes(),
                    x.getDepth(),
                    x.getPathCost(),
                    x.pathString());
        }

    }

}

