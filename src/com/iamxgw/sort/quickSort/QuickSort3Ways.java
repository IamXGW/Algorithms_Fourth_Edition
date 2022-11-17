package com.iamxgw.sort.quickSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.insertionSort.InsertionSort;

import java.util.Arrays;

public class QuickSort3Ways {

    private QuickSort3Ways(){}

    // 三路快速排序
    // 处理有**大量重复值**的数组，三路快排优势非常大。其余情况三路快排也并不慢多少
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    // 对 arr[l, r] 的数据进行排序
    private static void sort(Comparable[] arr, int l, int r) {
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }
        swap(arr, l, (int) (Math.random() * (r - l + 1) + l));
        Comparable v = arr[l];
        int lt = l;     // arr[l + 1, lt] < v
        int gt = r + 1; // arr[gt, r] > v
        int i = l + 1;  // arr[lt + 1, i) == v
        while (i < gt) {
            if (arr[i].compareTo(v) < 0) {
                swap(arr, lt + 1, i);
                ++lt;
                ++i; // 换过来的是等于 v 的数，或者是 i 所指的数自己交换
            } else if (arr[i].compareTo(v) == 0) {
                ++i;
            } else { // arr[i].compareTo(v) > 0
                swap(arr, gt - 1, i);
                --gt;
                // ++i; // 不能 ++i，因为不知道换过来的数和 v 的大小关系，需要进入下个循环判断
            }
        }
        swap(arr, l, lt);
        sort(arr, l, lt - 1);
        sort(arr, gt, r);
    }

    public static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
//        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 10000);
//        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 10);
        Integer[] arr = SortHelper.generateNearlyOrderedArray(10000, 10);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort", arr);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort2Ways", arr1);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort3Ways", arr2);
    }
}
