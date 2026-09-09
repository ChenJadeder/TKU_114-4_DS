//概念 6：Composition 表達 has-a 關係
// Composition 可以把大型問題拆成責任清楚的小類別。Customer 管理客戶資料，Order 管理訂單狀態與金額，不塞進同一個 class。

class Customer {
    private String id;
    private String name;

    Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    String label() {
        return id + " " + name;
    }
}

class Order {
    private String orderId;
    private Customer customer;
    private int total;

    Order(String orderId, Customer customer, int total) {
        this.orderId = orderId;
        this.customer = customer;
        this.total = Math.max(0, total);
    }

    String summary() {
        return orderId + " | " + customer.label() + " | $" + total;
    }
}

public class OrderCompositionDemo {
    public static void main(String[] args) {
        Customer customer = new Customer("C101", "Amy");
        Order order = new Order("O9001", customer, 2500);

        System.out.println(order.summary());
    }
}
