//課後作業二：課程代碼索引
//指定檔名：CourseBstIndex.java

import java.util.ArrayList;
import java.util.List;

class Course {
    final String courseCode;
    final String title;
    private int credit;

    Course(String courseCode, String title, int credit) {
        this.courseCode = courseCode;
        this.title = title;
        this.credit = credit;
    }

    int getCredit() {
        return credit;
    }

    void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public String toString() {
        return "Course{code='" + courseCode
                + "', title='" + title
                + "', credit=" + credit + "}";
    }
}

class CourseNode {
    Course data;
    CourseNode left;
    CourseNode right;

    CourseNode(Course data) {
        this.data = data;
    }
}

class CourseBst {
    private CourseNode root;

    boolean add(Course course) {
        if (!isValidCourse(course)) {
            return false;
        }

        if (root == null) {
            root = new CourseNode(course);
            return true;
        }

        CourseNode current = root;

        while (true) {
            int comparison = course.courseCode.compareTo(current.data.courseCode);

            if (comparison == 0) {
                return false;
            }

            if (comparison < 0) {
                if (current.left == null) {
                    current.left = new CourseNode(course);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new CourseNode(course);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Course find(String courseCode) {
        if (!isValidCode(courseCode)) {
            return null;
        }

        CourseNode current = root;

        while (current != null) {
            int comparison = courseCode.compareTo(current.data.courseCode);

            if (comparison == 0) {
                return current.data;
            }

            current = comparison < 0 ? current.left : current.right;
        }

        return null;
    }

    boolean updateCredit(String courseCode, int credit) {
        if (!isValidCredit(credit)) {
            return false;
        }

        Course course = find(courseCode);

        if (course == null) {
            return false;
        }

        course.setCredit(credit);
        return true;
    }

    boolean remove(String courseCode) {
        if (find(courseCode) == null) {
            return false;
        }

        root = remove(root, courseCode);
        return true;
    }

    private CourseNode remove(CourseNode node, String courseCode) {
        int comparison = courseCode.compareTo(node.data.courseCode);

        if (comparison < 0) {
            node.left = remove(node.left, courseCode);
        } else if (comparison > 0) {
            node.right = remove(node.right, courseCode);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            CourseNode successor = minimumNode(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data.courseCode);
        }

        return node;
    }

    private CourseNode minimumNode(CourseNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    List<Course> codeRange(String low, String high) {
        List<Course> result = new ArrayList<>();

        if (!isValidCode(low) || !isValidCode(high) || low.compareTo(high) > 0) {
            return result;
        }

        codeRange(root, low, high, result);
        return result;
    }

    private void codeRange(CourseNode node, String low, String high,
                           List<Course> result) {
        if (node == null) {
            return;
        }

        if (low.compareTo(node.data.courseCode) < 0) {
            codeRange(node.left, low, high, result);
        }

        if (low.compareTo(node.data.courseCode) <= 0
                && node.data.courseCode.compareTo(high) <= 0) {
            result.add(node.data);
        }

        if (node.data.courseCode.compareTo(high) < 0) {
            codeRange(node.right, low, high, result);
        }
    }

    List<Course> inorder() {
        List<Course> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(CourseNode node, List<Course> result) {
        if (node == null) {
            return;
        }

        inorder(node.left, result);
        result.add(node.data);
        inorder(node.right, result);
    }

    private boolean isValidCourse(Course course) {
        return course != null
                && isValidCode(course.courseCode)
                && isValidCredit(course.getCredit());
    }

    private boolean isValidCode(String courseCode) {
        return courseCode != null && !courseCode.trim().isEmpty();
    }

    private boolean isValidCredit(int credit) {
        return credit >= 1 && credit <= 6;
    }
}

public class CourseBstIndex {
    private static void printCourses(String title, List<Course> courses) {
        System.out.println(title);

        for (Course course : courses) {
            System.out.println(course);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        CourseBst index = new CourseBst();

        System.out.println("add CS2110="
                + index.add(new Course("CS2110", "Data Structures", 3)));

        System.out.println("add CS106A="
                + index.add(new Course("CS106A", "Programming Methodology", 3)));

        System.out.println("add JAVA101="
                + index.add(new Course("JAVA101", "Java Basics", 2)));

        System.out.println("add CS4820 ="
                + index.add(new Course("CS4820 ", "Algorithms", 3)));

        System.out.println("duplicate="
                + index.add(new Course("CS101", "Other Course", 4)));

        System.out.println("invalidCredit="
                + index.add(new Course("DB101", "Database", 7)));

        System.out.println("blankCode="
                + index.add(new Course(" ", "Invalid Course", 3)));

        System.out.println("find CS205=" + index.find("CS205"));
        System.out.println("findNONE=" + index.find("NONE"));

        System.out.println("update CS205="
                + index.updateCredit("CS205", 4));

        System.out.println("invalidUpdate="
                + index.updateCredit("CS205", 0));

        System.out.println("missingUpdate="
                + index.updateCredit("NONE", 3));

        printCourses("range [CS100, DS999]",
                index.codeRange("CS100", "DS999"));

        printCourses("invalid range [JAVA101, CS101]",
                index.codeRange("JAVA101", "CS101"));

        System.out.println("removeDS201=" + index.remove("DS201"));
        System.out.println("removeNONE=" + index.remove("NONE"));

        printCourses("inorder report", index.inorder());
    }
}