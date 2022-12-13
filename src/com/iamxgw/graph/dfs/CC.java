package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Connected Component
public class CC {
    private Graph G;
    private int[] vis;
    private int cccount = 0;

    public CC(Graph g) {
        this.G = g;
        this.vis = new int[G.V()];
        Arrays.fill(vis, -1);
        for (int v = 0; v < G.V(); ++v) {
            if (vis[v] == -1) {
                dfs(v, cccount);
                cccount++;
            }
        }
    }

    private void dfs(int v, int ccid) {
        vis[v] = ccid;
        for (int w : G.adj(v)) {
            if (vis[w] == -1) {
                dfs(w, ccid);
            }
        }
    }

    public int count() {
        return cccount;
    }

    public boolean isConnected(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return vis[v] == vis[w];
    }

    public List<Integer>[] components() {
        List<Integer>[] ret = new ArrayList[cccount];
        for (int i = 0; i < cccount; ++i) {
            ret[i] = new ArrayList<>();
        }
        for (int v = 0; v < G.V(); ++v) {
            ret[vis[v]].add(v);
        }
        return ret;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V()) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }

    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        CC cc = new CC(G);
        System.out.println(cc.count());
        System.out.println(cc.isConnected(0, 5));
        System.out.println(cc.isConnected(0, 6));
        List<Integer>[] comp = cc.components();
        for (List<Integer> list : comp) {
            System.out.println(list);
        }
    }
}
