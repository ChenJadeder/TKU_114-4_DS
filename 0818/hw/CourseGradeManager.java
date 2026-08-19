class CourseGrade {
    private final String studentId;
    private final String name;
    private final int daily;      // 平時
    private final int midterm;    // 期中
    private final int finalExam;  // 期末
    private final int attendance; // 出席

    CourseGrade(String studentId, String name, int daily, int midterm, int finalExam, int attendance) {
    this.studentId = (studentId == null || studentId.isBlank()) ? "UNKNOWN" : studentId;
    this.name = (name == null || name.isBlank()) ? "Unknown" : name;
    this.daily = clamp(daily);
    this.midterm = clamp(midterm);
    this.finalExam = clamp(finalExam);
    this.attendance = clamp(attendance);
}

private int clamp(int score) {
    if (score < 0) return 0;
    if (score > 100) return 100;
    return score;
}

double calculateFinalScore() {
    return daily * 0.5 + midterm * 0.2 + finalExam * 0.2 + attendance * 0.1;
}

String getLevel() {
    double s = calculateFinalScore();
    if (s >= 90) return "A";
    if (s >= 80) return "B";
    if (s >= 70) return "C";
    if (s >= 60) return "D";
    return "F";
}

private static String oneDecimal(double v) {
    double rounded = Math.round(v * 10.0) / 10.0;
    return String.valueOf(rounded); // 例如 63.0 也會顯示為 "63.0"
}

@Override
public String toString() {
    // 改為字串相加 + 手動一位小數
    return studentId + " " + name +
           " daily=" + daily +
           " mid=" + midterm +
           " fin=" + finalExam +
           " att=" + attendance +
           " total=" + oneDecimal(calculateFinalScore()) +
           " level=" + getLevel();
}
}

public class CourseGradeManager {
public static void main(String[] args) {
CourseGrade[] grades = new CourseGrade[] {
new CourseGrade("S101", "Amy", 85, 78, 92, 100),
new CourseGrade("S102", "Ben", 60, 55, 70, 80),
new CourseGrade("S103", "Cara", 95, 88, 84, 90),
new CourseGrade("S104", "Dan", 40, 50, 45, 70),
new CourseGrade("S105", "Eve", 75, 82, 79, 85)
};

double total = 0.0;
    double maxScore = -1.0;
    int bestIndex = -1;

    System.out.println("All records:");
    for (int i = 0; i < grades.length; i++) {
        CourseGrade g = grades[i];
        System.out.println("  " + g);
        double score = g.calculateFinalScore();
        total += score;
        if (score > maxScore) {
            maxScore = score;
            bestIndex = i;
        }
    }

    double avg = total / grades.length;
    // 平均仍用兩位小數（保持輸出完全一致）
    System.out.println("Average final score: " + String.format("%.2f", avg));

    // 最高分顯示 1 位小數 + 最佳學生摘要
    System.out.println("Highest: " + String.format("%.1f", maxScore) + " -> " + grades[bestIndex]);

    System.out.println("Failing (<60):");
    for (CourseGrade g : grades) {
        if (g.calculateFinalScore() < 60.0) {
            System.out.println("  " + g);
        }
    }
  }
}