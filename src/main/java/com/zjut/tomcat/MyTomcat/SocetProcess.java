package com.zjut.tomcat.MyTomcat;

import java.io.IOException;
import java.net.Socket;

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
			// 读进来并处理业务
			String mapping = MyTomcat.servletMapping.get(request.getUrl());
			if (StringUtil.isNotEmpty(mapping)) {
				Servlet servlet = MyTomcat.servlet.get(mapping);
				servlet.service(request, response);
			}
			// 向前台反馈内容
			response.writer("Hello Word, this is my content to you!");
		} catch (Exception e) {
			System.out.println("处理失败");
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (IOException e) {
					System.out.println("关闭socket失败");
					e.printStackTrace();
				}
			}
		}
	}

}
