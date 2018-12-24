package com.zjut.tomcat.MyTomcat;

import com.zjut.tomcat.http.Request;
import com.zjut.tomcat.http.Response;

public abstract class Servlet {

	public void service(Request request, Response response) {
		if ("GET".equals(request.getMethod())) {
			this.doGet(request, response);
		} else {
			this.doPost(request, response);
		}
	}
	
	public abstract void doGet(Request request, Response response);
	
	public abstract void doPost(Request request, Response response);
}
