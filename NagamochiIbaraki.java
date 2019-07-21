package project;

import java.util.Random;

public class NagamochiIbaraki {
  private Graph graph;
  //last two nodes in maximum adjacency temp store
  private int first, second;

  private int totalSum = 0;

    protected NagamochiIbaraki(Graph graph) {
        this.graph = graph;
    }

    //calculates edge connectivity
    protected int edgeConnectivity() {
      try {
        //we need to stop as there are only
        //two nodes
        if (graph.nodes() == 2) {
          return graph.getValue(0, 1);
        }
        // edge connectivity between two sets of nodes
        int totalAB = maximumAdjacency();

        // If the toal is Zero, graph is disconnected
        if (totalAB == 0) {
          System.out.println("Graph is disconnected");
          return 0;
        } else {
          //Merging the two sets of nodes, therefore we get a contracted graph
          mergeGraph();
          //System.out.println("New Graph (Contracted Graph): ");
          //Gab.printGraph();
          totalSum = Math.min(totalAB, edgeConnectivity());
        }

        return totalSum;
      }
      catch (Exception e) {
        return 0;
      }
    }

    //maximum adjacenecy ordering between two vertices
    private int maximumAdjacency() {
        try {
          int total = 0;
          Random random = new Random();
          //Store the order of nodes
          int[] temp_store = new int[graph.nodes()];

          //First you need to select the random node
          temp_store[0] = random.nextInt(graph.nodes());

          //Loop through remaining nodes to find the ordering
          for (int i = 1; i < temp_store.length; i++) {
            //Node to be selected for temp_store
            int tempstore_node = -1;
            int sum = 0;
            //Node to be selected
            int temp_node = 0;
            while (temp_node < temp_store.length) {
              int temp_sum = 0;
              boolean flag = false;

              for (int j = 0; j < i; j++) {
                //If the temp node is in temp_store,
                //we need to skip it as it is already
                //processed and in temp_store
                if (temp_node == temp_store[j])
                  flag = true;
              }

              //find the total edges at the vertex
              if (!flag) {
                int k = 0;
                while (k < i) {
                  temp_sum = temp_sum + graph.getValue(temp_node, temp_store[k]);
                  k++;
                }
              }

              //If the sum is greater than sum,
              //Need to save it to sum and add the node
              //to store node as it has more edges connections
              if (temp_sum > sum) {
                sum = temp_sum;
                tempstore_node = temp_node;
              }
              temp_node++;
            }

            //It means graph is disconnected as there is some break
            if (temp_node == -1) {
              return 0;
            }

            // we add the node to store with maximum edges
            temp_store[i] = tempstore_node;

          }
          //Last Node will me merged and last second will be used
          //for evaluating new degrees
          first = temp_store[temp_store.length - 1];
          second = temp_store[temp_store.length - 2];
          //Total adjacency for minimun edge connectivity node
          for (int l = 0; l < temp_store.length; l++) {
            total = total + graph.getValue(first, l);
          }
          return total;
        }
        catch (Exception e) {
          return 0;
        }
    }

    //Changing the degrees as we are merging nodes
    private void changeDegrees(int[][] tempGraph) {
      for (int i = 0; i < tempGraph.length; i++) {
        if (i != second) {
          tempGraph[second][i] = tempGraph[second][i] + tempGraph[first][i];
          tempGraph[i][second] = tempGraph[i][second] + tempGraph[i][first];
        }
      }
    }

    //Merge the values of nodes, performing row and column addition
    private void mergeNodes(Graph newGraph, int[][] tempGraph) {
      int row=0,col=0;
      for(int i = 0; i < tempGraph.length; i++){
        for(int j = 0; j < tempGraph.length; j++){
          // we need to avoid self loops as mentioned in question
          if((i != first) && (j != first)){
            newGraph.setValue(row, col++, tempGraph[i][j]);
          }
        }

        //Need to skip the node as we are merging it
        if(i == first){
          continue;
        }
        row++;
        col = 0;
      }
    }

    //Merge the nodes to reduce the graph
    private void mergeGraph() {
        //New Graph with reduced nodes as we are merging two nodes
        Graph newGraph = new Graph(graph.nodes() -1, graph.edges());

        //Copying graph to temp graph and do merge in
        //temp graph
        int[][] tempGraph = graph.getGraph();

        //New degrees are generated
        changeDegrees(tempGraph);

        //Merge the values of nodes, performing row and column addition
        mergeNodes(newGraph, tempGraph);

        this.graph = newGraph;
    }
}
