// 課後作業二：商品庫存 BST
// 指定檔名：ProductInventoryBst.java

class Product {
    private int id;
    private String name;
    private int stock;

    Product(int id, String name, int stock) {
        this.id = id;

        if (name == null || name.trim().length() == 0) {
            this.name = "Unknown";
        } else {
            this.name = name.trim();
        }

        if (stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }

    int getId() {
        return id;
    }

    int getStock() {
        return stock;
    }

    void restock(int amount) {
        if (amount > 0) {
            stock += amount;
        }
    }

    boolean sell(int amount) {
        if (amount <= 0 || amount > stock) {
            return false;
        }

        stock -= amount;
        return true;
    }

    @Override
    public String toString() {
        return id + " " + name + " stock=" + stock;
    }
}
// Product 存取
class ProductNode {
    Product product;
    ProductNode left;
    ProductNode right;

    ProductNode(Product product) {
        this.product = product;
    }
}

class ProductBst {
    private ProductNode root;

    boolean add(Product product) {
        if (product == null) {
            return false;
        }

        if (root == null) {
            root = new ProductNode(product);
            return true;
        }

        ProductNode current = root;

        while (true) {
            int id = product.getId();
            int currentId = current.product.getId();

            if (id == currentId) {
                return false;
            }

            if (id < currentId) {
                if (current.left == null) {
                    current.left = new ProductNode(product);
                    return true;
                }
                current = current.left;
            } else {
                if (current.right == null) {
                    current.right = new ProductNode(product);
                    return true;
                }
                current = current.right;
            }
        }
    }

    Product find(int id) {
        ProductNode current = root;

        while (current != null) {
            int currentId = current.product.getId();

            if (id == currentId) {
                return current.product;
            }

            if (id < currentId) {
                current = current.left;
            } else {
                current = current.right;
            }
        }

        return null;
    }

    boolean restock(int id, int amount) {
        Product product = find(id);

        if (product == null || amount <= 0) {
            return false;
        }

        product.restock(amount);
        return true;
    }

    boolean sell(int id, int amount) {
        Product product = find(id);

        if (product == null) {
            return false;
        }

        return product.sell(amount);
    }

    boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }

        root = remove(root, id);
        return true;
    }
    // 移除商品
    private ProductNode remove(ProductNode node, int id) {
        if (node == null) {
            return null;
        }

        int currentId = node.product.getId();

        if (id < currentId) {
            node.left = remove(node.left, id);
        } else if (id > currentId) {
            node.right = remove(node.right, id);
        } else {
            if (node.left == null) {
                return node.right;
            }

            if (node.right == null) {
                return node.left;
            }

            ProductNode successor = minimumNode(node.right);
            node.product = successor.product;
            node.right = remove(node.right, successor.product.getId());
        }

        return node;
    }

    private ProductNode minimumNode(ProductNode node) {
        ProductNode current = node;

        while (current.left != null) {
            current = current.left;
        }

        return current;
    }

    void inorderReport() {
        System.out.println("inorder report:");
        inorderReport(root);
    }

    private void inorderReport(ProductNode node) {
        if (node == null) {
            return;
        }

        inorderReport(node.left);
        System.out.println("  " + node.product);
        inorderReport(node.right);
    }
   // 顯示當前size
    int size() {
        return size(root);
    }

    private int size(ProductNode node) {
        if (node == null) {
            return 0;
        }

        return 1 + size(node.left) + size(node.right);
    }
}
// 商品主程式
public class ProductInventoryBst {
    public static void main(String[] args) {
        ProductBst inventory = new ProductBst();

        System.out.println("add 300=" +
                inventory.add(new Product(300, "Keyboard", 5)));
        System.out.println("add 100=" +
                inventory.add(new Product(100, "Mouse", 8)));
        System.out.println("add 500=" +
                inventory.add(new Product(500, "Monitor", 2)));
        System.out.println("add 200=" +
                inventory.add(new Product(200, "USB Hub", 4)));
        System.out.println("duplicate 100=" +
                inventory.add(new Product(100, "Duplicate Mouse", 1)));

        System.out.println("find 200=" + inventory.find(200));
        System.out.println("find 999=" + inventory.find(999));

        System.out.println("restock 300 by 10=" +
                inventory.restock(300, 10));
        System.out.println("sell 100 by 3=" +
                inventory.sell(100, 3));
        System.out.println("sell 500 by 10=" +
                inventory.sell(500, 10));
        System.out.println("restock missing 999=" +
                inventory.restock(999, 5));

        inventory.inorderReport();
        System.out.println("size=" + inventory.size());

        System.out.println("remove 300=" + inventory.remove(300));
        System.out.println("remove missing 999=" + inventory.remove(999));

        inventory.inorderReport();
        System.out.println("size after delete=" + inventory.size());
    }
}