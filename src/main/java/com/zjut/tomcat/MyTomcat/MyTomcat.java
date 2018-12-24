package com.zjut.tomcat.MyTomcat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;

import com.zjut.tomcat.utils.XMLUtil;

public class MyTomcat {

	private static int port = 8080;
	
	public static Map<String, String> servletMapping = new HashMap<>();
	
	public static Map<String, Servlet> servlet = new HashMap<>();
	
	/**
	 * 初始化配置到容器中
	 */
	private void init() {
		XMLUtil xml = new XMLUtil(XMLUtil.class.getResource("/") + "web.xml");
		List<Element> servletMappingList = xml.getNodes("servlet-mapping");
		List<Element> servletList = xml.getNodes("servlet");
		if (servletMappingList==null || servletList==null) {
			throw new RuntimeException("查找Servlet匹配文件失败");
		}
		for(Element element : servletMappingList) {
			servletMapping.put(element.element("url-pattern").getTextTrim(), element.element("servlet-name").getTextTrim());
		}
		try {
			for(Element element : servletList) {
				servlet.put(element.elementText("servlet-name"), (Servlet)Class.forName(element.elementText("servlet-class")).newInstance());
			}
		} catch (Exception e) {
		}
	}
	
	public void start() {
		try {
			ServerSocket socket = new ServerSocket(port);
			this.init();
			while(true) {
				Socket accept = socket.accept();
				SocetProcess process = new SocetProcess(accept);
				process.start();
			}
		} catch (Exception e) {
			System.out.println("启动失败");
		}
	}
	
	public static void main(String[] args) {
		MyTomcat tomcat = new MyTomcat();
		tomcat.start();
	}
}
