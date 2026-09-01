// 課堂實作題四：Top-K 最低價格
// 指定檔名：LowestKPriceTracker.java
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class LowestKPriceTracker {

    public static List<Integer> lowestK(List<Integer> prices, int k) {
        List<Integer> result = new ArrayList<>();

        // k 沒有有效大小就直接回傳空 List
        if (k <= 0) {
            return result;
        }

        // reverseOrder 讓 PriorityQueue 變成 Max Heap
        PriorityQueue<Integer> heap =
                new PriorityQueue<>(Comparator.reverseOrder());

        for (Integer price : prices) {
            // null 和負數不算有效價格
            if (price == null || price < 0) {
                continue;
            }

            heap.offer(price);

            // 超過 k 就把目前最大的價格丟掉
            if (heap.size() > k) {
                heap.poll();
            }
        }

        
        while (!heap.isEmpty()) {
            result.add(heap.poll());
        }

        // Max Heap poll 出來是由大到小，為遞增
        Collections.sort(result);

        return result;
    }

    public static void main(String[] args) {
        List<Integer> prices = new ArrayList<>();

        prices.add(50);
        prices.add(20);
        prices.add(null);
        prices.add(10);
        prices.add(-5);
        prices.add(30);
        prices.add(5);
        prices.add(20);
        prices.add(0);

        System.out.println("K = 4");
        System.out.println(lowestK(prices, 4));

        System.out.println("K = 0");
        System.out.println(lowestK(prices, 0));
    }
}
