package com.wyx.spring.v2;

import com.wyx.spring.v2.dao.UserDaoImpl;
import com.wyx.spring.v2.entity.User;
import com.wyx.spring.v2.ioc.BeanDefinition;
import com.wyx.spring.v2.ioc.PropertyValue;
import com.wyx.spring.v2.ioc.RuntimeBeanReference;
import com.wyx.spring.v2.ioc.TypedStringValue;
import com.wyx.spring.v2.service.UserService;
import com.wyx.spring.v2.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description : TODO 2020/8/11
 * @author : Just wyx
 * @Date : 2020/8/11
 */
public class testV2 {

	/**
	 * 存储半日例bean实例的map容器
	 */
	private Map<String, Object> singletonObjectMap = new HashMap<>();

	/**
	 * 存储BeanDefinition的容器
	 */
	private Map<String, BeanDefinition> beanDefinitionMap = new HashMap<>();


	/**
	 * 解析xml
	 */
	@Before
	public void loadXML() {

	}

	// 由A程序员编写
	@Test
	public void test(){
		/**
		 * 这时B程序员只需要调用A程序员提供的公共构建方法就能使用该对象
		 */
		UserService userService = getBean("userService");
		//实现用户查询功能
		Map<String, Object> map = new HashMap<>();
		map.put("username","李四");

		List<User> users = userService.queryUsers(map);
		System.out.println(users);
	}


	/**
	 * 提供一个公共的方法获取所有Bean对象实例
	 */
	public <T> T getBean(String beanName) {
		// 1、利用缓存，先从singletonObjectMap集合中获取对应的beanName实例
		Object singletonObject = this.singletonObjectMap.get(beanName);
		// 2、有对象信息，返回
		if (singletonObject != null) {
			return (T) singletonObject;
		}
		// 3、如果没有对象，则获取对应的BeanDefinition信息
		BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);


		if (beanDefinition.isSingleton()) {
			// 单例
			singletonObject = doCreateBean(beanDefinition);

			// 添加至缓存
			singletonObjectMap.put(beanName, beanDefinition);
		} else if (beanDefinition.isPrototype()) {
			// 多例
			singletonObject = doCreateBean(beanDefinition);
		}
		return (T) singletonObject;
	}

	private Object doCreateBean(BeanDefinition beanDefinition) {
		// 1、bean的实例化
		Object bean = createBeanByConstructor(beanDefinition);

		// 2、bean的属性填充
		populateBean(bean, beanDefinition);

		// 3、bean的初始化
		initilizeBean(bean, beanDefinition);

		return bean;
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
		Class<?> clazzType = beanDefinition.getClazzType();
		try {
			Method declaredMethod = clazzType.getDeclaredMethod(initMethod);
			declaredMethod.setAccessible(true);
			declaredMethod.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void populateBean(Object bean, BeanDefinition beanDefinition) {
		List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
		for (PropertyValue pv : propertyValues) {
			String name = pv.getName();
			// 这不是可用的value，这是一个封装对象
			Object value = pv.getValue();
			// 获取真正的value
			Object valueToUse = resoleValue(value);
			// 设置属性
			setProperty(bean, name, valueToUse);
		}

	}

	private void setProperty(Object bean, String name, Object valueToUse) {

		try {
			Class<?> aClass = bean.getClass();
			Field declaredField = aClass.getDeclaredField(name);
			declaredField.setAccessible(true);
			declaredField.set(bean, valueToUse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Object resoleValue(Object value) {
		if (value instanceof TypedStringValue) {
			TypedStringValue typedStringValue = (TypedStringValue) value;
			Class<?> targetType = typedStringValue.getTargetType();
			String stringValue = typedStringValue.getValue();

			if (targetType == Integer.class) {
				return Integer.valueOf(stringValue);
			} else if (targetType == String.class) {
				return stringValue;
			} else {
				// todo 还有其它类型，可以使用策略模式替换掉
			}
		} else if (value instanceof RuntimeBeanReference) {
			RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
			String ref = runtimeBeanReference.getRef();
			// 递归调用
			return getBean(ref);
		}
		return null;
	}

	private Object createBeanByConstructor(BeanDefinition beanDefinition) {
		// TODO 静态工厂方法 工厂实例

		// 构造器方式去创建bean实例
		Class<?> clazzType = beanDefinition.getClazzType();
		// 元参构造器
		try {
			Constructor<?> constructor = clazzType.getDeclaredConstructor();
			return constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
