package com.wyx.spring.v3.resistry;

import com.wyx.spring.v3.config.BeanDefinition;

/**
 * @Description :
 * 1,实现灰是封装了BeanDefinition集合信息
 * 2.接口类是提供对于其封装的BeanDefinition信息进行添加和获取功能
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public interface BeanDefinitionRegistry {

	/**
	 * 获取
	 */
	BeanDefinition getBeanDefinition(String beanName);

	/**
	 * 注册
	 */
	void registerBeanDefinition(String beanName, BeanDefinition bd);
}
