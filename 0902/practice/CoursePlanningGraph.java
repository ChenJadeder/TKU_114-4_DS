import java.util.*;

public class CoursePlanningGraph {

    // prerequisite -> dependent courses
    private final Map<String, List<String>> graph =
            new LinkedHashMap<>();

    // 加入一門課程
    public void addCourse(String course) {
        graph.putIfAbsent(course, new ArrayList<>());
    }

    // 加入先修關係：prerequisite -> course
    public boolean addPrerequisite(
            String prerequisite,
            String course) {

        if (!graph.containsKey(prerequisite)
                || !graph.containsKey(course)) {
            return false;
        }

        graph.get(prerequisite).add(course);
        return true;
    }

    // 判斷是否存在 from -> target 的 directed path
    public boolean isReachable(String from, String target) {

        if (!graph.containsKey(from)
                || !graph.containsKey(target)) {
            return false;
        }

        if (from.equals(target)) {
            return true;
        }

        Set<String> visited = new HashSet<>();
        return dfsReachable(from, target, visited);
    }

    // 找到 target 時可以提早停止
    private boolean dfsReachable(
            String current,
            String target,
            Set<String> visited) {

        visited.add(current);

        for (String next : graph.get(current)) {

            if (next.equals(target)) {
                return true;
            }

            if (!visited.contains(next)
                    && dfsReachable(next, target, visited)) {
                return true;
            }
        }

        return false;
    }

    // 找出指定課程直接或間接影響的所有課程
    public List<String> affectedCourses(String course) {
        List<String> result = new ArrayList<>();

        if (!graph.containsKey(course)) {
            return result;
        }

        Set<String> visited = new LinkedHashSet<>();

        // 起點先標記，可避免 cycle 又回到自己
        visited.add(course);
        collectAffected(course, visited);

        // course 本身不列為受自己影響的課程
        visited.remove(course);
        result.addAll(visited);

        return result;
    }

    // 完整 DFS，收集所有可以到達的課程
    private void collectAffected(
            String current,
            Set<String> visited) {

        for (String next : graph.get(current)) {

            if (!visited.contains(next)) {
                visited.add(next);
                collectAffected(next, visited);
            }
        }
    }

    // 顯示受指定課程影響的課程
    public void printAffected(String course) {

        if (!graph.containsKey(course)) {
            System.out.println(
                    course + ": missing course");
            return;
        }

        System.out.println(
                course + " affects "
                + affectedCourses(course));
    }

    public static void main(String[] args) {

        CoursePlanningGraph courses =
               penser new CoursePlanningGraph();

        courses.addCourse("Programming");
        courses.addCourse("DataStructures");
        courses.addCourse("Algorithms");
        courses.addCourse("Database");
        courses.addCourse("AI");

        // isolated course
        courses.addCourse("Art");

        courses.addPrerequisite(
                "Programming", "DataStructures");

        courses.addPrerequisite(
                "DataStructures", "Algorithms");

        courses.addPrerequisite(
                "Algorithms", "AI");

        courses.addPrerequisite(
                "Programming", "Database");

        System.out.println("=== Reachability ===");

        System.out.println(
                "Programming -> AI: "
                + courses.isReachable(
                        "Programming", "AI"));

        System.out.println(
                "AI -> Programming: "
                + courses.isReachable(
                        "AI", "Programming"));

        System.out.println(
                "Algorithms -> Algorithms: "
                + courses.isReachable(
                        "Algorithms", "Algorithms"));

        System.out.println();

        System.out.println("=== Affected Courses ===");

        courses.printAffected("Programming");
        courses.printAffected("DataStructures");

        System.out.println();

        System.out.println("=== Isolated Course ===");

        courses.printAffected("Art");

        System.out.println();

        System.out.println("=== Missing Course ===");

        courses.printAffected("Unknown");

        System.out.println(
                "Programming -> Unknown: "
                + courses.isReachable(
                        "Programming", "Unknown"));

        System.out.println();

        System.out.println("=== Empty Graph ===");

        CoursePlanningGraph empty =
                new CoursePlanningGraph();

        System.out.println(
                "Programming -> AI: "
                + empty.isReachable(
                        "Programming", "AI"));

        System.out.println(
                "Affected: "
                + empty.affectedCourses("Programming"));
    }
}
