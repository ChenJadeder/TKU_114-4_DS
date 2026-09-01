0831 Heap、PriorityQueue 與 Hash Table

本次練習主要實作 Heap、Java PriorityQueue、Comparator、Top-K 與 Hash Table separate chaining。

## 開發與測試環境

- Java：JDK 17 以上
- 本次線上測試：JDoodle
- JDoodle Java Version：26.0.2
- 原開發環境：Visual Studio Code + Extension Pack for Java

所有 `.java` 檔案皆以獨立程式方式進行測試。
# 形成性評量

## 陣列 index 7 的 parent、left、right 分別是多少？

使用：
```text
parent = (i - 1) / 2
left   = 2 * i + 1
right  = 2 * i + 2
```
代入 `i = 7`：
```text
parent = 3
left = 15
right = 16
```
所以答案為：

```text
parent = 3, left = 15, right = 16
```

##  為什麼 Heap 不等於完整排序陣列？

Heap 只要求 parent 和 child 之間符合 invariant。

例如 Min Heap：

```text
parent <= child
```

不要求同一層或不同 subtree 之間也按照大小排序，所以 Heap 的陣列內容不一定是完整遞增排列。

##  Bubble-down 有兩個 child 時，Min Heap 應選哪一個？

應該先找 left child 和 right child 中比較小的那一個，再和 parent 比較。

如果只固定選 left child，可能忽略更小的 right child，造成 Min Heap invariant 被破壞。

## 直接列印 PriorityQueue 能否證明 poll 順序？

不能。PriorityQueue 內部只需要維持 Heap 結構，不需要維持完整排序。

如果要確認實際取出順序，應使用： poll()
反覆取出元素。

如果不希望改變原本 Queue，可以先複製一份再 poll。

## Top-K 最大值為什麼使用大小 K 的 Min Heap？

Min Heap 的 root 是目前 Heap 中最小的元素。

當 Heap 超過 K 筆時，把 root 移除，就能淘汰目前候選資料中較小的值。

處理全部輸入後，Heap 最後留下的就是最大的 K 筆資料。

## Collision 是否代表 Hash Table 不能使用該 key？

Collision 只表示不同 key 經過 hash function 後得到相同 bucket。

使用 separate chaining 時，同一 bucket 可以保存多個 Entry，查詢時再比較真正的 key。

## put() 遇到相同 key 與不同 collision key 有什麼差異？

如果是相同 key：

```text
更新原本的 value
size 不增加
```

如果只是 bucket 相同，但 key 不同：

```text
新增另一個 Entry 到 chain
size 增加
```

因此 collision 不等於 duplicate key。

---

## 總結
這次實作讓我比較清楚 Heap 並不是排序陣列，而是維護 parent-child 關係的 Complete Binary Tree。

PriorityQueue 的重點除了 priority 本身，也要注意相同 priority 時的 tie-breaker。

Hash Table 部分則需要區分 collision 與相同 key。Separate chaining 發生 collision 時可以保留不同 key，而真正相同的 key 在 put 時才更新 value。

另外，Java 在處理負數 hash index 時，可以使用 `Math.floorMod()`，避免 `%` 得到負數後直接拿去當陣列 index。
