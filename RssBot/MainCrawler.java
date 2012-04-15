package com.searchweb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.searchweb.bot.BaiduBot;
import com.searchweb.core.AStrategy;
import com.searchweb.db.IRepository;
import com.searchweb.util.PropertiesFile;

@Component
public class MainCrawler {
	@Resource
	BaiduBot bb;
	
	static Properties sietsPropertiesClient = PropertiesFile.load("conf/sites.properties");
	
	public static ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
	
	static Properties strategyPropertiesClient = null; 
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
//		BaiduBot bot = new BaiduBot("", "");
		String sitename = args[0];
		try {
			String strategyName = getStrategryBySitename(sitename);
			initStrategy(strategyName);
			IRepository repo = (IRepository) Class.forName(strategyPropertiesClient.getProperty("dbRepository")).newInstance();
			Class<?> strategyClass = Class.forName(strategyPropertiesClient.getProperty("strategy"));
			Constructor<?> constructor = strategyClass.getConstructor(new Class[]{String.class, IRepository.class});
			AStrategy strategy = (AStrategy) constructor.newInstance(strategyName, repo);
			strategy.doStrategy();
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		
	}
	
	void sayA() {
		bb.sayA();
	}
	
	private static String getStrategryBySitename(String sitename) {
		String strategy = sietsPropertiesClient.getProperty(sitename);
		return strategy;
	}
	
	private static void initStrategy(String strategyName) {
		strategyPropertiesClient = PropertiesFile.load("conf/sites.properties".replace("sites", strategyName));
	}

}
