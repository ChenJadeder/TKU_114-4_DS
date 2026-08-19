interface PricingPolicy {
    int finalPrice(int originalPrice);
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 85 / 100;
    }
}

// 新增：學生九折
class StudentPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice) * 90 / 100;
    }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || !receiver.contains("@")) {
            return false;
        }
        System.out.println("EMAIL " + receiver + " -> " + message);
        return true;
    }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        System.out.println("CONSOLE " + receiver + " -> " + message);
        return true;
    }
}

// 新增：SMS 通道（簡單驗證長度）
class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || receiver.length() < 8) {
            return false;
        }
        System.out.println("SMS " + receiver + " -> " + message);
        return true;
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    // 不可修改 method body：僅透過新增 implementation 擴充
    boolean checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return false;
        }
        int amount = pricing.finalPrice(originalPrice);
        return channel.send(receiver, "order=" + orderId + ", amount=" + amount);
    }
}

public class CheckoutNotificationSystem {
    public static void main(String[] args) {
        CheckoutService vipEmail = new CheckoutService(new VipPricing(), new EmailChannel());
        CheckoutService regularConsole = new CheckoutService(new StandardPricing(), new ConsoleChannel());
        CheckoutService studentSms = new CheckoutService(new StudentPricing(), new SmsChannel());

        System.out.println("sent=" + vipEmail.checkout("O100", 2000, "amy@example.com")); // 1700
        System.out.println("sent=" + regularConsole.checkout("O101", 800, "counter"));     // 800
        System.out.println("sent=" + studentSms.checkout("O102", 1000, "09123456"));       // 900
        System.out.println("sent=" + studentSms.checkout("O103", 500, "short"));           // false: 無效 receiver
    }
}
