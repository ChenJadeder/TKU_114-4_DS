// 概念 5：Java PriorityQueue 與資料取出順序
//PriorityQueue 在java為 Min Priority Queue，不能當成完整排序結果。
import java.util.PriorityQueue;

public class PriorityNumberQueue {
    public static void main(String[] args) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int value : new int[]{40, 10, 30, 20}) {
            queue.offer(value);
            System.out.println("offer=" + value + " head=" + queue.peek());
        }

        System.out.print("poll order=");
        while (!queue.isEmpty()) {
            System.out.print(queue.poll());
            if (!queue.isEmpty()) System.out.print(",");
        }
        System.out.println();
        System.out.println("empty poll=" + queue.poll());
    }
}
