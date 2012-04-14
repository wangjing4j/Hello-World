package com.searchweb.bot;

import java.security.GeneralSecurityException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ThreadedRefreshHandler;
import com.gargoylesoftware.htmlunit.WebClient;
import com.searchweb.db.IRepository;
import com.searchweb.entity.Article;

public abstract class Abot implements Runnable {
	protected WebClient client = null;
	
	public static IRepository repo;
	
	public static boolean isInitLock = false;
	
	public String keyword;
	
	public String url;

	public Abot(String keyword, String url) {
		super();
		this.keyword = keyword;
		this.url = url;
		if(!isInitLock) {
			this.initialize();
//			this.loadRssMap(strategyName);
		}
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
	
	public void finalize() {
		this.closeClient();
	}
	

	public abstract Article getArticle(String keyword, String url);
	
	public Article UseGateBaiduToConvert(String hrefAttribute) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void pushToDB(Article article) {
		repo.add(article);
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		pushToDB(this.getArticle(this.keyword, this.url));
	}

}
