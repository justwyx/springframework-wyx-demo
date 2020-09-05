package com.wyx.springmvcv1demo.handler;

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
public class SaveUserHandler implements SimpleControllerHandler{
	@Override
	public ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/plain;charset=utf-8");
		resp.getWriter().write("hello world-SaveUserHandler");
		return null;
	}
}
