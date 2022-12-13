package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SingleSourcePath {
    private Graph G;
    private boolean[] vis;
    private int s;
    private int[] pre;

    public SingleSourcePath(Graph g, int s) {
        this.G = g;
        this.s = s;
        this.vis = new boolean[G.V()];
        pre = new int[G.V()];
        Arrays.fill(pre, -1);
        dfs(s, s);
    }

    private void dfs(int v, int parent) {
        vis[v] = true;
        pre[v] = parent;
        for (int w : G.adj(v)) {
            if (!vis[w]) {
                dfs(w, v);
            }
        }
    }

    public boolean isConnectedTo(int t) {
        validateVertex(t);
        return vis[t];
    }

    public Iterable<Integer> path(int t) {
        validateVertex(t);
        List<Integer> ret = new ArrayList<>();
        if (!isConnectedTo(t)) return ret;
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
        SingleSourcePath sspath = new SingleSourcePath(G, 0);
        System.out.println("0 -> 5 : " + sspath.path(5));
        System.out.println("0 -> 6 : " + sspath.path(6));
    }
}
