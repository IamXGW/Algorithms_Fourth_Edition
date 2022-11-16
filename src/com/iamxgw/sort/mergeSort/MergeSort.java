package com.iamxgw.sort.mergeSort;

import com.iamxgw.sort.SortHelper;

import java.util.Arrays;

public class MergeSort {

    private MergeSort(){}

    // 自顶向下的归并排序算法（原始实现，无优化）
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        sort(arr, 0, n - 1);
    }

    private static void sort(Comparable[] arr, int l, int r) {
        // 如果 arr 只包含一个元素，则该 arr 已经有序，可以返回
        if (l >= r) {
            return;
        }
        int mid = l + r >> 1;
        sort(arr, l, mid); // 排序左半边
        sort(arr, mid + 1, r); // 排序右半边
        merge(arr, l, mid, r); // 归并左、右两边
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
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 100000);
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort", arr);
    }
}
