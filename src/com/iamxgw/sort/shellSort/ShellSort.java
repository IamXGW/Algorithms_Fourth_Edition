package com.iamxgw.sort.shellSort;

import com.iamxgw.sort.SortHelper;

public class ShellSort {

    private ShellSort(){}

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        int h = 1;
        while (h < n / 3) h = 3 * h + 1;
        while (h >= 1) {
            for (int i = h; i < n; ++i) {
                for (int j = i; j >= h && arr[j].compareTo(arr[j - h]) < 0; j -= h) {
                    swap(arr, j, j - h);
                }
            }
            h /= 3;
        }
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        Integer[] arr = SortHelper.generateRandomArray(10000, 0, 10000);
        SortHelper.testSort("com.iamxgw.sort.shellSort.ShellSort", arr);
    }
}
