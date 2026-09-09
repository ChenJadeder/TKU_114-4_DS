// 概念範例 2：建構子資料驗證與預設值處理

/* item 建構子中檢查 name。
 * name 為 null 時，使用 "Unnamed"。
 * name 不是 null 時，先使用 trim() 移除前後空白。
 * 移除空白後若為空字串，使用 "Unnamed"。
 * points 使用 Math.max(0, points)，確保分數不小於 0。
 * 使用 describe() 顯示物件目前狀態。
 */

class CourseItem {
    private String name;
    private int points;

    CourseItem(String name, int points) {
        this.name = name == null ? "Unnamed" : name.trim();
        if (this.name.isEmpty()) {
            this.name = "Unnamed";
        }
        this.points = Math.max(0, points);
    }

    String describe() {
        return name + "：" + points + " 分";
    }
}

public class ConstructorValidationDemo {
    public static void main(String[] args) {
        /*
         * 測試 1：正常資料但名稱前後有空白。
         * 預期名稱會被整理為 Tree Practice，分數維持 20。
         */
        CourseItem first = new CourseItem("  Tree Practice  ", 20);

        /*
         * 測試 2：名稱只有空白且分數為負數。
         * 預期名稱改為 Unnamed，分數改為 0。
         */
        CourseItem second = new CourseItem("   ", -5);


        System.out.println(first.describe());
        System.out.println(second.describe());
    }
}
