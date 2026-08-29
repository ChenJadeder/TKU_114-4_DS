import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {

    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0 || name == null || name.isBlank()) {
                throw new IllegalArgumentException("Invalid ID or Name");
            }
            this.id = id;
            this.name = name.trim();
            this.score = clampScore(score);
        }

        private static int clampScore(int score) {
            if (score < 0) return 0;
            if (score > 100) return 100;
            return score;
        }

        public int getId() { return id; }
        public String getName() { return name; }
        public int getScore() { return score; }

        public void setScore(int score) {
            this.score = clampScore(score);
        }

        @Override
        public String toString() {
            return id + "|" + name + "|" + score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    // 新增學生
    public boolean add(Student student) {
        if (student == null || find(student.getId()) != null) {
            return false;
        }
        root = addHelper(root, student);
        return true;
    }

    private Node addHelper(Node node, Student student) {
        if (node == null) return new Node(student);
        if (student.getId() < node.student.getId()) {
            node.left = addHelper(node.left, student);
        } else if (student.getId() > node.student.getId()) {
            node.right = addHelper(node.right, student);
        }
        return node;
    }

    //尋找學生
    public Student find(int id) {
        Node cur = root;
        while (cur != null) {
            if (id == cur.student.getId()) return cur.student;
            cur = (id < cur.student.getId()) ? cur.left : cur.right;
        }
        return null;
    }

    // 更新成績
    public boolean updateScore(int id, int score) {
        Student student = find(id);
        if (student == null) return false;
        student.setScore(score);
        return true;
    }

    // 刪除學生
    public boolean remove(int id) {
        if (find(id) == null) return false;
        root = removeHelper(root, id);
        return true;
    }

    private Node removeHelper(Node node, int id) {
        if (node == null) return null;

        if (id < node.student.getId()) {
            node.left = removeHelper(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = removeHelper(node.right, id);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node minNode = findMin(node.right);
            node.student = minNode.student;
            node.right = removeHelper(node.right, minNode.student.getId());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    //range search
    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<>();
        if (lowId > highId) return result;
        rangeHelper(root, lowId, highId, result);
        return result;
    }

    private void rangeHelper(Node node, int low, int high, List<Student> result) {
        if (node == null) return;

        if (node.student.getId() > low) {
            rangeHelper(node.left, low, high, result);
        }
        if (node.student.getId() >= low && node.student.getId() <= high) {
            result.add(node.student);
        }
        if (node.student.getId() < high) {
            rangeHelper(node.right, low, high, result);
        }
    }

    // inorder
    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }
}
