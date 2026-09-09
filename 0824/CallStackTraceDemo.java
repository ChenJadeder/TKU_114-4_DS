// 概念 2：Call stack 與返回順序

//base ,frame LIFO back to recursive call
public class CallStackTraceDemo {
    static void trace(int level) {
        System.out.println("enter " + level);
        if (level == 0) {
            System.out.println("base"); //遇到base case寫出parameter與暫停位置
        } else {
            trace(level - 1);
        }
        System.out.println("leave " + level);
    }

    public static void main(String[] args) {
        trace(3);
    }
}