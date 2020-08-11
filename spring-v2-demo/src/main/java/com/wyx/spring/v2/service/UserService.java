package com.wyx.spring.v2.service;


import com.wyx.spring.v2.entity.User;

import java.util.List;
import java.util.Map;


public interface UserService {
	List<User> queryUsers(Map<String, Object> param);
}
