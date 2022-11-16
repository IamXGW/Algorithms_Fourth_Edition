package com.iamxgw.sort.insertionSort;

import com.iamxgw.sort.SortHelper;

import java.util.Arrays;

public class InsertionSort2 {

    private InsertionSort2(){}

    public static void sort(Comparable[] arr) {
        // 每次只是将元素复制到正确的位置，并不发生交换
        // 越近乎有序，原始 InsertionSort 更快，越近乎乱序，改进版的 InsertionSort 更快
        int n = arr.length;
        for (int i = 0; i < n; ++i) {
            Comparable e = arr[i];
            int j = i;
            for ( ; j > 0 && arr[j - 1].compareTo(e) > 0; --j) {
                arr[j] = arr[j - 1];
            }
            arr[j] = e;
        }
    }

    public static void main(String[] args) {
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 100000);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);

        SortHelper.testSort("com.iamxgw.sort.selectionSort.SelectionSort", arr);
        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSort2", arr1);
        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSort", arr2);


//        Integer[] arr = SortHelper.generateNearlyOrderedArray(100000, 0);
//        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
//        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
//
//        SortHelper.testSort("com.iamxgw.sort.selectionSort.SelectionSort", arr);
//        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSort2", arr1);
//        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSort", arr2);
    }
}
