package com.wyx.springmvcv1demo.handler;

import com.wyx.springmvcv1demo.model.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description : 指定处理类编写规范(可以针对返回值进行二次处理)
 * @author : Just wyx
 * @Date : 2020/9/5
 */
public interface SimpleControllerHandler {

	ModelAndView handleRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
