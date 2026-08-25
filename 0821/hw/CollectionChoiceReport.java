import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.LinkedHashMap;
import java.util.Map;

// 課後作業四：集合選擇報告與實作
// 指定檔名：CollectionChoiceReport.java
public class CollectionChoiceReport {
    public static void main(String[] args) {
        // 1. 保留搜尋紀錄且允許重複 使用 List
        List<String> history = new ArrayList<>();
        history.add("home");
        history.add("news");
        history.add("home");
        System.out.println("1) interface=List, impl=ArrayList -> " + history);

        // 2. 保存不重複會員編號 使用 Set
        Set<String> memberIds = new HashSet<>();
        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M001");
        System.out.println("2) interface=Set, impl=HashSet -> " + memberIds);

        // 3. 以學號查詢成績 使用  Map
        Map<String, Integer> grades = new LinkedHashMap<>();
        grades.put("S101", 90);
        grades.put("S102", 75);
        System.out.println("3) interface=Map, impl=LinkedHashMap -> S101=" + grades.get("S101"));

        // 4. 依到達順序處理列印工作 使用 Queue（ Deque) 
        Deque<String> printQueue = new ArrayDeque<>();
        printQueue.offerLast("J1");
        printQueue.offerLast("J2");
        System.out.println("4) interface=Queue, impl=ArrayDeque -> " + printQueue.pollFirst());

        // 5.  復原最近操作 使用 Stack
        Deque<String> undo = new ArrayDeque<>();
        undo.push("A");
        undo.push("B");
        System.out.println("5) interface=Stack, impl=ArrayDeque -> " + undo.pollFirst());

        
        Deque<String> q2 = new LinkedList<>();
        q2.offerLast("X");
        q2.offerLast("Y");
        System.out.println("alt queue (LinkedList) pollFirst=" + q2.pollFirst());
    }
}