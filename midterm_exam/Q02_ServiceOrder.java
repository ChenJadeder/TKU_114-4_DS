import java.util.ArrayList;
import java.util.List;

public class Q02_ServiceOrder {

    //  LineItem 封裝
    public static class LineItem {
        private final String name;
        private final int unitPrice;
        private final int quantity;

        public LineItem(String name, int unitPrice, int quantity) {
            this.name = name;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public String getName() {
            return name;
        }

        public int getUnitPrice() {
            return unitPrice;
        }

        public int getQuantity() {
            return quantity;
        }

        // 小計：單價 x 數量
        public int subtotal() {
            return unitPrice * quantity;
        }
    }

    private final String orderId;
    private final List<LineItem> items;

    //訂單
    public Q02_ServiceOrder(String orderId) {
        if (orderId == null || orderId.isBlank()) {
            throw new IllegalArgumentException("orderId 不能為 null 或 blank");
        }
        this.orderId = orderId.trim();
        this.items = new ArrayList<>();
    }

    //新增品項
    public boolean addItem(String name, int unitPrice, int quantity) {
        if (name == null || name.isBlank()) {
            return false;
        }
        if (unitPrice < 0 || quantity <= 0) {
            return false;
        }
        items.add(new LineItem(name.trim(), unitPrice, quantity));
        return true;
    }

    public int itemCount() {
        return items.size();
    }

    // 總金額加總
    public int totalAmount() {
        int total = 0;
        for (LineItem li : items) {
            total += li.subtotal();
        }
        return total;
    }

    // 尋找最高小計項目（平手保留較早者）
    public String largestItemName() {
        if (items.isEmpty()) {
            return "";
        }
        LineItem best = items.get(0);
        int bestSubtotal = best.subtotal();

        for (int i = 1; i < items.size(); i++) {
            LineItem cur = items.get(i);
            int s = cur.subtotal();
            // 若相同則保留較早加入者
            if (s > bestSubtotal) {
                bestSubtotal = s;
                best = cur;
            }
        }
        return best.getName();
    }

    // 回傳list
    public List<String> itemSummaries() {
        List<String> summaries = new ArrayList<>();
        for (LineItem li : items) {
            summaries.add(li.getName() + ": " + li.subtotal());
        }
        // 
        return List.copyOf(summaries);
    }

}
