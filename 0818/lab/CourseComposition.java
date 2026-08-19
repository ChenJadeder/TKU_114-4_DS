class Instructor {
private final String id;
private final String name;
    Instructor(String id, String name) {
    if (id == null) {
        this.id = "UNKNOWN";
    } else {
        String t = id.trim();
        this.id = t.length() == 0 ? "UNKNOWN" : t;
    }
    if (name == null) {
        this.name = "Unknown";
    } else {
        String t = name.trim();
        this.name = t.length() == 0 ? "Unknown" : t;
    }
}

String label() {
    return id + " " + name;
    }
}
class Course {
private String courseCode;
private String title;
private Instructor instructor; // Course has an Instructor
    Course(String courseCode, String title, Instructor instructor) {
    if (courseCode == null) {
        this.courseCode = "UNKNOWN";
    } else {
        String t = courseCode.trim();
        this.courseCode = t.length() == 0 ? "UNKNOWN" : t;
    }
    if (title == null) {
        this.title = "Untitled";
    } else {
        String t = title.trim();
        this.title = t.length() == 0 ? "Untitled" : t;
    }
    this.instructor = instructor; // 假設會傳入有效 item
}

String summary() {
    return courseCode + " " + title + " | " + instructor.label();
}
}
public class CourseComposition {
public static void main(String[] args) {
Instructor t1 = new Instructor("I001", "Dr. Lin");
Course c1 = new Course("CS61B", "Java OOP", t1);
Course c2 = new Course("CS2210", "Data Structures", t1); 
    System.out.println(c1.summary());
    System.out.println(c2.summary());
    }
}