# 锁
1.线程饥饿。
    1. 高优先级线程吞噬所有的低优先级线程的CPU时间。
    2. 线程被永久堵塞在一个等待进入同步块的状态。
    3. 线程在等待一个本身也处于永久等待完成的对象。
2. 可重入性。
3. ReentrantLock。一个可重入的互斥锁，为lock接口的主要实现。