// 課後作業一：學號索引
//指定檔名：`StudentBstIndex.java`。
class Student {
    int studentId;
    String name;
    double gpa;

    Student(int studentId, String name, double gpa) {
        this.studentId = studentId;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return String.format("[學號: %d | 姓名: %-8s | GPA: %.2f]", studentId, name, gpa);
    }
}

class StudentNode {
    Student student;
    StudentNode left;
    StudentNode right;

    StudentNode(Student student) {
        this.student = student;
    }
}

public class StudentBstIndex {
    private StudentNode root;

    // 1. 新增index
    public boolean insert(Student student) {
        if (student == null) return false;
        if (root == null) {
            root = new StudentNode(student);
            return true;
        }
        return insertHelper(root, student);
    }

    private boolean insertHelper(StudentNode current, Student student) {
        // 重複 
        if (student.studentId == current.student.studentId) {
            return false; 
        }

        if (student.studentId < current.student.studentId) {
            if (current.left == null) {
                current.left = new StudentNode(student);
                return true;
            }
            return insertHelper(current.left, student);
        } else {
            if (current.right == null) {
                current.right = new StudentNode(student);
                return true;
            }
            return insertHelper(current.right, student);
        }
    }

    // 2. 依學號search
    public Student search(int studentId) {
        return searchHelper(root, studentId);
    }

    private Student searchHelper(StudentNode current, int studentId) {
        if (current == null) return null;

        if (studentId == current.student.studentId) {
            return current.student;
        }
        if (studentId < current.student.studentId) {
            return searchHelper(current.left, studentId);
        } else {
            return searchHelper(current.right, studentId);
        }
    }

    // 3. 依學號delete
    public boolean delete(int studentId) {
        if (search(studentId) == null) {
            return false; // 找不到該學號，刪除失敗
        }
        root = deleteHelper(root, studentId);
        return true;
    }

    private StudentNode deleteHelper(StudentNode current, int studentId) {
        if (current == null) return null;

        if (studentId < current.student.studentId) {
            current.left = deleteHelper(current.left, studentId);
        } else if (studentId > current.student.studentId) {
            current.right = deleteHelper(current.right, studentId);
        } else {
            // 找到目標節點，依 Child 數量分三種情況處理
            
            // 無 child 或僅有單一 child
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // 擁有兩個 children 則尋找右子樹中的最小值進行替代
            StudentNode minNode = findMin(current.right);
            current.student = minNode.student; // 覆蓋當前節點數值
            current.right = deleteHelper(current.right, minNode.student.studentId); // 刪除原 Successor
        }
        return current;
    }

    private StudentNode findMin(StudentNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    // 4. 中序走訪 (Inorder Display) - 印出按學號排序後的結果
    public void printInOrder() {
        System.out.println("--- 學號索引清單 (升冪) ---");
        inOrderHelper(root);
        System.out.println("---------------------------");
    }

    private void inOrderHelper(StudentNode node) {
        if (node == null) return;
        inOrderHelper(node.left);
        System.out.println(node.student);
        inOrderHelper(node.right);
    }

    // 主程式
    public static void main(String[] args) {
        StudentBstIndex bst = new StudentBstIndex();

        System.out.println("=== 1. 測試學號 ===");
        System.out.println("新增 103: " + bst.insert(new Student(103, "Alice", 3.8)));
        System.out.println("新增 101: " + bst.insert(new Student(101, "Bob", 3.5)));
        System.out.println("新增 105: " + bst.insert(new Student(105, "Charlie", 3.9)));
        System.out.println("新增 102: " + bst.insert(new Student(102, "David", 3.2)));
        System.out.println("新增 104: " + bst.insert(new Student(104, "Eve", 3.7)));

        System.out.println("\n=== 2. 測試重複學號阻擋 ===");
        System.out.println("嘗試新增已存在的 103: " + bst.insert(new Student(103, "DuplicateAlice", 4.0)));

        System.out.println();
        bst.printInOrder();

        System.out.println("\n=== 3. 測試搜尋功能 ===");
        Student s1 = bst.search(102);
        System.out.println("搜尋 102 號: " + (s1 != null ? s1 : "未找到"));
        Student s2 = bst.search(999);
        System.out.println("搜尋 999 號: " + (s2 != null ? s2 : "未找到"));

        System.out.println("\n=== 4. 測試三種刪除情況 ===");
        //刪除 Leaf 節點 (104)
        System.out.println("刪除 Leaf (104): " + bst.delete(104));
        
        //刪除單一 Child 節點 (101)
        System.out.println("刪除 Single-Child Node (101): " + bst.delete(101));
        
        //刪除兩個 Children 的 Root 節點 (103)
        System.out.println("刪除 Two-Children Root (103): " + bst.delete(103));

        System.out.println();
        bst.printInOrder();
    }
}
