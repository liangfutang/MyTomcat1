package com.zjut.tomcat.servlet;

import com.zjut.tomcat.MyTomcat.Servlet;

public class MyFirstServlet extends Servlet{

	@Override
	public void doGet() {
		
	}

	@Override
	public void doPost() {
		
	}

	public static void main(String[] args) {
		MyFirstServlet myFirstServlet = new MyFirstServlet();
		if(new MyFirstServlet() instanceof Servlet) {
			
		}
	}
}
