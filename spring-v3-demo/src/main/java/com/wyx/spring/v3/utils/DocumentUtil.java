package com.wyx.spring.v3.utils;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.InputStream;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/16
 * @Date : 2020/8/16
 */
public class DocumentUtil {

	public static Document getDocument(InputStream inputStream) {
		try {
			return new SAXReader().read(inputStream);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}
}
