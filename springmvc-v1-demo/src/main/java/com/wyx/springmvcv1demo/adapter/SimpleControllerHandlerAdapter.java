package com.wyx.springmvcv1demo.adapter;

import com.wyx.springmvcv1demo.handler.HttpRequestHandler;
import com.wyx.springmvcv1demo.handler.SimpleControllerHandler;
import com.wyx.springmvcv1demo.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/9/5
 * @Date : 2020/9/5
 */
public class SimpleControllerHandlerAdapter implements HandlerAdapter{
	@Override
	public boolean supports(Object handler) {
		return (handler instanceof SimpleControllerHandler);
	}

	@Override
	public ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		return ((SimpleControllerHandler)handler).handleRequest(req, resp);
	}
}
