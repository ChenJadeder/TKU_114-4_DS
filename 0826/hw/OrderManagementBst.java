//課後作業六：訂單管理綜合系統
//指定檔名：OrderManagementBst.java
// BST 負責用 orderId 快速定位訂單；但決定後續狀態則是訂單狀態規則

import java.util.ArrayList;
import java.util.List;

class Order {
    final int orderId;
    final String customer;
    final double amount;
    private String status;

    Order(int orderId, String customer, double amount, String status) {
        this.orderId = orderId;
        this.customer = customer;
        this.amount = amount;
        this.status = status;
    }

    String getStatus() {
        return status;
    }

    void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{id=" + orderId
                + ", customer='" + customer
                + "', amount=" + amount
                + ", status='" + status + "'}";
    }
}

class OrderNode {
    Order data;
    OrderNode left;
    OrderNode right;

    OrderNode(Order data) {
        this.data = data;
    }
}

class OrderBst {
    private OrderNode root;

    boolean add(Order order) {
        if (!isValidOrder(order)) {
            return false;
        }

        if (root == null) {
            root = new OrderNode(order);
            return true;
        }

        OrderNode current = root;

        while (true) {
            if (order.orderId == current.data.orderId) {
                return false;
            }

            if (order.orderId < current.data.orderId) {
                if (current.left == null) {
                    current.left = new OrderNode(order);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new OrderNode(order);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Order find(int orderId) {
        OrderNode current = root;

        while (current != null) {
            if (orderId == current.data.orderId) {
                return current.data;
            }

            current = orderId < current.data.orderId
                    ? current.left
                    : current.right;
        }

        return null;
    }

    boolean updateStatus(int orderId, String newStatus) {
        if (!isValidStatus(newStatus)) {
            return false;
        }

        Order order = find(orderId);

        if (order == null) {
            return false;
        }

        order.setStatus(newStatus.trim().toUpperCase());
        return true;
    }

    boolean cancel(int orderId) {
        Order order = find(orderId);

        if (order == null || order.getStatus().equals("CANCELLED")) {
            return false;
        }

        order.setStatus("CANCELLED");
        return true;
    }

    boolean remove(int orderId) {
        Order order = find(orderId);

        if (order == null || !order.getStatus().equals("CANCELLED")) {
            return false;
        }

        root = remove(root, orderId);
        return true;
    }

    private OrderNode remove(OrderNode node, int orderId) {
        if (orderId < node.data.orderId) {
            node.left = remove(node.left, orderId);
        } else if (orderId > node.data.orderId) {
            node.right = remove(node.right, orderId);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            OrderNode successor = minimumNode(node.right);
            node.data = successor.data;
            node.right = remove(node.right, successor.data.orderId);
        }

        return node;
    }

    private OrderNode minimumNode(OrderNode node) {
        while (node.left != null) {
            node = node.left;
        }

        return node;
    }

    List<Order> idRangeReport(int low, int high) {
        List<Order> result = new ArrayList<>();

        if (low <= high) {
            idRangeReport(root, low, high, result);
        }

        return result;
    }

    private void idRangeReport(OrderNode node, int low, int high,
                               List<Order> result) {
        if (node == null) {
            return;
        }

        if (low < node.data.orderId) {
            idRangeReport(node.left, low, high, result);
        }

        if (low <= node.data.orderId && node.data.orderId <= high) {
            result.add(node.data);
        }

        if (node.data.orderId < high) {
            idRangeReport(node.right, low, high, result);
        }
    }

    double totalAmount() {
        return totalAmount(root);
    }

    private double totalAmount(OrderNode node) {
        if (node == null) {
            return 0;
        }

        return node.data.amount
                + totalAmount(node.left)
                + totalAmount(node.right);
    }

    List<Order> inorder() {
        List<Order> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }

    private void inorder(OrderNode node, List<Order> result) {
        if (node == null) {
            return;
        }

        inorder(node.left, result);
        result.add(node.data);
        inorder(node.right, result);
    }

    private boolean isValidOrder(Order order) {
        return order != null
                && isNotBlank(order.customer)
                && order.amount >= 0
                && isValidStatus(order.getStatus());
    }

    private boolean isValidStatus(String status) {
        return isNotBlank(status);
    }

    private boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }
}

public class OrderManagementBst {
    private static void printOrders(String title, List<Order> orders) {
        System.out.println(title);

        for (Order order : orders) {
            System.out.println(order);
        }

        System.out.println();
    }

    public static void main(String[] args) {
        OrderBst system = new OrderBst();

        System.out.println("add300="
                + system.add(new Order(300, "Mina", 1200.0, "NEW")));

        System.out.println("add100="
                + system.add(new Order(100, "Leo", 450.5, "PAID")));

        System.out.println("add500="
                + system.add(new Order(500, "Nora", 800.0, "SHIPPED")));

        System.out.println("add200="
                + system.add(new Order(200, "Ivy", 300.0, "NEW")));

        System.out.println("duplicate="
                + system.add(new Order(100, "Other", 99.0, "NEW")));

        System.out.println("negativeAmount="
                + system.add(new Order(400, "Invalid", -10.0, "NEW")));

        System.out.println("blankCustomer="
                + system.add(new Order(400, " ", 100.0, "NEW")));

        System.out.println("find200=" + system.find(200));
        System.out.println("find999=" + system.find(999));

        System.out.println("update200="
                + system.updateStatus(200, " paid "));

        System.out.println("invalidUpdate="
                + system.updateStatus(200, " "));

        System.out.println("missingUpdate="
                + system.updateStatus(999, "PAID"));

        System.out.println("removeActive300=" + system.remove(300));

        System.out.println("cancel300=" + system.cancel(300));
        System.out.println("cancelAgain300=" + system.cancel(300));

        printOrders("range [100, 350]",
                system.idRangeReport(100, 350));

        printOrders("invalid range [350, 100]",
                system.idRangeReport(350, 100));

        System.out.println("totalBeforeRemove=" + system.totalAmount());

        System.out.println("remove300=" + system.remove(300));
        System.out.println("removeMissing=" + system.remove(999));

        System.out.println("totalAfterRemove=" + system.totalAmount());

        printOrders("inorder report", system.inorder());
    }
}
