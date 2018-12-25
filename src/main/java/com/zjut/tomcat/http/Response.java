package com.zjut.tomcat.http;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

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
			fileUtil.hasResource(path);
			FileInputStream stream = new FileInputStream(path);
			byte[] buf = new byte[512];
			int len = 0;
			try {
				while((len=stream.read(buf)) != -1) {
					outputStream.write(buf, 0, len);
				}
			} catch (Exception e) {
				System.out.println("向页面写图片报错:" + e.getMessage());
			}
			stream.close();
			outputStream.flush();
			//outputStream.close();
		}
	}
	
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}
