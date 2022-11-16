package com.iamxgw.sort.mergeSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.insertionSort.InsertionSort;

import java.util.Arrays;

public class MergeSort2 {

    private MergeSort2(){}

    // 自顶向下的归并排序算法（含有 2 个优化方案）
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    private static void sort(Comparable[] arr, int l, int r) {
        // 优化 1：对于少量的数据（15 个）使用插入排序进行优化
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }
        int mid = l + r >> 1;
        sort(arr, l, mid); // 排序左半边
        sort(arr, mid + 1, r); // 排序右半边
        // 优化 2：如果 arr[mid] 已经小于等于 arr[mid + 1] 了，则不进行归并，因为此时左、右两部分已经都有序了
        // 此优化对于近乎有序的数组非常有效，但是对于一般情况，会有一定的性能损失，因为每次都需要判断以下 if 条件
        if (arr[mid].compareTo(mid + 1) > 0) {
            merge(arr, l, mid, r); // 归并左、右两边
        }
    }

    // 将 arr 的 [l, mid] 和 [mid + 1, r] 两个部分进行归并
    private static void merge(Comparable[] arr, int l, int mid, int r) {
        // Auxiliary
        Comparable[] aux = Arrays.copyOfRange(arr, l, r + 1);

        int i = l, j = mid + 1;
        for (int k = l; k <= r; ++k) {
            if (i > mid) {  // 左半部分已经处理完毕
                arr[k] = aux[j - l];
                ++j;
            } else if (j > r) { // 右半部分已经处理完毕
                arr[k] = aux[i - l];
                ++i;
            } else if (aux[i - l].compareTo(aux[j - l]) < 0) {  // 左半部分所指元素 < 右半部分所指元素
                arr[k] = aux[i - l];
                ++i;
            } else {    // 左半部分所指元素 >= 右半部分所指元素
                arr[k] = aux[j - l];
                ++j;
            }
        }
    }

    public static void main(String[] args) {
//        Integer[] arr = SortHelper.generateRandomArray(10000000, 0, Integer.MAX_VALUE);
//        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
//        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort", arr);
        Integer[] arr1 = {1,2,3,4,5,6,7,8,9};
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort2", arr1);
    }
}
