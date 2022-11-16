package com.iamxgw.sort.insertionSort;

import com.iamxgw.sort.SortHelper;

public class InsertionSort {

    private InsertionSort(){}

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            for (int j = i; j > 0 && arr[j].compareTo(arr[j - 1]) < 0; --j) {
                swap(arr, j, j - 1);
            }
        }
    }

    public static void sort(Comparable[] arr, int l, int r) {
        for (int i = l + 1; i <= r; ++i) {
            Comparable e = arr[i];
            int j = i;
            for ( ; j > l && arr[j - 1].compareTo(e) > 0; --j) {
                arr[j] = arr[j - 1];
            }
            arr[j] = e;
        }
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {

//        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 100000);
        SortHelper.testSort("com.iamxgw.sort.selectionSort.SelectionSort", arr);
        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSort", arr);
    }
}
