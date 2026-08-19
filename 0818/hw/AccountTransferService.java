class Account {
    private final String id;
    private int balance;

    Account(String id, int openingBalance) {
        this.id = (id == null || id.isBlank()) ? "UNKNOWN" : id;
        this.balance = Math.max(0, openingBalance);
    }

    boolean withdraw(int amount) {
        if (amount <= 0 || amount > balance) return false;
        balance -= amount;
        return true;
    }

    boolean deposit(int amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }

    int getBalance() {
        return balance;
    }

    String getId() {
        return id;
    }

    @Override
    public String toString() {
        return id + " balance=" + balance;
    }
}

class TransferService {
    static boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) return false;
        if (source == target) return false;
        if (amount <= 0) return false;

        // 先檢查來源餘額是否足夠
        if (source.getBalance() < amount) return false;

        // 來源先扣，若扣失敗則直接返回
        if (!source.withdraw(amount)) {
            return false;
        }

        // 目標入帳，如意外失敗則追溯來源
        if (!target.deposit(amount)) {
            // rollback
            source.deposit(amount);
            return false;
        }
        return true;
    }
}

public class Main {
    public static void main(String[] args) {
        Account a = new Account("A", 1000);
        Account b = new Account("B", 200);

        System.out.println("success: " + TransferService.transfer(a, b, 300));
        System.out.println(a);
        System.out.println(b);

        System.out.println("insufficient: " + TransferService.transfer(a, b, 10000));
        System.out.println(a);
        System.out.println(b);

        System.out.println("same account: " + TransferService.transfer(a, a, 100));
        System.out.println(a);

        System.out.println("null target: " + TransferService.transfer(a, null, 50));
        System.out.println(a);
    }
}