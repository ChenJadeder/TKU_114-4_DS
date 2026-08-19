此資料夾對應0818_Java物件導向複習與封裝.md中的除錯練習題目
## 除錯練習

### 除錯練習一：Constructor 沒有設定 field

```java
class BrokenProduct {
    private String name;

    BrokenProduct(String name) {
        name = name;
    }
}
```

問題：`name = name` 的左右兩邊都是 parameter，field 仍然是 `null`。

修正：

```java
this.name = name;
```

### 除錯練習二：Getter 暴露內部陣列

```java
int[] getScores() {
    return scores;
}
```

問題：Caller 取得內部陣列的 reference，可以繞過 class method 直接改動內容。

修正：

```java
return Arrays.copyOf(scores, scores.length);
```
