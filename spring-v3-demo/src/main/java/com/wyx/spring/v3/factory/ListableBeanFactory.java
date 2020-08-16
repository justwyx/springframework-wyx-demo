package com.wyx.spring.v3.factory;

import java.util.List;

/**
 * @Description :
 * 对于bean容器中的bean可以进行集合操作或者说叫批量操作
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public interface ListableBeanFactory extends BeanFactory{
	/**
	 * 可以根据指定类型获取它的实现类对象
	 * @param type
	 * @return
	 */
	List<Object> getBeansByTypes(Class type);

}
