/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package weightedninetail;

public class WeightedEdge extends AbstractGraph.Edge implements Comparable<WeightedEdge> {
    public double weight; // Weight on edge (u, v)

    /** Create a weighted edge on (u, v) */
    public WeightedEdge(int u, int v, double weight) {
        super(u, v);
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedEdge edge) {
        if (weight > edge.weight) return 1;
        else if (weight == edge.weight) return 0;
        else return -1;
    }
}
