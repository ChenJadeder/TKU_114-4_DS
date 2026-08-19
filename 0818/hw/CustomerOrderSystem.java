class Customer {
    private final String id;
    private final String name;

    Customer(String id, String name) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.name = (name == null || name.isBlank()) ? "Unknown" : name;
    }

    String label() {
        return id + " " + name;
    }
}

class OrderItem {
    private final String sku;
    private final String name;
    private final int unitPrice;
    private final int quantity;

    OrderItem(String sku, String name, int unitPrice, int quantity) {
        this.sku = (sku == null || sku.isBlank()) ? "UNKNOWN" : sku;
        this.name = (name == null || name.isBlank()) ? "Unnamed" : name;
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(0, quantity);
    }

    int subtotal() {
        return unitPrice * quantity;
    }

    @Override
    public String toString() {
        return sku + " " + name + " $" + unitPrice + " x " + quantity + " = $" + subtotal();
    }
}

class CustomerOrder {
    private final String orderId;
    private final Customer customer;
    private final OrderItem[] items;
    private int count;

    CustomerOrder(String orderId, Customer customer, int capacity) {
        this.orderId = (orderId == null || orderId.isBlank()) ? "UNKNOWN" : orderId;
        this.customer = customer;
        this.items = new OrderItem[Math.max(1, capacity)];
        this.count = 0;
    }

    boolean addItem(OrderItem item) {
        if (item == null || count >= items.length) return false;
        items[count++] = item;
        return true;
    }

    int itemCount() {
        return count;
    }

    int totalAmount() {
        int total = 0;
        for (int i = 0; i < count; i++) {
            total += items[i].subtotal();
        }
        return total;
    }

    String summary() {
        return orderId + " | " + customer.label() + " | items=" + itemCount() + " | total=$" + totalAmount();
    }

    void printItems() {
        for (int i = 0; i < count; i++) {
            System.out.println(items[i]);
        }
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer c1 = new Customer("C001", "Ben");
        Customer c2 = c1; // 共用同一位顧客的範例（可換成不同顧客）

        CustomerOrder order1 = new CustomerOrder("O9001", c1, 5);
        order1.addItem(new OrderItem("SKU-1", "Keyboard", 800, 2));
        order1.addItem(new OrderItem("SKU-2", "Mouse", 400, 1));
        order1.addItem(new OrderItem("SKU-3", "USB Hub", 300, 3));

        CustomerOrder order2 = new CustomerOrder("O9002", c2, 3);
        order2.addItem(new OrderItem("SKU-4", "Headset", 1200, 1));

        System.out.println(order1.summary());
        order1.printItems();

        System.out.println(order2.summary());
        order2.printItems();
    }
}