=== 程式名稱 ===
檔案：DefensiveCopyDemo.java
執行日期：2026-08-19
Java 版本：JDK 25

=== 編譯結果 ===
編譯指令：
javac -d /tmp/0818/DefensiveCopyDemo.java

編譯結果：成功

=== 測試案例 ：正常輸入 ===

結果:

original=\[0, 90, 70]
received=\[80, 0, 70]
snapshot=Java \[80, 90, 70]
average=80.0



實作變化:
新增 highestScore()；其他情況回傳最高成績。

=== 測試案例 ：實作變化 ===

預期結果：

original=\[0, 90, 70]
received=\[80, 0, 70]
snapshot=Java \[80, 90, 70]
average=80.0
highest=90



實際輸出：

original=\[0, 90, 70]
received=\[80, 0, 70]
snapshot=Java \[80, 90, 70]
average=80.0
highest=90



=== 測試案例：空陣列 ===



輸入內容：

\[]



預期輸出：

highest=-1



實際輸出：

highest=-1



