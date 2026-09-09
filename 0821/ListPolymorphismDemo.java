// 概念 1：用 List interface 隔離 implementation
// 變數、參數與回傳值只需要 List 行為時，優先宣告為 List<E>。確實需要某 implementation 特有 method 時才使用具體型態。

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListPolymorphismDemo {
    static void fillAndPrint(String label, List<String> list) {
        list.add("Tree");
        list.add("Heap");
        list.add("Graph");
        list.add(1, "List");

        System.out.println(label + "：" + list);
        System.out.println("index 2：" + list.get(2));
    }

    public static void main(String[] args) {
        List<String> arrayBased = new ArrayList<>();
        List<String> linked = new LinkedList<>();

        fillAndPrint("ArrayList", arrayBased);
        fillAndPrint("LinkedList", linked);
    }
}
