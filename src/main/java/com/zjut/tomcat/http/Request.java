package com.zjut.tomcat.http;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Request {

	private String method;
	
	private String url;
	
	public String getMethod() {
		return method;
	}

	public String getUrl() {
		return url;
	}

	public Request(InputStream inputStream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			String[] requestS = reader.readLine().split(" ");
			method = requestS[0];
			url = requestS[1];
		} catch (Exception e) {
			System.out.println("读取入参失败");
		}
	}
}
