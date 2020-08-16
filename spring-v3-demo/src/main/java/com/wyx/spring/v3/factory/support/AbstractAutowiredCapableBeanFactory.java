package com.wyx.spring.v3.factory.support;

import com.wyx.spring.v3.config.BeanDefinition;
import com.wyx.spring.v3.config.PropertyValue;
import com.wyx.spring.v3.resolver.BeanDefinitionValueResolver;
import com.wyx.spring.v3.utils.ReflectUtil;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/16
 * @Date : 2020/8/16
 */
public abstract class AbstractAutowiredCapableBeanFactory extends AbstractBeanFactory{

	@Override
	public Object createBean(BeanDefinition bd) {
		// 1、bean的实例化
		Object bean = createBeanByConstructor(bd);

		// 2、bean的属性填充
		populateBean(bean, bd);

		// 3、bean的初始化
		initilizeBean(bean, bd);

		return bean;
	}

	private Object createBeanByConstructor(BeanDefinition beanDefinition) {

		Class<?> clazzType = beanDefinition.getClazzType();
		return ReflectUtil.createBean(clazzType);
	}

	private void initilizeBean(Object bean, BeanDefinition beanDefinition) {
		// todo 需要针对aware接口标记的类进行特殊处理
		// todo 可以进行intilizingBean接口处理

		invokeInitmethod(bean, beanDefinition);

	}

	private void invokeInitmethod(Object bean, BeanDefinition beanDefinition) {
		String initMethod = beanDefinition.getInitMethod();
		if (initMethod == null) {
			return;
		}
		ReflectUtil.invokeMethod(bean, initMethod);
	}



	public void populateBean(Object bean, BeanDefinition beanDefinition) {
		List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
		for (PropertyValue pv : propertyValues) {
			String name = pv.getName();
			// 这不是可用的value，这是一个封装对象
			Object value = pv.getValue();
			// 获取真正的value
			BeanDefinitionValueResolver beanDefinitionValueResolver = new BeanDefinitionValueResolver(this);
			Object valueToUse = beanDefinitionValueResolver.resoleValue(value);
			// 设置属性
			ReflectUtil.setProperty(bean, name, valueToUse);
		}
	}
}
