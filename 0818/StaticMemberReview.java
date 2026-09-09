//概念 4：Instance member 與 static member

// Instance field 屬於各自的物件，每個物件都有獨立值；適合 static 的資料包括物件總數、固定常數，以及不依賴任何物件狀態的工具方法。
class SupportTicket {
    private static int nextNumber = 1;

    private int number;
    private String issue;

    SupportTicket(String issue) {
        number = nextNumber;
        nextNumber++;
        this.issue = issue;
    }

    static int getCreatedCount() {
        return nextNumber - 1;
    }

    @Override
    public String toString() {
        return "T" + number + " " + issue;
    }
}

public class StaticMemberReview {
    public static void main(String[] args) {
        SupportTicket first = new SupportTicket("Network");
        SupportTicket second = new SupportTicket("Printer");

        System.out.println(first);
        System.out.println(second);
        System.out.println("建立數量：" + SupportTicket.getCreatedCount());
    }
}
