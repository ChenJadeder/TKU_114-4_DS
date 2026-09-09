//概念 3：Encapsulation 不等於只有 getter 與 setter
/*
 * 建立 Equipment 類別，表示一項設備及其目前可用數量。
 * 在建構子中設定設備編號、名稱與初始庫存。
 * 建立 borrowOne() 方法，處理借用一件設備的操作。
 * 借用前先檢查 availableCount 是否大於 0。
 * 若庫存大於 0，借用成功並將庫存減少 1。
 * 若庫存為 0，借用失敗並回傳 false，庫存維持不變。
 * 透過 toString() 顯示每次操作後的設備狀態。
 */
class Product {
    private String id;
    private String name;
    private int stock;

    Product(String id, String name, int stock) {
        this.id = id;
        this.name = name;
        this.stock = Math.max(0, stock);
    }

    boolean sell(int quantity) {
        if (quantity <= 0 || quantity > stock) {
            return false;
        }
        stock -= quantity;
        return true;
    }

    void restock(int quantity) {
        if (quantity > 0) {
            stock += quantity;
        }
    }

    int getStock() {
        return stock;
    }

    @Override
    public String toString() {
        return id + " " + name + " stock=" + stock;
    }
}

public class ProductEncapsulationDemo {
    public static void main(String[] args) {
        Product product = new Product("P101", "Keyboard", 8);

        System.out.println("銷售 3 個：" + product.sell(3));
        System.out.println("銷售 9 個：" + product.sell(9));
        product.restock(4);

        System.out.println(product);
    }
}
