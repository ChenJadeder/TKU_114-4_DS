import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// 課後作業二：登入紀錄分析
// 指定檔名：LoginActivityReport.java

public class LoginActivityReport {

    // 帳號 -> 登入次數
    private Map<String, Integer> loginCounts;

    // 記錄不同的 IP
    private Set<String> uniqueIps;

    // 異常登入次數門檻 
    private static final int ANOMALY_THRESHOLD = 3;

    public LoginActivityReport() {
        loginCounts = new HashMap<>();
        uniqueIps = new HashSet<>();
    }

    public void addLogin(String account, String ip) {
        if (account == null || ip == null) {
            return;
        }

        // 寫入與查詢都使用相同的帳號格式
        String normalizedAccount = account.trim().toLowerCase();
        String normalizedIp = ip.trim();

        if (normalizedAccount.isEmpty() || normalizedIp.isEmpty()) {
            return;
        }

        loginCounts.put(
                normalizedAccount,
                loginCounts.getOrDefault(normalizedAccount, 0) + 1
        );

        // 相同 IP 重複 add 不會增加 Set 大小
        uniqueIps.add(normalizedIp);
    }

    // 查詢每個帳號次數
    public int getLoginCount(String account) {
        if (account == null) {
            return 0;
        }

        String normalizedAccount = account.trim().toLowerCase();

        if (normalizedAccount.isEmpty()) {
            return 0;
        }

        return loginCounts.getOrDefault(normalizedAccount, 0);
    }

    public int getUniqueIpCount() {
        return uniqueIps.size();
    }

    public static void main(String[] args) {
        LoginActivityReport report = new LoginActivityReport();

        report.addLogin("Alice", "192.168.1.1");
        report.addLogin("Bob", "192.168.1.2");
        report.addLogin("alice ", "192.168.1.1");
        report.addLogin("ALICE", "10.0.0.1");

        System.out.println("alice = " + report.getLoginCount("alice"));
        System.out.println("bob = " + report.getLoginCount("bob"));
        System.out.println("carol = " + report.getLoginCount("carol"));
        System.out.println("missing = " + report.getLoginCount("nobody"));
        System.out.println("unique IP = " + report.getUniqueIpCount());
    }
}
