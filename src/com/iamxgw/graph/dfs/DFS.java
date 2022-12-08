package com.iamxgw.graph.dfs;

import com.iamxgw.graph.basics.Graph;
import com.iamxgw.graph.basics.GraphTreeSet;

import java.util.ArrayList;
import java.util.List;

public class DFS {
    private Graph G;
    private boolean[] vis;
    private List<Integer> pre = new ArrayList<>();
    private List<Integer> post = new ArrayList<>();

    public DFS(Graph g) {
        this.G = g;
        this.vis = new boolean[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!vis[v]) {
                dfs(v);
            }
        }
    }

    private void dfs(int v) {
        vis[v] = true;
        pre.add(v);
        for (int w : G.adj(v)) {
            if (!vis[w]) {
                dfs(w);
            }
        }
        post.add(v);
    }

    public Iterable<Integer> pre() {
        return pre;
    }

    public Iterable<Integer> post() {
        return post;
    }

    public static void main(String[] args) {
        Graph G = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        DFS dfs = new DFS(G);
        System.out.println(dfs.pre);
        System.out.println(dfs.post);
    }
}
