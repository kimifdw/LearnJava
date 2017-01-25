# 并发
## 术语定义
1. 共享变量：在多个线程之间能够被共享的变量。他们被存放在堆内存中，Volatile只作用于共享变量
2. 内存屏障(Memory Barriers)：是一组处理器指令，用于实现对内存操作的顺序限制；
3. 缓冲行(Cache line)：缓存中可以分配的最小存储单位。
4. 原子操作（Atomic operation）: 不可中断的一个或一系列操作；
5. 缓存行填充（cache line fill）：当处理器识别到从内存中读取操作数是可缓存的，处理器读取整个缓存行到适当的缓存；
6. 缓存命中（cache hit）：如果进行高速缓存行填充操作的内存位置仍然是下次处理器访问的地址时，处理器从缓存中读取操作数；
7. 写命中(write hit): 当处理器将操作数写回到一个内存缓存的区域时，它首先会检查这个缓存的内存地址是否在缓存行中，如果存在一个有效的缓存行，则将处理器这个操作数写回到内存；
8. 写缺失（write misses the cache)：一个有效的缓存行被写入到不存在的内存区域；
9. CAS(Compare and Swap): 比较并设置。
## volatile
1. 定义：java编程语言允许线程访问共享变量，为了确保共享变量能被准确和一致的更新，线程应该确保通过排他锁单独获得这个变量。
2. 用于修饰在多线程环境下某一变量值的一致性；
3. 在LinkedTransferQueue中，使用了volatile，追加64字节提高并发编程性能；
## synchronized
- 锁一旦升级为重量级锁，就不会再降级；JVM会尽量维持在低能耗锁
1. 同步的基础
    1. 对于同步方法，锁是当前**实例对象**
    2. 对于静态同步方法，锁是当前对象的**class对象**
    3. 对于同步方法块，锁是Synchonized括号里配置的对象
2. 锁的四种状态
    1. 无状态锁；
    2. 偏向锁状态；
    3. 轻量级锁状态；
    4. 重量级锁状态；
3. 偏向锁
    1. 要解决的问题：大多数情况下锁不仅不存在多线程竞争，而且总是由同一线程多次获得。
    2. 锁的获得：测试对象头的Mark Word里是否存在存储着指向当前线程的偏向锁，如不存在，则再测试下Mark Word中偏向锁的标识是否为1，没有设置则使用CAS竞争锁；
    3. 锁的撤销：需要等待全局安全点，它会首先暂停拥有偏向锁的线程，然后检查持有偏向锁的线程是否活着，必须等到同步代码块执行完；
    4. 关闭偏向锁：在java6和java7中默认启用，通过jvm参数关闭偏向锁：**UseBiasedLocking=false**，那么默认会进入**轻量级锁**状态；
    5. 适用于只有一个线程访问同步块；
    6. 偏向锁的耗能比轻量级锁少
4. 轻量级锁
    1. 加锁：线程在执行同步块前，JVM会先在当前线程的栈桢中创建用于存储锁记录的空间，并将对象头中的Mark Word复制到锁记录中。
    2. 解锁：使用原子的CAS操作来讲Displaced Mark Word替换回到对象头；
    3. 如果始终得不到锁竞争的线程使用自旋会消耗CPU;
    3. 适用场景：追求响应时间；同步块执行速度非常快；
5. 重量级锁
    1. 线程竞争不使用自旋，不会消耗CPU；
    2. 适用场景：追求吞吐量；同步块执行时间较长
## JAVA线程池的分析和使用
1. 好处：
    1. 降低资源消耗。
    2. 提高响应速度。
    3. 提高线程的可管理性。
2. 使用
    `new ThreadPoolExecutor(corePoolSize,maximumPoolSize,keepAliveTime,milliseconds,runnableTaskQueue,handler)`
    1. corePoolSize(线程池的基本大小)：当提交一个任务到线程池时，线程池会创建一个线程来执行任务，即使其他空闲的基本线程能够执行新任务也会创建线程，等到需要执行的任务数大于线程池基本大小时不再创建。如果调用了线程池的**prestartAllCoreThreads**方法，线程池会提前创建并启动所有基本线程。
    2. runnableTaskQueue(任务队列)：用于保存等待执行的任务的阻塞队列，队列里处处的是以前提交的任务，需要等待线程空闲时执行。
        * ArrayBlockingQueue：基于数组结构的有界阻塞队列，FIFO（先进先出）
        * LinkedBlockingQueue：基于链表结构的阻塞队列，FIFI（先进先出），Executors.newFixedThreadPool()【设置corePoolSize==maximumPoolSize】使用这个队列
        * SynchronousQueue: 不存储元素的阻塞队列。每个插入操作必须等到另一个线程调用移除操作，Executors.newCachedThreadPool()使用这个队列
        * PriorityBlockingQueue: 一个具有优先级的无限阻塞队列。
    3. maximumPoolSize(线程池的最大值)   
    4. ThreadFactory：设置创建线程的工厂
    5. RejectedExecutionHandler(饱和策略)：当线程池处于饱和状态时采取的处理新提交的新任务的策略。默认AbortPolicy
        * AbortPolicy: 直接抛出异常；
        * CallerRunsPolicy: 只能调用者所在线程来运行任务；
        * DiscardOldestPolicy: 丢弃队列里最近的一个任务，并执行当前任务；
        * DiscardPolicy: 不处理，丢弃掉
    6. keepAliveTime（线程活动保持时间）：工作线程空闲后，保持存活的时间。
    7. TimeUnit(线程活动保持的单位)
3. 提交任务
    1. execute(): 没有返回值
    2. submit(): 返回Future返回值。*InterruptedException*(处理中断异常)；*ExecutionException*(处理无法执行任务异常)
4. 线程池关闭
    1. 原理：遍历线程池中的工作线程，逐个调用线程的interrupt方法来中断线程
    2. isShutdown(): 调用shutdown和shutdownnow都会变为true；
    3. isTerminaed()：所有任务已关闭后，才表示线程池关闭成功。
5. 线程池流程
    1. 判断基本线程池是否已满；
    2. 判断工作队列是否已满；
    3. 判断整个线程池是否已满；
6. 合理的配置线程池
    1. 分析任务特性
        1. 任务性质：CPU密集型任务，IO密集型任务和混合型任务。
            1. CPU密集型：尽可能小的线程（Ncpu+1），例如压缩和解压缩
            2. IO密集型：尽可能多的线程(2*Ncpu)
            3. `Runtime.getRuntime().availableProcessors()`：当前设备的CPU个数
        2. 任务优先级：高、中、低。使用*PriorityBlockingQueue*队列
        3. 任务的执行时间：长、中、短。使用不同规模的线程池来处理
        4. 任务的依赖性：是否依赖其他系统资源。设置线程数尽可能大。
    2. 建议使用*有界对列*
    3. 动态递增的增加线程池数目
7. 线程池的监控
    1. taskCount: 线程池需要执行的任务数量
    2. completedTaskCount: 线程池在运行过程中已完成的任务数量。
    3. largestTaskCount：曾经创建过的最大线程数量
    4. getPoolSize: 线程池的线程数量
    5. getActiveCount: 获取活动的线程数
    6. 重写线程池的beforeExecute、afterExecute和terminated方法。
8. 应急处理：如果在创建线程池的时候，指定的*corePoolSize<maximumPoolsize*是会出现你说的这种情况
 

