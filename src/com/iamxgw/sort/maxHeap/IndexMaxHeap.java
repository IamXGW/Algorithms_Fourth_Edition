package com.iamxgw.sort.maxHeap;

public class IndexMaxHeap<Item extends Comparable> {

    private Item[] data;
    private int[] indexes;
    private int count;
    private int capacity;

    /**
     * 引入 IndexMaxHeap
     *
     * 1）如果 data 中每个数据都很大，那么我们每次都操作 data 都很耗时
     * 2）如果我们想将 索引和数据 绑定在一起，比如，我们想根据 idx 来修改一下 data 中的内容，那 MaxHeap 是做不到的。
     * 因为，MaxHeap 中 data 的元素交换位置后，就和原来的索引失去了联系
     */

    public IndexMaxHeap(int capacity) {
        data = (Item[]) new Comparable[capacity + 1];
        indexes = new int[capacity + 1];
        this.capacity = capacity;
        this.count = 0;
    }

    public IndexMaxHeap(Item[] arr, int n) {
        data = (Item[]) new Comparable[n + 1];
        indexes = new int[n + 1];
        for (int i = 0; i < n; ++i) {
            data[i + 1] = arr[i];
            indexes[i + 1] = i + 1;
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
     * @param k
     */
    private void shiftDown(int k) {
        while (2 * k <= count) {
            int j = 2 * k;
            if (j + 1 <= count && data[indexes[j + 1]].compareTo(data[indexes[j]]) > 0) {
                j += 1;
            }
            if (data[indexes[k]].compareTo(data[indexes[j]]) >= 0) break;
            swapIndex(indexes, k, j);
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
            swapIndex(indexes, k, k / 2);
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
     * 往最大堆中下标位置 i（0-indexed）插入一个元素 item
     *
     * @param item
     */
    public void insert(int i, Item item) {
        assert count + 1 <= capacity;
        i += 1;
        assert i >= 1 && i <= capacity;

        data[i] = item;
        indexes[count + 1] = i;
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
        i += 1;
        assert i >= 1 && i <= capacity;
        return data[i];
    }

    /**
     * 将堆中索引 i（0-indexed）上的元素更改为 newItem
     * @param i
     * @param newItem
     */
    public void change(int i, Item newItem) {
        i += 1;
        assert i >= 1 && i <= capacity;
        data[i] = newItem;
        for (int j = 1; j <= count; ++j) {
            if (indexes[j] == i) {
                shiftDown(j);
                shiftUp(j);
                return;
            }
        }
    }

    private void swap(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    private void swapIndex(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // 测试 IndexMaxHeap
    public static void main(String[] args) {
        int N = 10000; // 堆中元素个数
        int M = 10000; // 堆中元素取值范围 [0, M)
        Integer[] arr = new Integer[N];
        for (int i = 0; i < N; i++) {
            arr[i] = new Integer((int) (Math.random() * M));
        }
        testIndexMapHeap(arr, M);
        testHeapifyIndexMaxHeap(arr, M);
    }

    private static void testIndexMapHeap(Integer[] arr, int M) {
        int N = arr.length;
        IndexMaxHeap<Integer> indexMaxHeap = new IndexMaxHeap<Integer>(N);
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
        // 随机 change maxheap 中的值
        for (int i = 0; i < 100; ++i) {
            indexMaxHeap.change((int) (Math.random() * N + 1), (int) (Math.random() * M));
        }
        // 确保 arr 数组是从大到小排列的
        for (int i = 1; i < N; i++) {
            assert arr[i - 1].compareTo(arr[i]) >= 0;
        }
    }

    private static void testHeapifyIndexMaxHeap(Integer[] arr, int M) {
        int N = arr.length;
        IndexMaxHeap<Integer> indexMaxHeap = new IndexMaxHeap<Integer>(arr, N);
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
        // 随机 change maxheap 中的值
        for (int i = 0; i < 100; ++i) {
            indexMaxHeap.change((int) (Math.random() * N + 1), (int) (Math.random() * M));
        }
        // 确保 arr 数组是从大到小排列的
        for (int i = 1; i < N; i++) {
            assert arr[i - 1].compareTo(arr[i]) >= 0;
        }
    }
}
