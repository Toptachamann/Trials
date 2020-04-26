
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import parcs.*;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        task curtask = new task();
        curtask.addJarFile("Coloring.jar");

        AMInfo info = new AMInfo(curtask, null);
        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("Runner");

        DefaultUndirectedGraph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(graph, 1, 2);
        Graphs.addEdgeWithVertices(graph, 1, 3);
        Graphs.addEdgeWithVertices(graph, 2, 3);

        HashMap<Integer, Integer> colorMap = new HashMap<>();
        c.write(graph);
        c.write(colorMap);
        c.write(1);
        // System.out.printf("Sent number %d\n", number);
        System.out.println("Waiting for result...");

        long start = System.nanoTime();

        Map<Integer, Integer> resultColoring = (HashMap<Integer, Integer>) c.readObject();
        System.out.println("Received final coloring");
        for (Map.Entry<Integer, Integer> color : resultColoring.entrySet()) {
            System.out.printf("%d -> %d\n", color.getKey(), color.getValue());
        }

        long end = System.nanoTime();

        System.out.printf("Time = %.4f s\n", (end - start) / 1e9);
        curtask.end();
    }

}
