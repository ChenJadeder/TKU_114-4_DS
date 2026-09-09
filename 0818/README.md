# 0818 Java 物件導向複習與封裝

## 一、資料夾說明

本資料夾依照作業性質分為：

- 根目錄：概念實作變化
- `lab/`：課堂實作題
- `hw/`：課後作業題
- `DEBUG/`：除錯練習
- `output/`：程式實際執行結果

## 二、執行環境

- 目標 Java 版本：JDK 17+
- 主要開發工具：Visual Studio Code
- VS Code 擴充套件：Extension Pack for Java
- 輔助測試環境：JDoodle JDK 25

> 程式以 JDK 17+ 語法為目標，並於 JDK 25 環境進行輔助測試。
> 實際使用的 JDK 版本與測試結果，應以 `output/` 中的紀錄為準。

## 三、檔案對照

### 概念實作變化

| 檔案 | 說明 | 執行結果 |
|---|---|---|
| `BankAccountReview.java` | 銀行帳戶封裝練習 | `output/BankAccountReview.md` |
| `ConstructorOverloadingDemo.java` | 建構子多載示範 | `output/ConstructorOverloadingDemo.md` |
| `DefensiveCopyDemo.java` | 防禦性複製示範 | `output/DefensiveCopyDemo.md` |
| `WalletTransactionSystem.java` | 電子錢包交易系統 | `output/WalletTransactionSystem.md` |

### DEBUG 除錯練習

| 檔案 | 說明 | 執行結果 |
|---|---|---|
| `DEBUG/BrokenProductFix.java` | 商品類別除錯練習 | `output/DEBUG/BrokenProductFix.txt` |
| `DEBUG/DefensiveGetterFix.java` | 防禦性 Getter 修正 | `output/DEBUG/DefensiveGetterFix.txt` |
| `DEBUG/note.md` | 除錯題目與修正說明 | 不適用 |

### 課堂實作題

| 檔案 | 說明 | 
|---|---|
| `lab/EquipmentInventory.java` | 設備庫存物件 |
| `lab/CourseComposition.java` | 課程與授課者 Composition | 
| `lab/BookArrayReport.java` | 物件陣列統計 | 
| `lab/MemberEqualityPractice.java` | 會員身分比較 |
| `lab/InventorySnapshotPractice.java` | Immutable 庫存快照 | 

### 課後作業題

| 檔案 | 說明 | 
|---|---|
| `hw/DigitalWalletSystem.java` | 封裝式電子錢包 | 
| `hw/CustomerOrderSystem.java` | 訂單與顧客管理 | 
| `hw/CourseGradeManager.java` | 課程成績物件系統 | 
| `hw/AccountTransferService.java` | 跨帳戶轉帳服務 | 
| `hw/WalletHistoryManager.java` | 電子錢包交易系統擴充 |



  
## 課後評量
Q:
```
BankAccount a = new BankAccount(...); BankAccount b = a; 中，建立了幾個物件？
為什麼 balance 不適合設成 static？
private field + public setter 是否一定完成良好封裝？說明原因。
Order 包含 Customer 應使用 inheritance 還是 composition？
對 null reference 呼叫 method 會發生什麼問題？
說明物件陣列比平行陣列容易維護的原因。
```
A:
```
1.a 與 b 指向同一物件時只建立 1 個物件；== 比較參考，equals 比較定義的相等。
2.balance 不適合 static，因為各帳戶餘額應獨立。
3.private field + 無驗證 setter ≠ 封裝；應以 domain method 控制變更。
4.Order has Customer 用 composition，而非 inheritance!
5.對 null 呼叫 method 會造成空指標異常。
6.物件陣列比平行陣列易維護，因為相關資料封裝在同一物件，避免索引對齊錯位。
```
AI 使用說明
本作業過程中使用 AI 協助理解 OOP 概念、除錯思路與排版命名建議；所有程式皆由本人撰寫、調整並可說明設計理由與不變條件。
未直接提交 AI 原樣輸出，並已自行編譯、執行、測試。
