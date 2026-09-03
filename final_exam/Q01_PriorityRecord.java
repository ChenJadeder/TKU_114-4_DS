import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class JobProcessor {

    //  Job Record 結構
    public record Job(String id, int priority, long sequence) {}

    /**
     * 處理工作順序
     * 
     * @param jobs 輸入的工作列表
     * @return 排序後的 Job ID 列表
     */
    public static List<String> processOrder(List<Job> jobs) {
        // 輸入 null 或 empty 時回傳新的 empty List
        if (jobs == null || jobs.isEmpty()) {
            return new ArrayList<>();
        }

        //  Comparator
        // 1. priority 數字越小越優先
        // 2. 相同 priority 依 sequence 小到大
        Comparator<Job> jobComparator = Comparator
                .comparingInt(Job::priority)
                .thenComparingLong(Job::sequence)
                .thenComparing(Job::id, Comparator.nullsLast(Comparator.naturalOrder()));

        // 使用 PriorityQueue 
        PriorityQueue<Job> minHeap = new PriorityQueue<>(jobComparator);

        // 忽略 null job，加入 Heap
        for (Job job : jobs) {
            if (job != null) {
                minHeap.add(job);
            }
        }

        // 依序取出 ID 放入新的 List，不修改原輸入 List
        List<String> sortedIds = new ArrayList<>();
        while (!minHeap.isEmpty()) {
            Job currentJob = minHeap.poll();
            sortedIds.add(currentJob.id());
        }

        return sortedIds;
    }
}
