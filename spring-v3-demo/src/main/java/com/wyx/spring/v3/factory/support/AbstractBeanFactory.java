package com.wyx.spring.v3.factory.support;

import com.wyx.spring.v3.config.BeanDefinition;
import com.wyx.spring.v3.factory.BeanFactory;
import com.wyx.spring.v3.resistry.DefaultSingletonBeanRegistry;
import com.wyx.spring.v3.resistry.SingletonBeanRegistry;

/**
 * @Description :
 * 抽象类AbstractBeanFactory主要是完成getBean操作的共性部分实现
 * 将特性部分的实现，让子类完成(抽象模板方法)
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

	@Override
	public Object getBean(String beanName) {
		// 1、利用缓存，先从singletonObjectMap集合中获取对应的beanName实例
		Object singletonObject = getSingleton(beanName);
		// 2、有对象信息，返回
		if (singletonObject != null) {
			return singletonObject;
		}
		// 3、如果没有对象，则获取对应的BeanDefinition信息
		BeanDefinition beanDefinition = getBeanDefinition(beanName);
		if (beanDefinition == null) {
			return null;
		}

		if (beanDefinition.isSingleton()) {
			// 单例
			singletonObject = createBean(beanDefinition);

			// 添加至缓存
			addSingleton(beanName, beanDefinition);
		} else if (beanDefinition.isPrototype()) {
			// 多例
			createBean(beanDefinition);
		}
		return singletonObject;
	}

    // 需要延迟到defaultListableBeanFactory去实现
	protected abstract BeanDefinition getBeanDefinition(String beanName);

	// 需要延迟到AbstractAutowriedCapableBeanFactory
	public abstract Object createBean(BeanDefinition bd);
}
