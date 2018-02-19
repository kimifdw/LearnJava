# Java8 Stream总结
## Iterator的差别
1. 无存储：Stream是基于数据源的对象，它本身不存储数据元素，而是通过管道将数据源的元素传递给操作。
2. 函数式编程：对Stream的任何修改都不会修改背后的数据源，比如对Stream执行filter操作并不会删除被过滤的元素，而是会产生一个不包含被过滤元素的新的Stream。
3. 延迟执行：Stream的操作由零个或多个中间操作（intermediate operation）和一个结束操作（terminal operation）两部分组成。只有执行了结束操作，Stream定义的中间操作才会依次执行，这就是Stream的延迟特性。
4. 可消费性：Stream只能被“消费”一次，一旦遍历过就会失效。就像容器的迭代器那样，想要再次遍历必须重新生成一个新的Stream。
## 接口
1. Function：从T到R的一元映射函数，将参数T传递给一个函数，返回R
    1. compose：嵌套关系
    2. andThen：转换了嵌套的顺序
    3. identity：传递自身的函数
2. Predicate：谓词函数，作为一个谓词演算推导真假值存在，返回boolean的函数
3. Consumer：从T到void的一元函数，接受一个入参但不返回任何结果的操作
4. Supplier：结果的供应者
## Strean的创建
> 依赖底层的StreamSupport类来完成Stream的创建
1. 通过集合的stream()方法或者parallelStream()
2. 使用流的静态方法，比如Stream.of(Object[]), IntStream.range(int, int) 或者 Stream.iterate(Object, UnaryOperator)。
3. 通过Arrays.stream(Object[])方法。
4. BufferedReader.lines()从文件中获得行的流。
5. Files类的操作路径的方法，如list、find、walk等。
6. 随机数流Random.ints()。
7. 其它一些类提供了创建流的方法，如BitSet.stream(), Pattern.splitAsStream(java.lang.CharSequence), 和 JarFile.stream()。
## Stream的转换
1. flatMap：每个元素转换得到的是Stream对象
2. peek：生成一个包含原Stream的所有元素的新Stream，同时会提供一个消费函数
3. limit：获取其前N个元素
4. skip：返回一个丢弃原Stream的前N个元素后剩下元素组成的新Stream
## Stream的汇聚
1. 可变汇聚：把输入的元素累积到一个可变的容器里
    1. collect
2. 其他汇聚
    1. reduce
    2. count
    
## 并行流
> 本质是使用Fork/Join模型