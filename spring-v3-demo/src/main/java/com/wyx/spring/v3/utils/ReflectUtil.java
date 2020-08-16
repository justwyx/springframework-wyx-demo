package com.wyx.spring.v3.utils;

import com.wyx.spring.v3.config.BeanDefinition;
import com.wyx.spring.v3.config.PropertyValue;
import com.wyx.spring.v3.config.RuntimeBeanReference;
import com.wyx.spring.v3.config.TypedStringValue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/16
 * @Date : 2020/8/16
 */
public class ReflectUtil {

	public static Object createBean(Class clazz) {
		// 元参构造器
		try {
			Constructor<?> constructor = clazz.getDeclaredConstructor();
			return constructor.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void invokeMethod(Object bean, String initMethod) {
		try {
			Method declaredMethod = bean.getClass().getDeclaredMethod(initMethod);
			declaredMethod.setAccessible(true);
			declaredMethod.invoke(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setProperty(Object bean, String name, Object valueToUse) {

		try {
			Class<?> aClass = bean.getClass();
			Field declaredField = aClass.getDeclaredField(name);
			declaredField.setAccessible(true);
			declaredField.set(bean, valueToUse);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Class<?> getTypeByFieldName(String beanClassName, String name) {
		try {
			Class<?> clazz = Class.forName(beanClassName);
			Field field = clazz.getDeclaredField(name);
			return field.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}



}
