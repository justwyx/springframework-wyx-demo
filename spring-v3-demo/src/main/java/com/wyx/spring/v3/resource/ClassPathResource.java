package com.wyx.spring.v3.resource;

import java.io.InputStream;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/16
 * @Date : 2020/8/16
 */
public class ClassPathResource implements Resource{
	private String location;

	public ClassPathResource(String location) {
		this.location = location;
	}

	@Override
	public InputStream getResource() {
		return this.getClass().getClassLoader().getResourceAsStream(location);
	}
}
