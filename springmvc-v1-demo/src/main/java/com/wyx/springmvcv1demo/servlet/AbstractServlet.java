package com.wyx.springmvcv1demo.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/9/5
 * @Date : 2020/9/5
 */
public abstract class AbstractServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doDispatch(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		doDispatch(req, resp);
	}


	protected abstract void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
