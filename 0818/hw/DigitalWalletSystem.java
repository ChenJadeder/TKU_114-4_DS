class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private int transactionCount;

    DigitalWallet(String walletId, String owner) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "UNKNOWN" : walletId;
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner;
        this.balance = 0;
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0) return false;
        balance += amount;
        transactionCount++;
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        transactionCount++;
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0) return false;
        balance += amount;
        transactionCount++;
        return true;
    }

    int getBalance() {
        return balance;
    }

    int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public String toString() {
        return walletId + " owner=" + owner + " balance=" + balance + " txCount=" + transactionCount;
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W1001", "Amy");

        System.out.println("deposit 1000: " + wallet.deposit(1000));   // true
        System.out.println("pay 250: " + wallet.pay(250));             // true
        System.out.println("pay 900 (insufficient): " + wallet.pay(900)); // false
        System.out.println("deposit -5 (invalid): " + wallet.deposit(-5)); // false
        System.out.println("refund 50: " + wallet.refund(50));         // true

        System.out.println(wallet); // 應顯示 balance 與交易次數，失敗操作不改變狀態
    }
}