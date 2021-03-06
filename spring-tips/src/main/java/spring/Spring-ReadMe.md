# spring知识点纪录
- 在spring中，所有的bean都是由BeanFactory来管理的。
- FactoryBean，一个产生或修饰对象生成的工厂bean，用到的设计模式：工厂模式／修饰器模式
> ioc的核心类
1. BeanFactory
2. ApplicationContext
> bean的生命周期
1. Spring instantiates the bean. 
- 实例化bean

2. Spring injects values and bean references into the bean’s properties. 
- 设置bean的对象属性

3. If the bean implements **BeanNameAware**, Spring passes the bean’s ID to the **setBeanName()** method.
- 如果以**BeanNameAware**实现，回调**setBeanName()**方法，传入ID
4. If the bean implements **BeanFactoryAware**, Spring calls the **setBeanFactory()** method, passing in the bean factory itself.
5. If the bean implements **ApplicationContextAware**, Spring calls the **setApplicationContext()** method, passing in a reference to the enclosing application context.
6. If the bean implements the **BeanPostProcessor** interface, Spring calls its **postProcessBeforeInitialization()** method.
7. If the bean implements the **InitializingBean** interface, Spring calls its **afterPropertiesSet()** method. Similarly, if the bean was declared with an init-method, then the specified initialization method is called. 
8. If the bean implements **BeanPostProcessor**, Spring calls its **postProcess-AfterInitialization()** method. 
9. At this point, the bean is ready to be used by the application and remains in the application context until the application context is destroyed. 
10. If the bean implements the **DisposableBean** interface, Spring calls its **destroy()** method. Likewise, if the bean was declared with a destroy-method, the specified method is called.

具体实例参见**Person**类，基于xml配置

> bean作用域
1. Singleton：单例模式［默认］，整个spring容器，bean实例只有一个
2. Prototype：原型模式，通过getBean方法获取Bean时，都将重新产生一个新的bean实例
3. Session：每次的http session都将产生一个新的实例
4. Request：每次http请求，都将产生一个新的实例

> springFramework核心
1. Core Spring container[DI]
    1. Beans
    2. Core
    3. Context
    4. Expression
    5. Context support
2. Aspect-oriented programming
    1. AOP
    2. Aspects
3. Data access & integration
    1. JDBC
    2. Transaction
    3. ORM
4. Web and remoting
    1. Web
    2. Web servlet
    3. Reactive Web
> 各版本spring的新增特性
1. spring3.x：
    1. 增强了spring mvc，增加了对rest风格的web服务和web应用的支持
    2. 新增expression表达式
    3. 支持servlet3的异步请求
2. spring4.x:
    1. 支持webSocket编程
    2. 支持java8的新特性
    3. 支持基于groovy的编程开发
    4. 支持异步Rest-Template的异步回调
3. spring5.x:
    1. 支持webflux方式
    
