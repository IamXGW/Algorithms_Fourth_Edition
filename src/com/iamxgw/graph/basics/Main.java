package com.iamxgw.graph.basics;

public class Main {
    public static void main(String[] args) {
        Graph g1 = new GraphTreeSet("src/com/iamxgw/graph/g.txt");
        System.out.println(g1.toString());

        Graph g2 = new GraphHashSet("src/com/iamxgw/graph/g.txt");
        System.out.println(g2.toString());

        Graph g3 = new GraphMatrix("src/com/iamxgw/graph/g.txt");
        System.out.println(g3.toString());
    }
}
