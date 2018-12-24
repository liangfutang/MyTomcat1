package com.zjut.tomcat.MyTomcat;

import com.zjut.tomcat.http.Request;
import com.zjut.tomcat.http.Response;

public abstract class Servlet {

	public void service(Request request, Response response) {
		if ("GET".equals(request.getMethod())) {
			doGet(request, response);
		} else {
			doPost(request, response);
		}
	}
	
	public abstract void doGet(Request request, Response response);
	
	public abstract void doPost(Request request, Response response);
}
