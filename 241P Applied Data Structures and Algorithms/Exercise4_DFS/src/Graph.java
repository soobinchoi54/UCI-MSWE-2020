import java.util.Stack;

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

    public void dfs() {
        // DFS uses Stack data structure
        // Nodes will be visited by LIFO stack
        Stack<Integer> s = new Stack<>();  // Create a stack

        clearDiscovered(); // Set all visited to 0
        s.push(start); // Start the "to discovered" at node start = 0

        while (!s.isEmpty()) {
            int nextNode; // Next node to visit
            int child;

            nextNode = s.pop();
            if (!visited[nextNode]) {
                visited[nextNode] = true; // Mark node as visited
                printNode(nextNode);
                for (child = vertex - 1; child >= 0; child--) {
                    if (adjMatrix[nextNode][child] > 0 && !visited[child]) { // If an edge exists and neighbor node has not been visited
                        s.push(child); // Push unvisited neighbor node
                    }
                }
            }
        }
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

        Graph gDFS = new Graph(graph);
        gDFS.dfs();

    }
}
