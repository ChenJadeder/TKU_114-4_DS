=== 程式名稱 ===
檔案：WalletTransactionSystem.java
執行日期：2026-08-19
Java 版本：JDK 25

=== 編譯結果 ===
編譯指令：
javac -d /tmp/0818/WalletTransactionSystem.java

編譯結果：成功

=== 測試案例 ：正常輸入 ===

結果:

deposit=true
pay 250=true
pay 900=false
refund=true
W001 owner=Amy balance=800
1 DEPOSIT 1000 balance=1000
2 PAY 250 balance=750
3 REFUND 50 balance=800


實作變化:
新增 transferTo(DigitalWallet target, int amount)。
轉帳成功時，來源錢包記錄 TRANSFER_OUT，目標錢包記錄 TRANSFER_IN；
任一錢包無空間、金額不合法或餘額不足時，兩邊都不得改變。

=== 測試案例 ：實作變化 ===

實際輸出：

deposit=true
pay 250=true
transfer 200=true
pay 900=false
refund=true
W001 owner=Amy balance=600
1 DEPOSIT 1000 balance=1000
2 PAY 250 balance=750
3 TRANSFER_OUT 200 balance=550
4 REFUND 50 balance=600
W002 owner=Ben balance=200
1 TRANSFER_IN 200 balance=200


