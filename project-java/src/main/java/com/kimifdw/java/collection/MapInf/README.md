# Map Interface
1. equal：the same key-value mappings;
2. 键必须是唯一的，值可以重复；
3. 散列是映射中存储元素时最常用的方式；
3. 使用散列的目的在于想要使用一个对象查找另一个对象
## HashMap
1. 基于散列表的实现，取代HashTable。
2. 利用散列码來取代对键的搜索；
3. 插入和查询“键值对”的开销是固定的，可以通过构造器容量和负载因子，以調整容器的性能；
4. 使用equals()判断当前的键是否与表中存在的相同；
5. equals()必须满足的5个条件
    1. 自反性
    2. 对称性
    3. 传递性
    4. 一致性
    5. 对任何不是null的x，x.equals(null)一定返回false
6. bucket: 表示实际散列表的数组
7. 容量：表中的bucket位数
8. 初始容量：表在创建时所拥有的bucket位数；
9. 尺寸：表中当前存储的项数
10. 负载因子：尺寸/容量
## LinkedHashMap
1. 取得键值对的顺序是其插入次序或最近最少使用的次序；
2. 散列化所有的元素
## TreeMap(SortedMap)
1. 基于**红黑树**的实现。
2. 返回的結果是经过排序的，唯一帶有submap（）方法返回一個子树；
## WeakHashMap
1. 弱键映射，允许释放映射所指向的对象；
## ConcurrentHashMap
1. 线程安全的Map，不涉及同步加锁；
## IdentityHashMap
1. 使用==代替equal()对键进行比较的散列映射；
