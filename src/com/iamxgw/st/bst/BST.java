package com.iamxgw.st.bst;

import java.rmi.Remote;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

public class BST<Key extends Comparable<Key>, Value> {
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            left = right = null;
        }

        public Node(Node node) {
            this.key = node.key;
            this.val = node.val;
            this.left = node.left;
            this.right = node.right;
        }
    }

    // 该 BST 的根节点
    private Node root;
    // 该 BST 中节点的个数
    private int count;

    public BST() {
        root = null;
        count = 0;
    }

    public void insert(Key key, Value val) {
        root = insertRecursion(root, key, val);
//        insertLoop(root, key, val);
    }

    /**
     * 向以 node 为根的 BST 中插入 key，val
     * 返回插入之后的 BST 的根
     *
     * @param node
     * @param key
     * @param val
     * @return
     */
    private Node insertRecursion(Node node, Key key, Value val) {
        if (node == null) {
            count++;
            return new Node(key, val);
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = insertRecursion(node.left, key, val);
        } else if (cmp > 0) {
            node.right = insertRecursion(node.right, key, val);
        } else {
            node.val = val;
        }
        return node;
    }

    /**
     * 向以 node 为根的 BST 中插入 key，val
     *
     * @param node
     * @param key
     * @param val
     */
    private void insertLoop(Node node, Key key, Value val) {
        if (node == null) {
            count++;
            node = new Node(key, val);
            return;
        }
        for (Node cur = node; cur != null; ) {
            int cmp = key.compareTo(cur.key);
            if (cmp < 0) {
                if (cur.left == null) {
                    count++;
                    cur.left = new Node(key, val);
                    break;
                } else {
                    cur = cur.left;
                }
            } else if (cmp > 0) {
                if (cur.right == null) {
                    count++;
                    cur.right = new Node(key, val);
                    break;
                } else {
                    cur = cur.right;
                }
            } else {
                cur.val = val;
                break;
            }
        }
    }

    /**
     * 该 BST 中是否包含 key
     *
     * @param key
     * @return
     */
    public boolean contain(Key key) {
        return search(root, key) != null;
    }

    /**
     * 找出该 BST 中 key 对应的 val，如果没有则返回 null
     *
     * @param key
     * @return
     */
    public Value search(Key key) {
        return search(root, key);
    }

    /**
     * 在以 node 为根的 BST 中，查找 key 对应的 val
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
     * 返回该 BST 的节点个数
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * 返回该 BST 是不是空树
     *
     * @return
     */
    public boolean isEmpty() {
        return count == 0;
    }

    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val.toString());
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.val.toString());
        inOrder(node.right);
    }

    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.val.toString());
    }

    public void levelOrder() {
        levelOrder(root);
    }

    private void levelOrder(Node node) {
        if (node == null) {
            return;
        }
        Deque<Node> deque = new ArrayDeque<>();
        deque.addLast(node);
        while (!deque.isEmpty()) {
            Node cur = deque.pollFirst();
            System.out.println(cur.val.toString());
            if (cur.left != null) {
                deque.addLast(cur.left);
            }
            if (cur.right != null) {
                deque.addLast(cur.right);
            }
        }
    }

    /**
     * 返回 BST 中最小的键值
     *
     * @return
     */
    public Node minimum() {
        return minimum(root);
    }

    private Node minimum(Node node) {
        if (node.left == null) {
            return node;
        }
        return minimum(node.left);
    }

    /**
     * 返回 BST 中最大的键值
     *
     * @return
     */
    public Node maximum() {
        return maximum(root);
    }

    private Node maximum(Node node) {
        if (node.right == null) {
            return node;
        }
        return maximum(node.right);
    }

    /**
     * 删除 BST 中的最小值
     */
    public void removeMin() {
        root = removeMin(root);
    }

    /**
     * 删除以 node 为根的 BST 中的最小值，并返回删除后的 BST 的根节点
     *
     * @param node
     * @return
     */
    private Node removeMin(Node node) {
        if (node.left == null) {
            count--;
            Node rightNode = node.right;
            node.right = null;
            return rightNode;
        }
        node.left = removeMin(node.left);
        return node;
    }

    /**
     * 删除 BST 中的最大值
     */
    public void removeMax() {
        root = removeMax(root);
    }

    /**
     * 删除以 node 为根的 BST 中的最大值，并返回删除后的 BST 的根节点
     *
     * @param node
     * @return
     */
    private Node removeMax(Node node) {
        if (node.right == null) {
            count--;
            Node leftNode = node.left;
            node.left = null;
            return leftNode;
        }
        node.right = removeMax(node.right);
        return node;
    }

    public void remove(Key key) {
        root = remove(root, key);
    }

    /**
     * 在以 node 为根的树中删除掉 key，然后返回删除后的根
     * 1. 如果待删除节点只有一个子树，那么用该子树顶替待删除节点
     * 2. 待删除节点有两个子树：
     *      1. 找到右子树中的最小值 s，并在右子树中将 s 删除
     *      2. 将 s 的左子树变为待删除节点的左子树，s 的右子树为待删除节点的右子树（该步骤保证 BST 的性质）
     *      3. 用 s 顶替待删除节点
     * @param node
     * @param key
     */
    private Node remove(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
            return node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node = null;
                --count;
                return rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node = null;
                --count;
                return leftNode;
            } else {
                Node successor = new Node(minimum(node.right));
                ++count;
                successor.left = node.left;
                successor.right = removeMin(node.right);
                node.left = node.right = null;
                --count;
                return successor;
            }
        }
    }

    /**
     * 查找范围在 [lo, hi] 的节点，放在队列中并返回
     * @param lo
     * @param hi
     * @return
     */
    public Iterable<Key> keys(Key lo, Key hi) {
        Deque<Key> deque = new ArrayDeque<>();
        keys(root, deque, lo, hi);
        return deque;
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

    public static void main(String[] args) {
        int N = 1000;
        Integer[] arr = new Integer[N];
        for (int i = 0; i < N; ++i) {
            arr[i] = new Integer(i);
        }
        for (int i = 0; i < N; ++i) {
            int idx = (int) (Math.random() * (i + 1));
            Integer t = arr[idx];
            arr[idx] = arr[i];
            arr[i] = t;
        }
        BST<Integer, String> bst = new BST<>();
        for (int i = 0; i < N; ++i) {
            bst.insert(new Integer(arr[i]), Integer.toString(arr[i]));
        }
        for (int i = 0; i < 2 * N; ++i) {
            String res = bst.search(new Integer(i));
            if (i < N) {
                if (!(res.equals(Integer.toString(i)))) {
                    System.out.println("1 ERROR!");
                }
            } else {
                if (res != null) {
                    System.out.println("2 ERROR!");
                }
            }
        }

//        bst.preOrder();
//        bst.inOrder();
//        bst.postOrder();
//        bst.levelOrder();

        // test keys
        int lo = N / 4, hi = N / 3;
        Iterable<Integer> keys = bst.keys(lo, hi);
        int keysCnt = 0;
        for (Integer key : keys) {
            if (!(key >= lo && key <= hi)) {
                System.out.println("keys ERROR!");
            }
            keysCnt++;
        }
        if ((hi - lo + 1) != keysCnt) {
            System.out.println("keys ERROR!");
        }

        // test minimum / maximum / removeMin / removeMax
//        Integer[] delTestArr = new Integer[N];
//        int l = 0, r = N - 1;
//        while (!bst.isEmpty()) {
//            int min = bst.minimum().key;
//            int max = bst.maximum().key;
//            delTestArr[l++] = min;
//            delTestArr[r--] = max;
//            bst.removeMin();
//            bst.removeMax();
//        }
//        if (!(delTestArr.length == N)) {
//            System.out.println("3 ERROR!");
//        }
//        for (int i = 0; i < N - 1; ++i) {
//            if (!(delTestArr[i].compareTo(delTestArr[i + 1]) < 0)) {
//                System.out.println("min ERROR!");
//            }
//        }

        // test remove
        int cnt = N;
        for (int i = 0; i < N / 2; ++i) {
            int delKey = (int) (Math.random() * N);
            if (bst.search(delKey) != null) {
                cnt--;
            }
            bst.remove(delKey);
            if (bst.search(delKey) != null) {
                System.out.println("Remove ERROR!");
            }
            if (bst.size() != cnt) {
                System.out.println("Remove2 ERROR!");
                System.out.println(cnt + " " + bst.size());
            }
        }
    }
}
