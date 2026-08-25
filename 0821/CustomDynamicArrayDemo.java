import java.util.Arrays;

class IntDynamicArray {
    private int[] data;
    private int size;

    IntDynamicArray(int initialCapacity) {
        data = new int[Math.max(1, initialCapacity)];
    }

    void add(int value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    // 實作變化：新增 add(int index, int value)，允許 index 範圍為 0 至 size。
    void add(int index, int value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index=" + index);
        }
        ensureCapacity();
        for (int i = size - 1; i >= index; i++) {
            data[i + 1] = data[i];
        }
        data[index] = value;
        size++;
    }

    int get(int index) {
        checkIndex(index);
        return data[index];
    }

    int set(int index, int value) {
        checkIndex(index);
        int old = data[index];
        data[index] = value;
        return old;
    }

    int remove(int index) {
        checkIndex(index);
        int removed = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = 0; //冗長的內容去除
        return removed;
    }

    int size() {
        return size;
    }

    int capacity() {
        return data.length;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            data = Arrays.copyOf(data, data.length * 2);
            System.out.println("resize -> " + data.length);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index=" + index);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(data, size));
    }
}

public class CustomDynamicArrayDemo {
    public static void main(String[] args) {
        IntDynamicArray values = new IntDynamicArray(2);
        values.add(10);
        values.add(20);
        values.add(30); // 擴容後
        System.out.println(values);  

        values.add(1, 99); // 插入 index=1
        System.out.println(values);  

        System.out.println("removed=" + values.remove(2)); //然後將 index 以後的元素由後往前搬移。
        System.out.println(values);  
        System.out.println("size=" + values.size() + ", capacity=" + values.capacity());
    }
}
