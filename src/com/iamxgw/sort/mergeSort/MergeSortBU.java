package com.iamxgw.sort.mergeSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.insertionSort.InsertionSort;

import java.util.Arrays;

public class MergeSortBU {

    private MergeSortBU(){}

    // 自底向上的归并排序，适用于链表排序
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        // 优化 1：小数组使用插入排序
        for (int sz = 0; sz < n; sz += 16) {
            InsertionSort.sort(arr, 0, Math.min(15, n - 1));
        }
        for (int sz = 16; sz < n; sz += sz) {
            for (int i = 0; i + sz < n; i += sz + sz) {
                // 对 arr[i, i + sz - 1] 和 arr[i + sz, i + sz + sz - 1] 两部分进行归并
                // i + sz < n 保证了右半部分的左边界存在；Math.min(n - 1, i + sz + sz - 1) 保证了右半部分的右边界不会越界
                // 优化 2：如果 arr[mid] <= arr[mid + 1] 则不需要合并
                if (arr[i + sz - 1].compareTo(arr[i + sz]) > 0) {
                    merge(arr, i, i + sz - 1, Math.min(n - 1, i + sz + sz - 1));
                }
            }
        }
        /**
         * 对于边界情况的一点思考：如果左半部分的长度也不够 sz 怎么办呢？
         * 这种情况下，merge 调用虽然可能会传一个不合法的 mid 值（mid 值大于数组长度），但是同时也会传一个一定合法的 r 值，
         * 因为 Math.min(n - 1, i + sz + sz - 1) 保证了 r 值一定是合法的。
         * 那么到了 merge 函数中，for 循环的条件是 (int k = l; k <= r; ++k)，此时一定是可以保证 i，j，k 不会越界的
         */
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
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 12);
//        Integer[] arr = SortHelper.generateNearlyOrderedArray(100000, 10);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort", arr);
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSort2", arr1);
        SortHelper.testSort("com.iamxgw.sort.mergeSort.MergeSortBU", arr2);
    }
}
