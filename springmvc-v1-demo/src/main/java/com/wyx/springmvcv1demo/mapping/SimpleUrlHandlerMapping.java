package com.wyx.springmvcv1demo.mapping;

import com.wyx.springmvcv1demo.handler.QueryUserHandler;
import com.wyx.springmvcv1demo.handler.SaveUserHandler;
import com.wyx.springmvcv1demo.handler.SimpleControllerHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/9/5
 * @Date : 2020/9/5
 */
public class SimpleUrlHandlerMapping implements HandlerMapping{
	private Map<String, Object> urlHandlerList = new HashMap<>();

	public SimpleUrlHandlerMapping() {
		urlHandlerList.put("/saveUser", new SaveUserHandler());
	}

	@Override
	public Object getHandler(HttpServletRequest request) throws Exception {
		String requestURI = request.getRequestURI();
		return this.urlHandlerList.get(requestURI);
	}
}
