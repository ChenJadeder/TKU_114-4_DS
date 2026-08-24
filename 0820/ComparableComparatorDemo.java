import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class RankedStudent implements Comparable<RankedStudent> {
    private final String id;
    private final String name;
    private final int score;

    RankedStudent(String id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    int getScore() {
        return score;
    }

    String getName() {
        return name;
    }

    @Override
    public int compareTo(RankedStudent other) {
        return id.compareTo(other.id); // natural order: by id
    }

    @Override
    public String toString() {
        return id + " " + name + " " + score;
    }
}

public class ComparableComparatorDemo {
    public static void main(String[] args) {
        List<RankedStudent> students = new ArrayList<>();
        students.add(new RankedStudent("S103", "Cara", 75));
        students.add(new RankedStudent("S101", "Amy", 90));
        students.add(new RankedStudent("S102", "Ben", 90));
        students.add(new RankedStudent("S104", "Zoe", 82));
        students.add(new RankedStudent("S105", "Alan", 82));

        // origin：natural order -> by id
        students.sort(null);
        System.out.println("by id=" + students);

        // by score desc, then name
        Comparator<RankedStudent> byScore =
                Comparator.comparingInt(RankedStudent::getScore)
                          .reversed()
                          .thenComparing(RankedStudent::getName);
        students.sort(byScore);
        System.out.println("by score=" + students);

        // third sorting
        // 先依姓名長度升冪，長度相同時再依姓名字典順序
        Comparator<RankedStudent> byNameLength =
                Comparator.comparingInt((RankedStudent s) -> s.getName().length())
                          .thenComparing(RankedStudent::getName);
        students.sort(byNameLength);
        System.out.println("by name length=" + students);
    }
}
