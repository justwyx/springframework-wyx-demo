package com.wyx.springmvcv1demo.servlet;

import com.wyx.springmvcv1demo.adapter.HandlerAdapter;
import com.wyx.springmvcv1demo.adapter.HttpRequestHandlerAdapter;
import com.wyx.springmvcv1demo.adapter.SimpleControllerHandlerAdapter;
import com.wyx.springmvcv1demo.mapping.BeanNameUrlHandlerMapping;
import com.wyx.springmvcv1demo.mapping.HandlerMapping;
import com.wyx.springmvcv1demo.mapping.SimpleUrlHandlerMapping;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/9/5
 * @Date : 2020/9/5
 */
public class DispatcherServlet extends AbstractServlet {
	private List<HandlerAdapter> handlerAdapterList = new ArrayList<>();
	private List<HandlerMapping> handlerMappingList = new ArrayList<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		handlerAdapterList.add(new HttpRequestHandlerAdapter());
		handlerAdapterList.add(new SimpleControllerHandlerAdapter());

		handlerMappingList.add(new BeanNameUrlHandlerMapping());
		handlerMappingList.add(new SimpleUrlHandlerMapping());
	}

	/**
	 * 分发请求
	 */
	@Override
	protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 根据请求，查找对应的处理类
			Object handler = getHandler(request);
			if (handler == null) {
				return;
			}

			// 调用处理类的方法，执行请求处理，并返回处理结果
			HandlerAdapter ha = getHandlerAdapter(handler);
			if (ha == null) {
				return;
			}
			ha.handlerRequest(handler, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private HandlerAdapter getHandlerAdapter(Object handler) {
		if (handlerAdapterList != null) {
			for (HandlerAdapter handlerAdapter : handlerAdapterList) {
				if (handlerAdapter.supports(handler)) {
					return handlerAdapter;
				}
			}
		}
		return null;
	}

	private Object getHandler(HttpServletRequest request) throws Exception {
		if (handlerMappingList != null) {
			for (HandlerMapping handlerMapping : handlerMappingList) {
				Object handler = handlerMapping.getHandler(request);
				if (handler != null) {
					return handler;
				}
			}
		}
		return null;
	}

}
