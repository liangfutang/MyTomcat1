package com.zjut.tomcat.MyTomcat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.dom4j.Element;

import com.zjut.tomcat.utils.StringUtil;
import com.zjut.tomcat.utils.XMLUtil;

public class MyTomcat {

	private static int port = 8080;
	
	//public static Map<String, String> servletMapping = new HashMap<>();
	
	//public static Map<String, Servlet> servlet = new HashMap<>();
	
	public static Map<Pattern, Servlet> servlet = new HashMap<>();
	
	/**
	 * 初始化配置到容器中
	 */
	private void init() {
		XMLUtil xml = new XMLUtil(XMLUtil.class.getResource("/") + "web.xml");
		List<Element> mapping = xml.getNodes("servlet-mapping");
		List<Element> servlets = xml.getNodes("servlet");
		if (mapping==null || servlets==null) {
			throw new RuntimeException("查询配置文件失败");
		}
		for(Element element : mapping) {
			String servletName = element.elementText("servlet-name");
			if (StringUtil.isEmpty(servletName)) {
				throw new RuntimeException("配置文件无效");
			}
			String servletClass = null;
			for(Element elementServlet : servlets) {
				if (elementServlet.elementText("servlet-name").equals(servletName)) {
					servletClass = elementServlet.elementText("servlet-class");
					break;
				}
			}
			Pattern pattern = Pattern.compile(element.elementText("url-pattern"));
			try {
				servlet.put(pattern, (Servlet)Class.forName(servletClass).newInstance());
			} catch (Exception e) {
				System.out.println("初始化配置文件失败");
			}
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
