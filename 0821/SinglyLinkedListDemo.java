class ListNode<T> {
    T value;
    ListNode<T> next;
    ListNode(T value) { this.value = value; }
}  // [node(value)|next]

class SimpleLinkedList<T> {
    private ListNode<T> head;
    private int size; // [head |size]

    void addFirst(T value) {
        ListNode<T> node = new ListNode<>(value);
        node.next = head;
        head = node;
        size++;
    }

    void addLast(T value) {
        ListNode<T> node = new ListNode<>(value);
        if (head == null) {
            head = node;
        } else {
            ListNode<T> cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        } //[head | node | next ]
        size++; 
    }

    T get(int index) {
        if (index < 0 || index >= size) throw new IndexOutOfBoundsException("index=" + index);
        ListNode<T> cur = head;
        for (int i = 0; i < index; i++) cur = cur.next;
        return cur.value;
    }

    boolean remove(T target) {
        if (head == null) return false;
        if (java.util.Objects.equals(head.value, target)) {
            head = head.next;
            size--;
            return true;
        }
        ListNode<T> cur = head;
        while (cur.next != null) {
            if (java.util.Objects.equals(cur.next.value, target)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    // 實作變化：移除並回傳 head；空 list 回傳 null
    T removeFirst() {
        if (head == null) return null;
        T val = head.value;
        head = head.next;
        size--;
        return val;
    }

    int size() { return size; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ListNode<T> cur = head;
        while (cur != null) {
            sb.append(cur.value);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        return sb.append("]").toString();
    }
}

public class SinglyLinkedListDemo {
    public static void main(String[] args) {
        SimpleLinkedList<String> list = new SimpleLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addFirst("X");

        System.out.println(list);                 // [X -> A -> B]
        System.out.println("index 1=" + list.get(1)); // A
        System.out.println("remove A=" + list.remove("A"));
        System.out.println(list + " size=" + list.size()); // [X, B] size=2

        System.out.println("removeFirst=" + list.removeFirst()); // X
        System.out.println(list + " size=" + list.size());       // [B] size=1
        System.out.println("removeFirst=" + list.removeFirst()); // B
        System.out.println("removeFirst=" + list.removeFirst()); // null
    }
}
