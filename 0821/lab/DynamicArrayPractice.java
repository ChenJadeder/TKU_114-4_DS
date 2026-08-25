// 課堂實作題五：Dynamic array 插入與刪除
//指定檔名：DynamicArrayPractice.java

class DynamicArray<T> {
    private Object[] data;
    private int size;

    DynamicArray(int capacity) {
        if (capacity <= 0) capacity = 1;
        data = new Object[capacity];
        size = 0;
    }

    void add(T value) {
        ensureCapacity();
        data[size] = value;
        size++;
    }

    void add(int index, T value) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException("index=" + index);
        ensureCapacity();
        for (int i = size - 1; i >= index; i--) {
            data[i + 1] = data[i];
        }
        data[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    T get(int index) {
        checkIndex(index);
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    T set(int index, T value) {
        checkIndex(index);
        T old = (T) data[index];
        data[index] = value;
        return old;
    }

    @SuppressWarnings("unchecked")
    T remove(int index) {
        checkIndex(index);
        T removed = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        size--;
        data[size] = null; // is exist? no then null
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
            Object[] bigger = new Object[data.length * 2];
            for (int i = 0; i < data.length; i++) {
                bigger[i] = data[i];
            }
            data = bigger;
            System.out.println("resize -> " + data.length);
        }
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index);
    }

    @Override
    public String toString() {
        String s = "[";
        for (int i = 0; i < size; i++) {
            if (i > 0) s += ", ";
            s += data[i];
        }
        s += "]";
        return s;
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        DynamicArray<String> as = new DynamicArray<>(2);
        as.add("A");
        as.add("B");
        as.add(1, "X");
        System.out.println(as);
        System.out.println("get(2)=" + as.get(2));
        System.out.println("set(0, Z) old=" + as.set(0, "Z"));
        System.out.println(as);
        System.out.println("remove(1)=" + as.remove(1));
        System.out.println(as + " size=" + as.size() + " cap=" + as.capacity());

        DynamicArray<Integer> ai = new DynamicArray<>(1);
        ai.add(10);
        ai.add(20);
        System.out.println(ai);
        try {
            ai.get(-1);
        } catch (Exception e) {
            System.out.println("get(-1) error=" + e.getMessage());
        }
        try {
            ai.add(3, 99);
        } catch (Exception e) {
            System.out.println("add(index=size) error=" + e.getMessage());
        }
        DynamicArray<Integer> empty = new DynamicArray<>(1);
        try {
            empty.remove(0);
        } catch (Exception e) {
            System.out.println("remove(empty) error=" + e.getMessage());
        }
    }
}