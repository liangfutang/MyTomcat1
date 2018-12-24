package com.zjut.tomcat.MyTomcat;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Pattern;

import com.zjut.tomcat.http.Request;
import com.zjut.tomcat.http.Response;
import com.zjut.tomcat.utils.StringUtil;

/**
 * 多线程处理获取到此处访问的事务
 * @author TLF
 *
 */
public class SocetProcess extends Thread{

	private Socket socket;
	
	public SocetProcess(Socket socket) {
		this.socket = socket;
	}
	
	@Override
	public void run() {
		try {
			Request request = new Request(socket.getInputStream());
			Response response = new Response(socket.getOutputStream());
			// 将访问的URL和容器中的所有数据匹配
			boolean isPattern = true;
			for(Map.Entry<Pattern, Servlet> entry : MyTomcat.servlet.entrySet()) {
				if (entry.getKey().matcher(request.getUrl()).matches()) {
					Servlet servlet = entry.getValue();
					servlet.service(request, response);
					isPattern = false;
					break;
				}
			}
			
			if (isPattern) {
				response.writer("404-Resource not found！");
			} else {
				response.writer("Hello word, This is my second tomcat");
			}
		} catch (Exception e) {
			System.out.println("处理业务失败");
		}
		
	}

}
