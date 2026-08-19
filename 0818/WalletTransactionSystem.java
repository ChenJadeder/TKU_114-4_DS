final class WalletTransaction {
    private final int sequence;
    private final String type;
    private final int amount;
    private final int balanceAfter;
// 新增實作變化

    WalletTransaction(int sequence, String type, int amount, int balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    @Override
    public String toString() {
        return sequence + " " + type + " " + amount
                + " balance=" + balanceAfter;
    }
}

class DigitalWallet {
    private final String walletId;
    private final String owner;
    private int balance;
    private final WalletTransaction[] transactions;
    private int transactionCount;

    DigitalWallet(String walletId, String owner, int historyCapacity) {
        this.walletId = walletId == null || walletId.isBlank()
                ? "UNKNOWN" : walletId;
        this.owner = owner == null || owner.isBlank() ? "Unknown" : owner;
        this.balance = 0;
        this.transactions = new WalletTransaction[Math.max(1, historyCapacity)];
        this.transactionCount = 0;
    }

    boolean deposit(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("DEPOSIT", amount);
        return true;
    }

    boolean pay(int amount) {
        if (amount <= 0 || amount > balance
                || transactionCount >= transactions.length) {
            return false;
        }
        balance -= amount;
        record("PAY", amount);
        return true;
    }

    boolean refund(int amount) {
        if (amount <= 0 || transactionCount >= transactions.length) {
            return false;
        }
        balance += amount;
        record("REFUND", amount);
        return true;
    }

    // 新增：跨錢包轉帳。任一檢查失敗時，兩邊都不得變更。
    boolean transferTo(DigitalWallet target, int amount) {
        if (target == null || target == this) {
            return false;
        }
        if (amount <= 0 || amount > this.balance) {
            return false;
        }
        // 兩邊都必須有空間
        if (this.transactionCount >= this.transactions.length
                || target.transactionCount >= target.transactions.length) {
            return false;
        }
        // 所有驗證通過後才修改狀態與留紀錄
        this.balance -= amount;
        this.record("TRANSFER_OUT", amount);

        target.balance += amount;
        target.record("TRANSFER_IN", amount);

        return true;
    }

    private void record(String type, int amount) {
        transactions[transactionCount] = new WalletTransaction(
                transactionCount + 1, type, amount, balance);
        transactionCount++;
    }

    void printStatement() {
        System.out.println(walletId + " owner=" + owner
                + " balance=" + balance);
        for (int i = 0; i < transactionCount; i++) {
            System.out.println(transactions[i]);
        }
    }
}

public class WalletTransactionSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W001", "Amy", 5);
        DigitalWallet target = new DigitalWallet("W002", "Ben", 5);

        System.out.println("deposit=" + wallet.deposit(1000));
        System.out.println("pay 250=" + wallet.pay(250));
        System.out.println("transfer 200=" + wallet.transferTo(target, 200)); // 成功
        System.out.println("pay 900=" + wallet.pay(900)); // 失敗
        System.out.println("refund=" + wallet.refund(50));

        wallet.printStatement();
        target.printStatement();
    }
}