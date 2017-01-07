package com.dgut.test;

import org.apache.tools.ant.Main;

public class Test3 {
	
	public  String reverse(String originStr) {
        if(originStr == null || originStr.length() <= 1) 
            return originStr;
        
        String ss= originStr.substring(1);
        System.out.println(ss);
        return reverse(originStr.substring(1)) + originStr.charAt(0);
    }
	
	public static void main(String[] args) {
		String a = "ABCDEF";
		Test3 t = new Test3();
		String b = t.reverse(a);
		System.out.println(b);
	}
}
