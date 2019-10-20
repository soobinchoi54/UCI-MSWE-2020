import java.util.ArrayList;

public class ConvertGraph {

    // Adjacency Matrix to Adjacency List (n * m)
    public static ArrayList[] MatrixtoList(int[][] matrix) {
        // initalize a new arraylist for converted matrix
        int n = matrix.length;
        int index;
        ArrayList<Integer>[] list = new ArrayList[n];
        for (index = 0; index < matrix.length; index++) {
            list[index] = new ArrayList<>();
        }
        // iterate row i from matrix
        for (int i = 0; i < matrix.length; i++) {
            // iterate item along the row for each row (aka column j)
            for (int j = 0; j < matrix.length; j++) {
                // if value at (i,j) is 1, there is an edge
                if (matrix[i][j] == 1) {
                    list[i].add(j);
                }
            }
        }

        // print converted list
        System.out.println("Adjacency Matrix to Adjancency List:");
        for (int i = 0; i < matrix.length; i++) {
            System.out.print("Vertex " + i + " is connected to:");
            System.out.print(list[i]);
            System.out.println();
        }
        System.out.println("Time complexity: O(n * n) assuming n vertices");
        System.out.println();
        return list;
    }

    // Adjacency List to Incidence Matrix (n * m)
    public static int[][] createMatrix(ArrayList<Integer>[] list) {
        // initialize matrix M with (vertex, edge)
        int[][] M = new int[4][4];
        int vertex;
        int n = M.length; // 4

        for (vertex = 0; vertex < n; vertex++) {
            // System.out.println(list[vertex]);
            for (int j = 0; j < list[vertex].size(); j++) {
                int edge = list[vertex].get(j);
                // System.out.print(list[vertex].get(j) + " ");
                // System.out.print(M[vertex][j] + " ");
                if (edge >= 0) {
                    M[vertex][edge] = 1;
                } else {
                    M[vertex][edge] = 0;
                }
            }
        }

        // print converted matrix
        System.out.println("Adjancency List to Incidence Matrix: ");
        for (int v = 0; v < vertex; v++) {
            for (int e = 0; e < vertex; e++) {
                System.out.print(M[v][e] + "  ");
            }
            System.out.println();
        }
        System.out.println("Time complexity: O(n * m) assuming n vertices and m edges");
        System.out.println();
        return M;
    }

    // Incidence Matrix to Adjacency List (n * m)
    public static ArrayList[] IncidencetoList(int[][] incidenceMatrix) {
        // initalize a new arraylist for incidence matrix
        int n = incidenceMatrix.length;
        int index;
        ArrayList<Integer>[] list = new ArrayList[n];
        for (index = 0; index < incidenceMatrix.length; index++) {
            list[index] = new ArrayList<>();
        }
        // iterate row i from matrix
        for (int i = 0; i < incidenceMatrix.length; i++) {
            // iterate item along the row for each row (aka column j)
            for (int j = 0; j < incidenceMatrix.length; j++) {
                // if value at (i,j) is 1, there is an edge
                if (incidenceMatrix[i][j] == 1) {
                    list[i].add(j);
                }
            }
        }

        // print converted list
        System.out.println("Incidence Matrix to Adjancency List:");
        for (int i = 0; i < incidenceMatrix.length; i++) {
            System.out.print("Vertex " + i + " is connected to:");
            System.out.print(list[i]);
            System.out.println();
        }
        System.out.println("Time complexity: O(n * n) assuming n vertices");
        return list;
    }

    public static void main(String[] args) {
        // initialize integer matrix 4 by 4
        int[][] matrix = new int[4][4];
        matrix[0] = new int[]{0, 1, 1, 0};
        matrix[1] = new int[]{1, 0, 0, 1};
        matrix[2] = new int[]{1, 0, 0, 1};
        matrix[3] = new int[]{0, 1, 1, 0};

        // execute all conversion methods
        IncidencetoList(createMatrix(MatrixtoList(matrix)));


    }

}
