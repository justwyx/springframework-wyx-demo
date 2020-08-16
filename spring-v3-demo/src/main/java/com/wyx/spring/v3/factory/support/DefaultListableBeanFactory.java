package com.wyx.spring.v3.factory.support;

import com.wyx.spring.v3.config.BeanDefinition;
import com.wyx.spring.v3.resistry.BeanDefinitionRegistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description :
 * 即是spring中真正管理bean实例的容器工厂
 * 同时又是管理beanDefinition的注册器
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public class DefaultListableBeanFactory extends AbstractAutowiredCapableBeanFactory implements BeanDefinitionRegistry {

	/**
	 * 存储BeanDefinition的容器
	 */
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return beanDefinitionMap.get(beanName);
	}

	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition bd) {
		this.beanDefinitionMap.put(beanName, bd);
	}
}
