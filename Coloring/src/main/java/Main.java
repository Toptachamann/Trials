
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import parcs.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        task curtask = new task();
        curtask.addJarFile("Coloring.jar");

        System.out.println("Stage = 1");

        AMInfo info = new AMInfo(curtask, null);
        point p = info.createPoint();
        channel c = p.createChannel();
        p.execute("Runner");

        System.out.println("Stage = 2");


        System.out.println("Stage = 3");

        HashMap<Integer, Integer> colorMap = new HashMap<>();

        DefaultUndirectedGraph<Integer, DefaultEdge> graph = readGraph();
        if(graph == null){
            System.out.println("Can't read graph from local file");
            System.out.println("Using default graph");
            graph = getDefaultGraph();
        }
        c.write(graph);
        c.write(colorMap);
        c.write(1);


        System.out.printf("Graph = %s\n", graph);
        System.out.println("Waiting for result...");

        long start = System.nanoTime();

        int code = c.readInt();
        if (code == 0) {
            System.out.println("No coloring available (strange)");
        } else {
            Map<Integer, Integer> resultColoring = (HashMap<Integer, Integer>) c.readObject();
            System.out.println("Received final coloring");
            for (Map.Entry<Integer, Integer> color : resultColoring.entrySet()) {
                System.out.printf("%d -> %d\n", color.getKey(), color.getValue());
            }
        }

        long end = System.nanoTime();

        System.out.printf("Time = %.4f s\n", (end - start) / 1e9);
        curtask.end();
    }

    private static DefaultUndirectedGraph<Integer, DefaultEdge> getDefaultGraph() {
        DefaultUndirectedGraph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultEdge.class);
        Graphs.addEdgeWithVertices(graph, 1, 2);
        Graphs.addEdgeWithVertices(graph, 1, 3);
        Graphs.addEdgeWithVertices(graph, 2, 3);
        return graph;
    }

    private static DefaultUndirectedGraph<Integer, DefaultEdge> readGraph() {
        try (BufferedReader reader = new BufferedReader(new FileReader("graph.txt"))) {
            DefaultUndirectedGraph<Integer, DefaultEdge> graph = new DefaultUndirectedGraph<>(DefaultWeightedEdge.class);
            String s = reader.readLine();
            int m = Integer.parseInt(s.split("\\s+")[1]);
            for (int i = 0; i < m; i++) {
                s = reader.readLine();
                String[] tokens = s.split("\\s+");
                Graphs.addEdgeWithVertices(graph, Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1]));
            }
            return graph;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
