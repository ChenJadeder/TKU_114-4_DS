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

        // 寫入與查詢都統一帳號格式
        String normalizedAccount = account.trim().toLowerCase();
        String normalizedIp = ip.trim();

        if (normalizedAccount.isEmpty() || normalizedIp.isEmpty()) {
            return;
        }

        loginCounts.put(
                normalizedAccount,
                loginCounts.getOrDefault(normalizedAccount, 0) + 1
        );

        // Set 會自動忽略重複 IP
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
    public void printAnomalyReport() {

        System.out.println("不重複的 IP 總數: " + getUniqueIpCount());
        System.out.println("異常門檻值:  " + ANOMALY_THRESHOLD);
        System.out.println("---------------------------------");

        boolean foundAnomaly = false;

        // 尋找異常帳號
        for (Map.Entry<String, Integer> entry : loginCounts.entrySet()) {
            if (entry.getValue() >= ANOMALY_THRESHOLD) {
                System.out.println("異常: " + entry.getKey() + "登入次數: " + entry.getValue() + " 次");
                foundAnomaly = true;
            }
        }

        if (!foundAnomaly) {
            System.out.println("未發現任何異常登入帳號。");
        }
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

        report.printAnomalyReport();
    }
}
