package spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * Created by kimiyu on 2017/1/15.
 */
public interface StrategyPatternWithSpring {
    void AlogorithmInterface();
}

@Component("strategyA")
class ConcreteStrategyA implements StrategyPatternWithSpring {

    @Override
    public void AlogorithmInterface() {
        System.out.printf("算法A实现;");
    }
}

@Component("strategyB")
class ConcreteStrategyB implements StrategyPatternWithSpring {

    @Override
    public void AlogorithmInterface() {
        System.out.printf("算法A实现;");
    }
}

class ConcreteTest {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        BeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();
        beanFactory.getBean("strategyA", ConcreteStrategyA.class).AlogorithmInterface();

    }
}


