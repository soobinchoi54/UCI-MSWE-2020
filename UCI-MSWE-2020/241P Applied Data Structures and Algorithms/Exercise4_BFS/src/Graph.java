import java.util.LinkedList;
import java.util.Queue;

public class Graph {

    int[][] adjMatrix;
    int start = 0;
    int vertex;
    boolean[] visited;

    public Graph(int[][] graph) {
        int i, j;

        vertex = graph.length;
        adjMatrix = new int[vertex][vertex];
        visited = new boolean[vertex];

        for (i = 0; i < vertex; i++)
            for (j = 0; j < vertex; j++)
                adjMatrix[i][j] = graph[i][j];
    }

    public void bfs() {
        // BFS uses Queue data structure
        // Nodes will be visited by FIFO queue
        Queue<Integer> q = new LinkedList<>();

        q.add(start);
        visited[start] = true; // mark first node as visited
        printNode(start);

        // while elements exist in the queue
        while (!q.isEmpty()) {
            int n = q.peek();
            int child = undiscovered(n); // if there is no unvisited child node left, return -1

            if (child != -1) { // discovered unvisited child node
                visited[child] = true; // mark child node as visited
                printNode(child);
                q.add(child);
            } else {
                q.remove();
            }
        }
        clearDiscovered(); // clear visited nodes
    }

    public int undiscovered(int n) {
        int j;
        for (j = 0; j < vertex; j++) {
            if (adjMatrix[n][j] > 0) {
                if (!visited[j])
                    return (j);
            }
        }
        return (-1);
    }

    public void clearDiscovered() {
        int i;
        for (i = 0; i < visited.length; i++)
            visited[i] = false;
    }

    public void printNode(int n) {
        System.out.print((char) (n + 65) + " ");
    }

    public static void main(String[] args) {
        int[][] graph = {
                //A  B  C  D  E  F  G  H  I  J
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 0},  // A
                {1, 0, 1, 1, 1, 0, 0, 0, 0, 0},  // B
                {0, 1, 0, 0, 1, 1, 0, 0, 0, 0},  // C
                {1, 1, 0, 0, 1, 0, 1, 0, 0, 0},  // D
                {0, 1, 1, 1, 0, 1, 1, 1, 0, 0},  // E
                {0, 0, 1, 0, 1, 0, 0, 1, 0, 0},  // F
                {0, 0, 0, 1, 1, 0, 0, 1, 1, 1},  // G
                {0, 0, 0, 0, 1, 1, 1, 0, 0, 1},  // H
                {1, 0, 0, 0, 0, 0, 1, 0, 0, 1},  // I
                {0, 0, 0, 0, 0, 0, 1, 1, 1, 0}}; // J

        Graph gBFS = new Graph(graph);
        gBFS.bfs();
    }
}
