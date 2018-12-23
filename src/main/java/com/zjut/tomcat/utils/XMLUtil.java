package com.zjut.tomcat.utils;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 解析XML文件
 * 
 * @author TLF
 *
 */
public class XMLUtil {

	private SAXReader saxReader;

	private Document document;

	public XMLUtil(String path) {
		saxReader = new SAXReader();
		try {
			document = saxReader.read(path);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
	
	public String getElementText(String url) {
		Element root = document.getRootElement();
		List<Element> servletMapping = root.elements("servlet-mapping");
		List<Element> servlet = root.elements("servlet");
		if (servletMapping==null || servlet==null) {
			throw new RuntimeException("该xml配置文件中没有相关的结点");
		}
		String servletMappingURL = null;
		String servletURL = null;
		for(Element element : servletMapping) {
			if (element.element("url-pattern").getTextTrim().equals(url)) {
				servletMappingURL = element.elementText("servlet-name");
				break;
			}
		}
		if (StringUtil.isNotEmpty(servletMappingURL)) {
			for(Element element : servlet) {
				if (element.elementText("servlet-name").equals(servletMappingURL)) {
					servletURL = element.elementText("servlet-class");
				}
			}
		}
		if (StringUtil.isNotEmpty(servletURL)) {
			return servletURL;
		}
		return null;
	}

	/**
	 * 获取根节点下面指定的结点
	 * 
	 * @param name
	 * @return
	 */
	public List<Element> getNodes(String name) {
		Element root = document.getRootElement();
		return root.elements(name);
	}
	
	public static void main(String[] args) {
		System.out.println("显示路径:" + XMLUtil.class.getClassLoader().getResource("../../WEB-INF"));
		XMLUtil xml = new XMLUtil(XMLUtil.class.getResource("../../") + "WEB-INF/web.xml");
		List<Element> nodes = xml.getNodes("servlet");
		for(Element element : nodes) {
			System.out.println("输出名为:" + element.getName());
		}
	}
}
