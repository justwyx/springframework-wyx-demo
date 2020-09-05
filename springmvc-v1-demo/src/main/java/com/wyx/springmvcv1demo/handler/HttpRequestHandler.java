package com.wyx.springmvcv1demo.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description : 指定处理类编写规范
 * @author : Just wyx
 * @Date : 2020/9/5
 */
public interface HttpRequestHandler {

	void handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
