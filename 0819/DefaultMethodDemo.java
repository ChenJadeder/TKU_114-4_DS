interface AlertChannel {
    boolean send(String receiver, String message);

    default boolean isValid(String receiver, String message) {
        return receiver != null && !receiver.isBlank()
                && message != null && !message.isBlank();
    }

    // 新增：預覽訊息，最長 10 字，超過則加 "..."
    default String preview(String message) {
        if (message == null) return "";
        String m = message.trim();
        if (m.length() <= 10) return m;
        return m.substring(0, 10) + "...";
    }
}

class EmailAlert implements AlertChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message) || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL to " + receiver + ": " + message);
        return true;
    }
}

class ConsoleAlert implements AlertChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (!isValid(receiver, message)) {
            return false;
        }
        System.out.println("CONSOLE " + receiver + ": " + message);
        return true;
    }
}

public class DefaultMethodDemo {
    public static void main(String[] args) {
        AlertChannel email = new EmailAlert();
        AlertChannel console = new ConsoleAlert();

        String longMsg = "Class starts at 10:10, please be on time";
        System.out.println("preview: " + email.preview(longMsg));   // 只顯示前 10 字
        System.out.println(email.send("amy@example.com", longMsg));
        System.out.println(console.send("B113", "Class starts"));
        System.out.println(email.send("invalid", "Class starts"));
    }
}
