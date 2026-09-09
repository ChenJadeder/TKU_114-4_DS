import java.util.*;
//課後作業六：服務中心排隊與取消
//指定檔名：ServiceCenterWorkflow.java

// 服務票券類別
class ServiceTicket {
    private String id;
    private String customerName;
    private String serviceType;

    public ServiceTicket(String id, String customerName, String serviceType) {
        this.id = id;
        this.customerName = customerName;
        this.serviceType = serviceType;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getServiceType() { return serviceType; }

    @Override
    public String toString() {
        return String.format("Ticket[ID: %s, 顧客: %s, 項目: %s]", id, customerName, serviceType);
    }
}

public class ServiceCenterWorkflow {
    // 依 ticket id 查詢
    private Map<String, ServiceTicket> ticketMap = new LinkedHashMap<>();
    private Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private Deque<ServiceTicket> completionStack = new ArrayDeque<>();
    // 防止重複 id
    private Set<String> existingIds = new HashSet<>();

    // 1. 建立票券
    public boolean createTicket(String id, String customerName, String serviceType) {
        if (existingIds.contains(id)) {
            System.out.println(" 建立失敗：ID [" + id + "] 已存在，不可重複。");
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, customerName, serviceType);
        ticketMap.put(id, ticket);
        waitingQueue.addLast(ticket); // 排在隊伍末端
        existingIds.add(id);
        System.out.println(" 成功建立票券：" + ticket);
        return true;
    }

    // 處理下一位顧客
    public ServiceTicket processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println(" 處理失敗：目前沒有人在等待隊列中。");
            return null;
        }
        ServiceTicket nextTicket = waitingQueue.removeFirst(); // 從前端取出
        completionStack.push(nextTicket); 
        System.out.println(" 正在處理並完成票券：" + nextTicket);
        return nextTicket;
    }

    //  取消等待中的票券 (只能作用於尚未處理的 ticket)
    public boolean cancelWaiting(String id) {
        if (!existingIds.contains(id)) {
            System.out.println(" 取消失敗：找不到 ID [" + id + "] 的票券。");
            return false;
        }
        
        ServiceTicket ticket = ticketMap.get(id);
        // 檢查是否仍在等待佇列中
        if (waitingQueue.contains(ticket)) {
            waitingQueue.remove(ticket);
            ticketMap.remove(id);
            existingIds.remove(id);
            System.out.println(" 成功取消等待中的票券：" + ticket);
            return true;
        } else {
            System.out.println("取消失敗：ID [" + id + "] 的票券已被處理或不在等待佇列中。");
            return false;
        }
    }

    //  復原最後一次的完成狀態
    public ServiceTicket undoLastCompletion() {
        if (completionStack.isEmpty()) {
            System.out.println(" 復原失敗：目前沒有已完成的歷程可供復原。");
            return null;
        }
        ServiceTicket lastCompleted = completionStack.pop(); // 從 Stack 彈出
        waitingQueue.addFirst(lastCompleted); // 放回 waiting queue 的最前端
        System.out.println(" 成功復原：已將票券放回 -> " + lastCompleted);
        return lastCompleted;
    }

    //  依 ID 查詢
    public ServiceTicket findById(String id) {
        return ticketMap.get(id);
    }

    //  印出目前狀態摘要
    public void printSummary() {
        System.out.println("\n----- 服務中心目前狀態 -----");
        System.out.println(" 等待隊列 (Front -> End): " + waitingQueue);
        System.out.println(" 完成歷程 (Top -> Bottom): " + completionStack);
        System.out.println("---------------------------\n");
    }

    // 測試所有指定情境
    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("=== 建立票券 ===");
        center.createTicket("T01", "Alice", "開戶");
        center.createTicket("T02", "Bob", "貸款諮詢");
        center.createTicket("T03", "Charlie", "換匯");
        center.printSummary();

        System.out.println("===  測試：重複 ID ===");
        center.createTicket("T01", "David", "掛失"); // 應失敗

        System.out.println("=== 測試：取消不存在的 ID & 取消等待中的票券 ===");
        center.cancelWaiting("T99"); // 應失敗（不存在）
        center.cancelWaiting("T02"); // 應成功（取消 Bob）
        center.printSummary();

        System.out.println("=== 測試：處理下一位顧客 ===");
        center.processNext(); // 應處理 Alice (T01)
        center.processNext(); // 應處理 Charlie (T03)
        center.printSummary();

        System.out.println("=== 測試：嘗試取消已處理的票券 ===");
        center.cancelWaiting("T01"); // 應失敗（已在完成歷程中）

        System.out.println("=== 測試：空 Queue 處理 ===");
        center.processNext(); // 應提示目前無人等待

        System.out.println("=== 測試：連續兩次 Undo ===");
        center.undoLastCompletion(); // 第一次復原：Charlie (T03) 回到隊首
        center.undoLastCompletion(); // 第二次復原：Alice (T01) 回到隊首（在 Charlie 前面）
        center.printSummary();

        System.out.println("===  測試：復原後的重新處理 ===");
        center.processNext(); // 應重新處理 Alice (T01)
        center.printSummary();
    }
}
