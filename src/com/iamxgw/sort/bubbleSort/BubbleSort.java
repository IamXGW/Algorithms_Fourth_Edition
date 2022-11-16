package com.iamxgw.sort.bubbleSort;

import com.iamxgw.sort.SortHelper;

import java.util.Arrays;

public class BubbleSort {

    private BubbleSort(){}

    public static void sort(Comparable[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; ++i) {
            boolean tag = false;
            for (int j = 0; j < n - i; ++j) {
                if (arr[j].compareTo(arr[j + 1]) > 0) {
                    swap(arr, j, j + 1);
                    tag = true;
                }
            }
            // 改进点，如果内层 for 循环没有交换，则说明数组已经有序，可以提前终止
            if (!tag) {
                break;
            }
        }
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
//        Integer[] arr = SortHelper.generateRandomArray(10000, 0, 100000);
//        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
//        SortHelper.testSort("com.iamxgw.sort.bubbleSort.BubbleSort", arr);
//        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSortAdvance", arr1);

        Integer[] arr = SortHelper.generateNearlyOrderedArray(10000, 0);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.bubbleSort.BubbleSort", arr);
        SortHelper.testSort("com.iamxgw.sort.insertionSort.InsertionSortAdvance", arr1);
    }
}
