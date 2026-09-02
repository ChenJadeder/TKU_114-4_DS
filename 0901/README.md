## 0901 HashMap_HashSet與Graph表示法
### 形成式評量
內容:

1. 相同 key 更新時，size 應如何改變？

相同 key 再次 `put()` 時只是更新原本的 value，沒有新增新的 entry，所以 `size` 不應增加。

例如：

```text
put("A", 10) → size = 1
put("A", 20) → size = 1
```

第二次只是更新為：

```text
A=10→ 20
```
就要和 collision 要分開。不同 key 就算進到相同 bucket，仍然是兩筆 entry，size 應增加。

2. Rehash 為何不能複製原 bucket index？

因為 bucket index 通常和 bucket count 有關：

做完resize 後 bucket count 改變，同一個 hash 得到的 index 也可能不同。

例如：

```text
hash = 8

8 mod 5  = 3
8 mod 11 = 8
```

所以 rehash 時不能直接把「舊 bucket 3」複製到「新 bucket 3」，而要對每個 key 重新計算新的 index。

這也是實作題一的部分。

3. `equals()` 為 true 時，`hashCode()` 必須符合什麼規則？

如果：

```java
a.equals(b) == true
//那麼也會
a.hashCode() == b.hashCode()
```

反方向則不一定成立。兩個不同entry有可能得到相同 hash code。

在課後作業三中：

```java
equals()
```

比較：

```text
studentId + courseId
```

所以 `hashCode()` 也使用同樣兩個欄位：

```java
Objects.hash(studentId, courseId)
```

4. HashMap、LinkedHashMap、TreeMap 的主要差異是什麼？

三者都是 Map，但主要差別在「順序」與實作結構。

```text
HashMap
→ 不保證 iteration order
→ 一般 key-value 查詢常用
→ 平均 get/put 為 O(1)

LinkedHashMap
→ 保留 insertion order
→ 需要按照加入順序造訪時使用
→ Hash Table 再加上 linked structure

TreeMap
→ 按 key 排序
→ 一般操作 O(log n)
→ 適合需要 sorted keys 的情況
```

像前面的選課重複檢查，如果要求：

```text
CS101
DS201
MA101
```

就不能假設 HashMap 自己會給這個順序；可以另外排序 key，或依需求使用 TreeMap。

5. Dense Graph 與 sparse Graph 分別適合哪種表示法？

Dense Graph 的 edge 很多，接近：

```text
O(V²)
```

通常適合 adjacency matrix。

Matrix 使用：

```text
O(V²)
```

空間，但：

```text
matrix[u][v]
```

可以 O(1) 判斷兩點是否相鄰。

Sparse Graph 的 edge 相對少，通常適合 adjacency list：

```text
O(V + E)
```

空間比較節省，而且列出某 vertex 的 neighbors 也很方便。


6. Undirected 與 directed edge 的新增操作有何不同？

Undirected Graph 的：

```text
A -- B
```

表示雙向關係，所以加入 edge 時要同時記：

```text
A → B
B → A
```

如果是 matrix：

```java
matrix[a][b] = true;
matrix[b][a] = true;
```

Directed Graph：

```text
A → B
```

只有 A 指向 B，因此只新增：

```text
A 的 outgoing → B
```

不能自動加入：

```text
B → A
```

這個差異也會影響 degree。Directed Graph 要區分：

```text
out-degree
in-degree
```

7. Weighted neighbor 為何不能只存 vertex 名稱？

因為 edge 除了「連到哪個 vertex」，還有：

```text
weight
```

例如：

```text
Taipei → Taichung, cost=300
Taipei → Kaohsiung, cost=600
```

如果 adjacency list 只存：

```text
[Taichung, Kaohsiung]
```

成本資料就消失了。

所以 weighted graph 的 neighbor 通常需要類似：

```text
Edge
├─ to
└─ weight
```

例如 Java：

```java
class Edge {
    String to;
    int weight;
}
```

因此 adjacency list 概念上會從：

```java
Map<String, Set<String>>
```

進一步變成類似：

```java
Map<String, List<Edge>>
```

或其他能同時保存 neighbor 和 weight 的結構。
