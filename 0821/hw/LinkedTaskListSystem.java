// 課後作業五：單向鏈結清單
//指定檔名：LinkedTaskListSystem.java

class Task {
    private final String id;
    private final String title;

    Task(String id, String title) {
        this.id = (id == null) ? "UNKNOWN" : id.trim();
        this.title = (title == null) ? "Untitled" : title.trim();
    }

    String getId() { return id; }

    @Override
    public String toString() {
        return id + " " + title;
    }
}

class TaskNode {
    Task value;
    TaskNode next;
    TaskNode(Task value) { this.value = value; }
}

class TaskLinkedList {
    private TaskNode head;
    private int size;

    private boolean exists(String id) {
        TaskNode cur = head;
        while (cur != null) {
            if (cur.value.getId().equals(id)) return true;
            cur = cur.next;
        }
        return false;
    }

    boolean addFirst(Task t) {
        if (t == null) return false;
        if (exists(t.getId())) return false;
        TaskNode node = new TaskNode(t);
        node.next = head;
        head = node;
        size++;
        return true;
    }

    boolean addLast(Task t) {
        if (t == null) return false;
        if (exists(t.getId())) return false;
        TaskNode node = new TaskNode(t);
        if (head == null) {
            head = node;
        } else {
            TaskNode cur = head;
            while (cur.next != null) cur = cur.next;
            cur.next = node;
        }
        size++;
        return true;
    }

    Task findById(String id) {
        TaskNode cur = head;
        while (cur != null) {
            if (cur.value.getId().equals(id)) return cur.value;
            cur = cur.next;
        }
        return null;
    }

    boolean removeById(String id) {
        if (head == null) return false;
        if (head.value.getId().equals(id)) {
            head = head.next;
            size--;
            return true;
        }
        TaskNode cur = head;
        while (cur.next != null) {
            if (cur.next.value.getId().equals(id)) {
                cur.next = cur.next.next;
                size--;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    boolean insertAfter(String existingId, Task t) {
        if (t == null) return false;
        if (exists(t.getId())) return false;
        TaskNode cur = head;
        while (cur != null) {
            if (cur.value.getId().equals(existingId)) {
                TaskNode node = new TaskNode(t);
                node.next = cur.next;
                cur.next = node;
                size++;
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    int size() { return size; }

    void printAll() {
        StringBuilder sb = new StringBuilder("[");
        TaskNode cur = head;
        while (cur != null) {
            sb.append(cur.value);
            cur = cur.next;
            if (cur != null) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());
    }
}

public class LinkedTaskListSystem {
    public static void main(String[] args) {
        TaskLinkedList list = new TaskLinkedList();
        //  first empty list 
        list.printAll();
 
        list.addFirst(new Task("T1", "Boot"));
        list.addLast(new Task("T2", "Load"));
        list.addLast(new Task("T3", "Run"));
        list.addLast(new Task("T2", "Dup")); // 重複 id -> false
        list.printAll();

        // find
        System.out.println("find T2=" + list.findById("T2"));

        // insertAfter middle
        list.insertAfter("T2", new Task("T4", "Patch"));
        list.printAll();

        // remove head / middle / tail / not found
        System.out.println("remove T1=" + list.removeById("T1")); // head
        list.printAll();
        System.out.println("remove T4=" + list.removeById("T4")); // middle
        list.printAll();
        System.out.println("remove T3=" + list.removeById("T3")); // tail
        list.printAll();
        System.out.println("remove X=" + list.removeById("X"));   // not found
        list.printAll();
        System.out.println("size=" + list.size());
    }
}