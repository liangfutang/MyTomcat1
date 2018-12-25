package com.zjut.tomcat.MyTomcat;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;
import java.util.regex.Pattern;

import com.zjut.tomcat.http.Request;
import com.zjut.tomcat.http.Response;
import com.zjut.tomcat.utils.FileUtil;
import com.zjut.tomcat.utils.StringUtil;

/**
 * 多线程处理获取到此处访问的事务
 * 
 * @author TLF
 *
 */
public class SocetProcess extends Thread {

	private Socket socket;

	public SocetProcess(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
			Request request = new Request(inputStream);
			Response response = new Response(outputStream);
			// 将访问的URL和容器中的所有数据匹配
			boolean isPattern = true;
			for (Map.Entry<Pattern, Servlet> entry : MyTomcat.servlet.entrySet()) {
				if (entry.getKey().matcher(request.getUrl()).matches()) {
					Servlet servlet = entry.getValue();
					servlet.service(request, response);
					isPattern = false;
					break;
				}
			}
			// 向前端输出静态资源或图片等二进制文件
			response.writerStatic("src/main/resources" + request.getUrl());
			/*
			 * if (isPattern) { response.writer("404-Resource not found！"); } else {
			 * response.writer("Hello word, This is my second tomcat"); }
			 */
		} catch (Exception e) {
			System.out.println("处理业务失败");
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
				if (outputStream != null) {
					outputStream.close();
				}
			} catch (Exception e2) {
			}
		}

	}

}
