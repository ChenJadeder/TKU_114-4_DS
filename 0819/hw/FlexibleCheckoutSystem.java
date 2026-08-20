// 課堂實作題五：安全型態判斷

// 指定檔名：`DeviceInspectionSystem.java`

// 需求：三種 PricingPolicy（原價、VIP 85 折、滿 2000 折 300）；三種 NotificationChannel（Email/SMS/Console）
// checkout() 回傳 CheckoutResult，包含 orderId、originalPrice、finalPrice、sending status


interface PricingPolicy {
    int finalPrice(int originalPrice);
    String name();
}

class StandardPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        return Math.max(0, originalPrice);
    }
    @Override
    public String name() { return "standard"; }
}

class VipPricing implements PricingPolicy {
    @Override
    public int finalPrice(int originalPrice) {
        int p = Math.max(0, originalPrice);
        return p * 85 / 100; // 85 折
    }
    @Override
    public String name() { return "vip85"; }
}

class ThresholdDiscountPricing implements PricingPolicy {
    private final int threshold;
    private final int discount;
    ThresholdDiscountPricing(int threshold, int discount) {
        this.threshold = Math.max(0, threshold);
        this.discount = Math.max(0, discount);
    }
    @Override
    public int finalPrice(int originalPrice) {
        int p = Math.max(0, originalPrice);
        if (p >= threshold) {
            p = p - discount;
        }
        return Math.max(0, p);
    }
    @Override
    public String name() { return "threshold(-" + discount + " if >= " + threshold + ")"; }
}

interface NotificationChannel {
    boolean send(String receiver, String message);
    String name();
}

class EmailChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || message == null) return false;
        String r = receiver.trim();
        String m = message.trim();
        if (r.isEmpty() || m.isEmpty()) return false;
        if (!r.contains("@")) return false;
        System.out.println("EMAIL " + r + " -> " + m);
        return true;
    }
    @Override
    public String name() { return "email"; }
}

class SmsChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || message == null) return false;
        String r = receiver.trim();
        String m = message.trim();
        if (r.isEmpty() || m.isEmpty()) return false;
        if (r.length() < 8) return false; // 長度驗證
        System.out.println("SMS " + r + " -> " + m);
        return true;
    }
    @Override
    public String name() { return "sms"; }
}

class ConsoleChannel implements NotificationChannel {
    @Override
    public boolean send(String receiver, String message) {
        if (receiver == null || message == null) return false;
        String r = receiver.trim();
        String m = message.trim();
        if (r.isEmpty() || m.isEmpty()) return false;
        System.out.println("CONSOLE " + r + " -> " + m);
        return true;
    }
    @Override
    public String name() { return "console"; }
}

final class CheckoutResult {
    private final String orderId;
    private final int originalPrice;
    private final int finalPrice;
    private final boolean sent;

    CheckoutResult(String orderId, int originalPrice, int finalPrice, boolean sent) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.sent = sent;
    }

    public String getOrderId() { return orderId; }
    public int getOriginalPrice() { return originalPrice; }
    public int getFinalPrice() { return finalPrice; }
    public boolean isSent() { return sent; }

    @Override
    public String toString() {
        return "CheckoutResult{orderId='" + orderId + "', original=" + originalPrice +
               ", final=" + finalPrice + ", sent=" + sent + "}";
    }
}

class CheckoutService {
    private final PricingPolicy pricing;
    private final NotificationChannel channel;

    CheckoutService(PricingPolicy pricing, NotificationChannel channel) {
        this.pricing = pricing;
        this.channel = channel;
    }

    // 回傳結果
    CheckoutResult checkout(String orderId, int originalPrice, String receiver) {
        if (orderId == null || orderId.isBlank() || originalPrice < 0) {
            return new CheckoutResult(safe(orderId), Math.max(0, originalPrice), 0, false);
        }
        int finalPrice = pricing.finalPrice(originalPrice);
        boolean sent = channel.send(receiver, "order=" + orderId + ", amount=" + finalPrice);
        return new CheckoutResult(orderId, originalPrice, finalPrice, sent);
    }

    private static String safe(String s) {
        if (s == null) return "UNKNOWN";
        String t = s.trim();
        return t.isEmpty() ? "UNKNOWN" : t;
    }
}

public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        // 三種 pricing
        PricingPolicy standard = new StandardPricing();
        PricingPolicy vip = new VipPricing();
        PricingPolicy full2000Minus300 = new ThresholdDiscountPricing(2000, 300);

        // 三種 channel
        NotificationChannel email = new EmailChannel();
        NotificationChannel sms = new SmsChannel();
        NotificationChannel console = new ConsoleChannel();

        // 六種組合測試
        CheckoutService s1 = new CheckoutService(standard, email);
        CheckoutService s2 = new CheckoutService(standard, sms);
        CheckoutService s3 = new CheckoutService(vip, console);
        CheckoutService s4 = new CheckoutService(vip, email);
        CheckoutService s5 = new CheckoutService(full2000Minus300, sms);
        CheckoutService s6 = new CheckoutService(full2000Minus300, console);

        System.out.println(s1.checkout("O100", 1800, "amy@example.com"));  // 1800 (standard) + email
        System.out.println(s2.checkout("O101", 800, "09123456"));          // 800 (standard) + sms
        System.out.println(s3.checkout("O102", 1000, "counter"));          // 850 (vip) + console
        System.out.println(s4.checkout("O103", 5000, "vip@example.com"));  // 4250 (vip) + email
        System.out.println(s5.checkout("O104", 2000, "09876543"));         // 1700 (>=2000 -300) + sms
        System.out.println(s6.checkout("O105", 1999, "Kiosk"));            // 1999 (<2000 no discount) + console

        // bounded
        System.out.println(s1.checkout("   ", 1000, "invalid"));           
        System.out.println(s2.checkout("O106", -5, "09110000"));           // 負數改為 false, final=0
    }
}