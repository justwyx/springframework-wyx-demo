package com.wyx.spring.v3.reader;

import com.wyx.spring.v3.resistry.BeanDefinitionRegistry;
import com.wyx.spring.v3.utils.DocumentUtil;
import org.dom4j.Document;

import java.io.InputStream;

/**
 * @Description :
 * 专门解析xml流对象的
 * @author : Just wyx
 * @Date : 2020/8/16
 */
public class XmlBeanDefinitionReader {
	private BeanDefinitionRegistry registry;

	public XmlBeanDefinitionReader(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	public void loadBeanDefinitions(InputStream inputStream) {
		// 创建文档对象
		Document document = DocumentUtil.getDocument(inputStream);
		XmlBeanDefinitionDocumentReader documentReader = new XmlBeanDefinitionDocumentReader(registry);
		documentReader.registerBeanDefintions(document.getRootElement());
	}
}
