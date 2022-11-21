package com.iamxgw.sort.maxHeap;

import java.util.Arrays;

public class IndexMaxHeapAdvanced<Item extends Comparable> {

    private Item[] data;
    private int[] indexes;
    private int[] reverse;
    private int count;
    private int capacity;

    /**
     * 使用 reverse 优化的 IndexMaxHeap
     */

    public IndexMaxHeapAdvanced(int capacity) {
        data = (Item[]) new Comparable[capacity + 1];
        indexes = new int[capacity + 1];
        reverse = new int[capacity + 1];
//        for (int i = 0; i <= capacity; ++i) {
//            reverse[i] = 0;
//        }
        this.capacity = capacity;
        this.count = 0;
    }

    private void shiftDown(int k) {
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[indexes[j + 1]].compareTo(data[indexes[j]]) > 0) {
                j += 1;
            }
            if (data[indexes[k]].compareTo(data[indexes[j]]) >= 0) break;
            swapIndex(indexes, reverse, k, j);
            k = j;
        }
    }

    /**
     * 将 k 位置的元素上浮
     *
     * @param k
     */
    private void shiftUp(int k) {
        while (k > 1 && data[indexes[k]].compareTo(data[indexes[k / 2]]) > 0) {
            swapIndex(indexes, reverse, k, k / 2);
            k /= 2;
        }
    }

    /**
     * 检查索引位置 i（0-indexed）是否存在元素
     *
     * @param i
     * @return
     */
    private boolean contain(int i) {
        i += 1;
        assert i >= 1 && i <= capacity;
        return reverse[i] != 0;
    }

    /**
     * 返回当前堆大小
     *
     * @return
     */
    public int size() {
        return count;
    }

    /**
     * 返回当前堆是不会空
     *
     * @return
     */
    public boolean isEmpty() {
        return count == 0;
    }

    /**
     * 往最大堆中下标位置 i（0-indexed）插入一个元素 item
     *
     * @param item
     */
    public void insert(int i, Item item) {
        assert contain(i);
        assert count + 1 <= capacity;
        i += 1;
        assert i >= 1 && i <= capacity;

        data[i] = item;
        indexes[count + 1] = i;
        reverse[i] = count + 1;
        ++count;
        shiftUp(count);
    }

    /**
     * 获取最大堆中的堆顶元素
     *
     * @return
     */
    public Item getMax() {
        assert count > 0;
        return data[indexes[1]];
    }

    /**
     * 在最大堆中取出堆顶元素
     *
     * @return
     */
    public Item extractMax() {
        assert count > 0;
        Item ret = data[indexes[1]];
        indexes[1] = indexes[count];
        reverse[indexes[count]] = 0;
        --count;
        shiftDown(1);
        return ret;
    }

    /**
     * 提取最大元素的下标
     *
     * @return
     */
    public int extractMaxIndex() {
        assert count > 0;
        int ret = indexes[1] - 1;
        indexes[1] = indexes[count];
        reverse[indexes[count]] = 0;
        --count;
        shiftDown(1);
        return ret;
    }

    /**
     * 根据下标提取堆中元素，下标 0-indexed
     *
     * @param i
     * @return
     */
    public Item getItem(int i) {
        assert contain(i);
        i += 1;
        assert i >= 1 && i <= capacity;
        return data[i];
    }

    /**
     * 将堆中索引 i（0-indexed）上的元素更改为 newItem
     * 通过使用 reverse 数组，将其时间复杂度从 O(N + logN) 优化到 O(LogN)
     *
     * @param i
     * @param newItem
     */
    public void change(int i, Item newItem) {
        assert contain(i);
        i += 1;
        assert i >= 1 && i <= capacity;
        data[i] = newItem;
//        for (int j = 1; j <= count; ++j) {
//            if (indexes[j] == i) {
//                shiftDown(j);
//                shiftUp(j);
//                return;
//            }
//        }
        int j = reverse[i];
        shiftDown(j);
        shiftUp(j);
    }

    private void swapIndex(int[] indexes, int[] reverse, int i, int j) {
        int t = indexes[i];
        indexes[i] = indexes[j];
        indexes[j] = t;

        reverse[indexes[i]] = j;
        reverse[indexes[j]] = i;
    }

    // 测试 IndexMaxHeap
    public static void main(String[] args) {

        int N = 1000000;
        IndexMaxHeapAdvanced<Integer> indexMaxHeap = new IndexMaxHeapAdvanced<Integer>(N);
        for (int i = 0; i < N; i++)
            indexMaxHeap.insert(i, (int) (Math.random() * N));
        assert indexMaxHeap.testIndexes();
    }

    private static void testIndexMapHeap(Integer[] arr, int M) {
        int N = arr.length;
        IndexMaxHeapAdvanced<Integer> indexMaxHeap = new IndexMaxHeapAdvanced<Integer>(N);
        for (int i = 0; i < N; ++i) {
            indexMaxHeap.insert(i, arr[i]);
        }
        for (int i = 0; i < N; i++) {
            Integer maxVal = indexMaxHeap.getMax();
            arr[i] = indexMaxHeap.extractMax();
            assert maxVal.equals(arr[i]);
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        // 确保 arr 数组是从大到小排列的
        for (int i = 1; i < N; i++) {
            assert arr[i - 1].compareTo(arr[i]) >= 0;
        }
        // 随机 change indexMaxHeap 中的值
        for (int i = 0; i < 100; ++i) {
            indexMaxHeap.change((int) (Math.random() * N + 1), (int) (Math.random() * M));
        }
        // 确保 arr 数组是从大到小排列的
        for (int i = 1; i < N; i++) {
            assert arr[i - 1].compareTo(arr[i]) >= 0;
        }
    }

    // 测试索引堆中的索引数组index和反向数组reverse
    // 注意:这个测试在向堆中插入元素以后, 不进行extract操作有效
    public boolean testIndexes() {

        int[] copyIndexes = new int[count + 1];
        int[] copyReverseIndexes = new int[count + 1];

        for (int i = 0; i <= count; i++) {
            copyIndexes[i] = indexes[i];
            copyReverseIndexes[i] = reverse[i];
        }

        copyIndexes[0] = 0;
        copyReverseIndexes[0] = 0;
        Arrays.sort(copyIndexes);
        Arrays.sort(copyReverseIndexes);

        // 在对索引堆中的索引和反向索引进行排序后,
        // 两个数组都应该正好是1...count这count个索引
        boolean res = true;
        for (int i = 1; i <= count; i++)
            if (copyIndexes[i - 1] + 1 != copyIndexes[i] ||
                    copyReverseIndexes[i - 1] + 1 != copyReverseIndexes[i]) {
                res = false;
                break;
            }

        if (!res) {
            System.out.println("Error!");
            return false;
        }

        return true;
    }
}
