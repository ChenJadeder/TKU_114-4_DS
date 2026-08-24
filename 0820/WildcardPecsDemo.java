import java.util.ArrayList;
import java.util.List;

public class WildcardPecsDemo {
    static double sum(List<? extends Number> values) {
        double total = 0.0;
        for (Number value : values) {
            total += value.doubleValue();
        }
        return total;
    }

    // practice：averge = 空 list or  null return 0.0
    static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (Number value : values) {
            total += value.doubleValue();
        }
        return total / values.size();
    }

    static void addDefaults(List<? super Integer> destination) {
        destination.add(60);
        destination.add(70);
    }

    static <T> void copy(List<? extends T> source,
                         List<? super T> destination) {
        for (T value : source) {
            destination.add(value);
        }
    }

    public static void main(String[] args) {
        //origin :Integer be producer（extends），Number be consumer（super）
        List<Integer> scores = new ArrayList<>(List.of(80, 90));
        List<Number> numbers = new ArrayList<>();

        addDefaults(scores);          // scores -> [80, 90, 60, 70]
        copy(scores, numbers);        // numbers <- scores

        System.out.println("scores=" + scores);
        System.out.println("numbers=" + numbers);
        System.out.println("sum(numbers)=" + sum(numbers));

        // test ：average support List<Integer> & List<Double>
        System.out.println("avg(ints)=" + average(scores));

        List<Double> doubles = new ArrayList<>(List.of(1.5, 2.5, 3.0));
        System.out.println("avg(doubles)=" + average(doubles));

        System.out.println("avg(empty)=" + average(new ArrayList<>()));
    }
}
