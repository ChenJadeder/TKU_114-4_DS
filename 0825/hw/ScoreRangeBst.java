// 課後作業三：排名範圍查詢
// 指定檔名：ScoreRangeBst.java

class ScoreStudent {
    private String studentId;
    private String name;
    private int score;

    ScoreStudent(String studentId, String name, int score) {
        this.studentId = (studentId == null) ? "UNKNOWN" : studentId.trim();
        this.name = (name == null) ? "Unknown" : name.trim();

        if (score < 0) {
            this.score = 0;
        } else if (score > 100) {
            this.score = 100;
        } else {
            this.score = score;
        }
    }

    String getStudentId() {
        return studentId;
    }

    int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score;
    }
}

class ScoreNode {
    ScoreStudent student;
    ScoreNode left;
    ScoreNode right;

    ScoreNode(ScoreStudent student) {
        this.student = student;
    }
}

class ScoreRangeTree {
    private ScoreNode root;

    // 先比 score，同分再比 studentId
    private int compare(ScoreStudent first, ScoreStudent second) {
        if (first.getScore() < second.getScore()) {
            return -1;
        }

        if (first.getScore() > second.getScore()) {
            return 1;
        }

        return first.getStudentId().compareTo(second.getStudentId());
    }

    boolean add(ScoreStudent student) {
        if (student == null) {
            return false;
        }

        if (root == null) {
            root = new ScoreNode(student);
            return true;
        }

        ScoreNode current = root;

        while (true) {
            int result = compare(student, current.student);

            if (result == 0) {
                return false;
            }

            if (result < 0) {
                if (current.left == null) {
                    current.left = new ScoreNode(student);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ScoreNode(student);
                    return true;
                }
                current = current.right;
            }
        }
    }

    ScoreStudent find(int score, String studentId) {
        ScoreStudent target = new ScoreStudent(studentId, "", score);
        ScoreNode current = root;

        while (current != null) {
            int result = compare(target, current.student);

            if (result == 0) {
                return current.student;
            }

            if (result < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    void printRange(int low, int high) {
        if (low > high) {
            System.out.println("range " + low + "~" + high + ": invalid");
            return;
        }

        System.out.println("range " + low + "~" + high + ":");
        printRange(root, low, high);
    }

    private void printRange(ScoreNode node, int low, int high) {
        if (node == null) {
            return;
        }

        int score = node.student.getScore();

        if (score >= low) {
            printRange(node.left, low, high);
        }

        if (score >= low && score <= high) {
            System.out.println("  " + node.student);
        }

        if (score <= high) {
            printRange(node.right, low, high);
        }
    }

    void inorder() {
        System.out.println("inorder:");
        inorder(root);
    }

    private void inorder(ScoreNode node) {
        if (node == null) {
            return;
        }

        inorder(node.left);
        System.out.println("  " + node.student);
        inorder(node.right);
    }

    int size() {
        return size(root);
    }

    private int size(ScoreNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }
}

public class ScoreRangeBst {
    public static void main(String[] args) {
        ScoreRangeTree tree = new ScoreRangeTree();

        System.out.println("add Amy=" +
                tree.add(new ScoreStudent("S101", "Amy", 88)));
        System.out.println("add Ben=" +
                tree.add(new ScoreStudent("S102", "Ben", 75)));
        System.out.println("add Cara=" +
                tree.add(new ScoreStudent("S103", "Cara", 92)));
        System.out.println("add Dan=" +
                tree.add(new ScoreStudent("S104", "Dan", 75)));
        System.out.println("add Eve=" +
                tree.add(new ScoreStudent("S105", "Eve", 60)));
        System.out.println("add Finn=" +
                tree.add(new ScoreStudent("S106", "Finn", 95)));

        // 同分但不同 studentId，可加入
        System.out.println("add same score=" +
                tree.add(new ScoreStudent("S107", "Gina", 88)));

        // score 與 studentId 都相同，視為重複
        System.out.println("add duplicate=" +
                tree.add(new ScoreStudent("S101", "Amy Copy", 88)));

        tree.inorder();
        System.out.println("size=" + tree.size());

        System.out.println("find 75/S104=" + tree.find(75, "S104"));
        System.out.println("find 75/S999=" + tree.find(75, "S999"));

        tree.printRange(75, 90);
        tree.printRange(88, 88);
        tree.printRange(95, 100);
        tree.printRange(90, 70);
    }
}