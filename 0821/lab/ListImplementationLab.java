import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
// 課堂實作題一：List Implementation 比較
//指定檔名：ListImplementationLab.java

public class ListImplementationLab {

    // 尾端新增
    static void appendEnd(List<Integer> list, int value) {
        list.add(value);
    }

    // 指定位置插入
    static void insertAt(List<Integer> list, int index, int value) {
        if (index < 0 || index > list.size()) return;
        list.add(index, value);
    }

    // 搜尋指定目標
    static int findIndex(List<Integer> list, int target) {
        for (int i = 0; i < list.size(); i++) {
            Integer v = list.get(i);
            if (v != null && v == target) return i;
        }
        return -1;
    }

    // 指定位置刪除
    static void removeAt(List<Integer> list, int index) {
        if (index < 0 || index >= list.size()) return;
        list.remove(index);
    }

    // 總和
    static int sum(List<Integer> list) {
        int total = 0;
        for (int i = 0; i < list.size(); i++) {
            Integer v = list.get(i);
            if (v != null) total += v;
        }
        return total;
    }

    static void scenario(String label, List<Integer> list) {
        appendEnd(list, 10);
        appendEnd(list, 20);
        appendEnd(list, 30);
        insertAt(list, 1, 99);
        int idx = findIndex(list, 20);
        removeAt(list, 2);  
        int total = sum(list);

        System.out.println(label + " -> " + list);
        System.out.println("findIndex(20)=" + idx + ", sum=" + total);
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        scenario("ArrayList", arrayList);
        scenario("LinkedList", linkedList);

        System.out.println();
        System.out.println("差異說明（可能的內部成本）：");
        System.out.println("- ArrayList: get(index) 快；在中間插入/刪除需要搬移後面元素");
        System.out.println("- LinkedList: 插入/刪除節點時調整連結較直覺；get(index) 需要從頭/尾走訪");
    }
}