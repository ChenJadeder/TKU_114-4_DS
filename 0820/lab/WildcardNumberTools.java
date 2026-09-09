// 課堂實作題四：Wildcard 數值工具
// 指定檔名：WildcardNumberTools.java

import java.util.ArrayList;
import java.util.List;

public class WildcardNumberTools {

    // 使用 ? extends Number
    static double average(List<? extends Number> values) {
        if (values == null || values.size() == 0) {
            return 0.0;
        }

        double total = 0.0;

        for (int i = 0; i < values.size(); i++) {
            Number value = values.get(i);
            total = total + value.doubleValue();
        }

        return total / values.size();
    }

    // 空清單時沒有最大值，因此回傳 Double.NaN
    static double maximum(List<? extends Number> values) {
        if (values == null || values.size() == 0) {
            return Double.NaN;
        }

        double max = values.get(0).doubleValue();

        for (int i = 1; i < values.size(); i++) {
            double current = values.get(i).doubleValue();

            if (current > max) {
                max = current;
            }
        }

        return max;
    }

    // 要把 Integer 加進 target，所以使用 ? super Integer
    static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null) {
            return;
        }

        if (start > end) {
            return;
        }

        for (int value = start; value <= end; value++) {
            target.add(value);
        }
    }

    public static void main(String[] args) {
        List<Integer> scores = new ArrayList<>();
        scores.add(80);
        scores.add(90);
        scores.add(70);

        List<Double> prices = new ArrayList<>();
        prices.add(1.5);
        prices.add(2.75);
        prices.add(0.5);

        List<Number> numbers = new ArrayList<>();

        System.out.println("scores=" + scores);
        System.out.println("average scores=" + average(scores));
        System.out.println("maximum scores=" + maximum(scores));

        System.out.println("prices=" + prices);
        System.out.println("average prices=" + average(prices));
        System.out.println("maximum prices=" + maximum(prices));

        List<Integer> empty = new ArrayList<>();
        System.out.println("average empty=" + average(empty));
        System.out.println("maximum empty=" + maximum(empty));

        addRange(scores, 3, 6);
        System.out.println("scores after addRange(3, 6)=" + scores);

        addRange(numbers, 10, 12);
        System.out.println("numbers after addRange(10, 12)=" + numbers);

        // start > end，不加入任何資料
        addRange(numbers, 8, 5);
        System.out.println("numbers after addRange(8, 5)=" + numbers);
    }
}