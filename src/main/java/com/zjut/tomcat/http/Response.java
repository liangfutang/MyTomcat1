package com.zjut.tomcat.http;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import com.zjut.tomcat.utils.FileUtil;

public class Response {

	public static final String responseHeader="HTTP/1.1 200 \r\n"
            + "Content-Type: text/html\r\n"
            + "\r\n";
	
	private String writer;
	
	private OutputStream outputStream;
	
	public Response(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	public void writer(String writerContent) {
		try {
			outputStream.write((responseHeader + writerContent).getBytes("GBK"));
			outputStream.flush();
			if (outputStream != null) {
				//outputStream.close();
			}
		} catch (Exception e) {
			System.out.println("输出失败");
		}
		
	}
	
	public void writerStatic(String path) throws IOException {
		String[] page = {".js", ".css", ".html", ".text", ".xml"};
		boolean isPage = false;
		for (String endStr : page) {
			if (path.endsWith(endStr)) {
				isPage = true;
				break;
			}
		}
		FileUtil fileUtil = new FileUtil();
		if (isPage) { // 是网页等字符文件使用字符流
			writer(fileUtil.getResourceReader(path));
		} else {  // 读取的是二进制流文件
			// 显示方式一：
			fileUtil.hasResource(path);
			FileInputStream stream = new FileInputStream(path);
			byte[] buf = new byte[512];
			int len = 0;
			String head  ="HTTP/1.0 200 OK\r\n" + 
					"Content-Type: application/octet-stream\r\n" +
					"Content-Disposition: inline;filename=" + new File(path).getName() + "\r\n" +
					"Content-Length: " + stream.available() + "\r\n"+ "\r\n"; //根据HTTP协议，在头信息下面需要有一个空行来结束头信息
			outputStream.write(head.getBytes());
			try {
				while((len=stream.read(buf)) != -1) {
					outputStream.write(buf, 0, len);
				}
			} catch (Exception e) {
				System.out.println("向页面写图片报错:" + e.getMessage());
			}
			stream.close();
			outputStream.flush();
			outputStream.close();
			
			// 显示方式二
			/*fileUtil.hasResource(path);
			File fileToSend = new File(path);
			FileInputStream stream = new FileInputStream(path);
			byte[] data = new byte[stream.available()];
			stream.read(data);
			
			PrintStream out = new PrintStream(outputStream, true);
			out.println("HTTP/1.0 200 OK");
			out.println("Content-Type: application/octet-stream");
			out.println("Content-Length: " + fileToSend.length());
			out.println();// 根据 HTTP 协议, 空行将结束头信息 
			out.write(data);
			stream.close();
			out.close();*/
		}
	}
	
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}
