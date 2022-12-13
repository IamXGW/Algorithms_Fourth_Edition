package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

public class BipartitionDetection {
    private Graph G;
    private boolean[] vis;
    private int[] colors;
    private boolean isBipartite = true;

    public BipartitionDetection(Graph g) {
        this.G = g;
        this.vis = new boolean[G.V()];
        colors = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!vis[v]) {
                if (!dfs(v, 1)) {
                    isBipartite = false;
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int color) {
        vis[v] = true;
        colors[v] = color;
        for (int w : G.adj(v)) {
            if (!vis[w]) {
                if (!dfs(w, 3 - color)) {
                    return false;
                }
            } else if (colors[w] == color) {
                return false;
            }
        }
        return true;
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        BipartitionDetection bipartitionDetection = new BipartitionDetection(G);
        System.out.println(bipartitionDetection.isBipartite());
    }
}
