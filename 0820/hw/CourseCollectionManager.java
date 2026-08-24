import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

//課後作業五：課程管理集合系統
class CourseEnrollment {
    private final String studentId;
    private final String name;
    private int score; // 0~100
    private final Set<String> tags = new HashSet<>();

    CourseEnrollment(String studentId, String name, int score) {
        this.studentId = (studentId == null) ? "UNKNOWN" : studentId.trim();
        this.name = (name == null) ? "Unknown" : name.trim();
        if (score < 0) score = 0;
        if (score > 100) score = 100;
        this.score = score;
    }

    String getStudentId() { return studentId; }
    String getName() { return name; }
    int getScore() { return score; }
    void setScore(int s) {
        if (s < 0) s = 0;
        if (s > 100) s = 100;
        this.score = s;
    }

    void addTag(String tag) {
        if (tag == null) return;
        String t = tag.trim().toLowerCase();
        if (t.length() > 0) tags.add(t);
    }

    boolean hasTag(String tag) {
        if (tag == null) return false;
        return tags.contains(tag.trim().toLowerCase());
    }

    @Override
    public String toString() {
        return studentId + " " + name + " score=" + score + " tags=" + tags;
    }
}

// add Manager to assist
class CourseCollectionManager {
    private final List<CourseEnrollment> order = new ArrayList<>();
    private final Set<String> registeredIds = new HashSet<>();
    private final Map<String, CourseEnrollment> byId = new HashMap<>();

    boolean enroll(CourseEnrollment e) {
        if (e == null) return false;
        String id = e.getStudentId();
        if (!registeredIds.add(id)) {  // if exist
            return false;
        }
        order.add(e);
        byId.put(id, e);
        return true;
    }

    // 1) Score
    boolean updateScore(String studentId, int score) {
        CourseEnrollment e = byId.get(studentId);
        if (e == null) return false;
        e.setScore(score);
        return true;
    }

    // 2) search tag
    List<CourseEnrollment> findByTag(String tag) {
        List<CourseEnrollment> result = new ArrayList<>();
        if (tag == null || tag.trim().length() == 0) return result;
        String key = tag.trim().toLowerCase();
        for (int i = 0; i < order.size(); i++) {
            CourseEnrollment e = order.get(i);
            if (e.hasTag(key)) {
                result.add(e);
            }
        }
        return result;
    }

    // 3) score-distribution（A/B/C/D/F）
    Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new HashMap<>();
        dist.put("A", 0); dist.put("B", 0); dist.put("C", 0); dist.put("D", 0); dist.put("F", 0);
        for (int i = 0; i < order.size(); i++) {
            int s = order.get(i).getScore();
            String k;
            if (s >= 90) k = "A";
            else if (s >= 80) k = "B";
            else if (s >= 70) k = "C";
            else if (s >= 60) k = "D";
            else k = "F";
            dist.put(k, dist.get(k) + 1);
        }
        return dist;
    }

    // 4) score desc， studentId
    List<CourseEnrollment> top(int count) {
        List<CourseEnrollment> copy = new ArrayList<>(order);
        copy.sort(new Comparator<CourseEnrollment>() {
            @Override
            public int compare(CourseEnrollment a, CourseEnrollment b) {
                if (a.getScore() != b.getScore()) {
                    return b.getScore() - a.getScore(); 
                }
                return a.getStudentId().compareTo(b.getStudentId());
            }
        });
        if (count >= copy.size()) return copy;
        List<CourseEnrollment> result = new ArrayList<>();
        for (int i = 0; i < count; i++) result.add(copy.get(i));
        return result;
    }

    // 5) 
    void removeBelow(int minimum) {
        
        order.removeIf(e -> e.getScore() < minimum);
        // 重建 Set/Map
        registeredIds.clear();
        byId.clear();
        for (int i = 0; i < order.size(); i++) {
            CourseEnrollment e = order.get(i);
            registeredIds.add(e.getStudentId());
            byId.put(e.getStudentId(), e);
        }
    }

    CourseEnrollment find(String studentId) {
        return byId.get(studentId);
    }

    List<CourseEnrollment> ranking() {
        List<CourseEnrollment> copy = new ArrayList<>(order);
        copy.sort(new Comparator<CourseEnrollment>() {
            @Override
            public int compare(CourseEnrollment a, CourseEnrollment b) {
                if (a.getScore() != b.getScore()) {
                    return b.getScore() - a.getScore();
                }
                return a.getStudentId().compareTo(b.getStudentId());
            }
        });
        return copy;
    }

    @Override
    public String toString() {
        return order.toString();
    }
}

public class CourseCollectionManagerDemo {
    public static void main(String[] args) {
        CourseCollectionManager book = new CourseCollectionManager();

       
        CourseEnrollment e1 = new CourseEnrollment("S101", "Amy", 88);
        CourseEnrollment e2 = new CourseEnrollment("S102", "Ben", 75);
        CourseEnrollment e3 = new CourseEnrollment("S103", "Cara", 92);
        CourseEnrollment e4 = new CourseEnrollment("S104", "Dan", 60);
        CourseEnrollment e5 = new CourseEnrollment("S105", "Eve", 75); // 同分 75
        CourseEnrollment e6 = new CourseEnrollment("S101", "Amy Dup", 99); // 重複學號，不應加入

        e1.addTag("Java");
        e1.addTag("  "); //add empty tag
        e2.addTag("Graph");
        e3.addTag("Java");
        e4.addTag("Tree");

        System.out.println("enroll e1=" + book.enroll(e1));
        System.out.println("enroll e2=" + book.enroll(e2));
        System.out.println("enroll e3=" + book.enroll(e3));
        System.out.println("enroll e4=" + book.enroll(e4));
        System.out.println("enroll e5=" + book.enroll(e5));
        System.out.println("enroll e6(dup id)=" + book.enroll(e6));

        System.out.println("all=" + book);
        System.out.println("find S102=" + book.find("S102"));

        System.out.println("updateScore S104->58 " + book.updateScore("S104", 58)); // D->F
        System.out.println("ranking=" + book.ranking());
        System.out.println("top(3)=" + book.top(3));
        System.out.println("dist=" + book.scoreDistribution());
        System.out.println("by tag 'java'=" + book.findByTag("java"));

        book.removeBelow(60);
        System.out.println("after removeBelow(60) all=" + book);
        System.out.println("dist(after)=" + book.scoreDistribution());
        System.out.println("ranking(after)=" + book.ranking());
    }
}