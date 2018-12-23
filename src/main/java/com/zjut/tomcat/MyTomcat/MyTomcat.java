package com.zjut.tomcat.MyTomcat;

import java.io.File;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

public class MyTomcat {

	private static int port = 8080;
	
	private static String javaClassPath = "src/main/java";
	
	private static Map<String, String> servletMapping = new HashMap<>();
	
	private static Map<String, Servlet> servlet = new HashMap<>();
	
	/**
	 * 初始化配置到容器中
	 */
	private void init() {
		// 如果再src/main/resources文件夹下新建好META-INF/services文件夹并建好配置文件则可以直接有ServiceLoader获取到所有实现了该接口的实现类
		//ServiceLoader<Servlet> servletImpl = ServiceLoader.load(Servlet.class);
		List<Servlet> servletImplList = getServletImplList(javaClassPath);
		
		
		
		//将所有实现了servlet接口的实现类配置到MATE-INF/services下面
		String servletServicesURL = "src/main/resources/META-INF/services";
		File servletServices = new File(servletServicesURL);
		if (!servletServices.exists()) {
			servletServices.mkdirs();
		}
		String[] servletServicesList = servletServices.list();
		// 如果该文件夹下所有文件
		for(String name : servletServicesList) {
			try {
				new File(servletServicesURL + "/" + name).delete();
			} catch (Exception e) {
			}
		}
		// 在servletServicesURL文件夹下新建配置文件
		File servletServicesConfig = new File(servletServicesURL + "/" + Servlet.class.getName());
		ServiceLoader<Servlet> servletImpl = ServiceLoader.load(Servlet.class);
		for(Servlet servlet : servletImpl) {
			System.out.println("实现了sevlet接口的所有实现类有:" + servlet.getClass().getName());
		}
	}
	
	/**
	 * 从根目录开始获取所有的实现了Servlet接口的类
	 * @param name
	 * @return
	 */
	private List<Servlet> getServletImplList(String name) {
		List<Servlet> servletImplList = new ArrayList<>();
		File file = new File(name);
		String[] fileList = file.list();
		for(String temName : fileList) {
			File temFile = new File(name + "/" + temName);
			if (temFile.isFile() && temName.endsWith(".java")) {  // 是java文件 
				// 判断其是否是接口的实现类
			}
			if (temFile.isDirectory()) {  // 是文件夹
				getServletImplList(name + "/" + temName);
			}
		}
		return servletImplList;
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
