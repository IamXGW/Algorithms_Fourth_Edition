package com.iamxgw.sort.selectionSort;

import com.iamxgw.sort.SortHelper;

public class SelectionSort {

    private SelectionSort(){}

    public static void sort(Comparable[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; ++i) {
            int minIdx = i;
            for (int j = i + 1; j < N; ++j) {
                if (arr[j].compareTo(arr[minIdx]) < 0) {
                    minIdx = j;
                }
            }
            swap(arr, i, minIdx);
        }
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        // O(N^2)
        Integer[] arr = SortHelper.generateRandomArray(10000, 0, 100000);
        SortHelper.testSort("com.iamxgw.sort.selectionSort.SelectionSort", arr);
    }
}
