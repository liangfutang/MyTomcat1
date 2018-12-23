package com.zjut.tomcat.utils;

public class StringUtil {

	public static boolean isEmpty(String name) {
		if (name != null && name != "") {
			return false;
		}
		return true;
	}
	
	public static boolean isNotEmpty(String name) {
		if (name==null || name=="") {
			return false;
		}
		return true;
	}
}
