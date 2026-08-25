import java.util.ArrayDeque;
import java.util.Deque;
//LIFO stack
// 課堂實作題二：瀏覽器返回功能
//指定檔名：BrowserBackStack.java 

public class BrowserBackStack {
    private final Deque<String> history = new ArrayDeque<>();

    void visit(String url) {
        if (url == null || url.trim().length() == 0) return;
        history.push(url.trim());
    }

    String back() {
        if (history.isEmpty()) return "EMPTY";
        history.pop(); //  pop now 
        if (history.isEmpty()) return "EMPTY";
        return history.peek();
    }

    String current() {
        if (history.isEmpty()) return "EMPTY";
        return history.peek();
    }

    public static void main(String[] args) {
        BrowserBackStack bs = new BrowserBackStack(); //連續測試至少五個操作    
        bs.visit("home");
        bs.visit("news");
        bs.visit("article");
        System.out.println("current=" + bs.current()); // article

        System.out.println("back -> " + bs.back());   // news
        System.out.println("back -> " + bs.back());   // home
        System.out.println("back -> " + bs.back());   // EMPTY

        bs.visit("search");
        System.out.println("current=" + bs.current()); // search
    }
}