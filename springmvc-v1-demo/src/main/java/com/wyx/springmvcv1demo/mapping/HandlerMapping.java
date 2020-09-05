package com.wyx.springmvcv1demo.mapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description : 它的实现类是用来请求和处理映射关系
 * 该接口的作用是提供对该映射关系的访问，比如说根据请求查找处理类
 * @author : Just wyx
 * @Date : 2020/9/5
 */
public interface HandlerMapping {

	Object getHandler(HttpServletRequest request) throws Exception;
}
