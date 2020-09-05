package com.wyx.springmvcv1demo.mapping;

import com.wyx.springmvcv1demo.handler.QueryUserHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/9/5
 * @Date : 2020/9/5
 */
public class BeanNameUrlHandlerMapping implements HandlerMapping{
	private Map<String, Object> urlHandlerList = new HashMap<>();

	public BeanNameUrlHandlerMapping() {
		urlHandlerList.put("/queryUser", new QueryUserHandler());
	}

	@Override
	public Object getHandler(HttpServletRequest request) throws Exception {
		String requestURI = request.getRequestURI();
		return this.urlHandlerList.get(requestURI);
	}
}
