package com.wyx.spring.v1;

import com.wyx.spring.v1.dao.UserDaoImpl;
import com.wyx.spring.v1.entity.User;
import com.wyx.spring.v1.service.UserService;
import com.wyx.spring.v1.service.UserServiceImpl;
import org.apache.commons.dbcp.BasicDataSource;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description : spring-v1
 * @author : Just wyx
 * @Date : 2020/8/11
 */
public class testV1_3 {

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
	 *
	 * 但是类对象太多，这个方法就会要大到爆炸
	 */
	public <T> T getBean(String beanName) {
		if ("userService".equals(beanName)) {
			UserServiceImpl userService = new UserServiceImpl();
			UserDaoImpl userDao = new UserDaoImpl();
			BasicDataSource dataSource = new BasicDataSource();
			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
			dataSource.setUrl("jdbc:mysql://localhost:3306/wyx_demo?characterEncoding=utf-8");
			dataSource.setUsername("root");
			dataSource.setPassword("root");
			userDao.setDataSource(dataSource);
			userService.setUserDao(userDao);
			return (T) userService;
		} else if ("beanName2".equals(beanName)) {
			// todo
		}
		return null;
	}


}
