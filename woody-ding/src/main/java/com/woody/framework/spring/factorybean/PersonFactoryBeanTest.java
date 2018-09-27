package com.woody.framework.spring.factorybean;

import com.woody.framework.reflect.Persom;
import org.springframework.beans.factory.FactoryBean;

public class PersonFactoryBeanTest implements FactoryBean<Persom> {

    private Persom persom;
    public void setPersom(Persom persom) {
        this.persom = persom;
    }

    @Override
    public Persom getObject() throws Exception {
        persom.setAge(18);
        persom.setName("里斯本");
        persom.setSex(0);
        return persom;
    }

    @Override
    public Class<?> getObjectType() {
        return Persom.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }
}
