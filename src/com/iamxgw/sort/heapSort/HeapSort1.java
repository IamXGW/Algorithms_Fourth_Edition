package com.iamxgw.sort.heapSort;

import com.iamxgw.sort.SortHelper;
import com.iamxgw.sort.maxHeap.MaxHeap;

public class HeapSort1 {

    private HeapSort1(){}

    /**
     * 使用 Heapify 的堆排序，优化了时间复杂度 O(NlogN) -> O(n)
     * 使用 MaxHeap 的 Heapify 构造方法构建最大堆，然后再 extractMax 并放入 arr 中
     * @param arr
     */
    public static void sort(Comparable[] arr) {
        int n = arr.length;
        MaxHeap<Comparable> maxHeap = new MaxHeap<>(arr, n);
        for (int i = n - 1; i >= 0; --i) {
            arr[i] = maxHeap.extractMax();
        }
    }

    public static void main(String[] args) {
        Integer[] arr = SortHelper.generateRandomArray(100000, 0, 100000);
        SortHelper.testSort("com.iamxgw.sort.heapSort.HeapSort1", arr);
    }
}
