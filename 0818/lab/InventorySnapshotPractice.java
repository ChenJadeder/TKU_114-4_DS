import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = (warehouseId == null || warehouseId.trim().isBlank())
                ? "UNKNOWN"
                : warehouseId.trim();
        this.quantities = (quantities == null)
                ? new int[0]
                : Arrays.copyOf(quantities, quantities.length);
    }

    String getWarehouseId() {
        return warehouseId;
    }

    int[] getQuantities() {
        return Arrays.copyOf(quantities, quantities.length);
    }

    int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }

    int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return warehouseId + " " + Arrays.toString(quantities);
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        // 指定測試：{5, 0, 3, 0} => total=8, outOfStock=2
        int[] data = {5, 0, 3, 0};
        InventorySnapshot snap = new InventorySnapshot("W-T1", data);

        // 驗證 constructor defensive copy：改動來源不影響 snapshot
        data[0] = -999;

        System.out.println("snapshot=" + snap);
        System.out.println("total=" + snap.totalQuantity());       // 應為 8
        System.out.println("outOfStock=" + snap.outOfStockCount()); // 應為 2

        // 驗證 getter defensive copy：改動外部取得的陣列不影響 snapshot
        int[] got = snap.getQuantities();
        if (got.length > 1) {
            got[1] = 999;
        }
        System.out.println("after getter mutation, snapshot=" + snap);

        // 邊界：null 陣列 -> 長度 0
        InventorySnapshot empty = new InventorySnapshot("W-EMPTY", null);
        System.out.println("empty total=" + empty.totalQuantity());         // 0
        System.out.println("empty outOfStock=" + empty.outOfStockCount());  // 0
    }
}