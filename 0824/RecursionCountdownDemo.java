// 概念 1：Base case、recursive case 與 progress
//recursion include Base,recursive case and progress

public class RecursionCountdownDemo {
    static void countDown(int number) {
        if (number <= 0) {
            System.out.println("GO");
            return;  //大量線性重複適合loop
        }
        System.out.println(number);
        countDown(number - 1);
    }
  //而recursion 直接對應問題定義
    public static void main(String[] args) {
        countDown(3);
        countDown(0);
    }
}