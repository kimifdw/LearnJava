# List Interface
## Positional access
根据列表中的位置操作元素。包含get、set、add、addAll和remove.
## Search
查找特定的元素并返回其对应在列表中的位置。包含indexOf和lastIndexOf.
## Iteration
继承Iterator。ListIterator提供方法
## Range-view
方法sublist提供任意范围的操作

1. an ordered collection
2. set method overwrites the last element.
3. sublist。返回的列表对象只能临时使用。
4. 允许包含重复元素。
5. 算法。
    1. sort【排序算法】。无法对equalA元素进行排序
    2. shuffle。随机交换list中的元素。
    3. reverse。 颠倒list的排序。
    4. rotate。轮询所有元素通过特殊的账号。
    5. swap。list中互换特定位置的元素。
    6. replaceAll。
    7. fill。用特殊值重写list中的每一个元素。
    8. copy。
    9. binarySearch。二分查找
6. ArrayList
7. LinkedList
8. CopyOnWriteArrayList
9. Vector[同步的ArrayList]