import java.util.*;

public class CampusNavigationSystem {

    static class Location {
        String id;
        String name;

        Location(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    private final Map<String, Location> locations =
            new HashMap<>();

    private final Map<String, List<String>> roads =
            new LinkedHashMap<>();

    public void addLocation(String id, String name) {
        locations.put(id, new Location(id, name));
        roads.putIfAbsent(id, new ArrayList<>());
    }

    // 校園道路視為雙向道路
    public boolean addRoad(String a, String b) {
        if (!locations.containsKey(a)
                || !locations.containsKey(b)) {
            return false;
        }

        roads.get(a).add(b);
        roads.get(b).add(a);
        return true;
    }

    // BFS 找最少 edge 路徑
    public List<String> shortestPath(String start, String target) {
        List<String> empty = new ArrayList<>();

        if (!locations.containsKey(start)
                || !locations.containsKey(target)) {
            return empty;
        }

        if (start.equals(target)) {
            return new ArrayList<>(List.of(start));
        }

        Queue<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> previous = new HashMap<>();

        queue.offer(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            String current = queue.poll();

            for (String next : roads.get(current)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    previous.put(next, current);

                    if (next.equals(target)) {
                        return buildPath(start, target, previous);
                    }

                    queue.offer(next);
                }
            }
        }

        return empty;
    }

    private List<String> buildPath(
            String start,
            String target,
            Map<String, String> previous) {

        List<String> path = new ArrayList<>();
        String current = target;

        while (current != null) {
            path.add(current);

            if (current.equals(start)) {
                break;
            }

            current = previous.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    public void printPath(List<String> path) {
        if (path.isEmpty()) {
            System.out.println("No path");
            return;
        }

        for (int i = 0; i < path.size(); i++) {
            Location location = locations.get(path.get(i));

            if (i > 0) {
                System.out.print(" -> ");
            }

            System.out.print(location.name);
        }

        System.out.println();
        System.out.println("Edges: " + (path.size() - 1));
    }

    public static void main(String[] args) {
        CampusNavigationSystem campus =
                new CampusNavigationSystem();

        campus.addLocation("LIB", "Library");
        campus.addLocation("CS", "CS Building");
        campus.addLocation("GYM", "Gym");
        campus.addLocation("LAB", "Laboratory");
        campus.addLocation("CAFE", "Cafe");
        campus.addLocation("DORM", "Dormitory");

        campus.addRoad("LIB", "CS");
        campus.addRoad("CS", "LAB");
        campus.addRoad("LIB", "GYM");
        campus.addRoad("GYM", "CAFE");
        campus.addRoad("CAFE", "LAB");

        System.out.println("=== Normal ===");
        List<String> path =
                campus.shortestPath("LIB", "LAB");
        campus.printPath(path);

        System.out.println("\n=== Start = Target ===");
        campus.printPath(
                campus.shortestPath("CS", "CS"));

        System.out.println("\n=== Unreachable ===");
        campus.printPath(
                campus.shortestPath("LIB", "DORM"));

        System.out.println("\n=== Missing ===");
        campus.printPath(
                campus.shortestPath("LIB", "UNKNOWN"));

        System.out.println("\n=== Empty ===");
        CampusNavigationSystem empty =
                new CampusNavigationSystem();

        System.out.println(
                empty.shortestPath("A", "B"));
    }
}
