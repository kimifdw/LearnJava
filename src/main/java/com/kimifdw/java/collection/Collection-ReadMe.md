# 集合框架知识点总结
1. 容器只能放对象，通过泛型机制实现。但其实质是由于java的单继承机制，因为所有的对象都是Object的子类。
2. 接口，目前存在14种容器接口，实际开发中常用的有ArrayList，LinkedList，HashMap,HashSet,LinkedHashMap.
## ArrayList
1. 顺序容器，即元素存放的数据与放进去的顺序相同，允许放入null元素，底层通过**数组**实现。
2. 多线程环境，需要使用Vector。
3. 时间复杂度(平均为O(n/2),最坏为O(n))
    1. size(),isEmpty(),get(),set()为O(1);
    2. add(E element)跟加入的位置有关，最好是O(1),最坏是O(n);[因为需要对剩余空间进行检查]
    3. add(int index,E element)为O(n/2)
    4. addAll()跟添加的元素个数成正比；
## LinkedList
1. 同时实现了List和Deque两个接口，即既可以作为一个顺序容器，也可以作为一个队列，同时也可以看做一个栈;
2. LinkedList底层通过双向链表实现。
3. 在多线程环境的并发访问，可以考虑用Collections.synchronizedList();
4. 允许在iterators时，进行insert和remove操作
5. 时间复杂度(平均为O(n/4),最坏O(n/2))
    1. add(E element)为O(1)
    2. add(int index,E element)最好为O(1),最差为O(n/4)
    3. remove(int index)为O(n/4)
## ArrayList和LinkedList总结
1. ArrayList在遍历元素方面有优势*但和LinkedList差距不大*，而LinkedList则在添加、删除元素时有优势;
2. LinkedList的主要优势在于允许在iterator时插入和删除元素；
3. ArrayList默认的capacity为10(java from 1.4-1.8),在初始化ArrayList时，能事先预估好最大的capacity；


    

