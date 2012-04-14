package com.searchweb.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URLDecoder;

import com.searchweb.core.AStrategy;

public class TestReflect {

	/**
	 * @param args
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws ClassNotFoundException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		
			
//		String strategy = "com.searchweb.core.BaiduStrategy";
//		Class<?> strategyClass = Class.forName(strategy);
//		Constructor<?> constructor = strategyClass.getConstructor(new Class[]{String.class});
//		AStrategy strat = (AStrategy) constructor.newInstance("baidu");
//		System.out.println(strat.strategyName);
		
		System.out.println(URLDecoder.decode("http://top.baidu.com/detail.php?b=1&w=%CD%F5%D0%A1%B2%A8"));
	}

}
