package com.iamxgw.sort.quickSort;

public class NutsAndBolts {
    /**
     * Given a set of n nuts of different sizes and n bolts of different sizes.
     * There is a one-one mapping between nuts and bolts. Match nuts and bolts efficiently.
     * Constraint: Comparison of a nut to another nut or a bolt to another bolt is not allowed.
     * It means a nut can only be compared with a bolt and a bolt can only be compared with a nut to see which one is bigger/smaller.
     * Another way of asking this problem is, to give a box with locks and keys where one lock can be opened by one key in the box. We need to match the pair.
     * from: https://www.geeksforgeeks.org/nuts-bolts-problem-lock-key-problem-using-quick-sort/
     */
    public static void main(String[] args) {
        char[] nuts = {'@', '#', '$', '%', '^', '&'};
        char[] bolts = {'$', '%', '&', '^', '@', '#'};

        int n = nuts.length;
        for (int i = 0; i < n; ++i) {
            System.out.println(nuts[i] + " -> " + bolts[i]);
        }
        matchPairs(nuts, bolts, 0, n - 1);
        System.out.println("******");
        for (int i = 0; i < n; ++i) {
            System.out.println(nuts[i] + " -> " + bolts[i]);
        }
    }

    private static void matchPairs(char[] nuts, char[] bolts, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(nuts, l, r, bolts[r]);
        partition(bolts, l, r, nuts[p]);
        matchPairs(nuts, bolts, l, p - 1);
        matchPairs(nuts, bolts, p + 1, r);
    }

    private static int partition(char[] arr, int l, int r, char v) {
        int i = l, j = r;
        while (true) {
            while (i <= r && arr[i] < v) ++i;
            while (j >= l && arr[j] > v) --j;
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            ++i;
            --j;
        }
        return j;
    }

    public static void swap(char[] arr, int i, int j) {
        char t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
