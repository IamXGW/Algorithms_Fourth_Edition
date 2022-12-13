package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

import java.util.ArrayList;
import java.util.List;

public class AllPairsPath {
    private Graph G;
    private SingleSourcePath[] paths;

    public AllPairsPath(Graph g) {
        this.G = g;
        this.paths = new SingleSourcePath[g.V()];
        for (int v  = 0; v < G.V(); ++v) {
            paths[v] = new SingleSourcePath(G, v);
        }
    }

    public boolean isConnectedTo(int s, int t) {
        validateVertex(t);
        validateVertex(s);
        return paths[s].isConnectedTo(t);
    }

    public Iterable<Integer> path(int s, int t) {
        validateVertex(t);
        validateVertex(s);
        List<Integer> ret = new ArrayList<>();
        if (!isConnectedTo(s, t)) return ret;
        return paths[s].path(t);
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V()) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }
    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        AllPairsPath allPairsPath = new AllPairsPath(G);
        System.out.println("0 -> 5 : " + allPairsPath.path(0,5));
        System.out.println("1 -> 6 : " + allPairsPath.path(1,6));
    }
}
