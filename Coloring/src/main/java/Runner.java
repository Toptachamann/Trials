
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import parcs.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Runner implements AM {
    private static int[] colors = {0, 1, 2, 3};

    @Override
    public void run(AMInfo info) {

        DefaultUndirectedGraph<Integer, DefaultEdge> graph = (DefaultUndirectedGraph<Integer, DefaultEdge>) info.parent.readObject();
        HashMap<Integer, Integer> colorMap = (HashMap<Integer, Integer>) info.parent.readObject();
        int currentVertex = info.parent.readInt();

        System.out.printf("Current vertex = %d\n", currentVertex);
        System.out.printf("Current color map = %s\n", colorMap);

        if (currentVertex > graph.vertexSet().size()) {
            info.parent.write(1);
            info.parent.write(colorMap);
        } else {
            List<channel> channels = new ArrayList<>();
            for (int color : colors) {
                if (canUse(colorMap, graph, currentVertex, color)) {
                    colorMap.put(currentVertex, color);

                    point p = info.createPoint();
                    channel c = p.createChannel();
                    p.execute("Runner");

                    c.write(graph);
                    c.write(colorMap);
                    c.write(currentVertex + 1);

                    channels.add(c);

                    colorMap.remove(currentVertex);
                }
            }

	    HashMap<Integer, Integer> resMap = null;
	    int finalRes = 0;

            for (channel c : channels) {
                int res = c.readInt();
                if (res > 0) {
		    finalRes = 1;
                    resMap = (HashMap<Integer, Integer>) c.readObject();
                }
            }
	    if(finalRes == 0) {
	            info.parent.write(0);
	    }else{
		    info.parent.write(1);
		    info.parent.write(resMap);
	    }
        }
    }

    private boolean canUse(Map<Integer, Integer> colorMap, Graph<Integer, DefaultEdge> graph, int vertex, int color) {
        for (int neighbor : Graphs.neighborListOf(graph, vertex)) {
            if (colorMap.getOrDefault(neighbor, -1) == color) {
                return false;
            }
        }
        return true;
    }
}
