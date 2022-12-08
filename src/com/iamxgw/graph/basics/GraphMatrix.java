package com.iamxgw.graph.basics;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GraphMatrix implements Graph {

    // 定点个数
    private int V;
    // 边的个数
    private int E;
    private int[][] adj;

    public GraphMatrix(String fileName) {
        File file = new File(fileName);
        try (Scanner sc = new Scanner(file)) {
            V = sc.nextInt();
            if (V < 0) throw new IllegalArgumentException("V must be non-negative");
            adj = new int[V][V];
            E = sc.nextInt();
            if (E < 0) throw new IllegalArgumentException("E must be non-negative");

            for (int i = 0; i < E; ++i) {
                int v = sc.nextInt();
                validateVertex(v);
                int w = sc.nextInt();
                validateVertex(w);
                if (v == w) throw new IllegalArgumentException("Self Loop is Detected!");
                if (adj[v][w] != 0) throw new IllegalArgumentException("Parallel Edges are Detected!");

                adj[v][w] = 1;
                adj[w][v] = 1;
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
        return adj[v][w] == 1;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        validateVertex(v);
        return () -> Arrays.stream(adj[v]).iterator();
    }

    @Override
    public int degree(int v) {
        validateVertex(v);
        return adj[v].length;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("V = " + V + ", E = " + E + "\n");
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                sb.append(String.format("%2d ", adj[i][j]));
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
