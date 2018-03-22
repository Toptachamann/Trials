import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.event.ConnectedComponentTraversalEvent;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListener;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.traverse.LexBreadthFirstIterator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {

  public static final int GIGA = 1000000000;
  private static final int ROOT = -1;

  public static void main(String[] args) {}

  public static void createLink() {

    Path filePath = Paths.get("C:", "File_Manager_Test", "file.txt");
    Path sibling = filePath.resolveSibling("symbolic_link.txt");
    try {
      Files.createLink(sibling, filePath);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void do3() {
    int a = 1 + 1;
    ArrayList<Integer> arrayList = new ArrayList<>();
    for (int i = 0; i < 50; i++) {
      Lock lock = new ReentrantLock();
      Runnable run1 =
          () -> {
            try {
              lock.lock();
              Thread.sleep(200);
              arrayList.add(5);
              System.out.println("5 added");
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              lock.unlock();
            }
          };
      Runnable run2 =
          () -> {
            try {
              Thread.sleep(100);
              lock.lock();
              arrayList.remove(0);
              System.out.println("5 deleted");
            } catch (InterruptedException e) {
              e.printStackTrace();
            } finally {
              lock.unlock();
            }
          };
      Thread t1 = new Thread(run1);
      Thread t2 = new Thread(run2);
      t1.start();
      t2.start();
      try {
        t1.join();
        t2.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      arrayList.clear();
    }
  }

  public static void do1() {
    /*int n = 1000;
    Random random = new Random();
    int[][] arr = new int[n][n];
    int[][] brr = new int[n][n];
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        arr[i][j] = random.nextInt(100);
        brr[i][j] = random.nextInt(100);
      }
    }
    double sum = 0;
    for(int i = 0; i < 10; i++){
      double secondsSeq = (double) seq(arr, brr)/GIGA;
      double secondsPar = (double) par(arr, brr)/GIGA;
      double dif = secondsSeq/secondsPar;
      sum += dif;
      System.out.println(secondsSeq + " seconds in sequence, " + secondsPar + " seconds in parallel");
      System.out.println(dif + " times faster");
    }
    System.out.println("\n" + (sum/10) + " times faster on average");*/
  }

  public static void do2() {
    for (Map.Entry<Object, Object> entry : System.getProperties().entrySet()) {
      Object key = entry.getKey();
      Object value = entry.getValue();
      System.out.print((key instanceof String) ? key : key.getClass().getCanonicalName());
      System.out.print(": ");
      System.out.println((value instanceof String) ? value : value.getClass().getCanonicalName());
    }
  }

  public static long seq(int[][] arr, int[][] brr) {
    int n = arr.length;
    int[][] c = new int[n][n];
    long time1 = System.nanoTime();
    for (int i = 0; i < n; i++) {
      for (int j = 0; j < n; j++) {
        int sum = 0;
        for (int k = 0; k < n; k++) {
          sum += arr[i][k] * brr[k][j];
        }
        c[i][j] = sum;
      }
    }
    long time2 = System.nanoTime();
    return time2 - time1;
  }

  public static long par(int[][] arr, int[][] brr) {
    int n = arr.length;
    int[][] c = new int[n][n];
    int maxProcess = 4;
    long time1 = System.nanoTime();
    CountDownLatch latch = new CountDownLatch(maxProcess);
    for (int p = 0; p < maxProcess; p++) {
      int processId = p;
      Runnable worker =
          () -> {
            int from = (n * processId) / maxProcess;
            int to = (n * (processId + 1)) / maxProcess;
            for (int i = from; i < to; i++) {
              for (int j = 0; j < n; j++) {
                int sum = 0;
                for (int k = 0; k < n; k++) {
                  sum += arr[i][k] * brr[k][j];
                }
                c[i][j] = sum;
              }
            }
            latch.countDown();
          };
      Thread thread = new Thread(worker);
      thread.start();
    }
    try {
      latch.await();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    long time2 = System.nanoTime();
    return time2 - time1;
  }

  public void zuzuzu() {
    Graph<Integer, DefaultEdge> graph =
        new DefaultUndirectedGraph<Integer, DefaultEdge>(DefaultEdge.class);
    graph.addVertex(1);
    graph.addVertex(2);
    graph.addVertex(3);
    graph.addVertex(4);
    LexBreadthFirstIterator<Integer, DefaultEdge> iterator = new LexBreadthFirstIterator<>(graph);
    iterator.addTraversalListener(
        new TraversalListener<Integer, DefaultEdge>() {
          @Override
          public void connectedComponentFinished(ConnectedComponentTraversalEvent e) {
            e.getType();
          }

          @Override
          public void connectedComponentStarted(ConnectedComponentTraversalEvent e) {
            System.out.println(e.getType());
          }

          @Override
          public void edgeTraversed(EdgeTraversalEvent<DefaultEdge> e) {
            System.out.println(e.getEdge());
          }

          @Override
          public void vertexTraversed(VertexTraversalEvent<Integer> e) {
            System.out.println("Vertex traversed " + e.getVertex());
          }

          @Override
          public void vertexFinished(VertexTraversalEvent<Integer> e) {
            System.out.println("Vertex finished " + e.getVertex());
          }
        });
    for (int i = 0; i < 4; i++) {
      System.out.println(iterator.next());
    }
  }

  public <V, E> List<V> mcs(Graph<V, E> graph) {
    VertexList<V> vertexList = new VertexList<>(graph.vertexSet());
    int vertexNum = graph.vertexSet().size();
    List<V> order = new ArrayList<>(vertexNum);
    for (int k = 0; k < vertexNum; k++) {
      V vertex = vertexList.getWithMaxCardinality();
      order.add(vertex);

      for (E edge : graph.edgesOf(vertex)) {
        V opposite = Graphs.getOppositeVertex(graph, edge, vertex);
        if (vertexList.containsVertex(opposite)) {}
      }
    }
    return null;
  }

  public void zuzu() {
    Graph<Integer, String> graph = new DefaultUndirectedGraph<>(String.class);
    graph.addVertex(1);
    graph.addVertex(2);
    graph.addVertex(3);
    graph.addVertex(4);
    System.out.println(graph.addEdge(1, 2));
    System.out.println(graph.addEdge(2, 3));
    System.out.println(graph.addEdge(2, 4));
    String edge = graph.getEdge(2, 1);
    System.out.println(graph.getEdgeSource(edge));
    System.out.println(graph.getEdgeTarget(edge));
  }

  private class VertexList<V> {
    private static final int ROOT = -1;
    int maxCardinality;
    private ArrayList<Integer> prev;
    private ArrayList<V> value;
    private ArrayList<Integer> next;
    private ArrayList<Integer> head;
    private Map<V, Integer> positionMap;

    public VertexList(Set<V> vertices) {
      int size = vertices.size();
      init(size);
      int pos = 0;
      for (V v : vertices) {
        positionMap.put(v, pos++);
        value.add(v);
      }
      for (int i = 0; i < size; i++) {
        prev.add(i - 1);
        next.add(i + 1);
        head.add(ROOT);
      }
      next.set(size - 1, ROOT);
      head.set(0, 0);
      maxCardinality = 0;
    }

    public V getWithMaxCardinality() {
      int headPosition = head.get(maxCardinality);
      V vertex = value.get(headPosition);
      positionMap.remove(vertex);
      if (next.get(headPosition) == ROOT) {
        // this list becomes empty
        head.set(maxCardinality, ROOT);
        do {
          --maxCardinality;
        } while (maxCardinality >= 0 && head.get(maxCardinality) == ROOT);
        return vertex;
      } else {
        // this list doesn't become empty
        remove(headPosition);
        return vertex;
      }
    }

    public void increaseCardinality(V vertex) {
      int pos = positionMap.get(vertex);
    }

    private void remove(int position) {
      value.set(position, null);
      int prevPos = prev.get(position);
      int nextPos = next.get(position);
      if (prevPos != ROOT) {
        next.set(prevPos, nextPos);
      }
      if (nextPos != ROOT) {
        prev.set(nextPos, prevPos);
      }
    }

    public boolean containsVertex(V vertex) {
      return positionMap.containsKey(vertex);
    }

    private void init(int size) {
      prev = new ArrayList<>(size);
      value = new ArrayList<>(size);
      next = new ArrayList<>(size);
      head = new ArrayList<>(size);
      positionMap = new HashMap<>(size);
    }
  }
}
