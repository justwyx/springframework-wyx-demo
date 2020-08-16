package com.wyx.spring.v3;

import com.wyx.spring.v3.entity.User;
import com.wyx.spring.v3.factory.support.DefaultListableBeanFactory;
import com.wyx.spring.v3.reader.XmlBeanDefinitionReader;
import com.wyx.spring.v3.resource.ClassPathResource;
import com.wyx.spring.v3.resource.Resource;
import com.wyx.spring.v3.service.UserService;
import com.wyx.spring.v3.utils.DocumentUtil;
import org.dom4j.Document;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description : TODO 2020/8/16
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public class TestV3 {

	private DefaultListableBeanFactory beanFactory;

	/**
	 * 解析xml
	 */
	@Before
	public void beforse(){
		// TODO XML解析
		//完成XML解析，其实就是完成BeanDefinition的注册
		// XML解析，解析的结果，放入beanDefinitions中
		String location = "beans.xml";
		// 获取流对象(用了策略模式)
		Resource resource = new ClassPathResource(location);
		InputStream inputStream = resource.getResource();


		beanFactory = new DefaultListableBeanFactory();
		// 按照spring定义的标签语义去解析Document文档
		XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
		beanDefinitionReader.loadBeanDefinitions(inputStream);
	}

	// 由A程序员编写
	@Test
	public void test(){
		/**
		 * 这时B程序员只需要调用A程序员提供的公共构建方法就能使用该对象
		 */
		UserService userService = (UserService) beanFactory.getBean("userService");
		//实现用户查询功能
		Map<String, Object> map = new HashMap<>();
		map.put("username","李四");

		List<User> users = userService.queryUsers(map);
		System.out.println(users);
	}
}
