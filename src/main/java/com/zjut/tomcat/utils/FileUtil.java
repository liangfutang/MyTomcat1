package com.zjut.tomcat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class FileUtil {

	/**
	 * 读取字符流
	 * 
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public String getResourceReader(String path) throws IOException {
		hasResource(path);
		BufferedReader br = new BufferedReader(new FileReader(path));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = br.readLine()) != null) {
			sb.append(br.readLine());
		}
		br.close();
		return sb.toString();
	}

	/**
	 * 判断是否有该资源
	 * 
	 * @param path
	 * @return
	 */
	public void hasResource(String path) {
		// 判断请求的资源是否存在
		//String root = "src/main/resources";
		//path = root + path;
		File file = new File(path);
		if (!file.exists()) {
			throw new RuntimeException("请求的资源不存在");
		}
	}

	public static void main(String[] args) throws IOException {
		Pattern compile = Pattern.compile("jpg");
		boolean matches = compile.matcher("jpg").matches();
		System.out.println(matches);

	}
}
