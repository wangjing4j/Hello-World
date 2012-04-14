package com.searchweb;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Properties;

import com.searchweb.core.AStrategy;
import com.searchweb.db.IRepository;
import com.searchweb.entity.Article;
import com.searchweb.util.PropertiesFile;

public class MainCrawler {
	
	static Properties sietsPropertiesClient = PropertiesFile.load("conf/sites.properties");
	
	static Properties strategyPropertiesClient = null; 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sitename = args[0];
		try {
			String strategyName = getStrategryBySitename(sitename);
			initStrategy(strategyName);
			IRepository repo = (IRepository) Class.forName(strategyPropertiesClient.getProperty("dbRepository")).newInstance();
			Class<?> strategyClass = Class.forName(strategyPropertiesClient.getProperty("strategy"));
			Constructor<?> constructor = strategyClass.getConstructor(new Class[]{String.class, IRepository.class});
			AStrategy strategy = (AStrategy) constructor.newInstance(strategyName, repo);
			strategy.doStrategy();
//			if(strategy.hasNewKeyword()) {
//				Map<String, String> keywordMap = strategy.getKeywordMap();
//				strategy.getArticle(keywordMap, repo);
//			}
			
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
	
	private static String getStrategryBySitename(String sitename) {
		String strategy = sietsPropertiesClient.getProperty(sitename);
		return strategy;
	}
	
	private static void initStrategy(String strategyName) {
		strategyPropertiesClient = PropertiesFile.load("conf/sites.properties".replace("sites", strategyName));
	}

}
