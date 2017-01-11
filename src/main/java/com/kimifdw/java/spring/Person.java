package com.kimifdw.java.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.ClassPathResource;

/**
 * ApplicationContext Bean生命周期
 * Created by kimiyu on 17/1/8.
 */
public class Person implements BeanNameAware, BeanFactoryAware, ApplicationContextAware, InitializingBean, DisposableBean {

    private String name;

    public Person() {
        System.out.println("1.Person类构造方法");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        System.out.println("2.set方法被调用");
    }

    // 自定义初始化方法
    public void myInit() {
        System.out.println("8.myInit被调用");
    }

    //自定义的销毁方法
    public void myDestroy() {
        System.out.println("11.myDestroy被调用");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        System.out.println("4.setBeanFactory被调用,beanFactory");
    }

    @Override
    public void setBeanName(String beanName) {
        System.out.println("3.setBeanName被调用,beanName:" + beanName);
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("10.destroy被调用");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("7.afterPropertiesSet被调用");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("5.setApplicationContext被调用");
    }

    public String toString() {
        return "name is " + name;
    }
}

class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("6.postProcessBeforeInitialization被调用");
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("9.postProcessAfterInitialization被调用");
        return bean;
    }
}

class PersonTest {
    public static void main(String[] args) {
        System.out.println("开始初始化容器");
//        ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
        // 利用beanFactory获取ioc容器
        // 1. 创建IOC配置文件的抽象资源
        ClassPathResource classPathResource = new ClassPathResource("applicationContext.xml");
        // 2. 创建BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        // 3. 创建载入BeanDefinition的读取器
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        // 4. 读取配置资源
        reader.loadBeanDefinitions(classPathResource);
        System.out.println("xml加载完毕");

        Person person1 = (Person) defaultListableBeanFactory.getBean("person1");
        System.out.println(person1);
        System.out.println("关闭容器");
        defaultListableBeanFactory.destroySingletons();


        // 先执行destroy，后执行myDestroy方法
//        ((ClassPathXmlApplicationContext) ac).close();
    }
}
