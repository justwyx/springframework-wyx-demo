package com.wyx.spring.v2;

import com.wyx.spring.v2.entity.User;
import com.wyx.spring.v2.ioc.BeanDefinition;
import com.wyx.spring.v2.ioc.PropertyValue;
import com.wyx.spring.v2.ioc.RuntimeBeanReference;
import com.wyx.spring.v2.ioc.TypedStringValue;
import com.wyx.spring.v2.service.UserService;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
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
public class TestV2 {

	/**
	 * 存储单例bean实例的map容器
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
	public void beforse(){
		// TODO XML解析
		//完成XML解析，其实就是完成BeanDefinition的注册
		// XML解析，解析的结果，放入beanDefinitions中
		String location = "beans.xml";
		// 获取流对象
		InputStream inputStream = getInputStream(location);
		// 创建文档对象
		Document document = createDocument(inputStream);

		// 按照spring定义的标签语义去解析Document文档
		registerBeanDefinitions(document.getRootElement());
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

	private void registerBeanDefinitions(Element rootElement) {
		// 获取<bean>和自定义标签（比如mvc:interceptors）
		List<Element> elements = rootElement.elements();
		for (Element element : elements) {
			// 获取标签名称
			String name = element.getName();
			if (name.equals("bean")) {
				// 解析默认标签，其实也就是bean标签
				parseDefaultElement(element);
			} else {
				// 解析自定义标签，比如aop:aspect标签
				parseCustomElement(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void parseDefaultElement(Element beanElement) {
		try {
			if (beanElement == null) {
				return;
			}
			// 获取id属性
			String id = beanElement.attributeValue("id");

			// 获取name属性
			String name = beanElement.attributeValue("name");

			// 获取class属性
			String clazzName = beanElement.attributeValue("class");
			if (clazzName == null || "".equals(clazzName)) {
				return;
			}

			// 获取init-method属性
			String initMethod = beanElement.attributeValue("init-method");
			// 获取scope属性
			String scope = beanElement.attributeValue("scope");
			scope = scope != null && !scope.equals("") ? scope : "singleton";

			// 获取beanName
			String beanName = id == null ? name : id;
			Class<?> clazzType = Class.forName(clazzName);
			beanName = beanName == null ? clazzType.getSimpleName() : beanName;
			// 创建BeanDefinition对象
			// 此次可以使用构建者模式进行优化
			BeanDefinition beanDefinition = new BeanDefinition(clazzName, beanName);
			beanDefinition.setInitMethod(initMethod);
			beanDefinition.setScope(scope);
			// 获取property子标签集合
			List<Element> propertyElements = beanElement.elements();
			for (Element propertyElement : propertyElements) {
				parsePropertyElement(beanDefinition, propertyElement);
			}

			// 注册BeanDefinition信息
			this.beanDefinitionMap.put(beanName, beanDefinition);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void parsePropertyElement(BeanDefinition beanDefination, Element propertyElement) {
		if (propertyElement == null)
			return;

		// 获取name属性
		String name = propertyElement.attributeValue("name");
		// 获取value属性
		String value = propertyElement.attributeValue("value");
		// 获取ref属性
		String ref = propertyElement.attributeValue("ref");

		// 如果value和ref都有值，则返回
		if (value != null && !value.equals("") && ref != null && !ref.equals("")) {
			return;
		}

		/**
		 * PropertyValue就封装着一个property标签的信息
		 */
		PropertyValue pv = null;

		if (value != null && !value.equals("")) {
			// 因为spring配置文件中的value是String类型，而对象中的属性值是各种各样的，所以需要存储类型
			TypedStringValue typeStringValue = new TypedStringValue(value);

			Class<?> targetType = getTypeByFieldName(beanDefination.getClazzName(), name);
			typeStringValue.setTargetType(targetType);

			pv = new PropertyValue(name, typeStringValue);
			beanDefination.addPropertyValue(pv);
		} else if (ref != null && !ref.equals("")) {

			RuntimeBeanReference reference = new RuntimeBeanReference(ref);
			pv = new PropertyValue(name, reference);
			beanDefination.addPropertyValue(pv);
		} else {
			return;
		}
	}

	private Class<?> getTypeByFieldName(String beanClassName, String name) {
		try {
			Class<?> clazz = Class.forName(beanClassName);
			Field field = clazz.getDeclaredField(name);
			return field.getType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseCustomElement(Element element) {
		// AOP、TX、MVC标签的解析，都是走该流程
	}

	private InputStream getInputStream(String location) {
		return this.getClass().getClassLoader().getResourceAsStream(location);
	}
	private Document createDocument(InputStream inputStream) {
		try {
			SAXReader reader = new SAXReader();
			return reader.read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
