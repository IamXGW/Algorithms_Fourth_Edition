package com.iamxgw.st.redBlackTree;

import com.iamxgw.st.FileOperations;
import com.iamxgw.st.avl.AVLTree;
import com.iamxgw.st.bst.BST;

import java.util.*;

public class RBTree<Key extends Comparable<Key>, Value> {
    public static final boolean RED = true;
    public static final boolean BLACK = false;

    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private boolean color;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            this.left = this.right = null;
            // 新节点默认为红色。因为 2-3 树不会向一个空节点插入值
            this.color = RED;
        }
    }

    // 该 RBTree 的根节点
    private Node root;
    // 该 RBTree 中节点的个数
    private int count;

    public RBTree() {
        root = null;
        count = 0;
    }

    public void insert(Key key, Value val) {
        root = insert(root, key, val);
        // 根节点永远为黑色
        root.color = BLACK;
    }

    /**
     * 向以 node 为根的 RBTree 中插入 key，val
     * 返回插入之后的 RBTree 的根
     *
     * @param node
     * @param key
     * @param val
     * @return
     */
    private Node insert(Node node, Key key, Value val) {
        if (node == null) {
            count++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insert(node.left, key, val);
        } else if (cmp > 0) {
            node.right = insert(node.right, key, val);
        } else {
            node.val = val;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = leftRotate(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rightRotate(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        return node;
    }

    /**
     * 该 RBTree 中是否包含 key
     *
     * @param key
     * @return
     */
    public boolean contains(Key key) {
        return search(root, key) != null;
    }

    /**
     * 获取当前 node 的颜色
     * 根节点的颜色统一为黑色
     * @param node
     * @return
     */
    public boolean isRed(Node node) {
        if (node == null) {
            return false;
        }
        return node.color;
    }

    /**
     * 找出该 RBTree 中 key 对应的 val，如果没有则返回 null
     *
     * @param key
     * @return
     */
    public Value search(Key key) {
        return search(root, key);
    }

    /**
     * 在以 node 为根的 RBTree 中，查找 key 对应的 val
     * 存在则返回 val，否则返回 null
     *
     * @param node
     * @param key
     * @return
     */
    private Value search(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return search(node.left, key);
        } else if (cmp > 0) {
            return search(node.right, key);
        } else {
            return node.val;
        }
    }

    /**
     * 返回该 RBTree 的节点个数
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * 返回该 RBTree 是不是空树
     *
     * @return
     */
    public boolean isEmpty() {
        return count == 0;
    }

    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    //   node                     x
    //  /   \     左旋转         /  \
    // T1   x   --------->   node   T3
    //     / \              /   \
    //    T2 T3            T1   T2
    private Node leftRotate(Node node) {
        Node x = node.right;

        node.right = x.left;
        x.left = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    //     node                   x
    //    /   \     右旋转       /  \
    //   x    T2   ------->   y   node
    //  / \                       /  \
    // y  T1                     T1  T2
    private Node rightRotate(Node node) {
        Node x = node.left;

        node.left = x.right;
        x.right = node;

        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 颜色翻转
    private void flipColors(Node node) {
        node.color = RED;
        node.left.color = node.right.color = BLACK;
    }

    private void keys(Node node, Deque<Key> deque, Key lo, Key hi) {
        if (node == null) {
            return;
        }
        int cmplo = lo.compareTo(node.key);
        int cmphi = hi.compareTo(node.key);
        if (cmplo < 0) {
            keys(node.left, deque, lo, hi);
        }
        if (cmplo <= 0 && cmphi >= 0) {
            deque.addLast(node.key);
        }
        if (cmphi > 0) {
            keys(node.right, deque, lo, hi);
        }
    }

    private static void testSpeed() {
        System.out.println("Pride and Prejudice");

        Vector<String> words = new Vector<>();
        if (FileOperations.readFile("src/com/iamxgw/st/pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

//             Collections.sort(words);

            // Test BST
            long startTime = System.nanoTime();

            BST<String, Integer> bst = new BST<>();
            for (String word : words) {
                if (bst.contain(word))
                    bst.insert(word, bst.search(word) + 1);
                else
                    bst.insert(word, 1);
            }

            for(String word: words)
                bst.contain(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;
            System.out.println("BST: " + time + " s");


            // Test AVL Tree
            startTime = System.nanoTime();

            AVLTree<String, Integer> avl = new AVLTree<>();
            for (String word : words) {
                if (avl.contains(word))
                    avl.insert(word, avl.search(word) + 1);
                else
                    avl.insert(word, 1);
            }

            for(String word: words)
                avl.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("AVL: " + time + " s");

            // Test RBTree Tree
            startTime = System.nanoTime();

            RBTree<String, Integer> rbtree = new RBTree<>();
            for (String word : words) {
                if (rbtree.contains(word))
                    rbtree.insert(word, rbtree.search(word) + 1);
                else
                    rbtree.insert(word, 1);
            }

            for(String word: words)
                rbtree.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("RBTree: " + time + " s");
        }

        System.out.println();
    }

    private static void testRBTree() {
        System.out.println("Pride and Prejudice");

        Vector<String> words = new Vector<>();
        if (FileOperations.readFile("src/com/iamxgw/st/pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            RBTree<String, Integer> map = new RBTree<>();
            for (String word : words) {
                if (map.contains(word)) {
                    map.insert(word, map.search(word) + 1);
                } else {
                    map.insert(word, 1);
                }
            }

            System.out.println("Total different words: " + map.size());
            System.out.println("Frequency of PRIDE: " + map.search("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.search("prejudice"));
        }

        System.out.println();
    }

    private static void testSpeend1() {
        int n = 20000000;

        Random random = new Random(n);
        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(random.nextInt(Integer.MAX_VALUE));

        // Test BST
        long startTime = System.nanoTime();

        BST<Integer, Integer> bst = new BST<>();
        for (Integer x: testData)
            bst.insert(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("BST: " + time + " s");


        // Test AVL
        startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.insert(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.insert(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
        System.out.println();
    }

    private static void testSpeend2() {
        // int n = 20000000;
        int n = 20000000;

        ArrayList<Integer> testData = new ArrayList<>(n);
        for(int i = 0 ; i < n ; i ++)
            testData.add(i);

        // Test AVL
        long startTime = System.nanoTime();

        AVLTree<Integer, Integer> avl = new AVLTree<>();
        for (Integer x: testData)
            avl.insert(x, null);

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("AVL: " + time + " s");


        // Test RBTree
        startTime = System.nanoTime();

        RBTree<Integer, Integer> rbt = new RBTree<>();
        for (Integer x: testData)
            rbt.insert(x, null);

        endTime = System.nanoTime();

        time = (endTime - startTime) / 1000000000.0;
        System.out.println("RBTree: " + time + " s");
    }

    public static void main(String[] args) {
        testSpeed();
//        testRBTree();
        testSpeend1();
        testSpeend2();
    }
}
