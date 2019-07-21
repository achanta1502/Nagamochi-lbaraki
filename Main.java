package project;

import java.util.Random;

public class Main {

    public static void main(String[] args) {

    System.out.println("Testing min cut is 3");
    Graph graph = new Graph(4, 5);
    int[][] temp = new int[][]{
        {0, 3, 1, 2},
        {3, 0, 0, 4},
        {1, 0, 0, 2},
        {2, 4, 2, 0}
    };
    graph.setGraph(temp);
    NagamochiIbaraki ni = new NagamochiIbaraki(graph);
    System.out.println(graph.toString());
    //The minimum cut is 3. Using this for test case
    System.out.println("Minimum cut for the graph is " + ni.edgeConnectivity());

    for(int e = 50; e < 550; e = e + 5) {
        graph = new Graph(25, e);
        System.out.println("Edge selected is " + e);
        graph.createGraph();
        ni = new NagamochiIbaraki(graph);
        //System.out.println(graph.toString());
        int min_cut = ni.edgeConnectivity();
        if (min_cut != 0)
          System.out.println("Minimum cut for the graph is " + min_cut);
        else
          System.out.println("Graph is disconnected");
        System.out.println();
      }
      }
}
