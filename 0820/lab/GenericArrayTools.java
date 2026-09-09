// 課堂實作題二：Generic 陣列工具
// 指定檔名：GenericArrayTools.java

public class GenericArrayTools {

    // 計算陣列中與 target 相同的資料數量
    static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) {
            return 0;
        }

        int count = 0;

        for (int i = 0; i < data.length; i++) {
            if (target == null) {
                if (data[i] == null) {
                    count++;
                }
            } else {
                if (target.equals(data[i])) {
                    count++;
                }
            }
        }

        return count;
    }

    // 取得最後一筆；null 或空陣列時回傳 null
    static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }

        return data[data.length - 1];
    }

    // 交換兩個 index 的資料
    static <T> void swap(T[] data, int first, int second) {
        if (data == null || data.length == 0) {
            return;
        }

        if (first < 0 || second < 0
                || first >= data.length || second >= data.length) {
            return;
        }

        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    static <T> void printArray(T[] data) {
        if (data == null) {
            System.out.println("null");
            return;
        }

        System.out.print("[");
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                System.out.print(", ");
            }
            System.out.print(data[i]);
        }
        System.out.println("]");
    }

    public static void main(String[] args) {
        String[] names = {"Amy", "Ben", "Amy", null};
        Integer[] scores = {80, 90, 70, 90};

        System.out.println("Amy count=" + countMatches(names, "Amy"));
        System.out.println("null count=" + countMatches(names, null));
        System.out.println("90 count=" + countMatches(scores, 90));

        System.out.println("last name=" + last(names));
        System.out.println("last score=" + last(scores));
        System.out.println("last empty=" + last(new String[0]));
        System.out.println("last null=" + last(null));

        System.out.print("before swap names=");
        printArray(names);

        swap(names, 0, 2);
        System.out.print("after swap names=");
        printArray(names);

        swap(scores, 1, 3);
        System.out.print("after swap scores=");
        printArray(scores);

        // 不合法 陣列不改變
        swap(names, -1, 2);
        swap(scores, 0, 10);

        System.out.print("after invalid swap names=");
        printArray(names);

        System.out.print("after invalid swap scores=");
        printArray(scores);
    }
}