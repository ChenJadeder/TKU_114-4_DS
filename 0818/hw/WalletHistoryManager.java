final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = (type == null) ? "UNKNOWN" : type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    int getSequence() {
        return sequence;
    }

    String getType() {
        return type;
    }

    int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] history;
    private int count;

    DigitalWallet(String walletId, String owner, int capacity) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner;
        this.balance = 0;
        this.history = new WalletTransaction[Math.max(1, capacity)];
        this.count = 0;
    }

    private boolean hasSpace() {
        return count < history.length;
    }

    private void record(String type, int amount) {
        history[count] = new WalletTransaction(count + 1, type, amount, balance);
        count++;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || !hasSpace()) return false;
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance || !hasSpace()) return false;
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || !hasSpace()) return false;
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    // 來源 -> 目標 轉帳。兩邊都要有空間，且來源餘額足夠；任何條件不符，兩邊都不得改變。
    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this) return false;
        if (amount <= 0) return false;
        if (this.balance < amount) return false;
        if (!this.hasSpace() || !target.hasSpace()) return false;

        // 所有驗證通過後才進行狀態變更
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    WalletTransaction findTransaction(int sequence) {
        if (sequence <= 0) return null;
        for (int i = 0; i < count; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    int totalByType(String type) {
        if (type == null) return 0;
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (type.equalsIgnoreCase(history[i].getType())) {
                total += history[i].getAmount();
            }
        }
        return total;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner + " balance=" + balance);
        for (int i = 0; i < count; i++) {
            System.out.println(history[i]);
        }
    }

    @Override
    public String toString() {
        return walletId + " owner=" + owner + " balance=" + balance + " tx=" + count + "/" + history.length;
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        // 建立兩個錢包，容量設小一點以測試「陣列已滿不得修改餘額」
        DigitalWallet a = new DigitalWallet("W-A", "Alice", 3);
        DigitalWallet b = new DigitalWallet("W-B", "Bob", 3);

        System.out.println("A deposit 1000: " + a.deposit(1000)); // 成功
        System.out.println("A pay 300: " + a.pay(300));           // 成功
        System.out.println("A -> B transfer 200: " + a.transferTo(b, 200)); // 成功

        // 驗證查詢與加總
        System.out.println("A find #2: " + a.findTransaction(2));
        System.out.println("A total PAY: " + a.totalByType("PAY"));

        // 填滿 B 的歷史後，再嘗試轉帳（應失敗且兩邊不改變）
        System.out.println("B deposit 50: " + b.deposit(50));  // 第二筆
        System.out.println("B refund 20: " + b.refund(20));    // 第三筆 -> 滿了

        System.out.println("A -> B transfer 100 (should fail due to B full): " + a.transferTo(b, 100));

        System.out.println("--- Statements ---");
        a.printStatement();
        b.printStatement();
    }
}