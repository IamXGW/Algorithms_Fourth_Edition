package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;


public class CycleDetection {
    private Graph G;
    private boolean[] vis;
    private boolean hasCycle;

    public CycleDetection(Graph g) {
        this.G = g;
        this.vis = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!vis[v]) {
                if (dfs(v, v)) {
                    hasCycle = true;
                    break;
                }
            }
        }
    }

    private boolean dfs(int v, int parent) {
        vis[v] = true;
        for (int w : G.adj(v)) {
            if (!vis[w]) {
                if (dfs(w, v)) {
                    return true;
                }
            } else if (w != parent) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCycle() {
        return hasCycle;
    }


    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        CycleDetection cycleDetection = new CycleDetection(G);
        System.out.println(cycleDetection.hasCycle);
    }
}
