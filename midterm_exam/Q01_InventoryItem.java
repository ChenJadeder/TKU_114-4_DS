public class Q01_InventoryItem {
    // prviate final
    private final String id;
    private final String name;
    private int stock;

    // (Constructor)
    public Q01_InventoryItem(String id, String name, int stock) {
        // Validation
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID 不能為 null 或空白字串");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name 不能為 null 或空白字串");
        }

        // Data Sanitization
        this.id = id.trim();
        this.name = name.trim();

        // 庫存
        this.stock = (stock < 0) ? 0 : stock;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStock() {
        return stock;
    }

    // restock
    public boolean restock(int amount) {
        if (amount > 0) {
            this.stock += amount;
            return true;
        }
        return false;
    }

    // 出貨與銷售
    public boolean sell(int amount) {
        if (amount > 0 && this.stock >= amount) {
            this.stock -= amount;
            return true;
        }
        return false;
    }

    // 狀態回傳 
    public String status() {
        return id + " | " + name + " | " + stock;
    }
//A14997777
}