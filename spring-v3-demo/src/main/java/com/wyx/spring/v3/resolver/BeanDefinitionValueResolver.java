package com.wyx.spring.v3.resolver;

import com.wyx.spring.v3.config.RuntimeBeanReference;
import com.wyx.spring.v3.config.TypedStringValue;
import com.wyx.spring.v3.factory.BeanFactory;

/**
 * @Description :
 * 专门负责beanDefinition中value值的转换
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public class BeanDefinitionValueResolver {
	private BeanFactory beanFactory;

	public BeanDefinitionValueResolver(BeanFactory beanFactory) {
		this.beanFactory = beanFactory;
	}

	public Object resoleValue(Object value) {
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
			return beanFactory.getBean(ref);
		}
		return null;
	}
}
