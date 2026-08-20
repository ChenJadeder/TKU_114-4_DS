// 課後作業二 
// 指定檔名 :`DeliveryStrategySystem.java`
// 註解簡短：先做防呆，再算費用，級距只看重量也一起放進來

interface DeliveryMethod {
    int fee(int weightKg, int distanceKm);
    String estimate(int distanceKm);
    String methodName();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public int fee(int weightKg, int distanceKm) {
        int w = (weightKg < 0) ? 0 : weightKg;
        int d = (distanceKm < 0) ? 0 : distanceKm;
        int base = 60;
        int byDistance = 5 * d;
        int byWeight = 10 * w;
        return base + byDistance + byWeight;
    }

    @Override
    public String estimate(int distanceKm) {
        int d = (distanceKm < 0) ? 0 : distanceKm;
        int days;
        if (d <= 5) {
            days = 1;
        } else if (d <= 20) {
            days = 2;
        } else {
            days = 3;
        }
        return "宅配預估 " + days + " 天";
    }

    @Override
    public String methodName() {
        return "HomeDelivery";
    }
}

class StorePickup implements DeliveryMethod {
    @Override
    public int fee(int weightKg, int distanceKm) {
        return 60; // 固定費用
    }

    @Override
    public String estimate(int distanceKm) {
        return "超商取貨預估 2-3 天可取";
    }

    @Override
    public String methodName() {
        return "StorePickup";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public int fee(int weightKg, int distanceKm) {
        return 0; // 自取 0 元
    }

    @Override
    public String estimate(int distanceKm) {
        return "門市自取（備貨約 1 小時）";
    }

    @Override
    public String methodName() {
        return "SelfPickup";
    }
}

// 只看重量級距的策略
class WeightTierDelivery implements DeliveryMethod {
    private static final boolean distance = false; // 想觀察時改為 true

    @Override
    public int fee(int weightKg, int distanceKm) {
        int w = (weightKg < 0) ? 0 : weightKg;

        if (w <= 5) {
            if (distance) System.out.println("tier: <=5kg");
            return 80;       // 0~5kg
        } else if (w <= 10) {
            if (distance) System.out.println("tier: 6~10kg");
            return 120;      // 6~10kg
        } else if (w <= 20) {
            if (distance) System.out.println("tier: 11~20kg");
            return 180;      // 11~20kg
        } else {
            if (distance) System.out.println("tier: >20kg");
            return 180 + (w - 20) * 10; // 超過 20kg 每 kg 多 10 元
        }
    }

    @Override
    public String estimate(int distanceKm) {
        return "宅配預估 2 天";
    }

    @Override
    public String methodName() {
        return "WeightTierDelivery";
    }
}

class OrderService {
    private DeliveryMethod method;

    OrderService(DeliveryMethod method) {
        this.method = method;
    }

    int shippingFee(int weightKg, int distanceKm) {
        return method.fee(weightKg, distanceKm);
    }

    String estimate(int distanceKm) {
        return method.estimate(distanceKm);
    }

    String summary(int weightKg, int distanceKm) {
        int fee = shippingFee(weightKg, distanceKm);
        String eta = estimate(distanceKm);
        return method.methodName() + " fee=" + fee + " | " + eta;
    }
}

public class DeliveryStrategySystem {
    public static void main(String[] args) {
        // 原計畫
        OrderService home = new OrderService(new HomeDelivery());
        OrderService store = new OrderService(new StorePickup());
        OrderService self = new OrderService(new SelfPickup());

        System.out.println(home.summary(3, 10));   // 60 + 5*10 + 10*3 = 140
        System.out.println(store.summary(5, 50));  // 60
        System.out.println(self.summary(2, -1));   // 0
    System.out.println("若只看重量的級距:");
        OrderService weightTier = new OrderService(new WeightTierDelivery());
        System.out.println(weightTier.summary(3, 10));   // 80
        System.out.println(weightTier.summary(8, 50));   // 120
        System.out.println(weightTier.summary(15, 2));   // 180
    }
}