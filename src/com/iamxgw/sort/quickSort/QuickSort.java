package com.iamxgw.sort.quickSort;

import com.iamxgw.sort.SortHelper;

import java.util.Arrays;

public class QuickSort {

    private QuickSort(){}

    // 原始快速排序
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    // 对 arr[l, r] 的数据进行排序
    private static void sort(Comparable[] arr, int l, int r) {
        if (l >= r) {
            return;
        }
        int p = partition(arr, l, r);
        sort(arr, l, p - 1);
        sort(arr, p + 1, r);
    }

    // 对 arr[l, r] 的部分进行 partition 操作
    // 返回 p，使得 arr[l, p - 1] < arr[p] ; arr[p + 1, r] >= arr[p]
    private static int partition(Comparable[] arr, int l, int r) {
        Comparable v = arr[l];
        int j = l;
        for (int i = l + 1; i <= r; ++i) {
            if (arr[i].compareTo(v) < 0) {
                ++j;
                swap(arr, j, i);
            }
        }
        swap(arr, l, j);
        return j;
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 1000000);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort", arr);
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort2", arr1);
    }
}
