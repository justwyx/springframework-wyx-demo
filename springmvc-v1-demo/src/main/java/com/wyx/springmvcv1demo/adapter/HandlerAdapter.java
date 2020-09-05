package com.wyx.springmvcv1demo.adapter;

import com.wyx.springmvcv1demo.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description : 该接口是DispatcherService用来调用不同类型处理器的统一类型
 * @author : Just wyx
 * @Date : 2020/9/5
 */
public interface HandlerAdapter {
	/**
	 * 用来匹配处理器和适配器
	 */
	boolean supports(Object handler);

	ModelAndView handlerRequest(Object handler, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
