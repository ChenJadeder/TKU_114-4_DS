import java.util.Arrays;

public class MetroMatrixGraph {
    private String[] stations;
    private boolean[][] matrix;
    private int edgeCount;

    public MetroMatrixGraph(String[] stations) {
        // 複製站點資料，建立固定大小 matrix
        this.stations = Arrays.copyOf(stations, stations.length);
        this.matrix = new boolean[stations.length][stations.length];
        this.edgeCount = 0;
    }

    // 用站名找出對應的 matrix index
    private int findStation(String name) {
        for (int i = 0; i < stations.length; i++) {
            if (stations[i].equals(name)) {
                return i;
            }
        }

        return -1;
    }

    public boolean addConnection(String a, String b) {
        int idxA = findStation(a);
        int idxB = findStation(b);

        // 站點不存在或連到自己時不新增
        if (idxA == -1 || idxB == -1 || idxA == idxB) {
            return false;
        }

        // edge 已存在，不重複增加 edgeCount
        if (matrix[idxA][idxB]) {
            return false;
        }

        // undirected edge 必須設定兩個方向
        matrix[idxA][idxB] = true;
        matrix[idxB][idxA] = true;

        edgeCount++;
        return true;
    }

    public int degree(String station) {
        int idx = findStation(station);

        if (idx == -1) {
            return -1;
        }

        int count = 0;

        // 該 station 的 row 中，每個 true 都是一個 neighbor
        for (int i = 0; i < stations.length; i++) {
            if (matrix[idx][i]) {
                count++;
            }
        }

        return count;
    }

    public void printNeighbors(String station) {
        int idx = findStation(station);

        if (idx == -1) {
            System.out.println("站點不存在: " + station);
            return;
        }

        System.out.print(station + " 的鄰近站點: ");

        boolean found = false;

        for (int i = 0; i < stations.length; i++) {
            if (matrix[idx][i]) {
                System.out.print(stations[i] + " ");
                found = true;
            }
        }

        if (!found) {
            System.out.print("(無)");
        }

        System.out.println();
    }

    public int getEdgeCount() {
        return edgeCount;
    }

    public void printMatrix() {
        System.out.println("=== 捷運鄰接矩陣 ===");

        System.out.printf("%-12s", "");

        for (String station : stations) {
            System.out.printf("%-12s", station);
        }

        System.out.println();

        for (int i = 0; i < stations.length; i++) {
            System.out.printf("%-12s", stations[i]);

            for (int j = 0; j < stations.length; j++) {
                System.out.printf(
                        "%-12d",
                        matrix[i][j] ? 1 : 0
                );
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[] stations = {
            "台北車站",
            "中山",
            "西門",
            "東門",
            "中正紀念堂"
        };

        MetroMatrixGraph metro =
                new MetroMatrixGraph(stations);

        metro.addConnection("台北車站", "中山");
        metro.addConnection("台北車站", "西門");
        metro.addConnection("台北車站", "中正紀念堂");
        metro.addConnection("西門", "中正紀念堂");
        metro.addConnection("中正紀念堂", "東門");

        // duplicate，edge count 不應增加
        boolean duplicate =
                metro.addConnection("台北車站", "中山");

        metro.printMatrix();

        System.out.println(
                "總邊數: " + metro.getEdgeCount()
        );

        System.out.println(
                "台北車站 degree: " + metro.degree("台北車站")
        );

        metro.printNeighbors("台北車站");
        metro.printNeighbors("東門");

        System.out.println(
                "重複 edge 新增成功: " + duplicate
        );
    }
}
