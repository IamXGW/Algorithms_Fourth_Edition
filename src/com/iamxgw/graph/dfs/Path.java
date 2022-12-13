package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Path {
    private Graph G;
    private boolean[] vis;
    private int s, t;
    private int[] pre;

    public Path(Graph g, int s, int t) {
        this.G = g;
        validateVertex(s);
        validateVertex(t);
        this.s = s;
        this.t = t;
        this.vis = new boolean[G.V()];
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        dfs(s, s);
    }

    private boolean dfs(int v, int parent) {
        vis[v] = true;
        pre[v] = parent;
        if (v == t) return true;
        for (int w : G.adj(v)) {
            if (!vis[w]) {
                if (dfs(w, v)) return true;
            }
        }
        return false;
    }

    public boolean isConnected() {
        return vis[t];
    }

    public Iterable<Integer> path() {
        List<Integer> ret = new ArrayList<>();
        if (!isConnected()) return ret;
        int cur = t;
        while (pre[cur] != cur) {
            ret.add(cur);
            cur = pre[cur];
        }
        ret.add(cur);
        Collections.reverse(ret);
        return ret;
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= G.V()) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }
    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        Path path = new Path(G, 0, 5);
        System.out.println("0 -> 5 : " + path.path());
        Path path2 = new Path(G, 0, 6);
        System.out.println("0 -> 6 : " + path2.path());
    }
}
