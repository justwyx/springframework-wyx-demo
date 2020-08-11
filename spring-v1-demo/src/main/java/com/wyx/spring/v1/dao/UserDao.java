package com.wyx.spring.v1.dao;

import com.wyx.spring.v1.entity.User;

import java.util.List;
import java.util.Map;


public interface UserDao {

	List<User> queryUserList(Map<String, Object> param);
}
