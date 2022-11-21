package com.iamxgw.sort.heapSort;

import com.iamxgw.sort.SortHelper;

import java.util.Arrays;

public class HeapSort2 {

    private HeapSort2() {
    }

    /**
     * 使用原地的堆排序，优化了空间复杂度 O(N) -> O(1)
     * 默认 arr 最后一个元素是已经排好序的（arr 中的最大值）
     * @param arr
     */
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        // 先将整个数组进行 Heapify
        for (int i = (n - 1 - 1) / 2; i >= 0; --i) {
            shiftDown(arr, i, n);
        }
        // 交换数组末尾的数和 arr[0]（堆中最大值），然后对刚换过来的 arr[0] shiftDown
        for (int i = n - 1; i > 0; --i) {
            swap(arr, i, 0);
            shiftDown(arr, 0, i);
        }
    }

    /**
     * 优化的 shiftDown，使用赋值操作取代不断的 swap 操作
     * @param arr
     * @param k
     * @param n
     */
    private static void shiftDown(Comparable[] arr, int k, int n) {
        Comparable e = arr[k];
        while (2 * k + 1 < n) {
            int j = 2 * k + 1;
            if (j + 1 < n && arr[j + 1].compareTo(arr[j]) > 0) {
                j += 1;
            }
            if (e.compareTo(arr[j]) >= 0) break;
            arr[k] = arr[j];
            k = j;
        }
        arr[k] = e;
    }

    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 100000);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.heapSort.HeapSort", arr);
        SortHelper.testSort("com.iamxgw.sort.heapSort.HeapSort1", arr1);
        SortHelper.testSort("com.iamxgw.sort.heapSort.HeapSort2", arr2);
        Arrays.sort(arr3);
        for (int i = 0; i < 100000; ++i) {
            if (arr[i].equals(arr1[i]) && arr[i].equals(arr2[i]) && arr[i].equals(arr3[i])) {

            } else {
                System.out.println("ERROR!");
            }
        }
    }
}
