package com.iamxgw.sort.maxHeap;

public class MaxHeap<Item extends Comparable> {

    private Item[] data;
    private int count;
    private int capacity;

    public MaxHeap(int capacity) {
        data = (Item[]) new Comparable[capacity + 1];
        this.capacity = capacity;
        this.count = 0;
    }

    public MaxHeap(Item[] arr, int n) {
        data = (Item[]) new Comparable[n + 1];
        for (int i = 0; i < n; ++i) {
            data[i + 1] = arr[i];
        }
        this.count = n;
        this.capacity = n;
        // 从第一个非叶子节点开始，逐渐的进行 shiftDown
        // 这个过程又叫 Heapify
        for (int i = count / 2; i >= 1; --i) {
            shiftDown(i);
        }
    }

    /**
     * 将 k 位置的元素下沉
     * 最大堆的核心辅助函数
     *
     * @param k
     */
    private void shiftDown(int k) {
        // 如果有子节点，循环才会继续
        // 如果有子节点，则一定有左子节点（2 * k）
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[j + 1].compareTo(data[j]) > 0) {
                j += 1;
            }
            if (data[k].compareTo(data[j]) >= 0) break;
            swap(data, k, j);
            k = j;
        }
    }

    /**
     * 将 k 位置的元素上浮
     *
     * @param k
     */
    private void shiftUp(int k) {
        while (k > 1 && data[k].compareTo(data[k / 2]) > 0) {
            swap(data, k, k / 2);
            k /= 2;
        }
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
     * 往最大堆中插入一个元素 item
     *
     * @param item
     */
    public void insert(Item item) {
        assert count + 1 <= capacity;
        ++count;
        data[count] = item;
        shiftUp(count);
    }

    /**
     * 获取最大堆中的堆顶元素
     *
     * @return
     */
    public Item getMax() {
        assert count > 0;
        return data[1];
    }

    /**
     * 在最大堆中取出堆顶元素
     *
     * @return
     */
    public Item extractMax() {
        assert count > 0;
        Item ret = data[1];
        data[1] = data[count];
        --count;
        shiftDown(1);
        return ret;
    }

    private static void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // 测试 MaxHeap
    public static void main(String[] args) {

        int N = 10000; // 堆中元素个数
        int M = 10000; // 堆中元素取值范围 [0, M)
        MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(N);
        for (int i = 0; i < N; i++) {
            maxHeap.insert(new Integer((int) (Math.random() * M)));
        }
        Integer[] arr = new Integer[N];
        // 将 maxheap 中的数据逐渐使用 extractMax 取出来
        // 取出来的顺序应该是按照从大到小的顺序取出来的
        for (int i = 0; i < N; i++) {
            arr[i] = maxHeap.extractMax();
            System.out.print(arr[i] + " ");
        }
        System.out.println();
        // 确保 arr 数组是从大到小排列的
        for (int i = 1; i < N; i++) {
            assert arr[i - 1].compareTo(arr[i]) >= 0;
        }
    }
}
