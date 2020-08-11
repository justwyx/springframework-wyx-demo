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
public class testV1 {

	// 由B程序员编写
	@Test
	public void test(){
		/**
		 * UserServiceImpl本是由A程序员编写的
		 * 现在B程序员想用 UserServiceImpl里的查询方法
		 * 但现在B程序员为了用UserServiceImpl里的方法，还需要去进行业务对象的构建
		 * 且B程序员并不了解业务对象的构造细节
		 */
		UserServiceImpl userService = new UserServiceImpl();
		UserDaoImpl userDao = new UserDaoImpl();
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://localhost:3306/wyx_demo?characterEncoding=utf-8");
		dataSource.setUsername("root");
		dataSource.setPassword("root");
		userDao.setDataSource(dataSource);
		userService.setUserDao(userDao);

		//实现用户查询功能
		Map<String, Object> map = new HashMap<>();
		map.put("username","李四");

		List<User> users = userService.queryUsers(map);
		System.out.println(users);
	}

}
