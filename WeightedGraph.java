/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package weightedninetail;

import java.util.*;

public class WeightedGraph<V> extends AbstractGraph<V> {
    // Default constructor
    public WeightedGraph() {}

    // Construct a WeightedGraph from vertices and edges in arrays
    public WeightedGraph(V[] vertices, int[][] edges) {
        createWeightedGraph(Arrays.asList(vertices), edges);
    }

    // Construct a WeightedGraph from vertices and edges in list
    public WeightedGraph(int[][] edges, int numberOfVertices) {
        List<V> vertices = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            vertices.add((V)(Integer.valueOf(i)));
        }
        createWeightedGraph(vertices, edges);
    }

    // Construct a WeightedGraph from vertices and edge list
    public WeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
        createWeightedGraph(vertices, edges);
    }

    // Construct a WeightedGraph from edge array
    public WeightedGraph(List<WeightedEdge> edges, int numberOfVertices) {
        List<V> vertices = new ArrayList<>();
        for (int i = 0; i < numberOfVertices; i++) {
            vertices.add((V)(Integer.valueOf(i)));
        }
        createWeightedGraph(vertices, edges);
    }

    private void createWeightedGraph(List<V> vertices, int[][] edges) {
        this.vertices = vertices;
        for (int i = 0; i < vertices.size(); i++) {
            neighbors.add(new ArrayList<>());
        }
        for (int[] edge : edges) {
            neighbors.get(edge[0]).add(new WeightedEdge(edge[0], edge[1], edge[2]));
        }
    }

    private void createWeightedGraph(List<V> vertices, List<WeightedEdge> edges) {
        this.vertices = vertices;
        for (int i = 0; i < vertices.size(); i++) {
            neighbors.add(new ArrayList<>());
        }
        for (WeightedEdge edge : edges) {
            neighbors.get(edge.u).add(edge);
        }
    }

    public double getWeight(int u, int v) throws Exception {
        for (Edge edge : neighbors.get(u)) {
            if (edge.v == v) {
                return ((WeightedEdge) edge).weight;
            }
        }
        throw new Exception("Edge does not exist");
    }

    public void printWeightedEdges() {
        for (int i = 0; i < getSize(); i++) {
            System.out.print(getVertex(i) + " (" + i + "): ");
            for (Edge edge : neighbors.get(i)) {
                System.out.print("(" + edge.u + ", " + edge.v + ", " + ((WeightedEdge) edge).weight + ") ");
            }
            System.out.println();
        }
    }

    public boolean addEdge(int u, int v, double weight) {
        return addEdge(new WeightedEdge(u, v, weight));
    }

    public MST getMinimumSpanningTree() {
        return getMinimumSpanningTree(0);
    }

    public MST getMinimumSpanningTree(int startingVertex) {
        double[] cost = new double[getSize()];
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[startingVertex] = 0;

        int[] parent = new int[getSize()];
        parent[startingVertex] = -1;
        double totalWeight = 0;

        List<Integer> T = new ArrayList<>();
        while (T.size() < getSize()) {
            int u = -1;
            double currentMinCost = Double.POSITIVE_INFINITY;
            for (int i = 0; i < getSize(); i++) {
                if (!T.contains(i) && cost[i] < currentMinCost) {
                    currentMinCost = cost[i];
                    u = i;
                }
            }

            T.add(u);
            totalWeight += cost[u];

            for (Edge e : neighbors.get(u)) {
                if (!T.contains(e.v) && cost[e.v] > ((WeightedEdge) e).weight) {
                    cost[e.v] = ((WeightedEdge) e).weight;
                    parent[e.v] = u;
                }
            }
        }

        return new MST(startingVertex, parent, T, totalWeight);
    }

    public class MST extends Tree {
        private final double totalWeight;

        public MST(int root, int[] parent, List<Integer> searchOrder, double totalWeight) {
            super(root, parent, searchOrder);
            this.totalWeight = totalWeight;
        }

        public double getTotalWeight() {
            return totalWeight;
        }
    }

    public ShortestPathTree getShortestPath(int sourceVertex) {
        double[] cost = new double[getSize()];
        Arrays.fill(cost, Double.POSITIVE_INFINITY);
        cost[sourceVertex] = 0;

        int[] parent = new int[getSize()];
        parent[sourceVertex] = -1;

        List<Integer> T = new ArrayList<>();
        while (T.size() < getSize()) {
            int u = -1;
            double currentMinCost = Double.POSITIVE_INFINITY;
            for (int i = 0; i < getSize(); i++) {
                if (!T.contains(i) && cost[i] < currentMinCost) {
                    currentMinCost = cost[i];
                    u = i;
                }
            }

            T.add(u);

            for (Edge e : neighbors.get(u)) {
                if (!T.contains(e.v) && cost[e.v] > cost[u] + ((WeightedEdge) e).weight) {
                    cost[e.v] = cost[u] + ((WeightedEdge) e).weight;
                    parent[e.v] = u;
                }
            }
        }

        return new ShortestPathTree(sourceVertex, parent, T, cost);
    }

    public class ShortestPathTree extends Tree {
        private final double[] cost;

        public ShortestPathTree(int source, int[] parent, List<Integer> searchOrder, double[] cost) {
            super(source, parent, searchOrder);
            this.cost = cost;
        }

        public double getCost(int v) {
            return cost[v];
        }

        public void printAllPaths() {
            System.out.println("All shortest paths from " + vertices.get(getRoot()) + " are:");
            for (int i = 0; i < cost.length; i++) {
                printPath(i);
                System.out.println("(cost: " + cost[i] + ")");
            }
        }
    }
}
