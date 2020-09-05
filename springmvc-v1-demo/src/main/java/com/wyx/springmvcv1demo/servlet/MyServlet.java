package com.wyx.springmvcv1demo.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description : TODO 2020/9/5
 * @author : Just wyx
 * @Date : 2020/9/5
 */
public class MyServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException{
		resp.setContentType("text/plain;charset=utf-8");
		resp.getWriter().write("hello world");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		doPost(req, resp);
	}
}
