package com.wyx.spring.v3.resistry;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/16
 * @Date : 2020/8/16
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry{

	/**
	 * 存储单例bean实例的map容器（线程安全的单例管理）
	 */
	private Map<String, Object> singletonObjectMap = new HashMap<>();


	@Override
	public Object getSingleton(String beanName) {
		return singletonObjectMap.get(beanName);
	}

	@Override
	public void addSingleton(String beanName, Object bean) {
		// 可以使用双重检查锁进行安全处理
		this.singletonObjectMap.put(beanName, bean);
	}
}
