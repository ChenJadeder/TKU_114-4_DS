// 課後作業六：訂單索引系統
// 指定檔名：OrderBstSystem.java

class Order {
    private String orderId;
    private String customer;
    private int amount;

    Order(String orderId, String customer, int amount) {
        if (orderId == null || orderId.trim().length() == 0) {
            this.orderId = "UNKNOWN";
        } else {
            this.orderId = orderId.trim();
        }

        if (customer == null || customer.trim().length() == 0) {
            this.customer = "Unknown";
        } else {
            this.customer = customer.trim();
        }

        if (amount < 0) {
            this.amount = 0;
        } else {
            this.amount = amount;
        }
    }

    String getOrderId() {
        return orderId;
    }

    int getAmount() {
        return amount;
    }

    boolean updateAmount(int amount) {
        if (amount < 0) {
            return false;
        }

        this.amount = amount;
        return true;
    }

    @Override
    public String toString() {
        return orderId + " customer=" + customer + " amount=" + amount;
    }
}

class OrderNode {
    Order order;
    OrderNode left;
    OrderNode right;

    OrderNode(Order order) {
        this.order = order;
    }
}

class OrderBst {
    private OrderNode root;

    boolean add(Order order) {
        if (order == null) {
            return false;
        }

        if (root == null) {
            root = new OrderNode(order);
            return true;
        }

        OrderNode current = root;

        while (true) {
            int compare = order.getOrderId()
                    .compareTo(current.order.getOrderId());

            if (compare == 0) {
                return false;
            }

            if (compare < 0) {
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

    Order find(String orderId) {
        if (orderId == null) {
            return null;
        }

        String target = orderId.trim();
        OrderNode current = root;

        while (current != null) {
            int compare = target.compareTo(current.order.getOrderId());

            if (compare == 0) {
                return current.order;
            }

            if (compare < 0) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    boolean updateAmount(String orderId, int newAmount) {
        Order order = find(orderId);

        if (order == null) {
            return false;
        }

        return order.updateAmount(newAmount);
    }

    boolean cancel(String orderId) {
        if (find(orderId) == null) {
            return false;
        }

        root = cancel(root, orderId.trim());
        return true;
    }

    private OrderNode cancel(OrderNode node, String orderId) {
        if (node == null) {
            return null;
        }

        int compare = orderId.compareTo(node.order.getOrderId());

        if (compare < 0) {
            node.left = cancel(node.left, orderId);
        } else if (compare > 0) {
            node.right = cancel(node.right, orderId);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            OrderNode successor = minimumNode(node.right);
            node.order = successor.order;
            node.right = cancel(node.right,
                    successor.order.getOrderId());
        }

        return node;
    }

    private OrderNode minimumNode(OrderNode node) {
        OrderNode current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    void rangeReport(String low, String high) {
        if (low == null || high == null) {
            System.out.println("range: invalid");
            return;
        }

        low = low.trim();
        high = high.trim();

        if (low.compareTo(high) > 0) {
            System.out.println("range " + low + "~" + high + ": invalid");
            return;
        }

        System.out.println("range " + low + "~" + high + ":");
        rangeReport(root, low, high);
    }

    private void rangeReport(OrderNode node, String low, String high) {
        if (node == null) {
            return;
        }

        String id = node.order.getOrderId();

        if (id.compareTo(low) > 0) {
            rangeReport(node.left, low, high);
        }

        if (id.compareTo(low) >= 0 && id.compareTo(high) <= 0) {
            System.out.println("  " + node.order);
        }

        if (id.compareTo(high) < 0) {
            rangeReport(node.right, low, high);
        }
    }

    int size() {
        return size(root);
    }

    private int size(OrderNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }

    Integer totalAmount() {
        if (root == null) {
            return null;
        }

        return totalAmount(root);
    }

    private int totalAmount(OrderNode node) {
        if (node == null) {
            return 0;
        }

        return node.order.getAmount()
                + totalAmount(node.left)
                + totalAmount(node.right);
    }

    void inorderReport() {
        System.out.println("inorder report:");
        inorderReport(root);
    }

    private void inorderReport(OrderNode node) {
        if (node == null) {
            return;
        }

        inorderReport(node.left);
        System.out.println("  " + node.order);
        inorderReport(node.right);
    }

    void printSummary() {
        System.out.println("size=" + size());
        System.out.println("total amount=" + totalAmount());
    }
}

public class OrderBstSystem {
    public static void main(String[] args) {
        OrderBst orders = new OrderBst();

        System.out.println("add O300="
                + orders.add(new Order("O300", "Amy", 1200)));
        System.out.println("add O100="
                + orders.add(new Order("O100", "Ben", 500)));
        System.out.println("add O500="
                + orders.add(new Order("O500", "Cara", 2100)));
        System.out.println("add O200="
                + orders.add(new Order("O200", "Dan", 800)));
        System.out.println("add O400="
                + orders.add(new Order("O400", "Eve", 1500)));

        System.out.println("duplicate O100="
                + orders.add(new Order("O100", "Other", 999)));

        System.out.println("find O200=" + orders.find("O200"));
        System.out.println("find O999=" + orders.find("O999"));

        System.out.println("update O300 to 1800="
                + orders.updateAmount("O300", 1800));
        System.out.println("update missing O999="
                + orders.updateAmount("O999", 100));

        orders.inorderReport();
        orders.printSummary();

        orders.rangeReport("O150", "O450");
        orders.rangeReport("O900", "O100");

        System.out.println("cancel O300=" + orders.cancel("O300"));
        System.out.println("cancel missing O999=" + orders.cancel("O999"));

        orders.inorderReport();
        orders.printSummary();
    }
}