package com.searchweb.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.searchweb.bot.Abot;
import com.searchweb.db.IRepository;
import com.searchweb.util.PropertiesFile;

/**
 * init crawler bot httpuseragnt...
 * @author mwang2
 *
 */
public abstract class AStrategy {
	public String strategyName;
	
	public IRepository repo;
	
	public Map<String/*category*/, HashMap<String, String>/*lastPubTime和keyword*/> lastPubMap = new HashMap<String, HashMap<String, String>>();
	
	protected WebClient client = null;
	
	public static boolean isInitLock = false;
	
	protected Map<String, String> rssMap = new HashMap<String, String>();

	private Properties strategyPropertiesClient = null;
	
	public AStrategy(String strategyName, IRepository repo) {
		this.strategyName = strategyName;
		this.repo = repo;
		if(!isInitLock) {
			this.initialize();
			strategyPropertiesClient  = PropertiesFile.load("conf/sites.properties".replace("sites", strategyName));
			//数据库设计为按不同的类别，读取lastPubTime,按时间倒排序
			lastPubMap = readFromDb();
//			this.loadRssMap(strategyName);
		}
	}

	
	
	private Map<String, HashMap<String, String>> readFromDb() {
		// TODO Auto-generated method stub
		//read keywords from db when rebooting
		return null;
	}



	public void setClient() {
		if (this.client == null) {
			client = new WebClient(BrowserVersion.FIREFOX_3);
			client.setTimeout(0); // Set to zero for an infinite wait.
			client.setJavaScriptEnabled(true);
			client.setRedirectEnabled(true);
			client.setThrowExceptionOnScriptError(false);
			client.setThrowExceptionOnFailingStatusCode(false);
			
			client.getCookieManager().setCookiesEnabled(true);
			client.getCookieManager().clearCookies();
			
			WebClient.setIgnoreOutsideContent(true);
			client.setRefreshHandler(new ThreadedRefreshHandler());
			client.setAjaxController(new NicelyResynchronizingAjaxController());
			try {
				client.setUseInsecureSSL(true);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void closeClient(){
		this.client.closeAllWindows();		
	}
	
	
	public void initialize() {
		this.setClient();
	}
	
	public void loadRssMap(String strategyName) {
		Properties rssMapProperties = PropertiesFile.load("conf/sites.properties".replace("sites", strategyName));
		for(Object key : rssMapProperties.keySet()) {
			rssMap.put(key.toString(), rssMapProperties.getProperty(key.toString()));
		}
	}
	public abstract void doStrategy();
	

	public abstract boolean hasNewKeyword(String category, String url); 
	
	public abstract Map<String, String> getKeywordMap(String category, String url);
	
	public void getArticles(Map<String, String> keywordMap) {
		String botClass = strategyPropertiesClient.getProperty("crawlBot");
		try {
			for (String keyword : keywordMap.keySet()) {
				Class<?> bot = Class.forName(botClass);
				Constructor<?> botConstructor = bot.getConstructor(new Class[] {String.class, String.class });
				Abot abot = (Abot) botConstructor.newInstance(keyword, keywordMap.get(keyword));
				Thread botThead = new Thread(abot);
				botThead.start();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void finalize() {
		this.closeClient();
	}
	
}
