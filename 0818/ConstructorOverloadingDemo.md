=== 程式名稱 ===
檔案：ConstructorOverloadingDemo.java
執行日期：2026-08-19
Java 版本：JDK 25

=== 編譯結果 ===
編譯指令：
javac -d /tmp/0818/ConstructorOverloadingDemo.java

編譯結果：成功

=== 測試案例 ：正常輸入 ===



結果:

CS101 Java 1/30
DS201 Tree Lab 2/2
third enrollment=false



實作變化:

新增一個只接受 code 的 constructor，

title 預設為 Independent Study，capacity 預設為 10，並且必須透過 this(...) 完成初始化。


=== 測試案例 ：實作變化 ===



預期結果：

CS101 Java 1/30
DS201 Tree Lab 2/2
third enrollment=false
CS199 Independent Study 0/10



實際輸出：

CS101 Java 1/30
DS201 Tree Lab 2/2
third enrollment=false
CS199 Independent Study 0/10



