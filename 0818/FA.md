
## 課後評量
Q:
1. `BankAccount a = new BankAccount(...); BankAccount b = a;` 中，建立了幾個物件？
2. 為什麼 `balance` 不適合設成 `static`？
3. `private field + public setter` 是否一定完成良好封裝？說明原因。
4. `Order` 包含 `Customer` 應使用 inheritance 還是 composition？
5. 對 `null` reference 呼叫 method 會發生什麼問題？
6. 說明物件陣列比平行陣列容易維護的原因。

A:
1.a 與 b 指向同一物件時只建立 1 個物件；== 比較參考，equals 比較定義的相等。
2.balance 不適合 static，因為各帳戶餘額應獨立。
3.private field + 無驗證 setter ≠ 封裝；應以 domain method 控制變更。
4.Order has Customer 用 composition，而非 inheritance!
5.對 null 呼叫 method 會造成空指標異常。
6.物件陣列比平行陣列易維護，因為相關資料封裝在同一物件，避免索引對齊錯位。
