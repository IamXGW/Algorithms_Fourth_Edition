package com.iamxgw.st.hashTable;

import com.iamxgw.st.FileOperations;
import com.iamxgw.st.avl.AVLTree;
import com.iamxgw.st.bst.BST;
import com.iamxgw.st.redBlackTree.RBTree;

import java.util.TreeMap;
import java.util.Vector;

public class HashTable<Key extends Comparable<Key>, Value> {
    private final int[] capacity
            = {53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};
    private static final int upperTol = 10;
    private static final int lowerTol = 2;
    private int capacityIdx = 0;

    private TreeMap<Key, Value>[] hashtable;
    private int size;
    private int M;

    public HashTable() {
        this.M = capacity[capacityIdx];
        this.size = 0;
        this.hashtable = new TreeMap[M];
        for (int i = 0; i < M; ++i) {
            hashtable[i] = new TreeMap<>();
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Value get(Key key) {
        int hash = hash(key);
        return hashtable[hash].get(key);
    }

    public boolean contains(Key key) {
        int hash = hash(key);
        return hashtable[hash].containsKey(key);
    }

    public void set(Key key, Value value) {
        int hash = hash(key);
        if (hashtable[hash].containsKey(key)) {
            hashtable[hash].put(key, value);
        } else {
            throw new IllegalArgumentException(key + " doesn't exits!");
        }
    }

    public void add(Key key, Value value) {
        int hash = hash(key);
        TreeMap<Key, Value> map = hashtable[hash];
        if (!map.containsKey(key)) {
            map.put(key, value);
            size++;

            if (size >= upperTol * M && capacityIdx + 1 < capacity.length) {
                capacityIdx++;
                resize(capacity[capacityIdx]);
            }
        } else {
            hashtable[hash].put(key, value);
        }
    }

    public Value remove(Key key) {
        int hash = hash(key);
        TreeMap<Key, Value> map = hashtable[hash];
        Value ret = null;
        if (map.containsKey(key)) {
            ret = map.remove(key);
            size--;

            if (size < lowerTol * M && capacityIdx - 1 >= 0) {
                capacityIdx--;
                resize(capacity[capacityIdx]);
            }
        }
        return ret;
    }

    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % M;
    }

    private void resize(int newM) {
        TreeMap<Key, Value>[] newHashTable = new TreeMap[newM];
        for (int i = 0; i < newM; ++i) {
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;

        for (int i = 0; i < oldM; ++i) {
            TreeMap<Key, Value> map = hashtable[i];
            for (Key key : map.keySet()) {
                int hash = hash(key);
                newHashTable[hash].put(key, map.get(key));
            }
        }
        this.hashtable = newHashTable;
    }

    public static void main(String[] args) {
        System.out.println("Pride and Prejudice");

        Vector<String> words = new Vector<>();
        if(FileOperations.readFile("src/com/iamxgw/st/pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

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

            // Test HashTable
            startTime = System.nanoTime();

            HashTable<String, Integer> ht = new HashTable<>();
            //HashTable<String, Integer> ht = new HashTable<>(131071);
            for (String word : words) {
                if (ht.contains(word))
                    ht.set(word, ht.get(word) + 1);
                else
                    ht.add(word, 1);
            }

            for (String word : words)
                ht.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;
            System.out.println("HashTable: " + time + " s");
        }
    }
}
