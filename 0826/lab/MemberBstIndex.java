// 課堂實作題六：Member BST Index
// 指定檔名：MemberBstIndex.java
// BST的方向由memberID決定
import java.util.ArrayList;
import java.util.List;

class Member {
    final int memberId;
    final String name;
    private String email;

    Member(int memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    String getEmail() {
        return email;
    }

    void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Member{id=" + memberId
                + ", name='" + name
                + "', email='" + email + "'}";
    }
}

class MemberNode {
    Member data;
    MemberNode left;
    MemberNode right;

    MemberNode(Member data) {
        this.data = data;
    }
}

class MemberBst {
    private MemberNode root;

    boolean add(Member member) {
        if (member == null || !isValidEmail(member.getEmail())) {
            return false;
        }

        if (root == null) {
            root = new MemberNode(member);
            return true;
        }

        MemberNode current = root;

        while (true) {
            if (member.memberId == current.data.memberId) {
                return false;
            }

            if (member.memberId < current.data.memberId) {
                if (current.left == null) {
                    current.left = new MemberNode(member);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new MemberNode(member);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Member find(int memberId) {
        MemberNode current = root;

        while (current != null) {
            if (memberId == current.data.memberId) {
                return current.data;
            }

            current = memberId < current.data.memberId
                    ? current.left
                    : current.right;
        }

        return null;
    }

    boolean updateEmail(int memberId, String newEmail) {
        if (!isValidEmail(newEmail)) {
            return false;
        }

        Member member = find(memberId);

        if (member == null) {
            return false;
        }

        member.setEmail(newEmail.trim());
        return true;
    }

    boolean remove(int memberId) {
        if (find(memberId) == null) {
            return false;
        }

        root = remove(root, memberId);
        return true;
    }

    private MemberNode remove(MemberNode node, int memberId) {
        if (memberId < node.data.memberId) {
            node.left = remove(node.left, memberId);
        } else if (memberId > node.data.memberId) {
            node.right = remove(node.right, memberId);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            MemberNode successor = minimumNode(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data.memberId);
        }

        return node;
    }

    private MemberNode minimumNode(MemberNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    List<Member> inorder() {
        List<Member> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(MemberNode node, List<Member> result) {
        if (node == null) {
            return;
        }

        inorder(node.left, result);
        result.add(node.data);
        inorder(node.right, result);
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.trim().isEmpty();
    }
}

public class MemberBstIndex {
    private static void printReport(MemberBst index) {
        System.out.println("inorder report");

        for (Member member : index.inorder()) {
            System.out.println(member);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        MemberBst index = new MemberBst();

        System.out.println("add300="
                + index.add(new Member(300, "Mina", "mina@example.com")));

        System.out.println("add100="
                + index.add(new Member(100, "Leo", "leo@example.com")));

        System.out.println("add500="
                + index.add(new Member(500, "Nora", "nora@example.com")));

        System.out.println("add200="
                + index.add(new Member(200, "Ivy", "ivy@example.com")));

        System.out.println("duplicate="
                + index.add(new Member(100, "Other", "other@example.com")));

        System.out.println("blankEmail="
                + index.add(new Member(400, "Blank", "   ")));

        System.out.println("find200=" + index.find(200));
        System.out.println("find999=" + index.find(999));

        System.out.println("update200="
                + index.updateEmail(200, "ivy.new@example.com"));

        System.out.println("invalidUpdate="
                + index.updateEmail(200, " "));

        System.out.println("missingUpdate="
                + index.updateEmail(999, "missing@example.com"));

        printReport(index);

        System.out.println("remove300=" + index.remove(300));
        System.out.println("remove999=" + index.remove(999));

        printReport(index);
    }
}