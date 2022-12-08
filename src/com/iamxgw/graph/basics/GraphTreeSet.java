package com.iamxgw.graph.basics;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.TreeSet;

public class GraphTreeSet implements Graph {

    // 定点个数
    private int V;
    // 边的个数
    private int E;
    private TreeSet<Integer>[] adj;

    public GraphTreeSet(String fileName) {
        File file = new File(fileName);
        try (Scanner sc = new Scanner(file)) {
            V = sc.nextInt();
            if (V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new TreeSet[V];
            for (int i = 0; i < V; ++i) {
                adj[i] = new TreeSet<>();
            }
            E = sc.nextInt();
            if (E < 0) throw new IllegalArgumentException("E must be non-negative");

            for (int i = 0; i < E; ++i) {
                int v = sc.nextInt();
                validateVertex(v);
                int w = sc.nextInt();
                validateVertex(w);
                if (v == w) throw new IllegalArgumentException("Self Loop is Detected!");
                if (adj[v].contains(w)) throw new IllegalArgumentException("Parallel Edges are Detected!");

                adj[v].add(w);
                adj[w].add(v);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void validateVertex(int v) {
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("Vertex " + v + " is invalid");
        }
    }

    @Override
    public int V() {
        return V;
    }

    @Override
    public int E() {
        return E;
    }

    @Override
    public boolean hasEdge(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        return adj[v].contains(w);
    }

    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return adj[v];
    }

    @Override
    public int degree(int v) {
        validateVertex(v);
        return adj[v].size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("V = " + V + ", E = " + E + "\n");
        for (int v = 0; v < V; ++v) {
            sb.append(v + " : ");
            for (int w : adj[v]) {
                sb.append(w + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
