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
public class testV1_2 {

	// 由A程序员编写
	@Test
	public void test(){
		/**
		 * 这时B程序员只需要调用A程序员提供的公共构建方法就能使用该对象
		 */
		UserService userService = getUserServiceImpl();
		//实现用户查询功能
		Map<String, Object> map = new HashMap<>();
		map.put("username","李四");

		List<User> users = userService.queryUsers(map);
		System.out.println(users);
	}


	/**
	 * 构造细节太麻烦，A程序员提供了一个公共的方法来创建UserServiceImpl对象
	 *
	 * 但这样要为每一个对象提供一个实例方法
	 */
	public UserServiceImpl getUserServiceImpl() {
		UserServiceImpl userService = new UserServiceImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/wyx_demo?characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		userDao.setDataSource(dataSource);
		userService.setUserDao(userDao);
		return userService;
	}


}
