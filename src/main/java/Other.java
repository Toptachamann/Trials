/*
import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Random;

public class Other {
  public static void main(String[] args) {
    double ratio = 0;
    for (int i = 0; i < 100; i++) {
      CompleteGraphGenerator<Integer, DefaultEdge> generator = new CompleteGraphGenerator<>(1000);
      Graph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
            */
/*generator.generateGraph(graph, new VertexFactory<Integer>() {
                int num = 0;

                @Override
                public Integer createVertex() {
                    return num++;
                }
            });*//*

      graph = gen();
      ChordalityInspector<Integer, DefaultEdge> inspector = new ChordalityInspector<>(graph);
      Instant startLexBFS = Instant.now();
      List<Integer> lexBFSOrder = inspector.lexBfs();
      Duration lexBFSDuration = Duration.between(startLexBFS, Instant.now());

      Instant startMCS = Instant.now();
      List<Integer> mcsOrder = inspector.mcs();
      Duration mcsDuration = Duration.between(startMCS, Instant.now());

      System.out.println("LexBFS: " + lexBFSDuration.toString());
      System.out.println("MCS: " + mcsDuration.toString());
      System.out.println(mcsDuration.compareTo(lexBFSDuration) > 0 ? "LexBFS" : "MCS");
      System.out.println(inspector.isPerfectEliminationOrder(lexBFSOrder));
      System.out.println(inspector.isPerfectEliminationOrder(mcsOrder));
      ratio += (double) lexBFSDuration.toNanos() / mcsDuration.toNanos();
    }
    System.out.println("\n" + ratio / 100);
  }

  public static Graph<Integer, DefaultEdge> gen() {
    Graph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
    int vertexBound = 2000;
    for (int i = 0; i < vertexBound; i++) {
      graph.addVertex(i);
    }
    int edgeBound = 1000000;
    Random random = new Random();
    for (int j = 0; j < edgeBound; j++) {
      int u = random.nextInt(vertexBound);
      int v = random.nextInt(vertexBound);
      graph.addEdge(u, v);
    }
    return graph;
  }
}
*/
