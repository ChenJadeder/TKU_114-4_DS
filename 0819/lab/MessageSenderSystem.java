//課堂實作題二：訊息發送 Interface
//指定檔名：`MessageSenderSystem.java`

//完成標準：空白 receiver 或 message 必須處理；新增 sender 時不修改 `notify()`。
interface MessageSender {
    boolean send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        // 不能是 null 或空白，需含 @
        if (receiver == null || message == null) {
            return false;
        }
        String r = receiver.trim();
        String m = message.trim();
        if (r.length() == 0 || m.length() == 0) {
            return false;
        }
        if (r.indexOf('@') < 0) {
            return false;
        }
        System.out.println("EMAIL " + r + " -> " + m);
        return true;
    }
}

class SmsSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        // 長度至少 8
        if (receiver == null || message == null) {
            return false;
        }
        String r = receiver.trim();
        String m = message.trim();
        if (r.length() == 0 || m.length() == 0) {
            return false;
        }
        if (r.length() < 8) {
            return false;
        }
        System.out.println("SMS " + r + " -> " + m);
        return true;
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public boolean send(String receiver, String message) {
        // 空白就不送
        if (receiver == null || message == null) {
            return false;
        }
        String r = receiver.trim();
        String m = message.trim();
        if (r.length() == 0 || m.length() == 0) {
            return false;
        }
        System.out.println("CONSOLE " + r + " -> " + m);
        return true;
    }
}

public class MessageSenderSystem {
    static void notify(MessageSender sender, String receiver, String message) {
        // 不會因為新增 sender 而修改
        boolean ok = sender.send(receiver, message);
        System.out.println("sent=" + ok);
    }

    public static void main(String[] args) {
        MessageSender email = new EmailSender();
        MessageSender sms = new SmsSender();
        MessageSender console = new ConsoleSender();

        notify(email, "amy@example.com", "Class starts");
        notify(sms, "09126666", "Code review");
        notify(sms, "shorts", "Code review");
        notify(console, "Room B113", "Standup");
        notify(email, "invalid", "Class starts");
        notify(console, " ", "   ");
    }
}
