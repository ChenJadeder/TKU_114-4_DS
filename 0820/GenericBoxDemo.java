// 概念 1：為什麼需要 Generic
//沒有Generic 成為參數, 使用Object 保存資料需要cast才能取出,但注意Generic 不能直接使用 primitive type

class Box<T> {
    private T value;

    void set(T value) {
        this.value = value;
    }

    T get() {
        return value;
    }

    boolean isEmpty() {
        return value == null;
    }
}

public class GenericBoxDemo {
    public static void main(String[] args) {
        Box<String> textBox = new Box<>();
        Box<Integer> numberBox = new Box<>();

        textBox.set("Java");
        numberBox.set(114);

        System.out.println(textBox.get().toUpperCase());
        System.out.println(numberBox.get() + 1);
        System.out.println("textBox empty：" + textBox.isEmpty());
    }
}