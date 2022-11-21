package com.iamxgw.sort.heapSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.maxHeap.MaxHeap;

import java.util.Arrays;

public class HeapSort {

    private HeapSort(){}

    /**
     * 原始堆排序
     * 先将 arr 中每个元素 insert 堆中，然后再从堆中 extractMax 放入 arr 中
     * @param arr
     */
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        MaxHeap<Comparable> maxHeap = new MaxHeap<>(n);
        for (int i = 0; i < n; ++i) {
            maxHeap.insert(arr[i]);
        }
        for (int i = n - 1; i >= 0; --i) {
            arr[i] = maxHeap.extractMax();
        }
    }

    public static void main(String[] args) {
        int n = 417;
        Integer[] arr = SortHelper.generateRandomArray(n, 0, 100000);
        Integer[] arr1 = Arrays.copyOf(arr, arr.length);
        SortHelper.testSort("com.iamxgw.sort.heapSort.HeapSort", arr);
        Arrays.sort(arr1);
        for (int i = 0; i < n; ++i) {
            if (!arr[i].equals(arr1[i])) System.out.println(i + "*" + "arr: " + arr[i] + ", arr1:" + arr1[i]);
        }
    }
}
