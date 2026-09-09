//概念 5：Object reference、alias 與 null
//Java 的物件變數保存的是 reference，讓兩個變數可以指向同一個物件，這種情況稱為 alias。
//並透過其中一個 reference 修改物件，另一個 reference 看到的狀態也會改變。

class ScoreRecord {
    private String name;
    private int score;

    ScoreRecord(String name, int score) {
        this.name = name;
        this.score = score;
    }

    void addBonus(int points) {
        if (points > 0) {
            score += points;
        }
    }

    int getScore() {
        return score;
    }

    ScoreRecord copy() {
        return new ScoreRecord(name, score);
    }
}

public class ObjectReferenceReview {
    public static void main(String[] args) {
        ScoreRecord first = new ScoreRecord("Amy", 70);
        ScoreRecord alias = first;
        ScoreRecord copy = first.copy();

        alias.addBonus(10);

        System.out.println("first：" + first.getScore());
        System.out.println("alias：" + alias.getScore());
        System.out.println("copy：" + copy.getScore());
        System.out.println("first == alias：" + (first == alias));
        System.out.println("first == copy：" + (first == copy));
    }
}
