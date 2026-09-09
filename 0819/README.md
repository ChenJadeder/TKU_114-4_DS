# 0819 繼承、介面與多型


## 一、資料夾說明

本資料夾依照作業性質分為：

- 根目錄：概念實作變化
- `lab/`：課堂實作題
- `hw/`：課後作業題


## 二、執行環境


- 目標 Java 版本：JDK 17+
- 主要開發工具：Visual Studio Code
- VS Code 擴充套件：Extension Pack for Java
- 輔助測試環境：JDoodle JDK 25

> 本版本另以 OpenJDK 21 於獨立環境逐檔重新編譯驗證，結果如下表。

## 三、檔案對照（22 個規定範例，逐一實測編譯結果）

### 概念實作變化（12）

| 檔案 | 說明 |
|---|---|---|
| `InheritanceBasics.java` | Inheritance 基礎示範 |
| `OverridePayDemo.java` | Method overriding 與 `@Override` | 
| `PolymorphismArrayDemo.java` | Polymorphism 與 dynamic dispatch | 
| `AbstractNotificationDemo.java` | Abstract class 共同骨架示範 | 
| `PaymentInterfaceDemo.java` | Interface 定義可替換行為契約 | 
| `MultipleInterfaceDemo.java` | 一個 class 實作多個 interface | 
| `StrategyCompositionDemo.java` | Abstract/interface/composition 選擇 |
| `ConstructorChainDemo.java` | `super` 與 constructor chain |
| `SafeCastingDemo.java` | Upcasting、downcasting 與 `instanceof` | 
| `DefaultMethodDemo.java` | Interface default method | 
| `PolymorphicFactoryDemo.java` | Polymorphic parameter 與 return type | 
| `CheckoutNotificationSystem.java` | 多管道通知與費用計算綜合應用 | 

### 課堂實作題（5）

| 檔案 | 說明 | 編譯/執行 |
|---|---|---|
| `lab/TransportFareSystem.java` | 交通票價多型系統 | ✅ 成功 |
| `lab/MessageSenderSystem.java` | 訊息發送 Interface | ✅ 成功 |
| `lab/DocumentCapabilityDemo.java` | 匯出與壓縮能力（多重 interface） | ✅ 成功 |
| `lab/EmployeeConstructorChain.java` | 建構鏈與員工類型 | ✅ 成功 |
| `lab/DeviceInspectionSystem.java` | 安全型態判斷（pattern matching instanceof） | ✅ 成功 |

### 課後作業（5）

| 檔案 | 說明 | 編譯/執行 |
|---|---|---|
| `hw/PayrollPolymorphismSystem.java` | 員工薪資與獎金系統 | ✅ 成功 |
| `hw/DeliveryStrategySystem.java` | 多方式配送系統 | ✅ 成功 |
| `hw/MediaProcessingSystem.java` | 媒體檔案處理 | ✅ 成功 |
| `hw/ReportExporterFactory.java` | 報表輸出 Factory | ✅ 成功 |
| `hw/FlexibleCheckoutSystem.java` | 通知與費用系統擴充 | ✅ 成功 |






## 課後評量（形成性評量）


Q:
```
1. Car extends Engine 為什麼通常不是合理 inheritance？
2. Overloading 與 overriding 的差異是什麼？
3. Shape shape = new CircleShape(3); 的 reference type 與 object type 各是什麼？
4. Abstract class 與 interface 各適合什麼情況？
5. 為什麼 interface method 的實作通常必須宣告 public？
6. 說明 List<String> names = new ArrayList<>(); 如何使用 polymorphism。
```

A:
```
1. Car 與 Engine 是 has-a（Car 裡面有一個 Engine），不是 is-a（Car 不是一種 Engine）；用 extends 會讓 Car 繼承 Engine 不相關的方法，語意不合理，應改用 composition（Car 內部持有 Engine 物件）。
2. Overloading 是同一個 class 中方法名稱相同但參數列表不同，在 compile time 依參數決定要呼叫哪個版本；overriding 是 subclass 重新實作 superclass 的同名同參數方法，在 runtime 依實際物件型態決定執行哪個版本（dynamic dispatch）。
3. reference type 是 Shape，object type 是 CircleShape。
4. Abstract class 適合關係緊密、需要共享 state 與部分實作的 is-a 家族；interface 適合描述可替換的能力（can-do），不要求共享 state，且允許一個 class 同時具備多種能力。
5. Interface 裡宣告的 method 預設就是 public abstract，Java 規定 override 時存取權限不能比原本更嚴格，所以實作端必須宣告 public，否則編譯錯誤。
6. names 的 reference type 是 List，實際物件是 ArrayList，這是 upcasting；呼叫端只透過 List 定義的方法操作，不依賴 ArrayList 的具體實作，未來可以換成 LinkedList 而不用改呼叫端程式碼，這就是 interface-based polymorphism。
```

## AI 使用說明

本作業過程中使用 AI 協助理解 OOP 概念、除錯思路與排版命名建議；所有程式皆由本人撰寫、調整並可說明設計理由與不變條件。
未直接提交 AI 原樣輸出，並已自行編譯、執行、測試。
