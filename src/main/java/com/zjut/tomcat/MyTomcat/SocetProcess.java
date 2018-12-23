package com.zjut.tomcat.MyTomcat;

import java.net.Socket;

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
		
	}

}
