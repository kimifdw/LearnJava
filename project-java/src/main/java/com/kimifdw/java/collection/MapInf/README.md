# Map Interface
1. equal：the same key-value mappings;
2. 键必须是唯一的，值可以重复；
3. 散列是映射中存儲元素時最常用的方式；
## HashMap
1. 基于散列表的实现，取代HashTable。
2. 利用散列碼來取代對鍵的搜索；
3. 插入和查詢“鍵值對”的開銷是固定的，可以通過構造器容量和負載因子，以調整容器的性能；
## LinkedHashMap
1. 取得键值对的顺序是其插入次序或最近最少使用的次序；
## TreeMap(SortedMap)
1. 基于**红黑树**的实现。
2. 返回的結果是經過排序的，唯一帶有submap（）方法返回一個子樹；
## WeakHashMap
1. 弱鍵映射，允許釋放映射所指向的對象；
## ConcurrentHashMap
1. 綫程安全的Map，不涉及同步加鎖；
## IdentityHashMap
1. 使用==代替equal()對鍵進行比較的散列映射；
