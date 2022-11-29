package com.iamxgw.st.avl;

import com.iamxgw.st.FileOperations;
import com.iamxgw.st.bst.BST;

import java.util.*;

// AVL tree (named after inventors Adelson-Velsky and Landis)
public class AVLTree<Key extends Comparable<Key>, Value> {
    private class Node {
        private Key key;
        private Value val;
        private Node left, right;
        private int height;

        public Node(Key key, Value val) {
            this.key = key;
            this.val = val;
            left = right = null;
            this.height = 1;
        }
    }

    // 该 BST 的根节点
    private Node root;
    // 该 BST 中节点的个数
    private int count;

    public AVLTree() {
        root = null;
        count = 0;
    }

    public void insert(Key key, Value val) {
        root = insert(root, key, val);
    }

    /**
     * 向以 node 为根的 BST 中插入 key，val
     * 如果 key 存在，则更新 key 的值
     * 返回插入之后的 BST 的根
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

        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

        // 平衡维护
        int balanceFactor = getBalanceFactor(node);
        // LL：在 node 左子树的左边添加元素，导致的不平衡
        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRatate(node);
        }
        // RR
        if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        // LR
        if (balanceFactor > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRatate(node);
        }
        // RL
        if (balanceFactor < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRatate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    /**
     * 该 BST 中是否包含 key
     *
     * @param key
     * @return
     */
    public boolean contains(Key key) {
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

    private void inOrder(Node node, List<Key> keys) {
        if (node == null) {
            return;
        }
        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
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
     *
     * @param node
     * @param key
     */
    private Node remove(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        Node retNode = null;
        if (cmp < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        } else if (cmp > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else {
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                --count;
                retNode = rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                --count;
                retNode = leftNode;
            } else {
                Node successor = minimum(node.right);
//                ++count;
                /**
                 * 这两句话顺序不能颠倒，会报错
                 * https://coding.imooc.com/learn/questiondetail/gDANwPN0l91XK120.html
                 */
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
//                --count;
                retNode = successor;
            }
        }

        if (retNode == null) {
            return null;
        }

        retNode.height = Math.max(getHeight(retNode.left), getHeight(retNode.right)) + 1;

        // 平衡维护
        int balanceFactor = getBalanceFactor(retNode);
        // LL：在 node 左子树的左边添加元素，导致的不平衡
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0) {
            return rightRatate(retNode);
        }
        // RR
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0) {
            return leftRotate(retNode);
        }
        // LR
        if (balanceFactor > 1 && getBalanceFactor(retNode.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRatate(retNode);
        }
        // RL
        if (balanceFactor < -1 && getBalanceFactor(retNode.right) > 0) {
            retNode.right = rightRatate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    /**
     * 在 BST 中找 key 的 floor（小于等于 key 的最大键）
     *
     * @param key
     * @return
     */
    public Key floor(Key key) {
        Node node = floor(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    /**
     * 如果 node.key 等于 key 则返回 node
     * 否则，如果 key 小于 node.key，则 key 的 floor 一定在 node 的左子树中
     *      如果 key 大于 node.key，只有当 node.right 中有小于等于 key 的值时，floor 才在 node 的右子树中，
     *          否则，当前 node 就是 key 的 floor
     *
     * @param node
     * @param key
     * @return
     */
    private Node floor(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp < 0) {
            return floor(node.left, key);
        }
        Node t = floor(node.right, key);
        if (t == null) {
            return node;
        } else {
            return t;
        }
    }

    /**
     * 在 BST 中找 key 的 ceil（大于等于 key 的最小键）
     *
     * @param key
     * @return
     */
    public Key ceil(Key key) {
        Node node = ceil(root, key);
        if (node == null) {
            return null;
        }
        return node.key;
    }

    /**
     * 将 floor 的左变成右，小于变成大于
     *
     * @param node
     * @param key
     * @return
     */
    private Node ceil(Node node, Key key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node;
        } else if (cmp > 0) {
            return ceil(node.left, key);
        }
        Node t = ceil(node.left, key);
        if (t == null) {
            return node;
        } else {
            return t;
        }
    }

    /**
     * 查找范围在 [lo, hi] 的节点，放在队列中并返回
     *
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

    // 获取 node 的高度
    private int getHeight(Node node) {
        if (node == null) return 0;
        return node.height;
    }

    // 获取 node 的平衡因子
    private int getBalanceFactor(Node node) {
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    /**
     * 对节点 y 进行向右旋转操作，返回旋转后新的根节点 x
     *        y                              x
     *       / \                           /   \
     *      x   T4     向右旋转 (y)        z     y
     *     / \       - - - - - - - ->    / \   / \
     *    z   T3                       T1  T2 T3 T4
     *   / \
     * T1   T2
     *
     * @param y
     * @return
     */
    private Node rightRatate(Node y) {
        Node x = y.left;
        Node T3 = x.right;
        x.right = y;
        y.left = T3;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * 对节点 y 进行向左旋转操作，返回旋转后新的根节点 x
     *   y                             x
     * /  \                          /   \
     * T1   x      向左旋转 (y)       y     z
     *     / \   - - - - - - - ->   / \   / \
     *    T2  z                     T1 T2 T3 T4
     *       / \
     *      T3 T4
     *
     * @param y
     * @return
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;
        x.left = y;
        y.right = T2;
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;
        return x;
    }

    /**
     * 判断是不是一棵 BST
     * 使用「BST 中序遍历是有序的」性质
     * LC 98
     *
     * @return
     */
    private boolean isBST() {
        List<Key> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); ++i) {
            if (keys.get(i - 1).compareTo(keys.get(i)) > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断是不是平衡树
     *
     * @return
     */
    private boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null) {
            return true;
        }
        if (Math.abs(getBalanceFactor(node)) > 1) {
            return false;
        }
        return isBalanced(node.left) && isBalanced(node.right);
    }

    private static void testAVL() {
        System.out.println("Pride and Prejudice");

        Vector<String> words = new Vector<>();
        if (FileOperations.readFile("src/com/iamxgw/st/pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
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

            System.out.println("is BST : " + map.isBST());
            System.out.println("is Balanced : " + map.isBalanced());

            for (String word : words) {
                map.remove(word);
                if (!map.isBalanced() || !map.isBST()) {
                    throw new RuntimeException("ERROR");
                }
            }
        }

        System.out.println();
    }

    private static void testBSTVsAVL() {
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
        }

        System.out.println();
    }

    public static void main(String[] args) {
        testAVL();
//        testBSTVsAVL();
    }
}
