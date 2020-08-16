package com.wyx.spring.v3.resistry;

/**
 * @Description :
 * 1.实现类封装了spring容器创建出来的所有单例bean集合
 * 2.接口提供了对于其封装的数据先进操作的接口功能(获限bean/添加bean)
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public interface SingletonBeanRegistry {

	/**
	 * 获取单例bean
	 */
	Object getSingleton(String beanName);

	/**
	 * 添加单例bean
	 */
	void addSingleton(String beanName, Object bean);
}
