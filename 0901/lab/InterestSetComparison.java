import java.util.HashSet;
import java.util.Set;

public class InterestSetComparison {

    public static Set<String> union(
        Set<String> first,
        Set<String> second) {
    //加入將second元素加入至first
    Set<String> result = new HashSet<>(first);
    result.addAll(second);

    return result;
}
    //加入將first與second共有元素加入至first
    public static Set<String> intersection(
        Set<String> first,
        Set<String> second) {

    Set<String> result = new HashSet<>(first);
    result.retainAll(second);

    return result;
}
    //只存在於 first，不存在於 second 的元素
    public static Set<String> firstOnly(
        Set<String> first,
        Set<String> second) {

    Set<String> result = new HashSet<>(first);
    result.removeAll(second);

    return result;
}
    //只存在於 second，不存在於 first 的元素
    public static Set<String> secondOnly(
            Set<String> first,
            Set<String> second) {

        Set<String> result = new HashSet<>(second);
        result.removeAll(first);

        return result;
    }

    public static void main(String[] args) {
    Set<String> first = new HashSet<>();
    first.add("Java");
    first.add("C++");
    first.add("Game");

    Set<String> second = new HashSet<>();
    second.add("Java");
    second.add("Python");
    second.add("Game");

    System.out.println("union = " + union(first, second));
    System.out.println("intersection = " + intersection(first, second));
    System.out.println("first only = " + firstOnly(first, second));
    System.out.println("second only = " + secondOnly(first, second));

    //確認不修改輸入 Set
    System.out.println("first  = " + first);
    System.out.println("second  = " + second);
}
}
