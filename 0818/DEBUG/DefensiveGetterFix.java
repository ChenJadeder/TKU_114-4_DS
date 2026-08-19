import java.util.Arrays;

// 修正點：constructor 與 getter 都做 defensive copy，避免外部透過 reference 改動內部陣列
class ScoreHolder {
    private final int[] scores;

    ScoreHolder(int[] scores) {
        if (scores == null) {
            this.scores = new int[0];
        } else {
            this.scores = Arrays.copyOf(scores, scores.length);
        }
    }

    int[] getScores() {
        return Arrays.copyOf(scores, scores.length);
    }

    @Override
    public String toString() {
        return Arrays.toString(scores);
    }
}

public class DefensiveGetterFix {
    public static void main(String[] args) {
        int[] raw = {70, 80};
        ScoreHolder holder = new ScoreHolder(raw);

        // 修改來源陣列，不應影響 holder
        raw[0] = 0;

        // 取得 getter 回傳的陣列後再修改，也不影響 holder
        int[] copy = holder.getScores();
        copy[1] = 0;

        System.out.println("raw=" + Arrays.toString(raw));       // [0, 80]
        System.out.println("copy=" + Arrays.toString(copy));     // [70, 0]
        System.out.println("holder=" + holder);                  // [70, 80]
    }
}