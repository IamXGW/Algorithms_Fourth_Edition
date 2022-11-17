package com.iamxgw.sort.quickSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.insertionSort.InsertionSort;

import java.util.Arrays;

public class QuickSort2Ways {

    private QuickSort2Ways(){}

    // 双路快速排序
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    // 对 arr[l, r] 的数据进行排序
    private static void sort(Comparable[] arr, int l, int r) {
        // 优化 1：对于小规模的数组使用插入排序
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }
        int p = partitioin(arr, l, r);
        sort(arr, l, p - 1);
        sort(arr, p + 1, r);
    }

    // 对 arr[l, r] 的部分进行 partition 操作
    // 返回 p，使得 arr[l, p - 1] <= arr[p] ; arr[p + 1, r] >= arr[p]
    // 注意左右两边都含有等于 arr[p] 的数
    // 这样操作是为了，对于一个含有大量重复元素的数组，可以将左、右两边尽量的平分
    private static int partitioin(Comparable[] arr, int l, int r) {
        // 优化 2：随机在 arr[l, r] 中选择一个数作为标定点
        // 对于一个近乎有序的数组，这样可以防止左、右两边严重失衡，对于已经有序的数组会导致二叉树退化成链表
        swap(arr, l, (int) (Math.random() * (r - l + 1)) + l);
        Comparable v = arr[l];

        int i = l + 1, j = r;
        while (true) {
            while (i <= r && arr[i].compareTo(v) < 0) ++i;
            while (j >= l + 1 && arr[j].compareTo(v) > 0) --j;
            if (i > j) {
                break;
            }
            swap(arr, i, j);
            ++i;
            --j;
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
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 10);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort", arr);
        SortHelper.testSort("com.iamxgw.sort.quickSort.QuickSort2Ways", arr1);
    }
}
