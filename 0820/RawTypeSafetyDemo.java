//  概念 8：Raw type 與 compile-time type safety
//移除 `@SuppressWarnings`，使用 `javac -Xlint:unchecked RawTypeSafetyDemo.java` 編譯，
//記錄 compiler 指出的 warning 位置，再將 raw list 改成 `List<String>`。

import java.util.ArrayList;
import java.util.List;

public class RawTypeSafetyDemo {
    static void rawTypeExample() {
        List<String> raw = new ArrayList<>();
        raw.add("Amy");

        String value = raw.get(0);
        System.out.println(value);
    }

    static void genericExample() {
        List<String> names = new ArrayList<>();
        names.add("Amy");
        names.add("Ben");
        System.out.println(names);
    }

    public static void main(String[] args) {
        rawTypeExample();
        genericExample();
    }
}
