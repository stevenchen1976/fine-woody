package com.woody.framework.spring.beanfactory;

import com.woody.framework.reflect.Persom;
import com.woody.framework.spring.factorybean.PersonFactoryBeanTest;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class BeanFactoryTest {

    public static void main(String[] args) throws Exception {

        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource resource = patternResolver.getResource("classpath:/spring/application-beans.xml");

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(factory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);

        Persom person = factory.getBean("persom", Persom.class);

        person.saySomething();

        Persom personFactoryBean =(Persom)factory.getBean("personFactoryBean");
        personFactoryBean.saySomething();
        PersonFactoryBeanTest personFactoryBeanTest =(PersonFactoryBeanTest)factory.getBean("&personFactoryBean");
        personFactoryBeanTest.getObject();
        System.out.println(personFactoryBeanTest.isSingleton());
    }
}
