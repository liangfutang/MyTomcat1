package com.zjut.tomcat.http;

import java.io.OutputStream;

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
				outputStream.close();
			}
		} catch (Exception e) {
			System.out.println("输出失败");
		}
		
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}
}
