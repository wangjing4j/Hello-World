package com.searchweb.core;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.xml.XmlPage;
import com.searchweb.db.IRepository;

public class BaiduStrategy extends AStrategy {
	public static boolean hasLoadRssMap = false;
	
	
	public BaiduStrategy(String strategyName, IRepository repo) {
		super(strategyName, repo);
		if(!hasLoadRssMap) {
			this.loadRssMap(strategyName);
		}
	}

	@Override
	public boolean hasNewKeyword(String category, String url) {
		// TODO Auto-generated method stub
		String lastPubTime = lastPubMap.get(category).get("lastPubTime");
		Set<String> keywordsSet = lastPubMap.get(category).keySet();
		XmlPage page = null;
		try {
			page = this.client.getPage(url);
			String tmpTime = page.getFirstByXPath("");
			if(lastPubTime.equals(tmpTime)) {
				return false;
			}
			List<?> keywordsList = page.getByXPath("keywordPath");
			for(Object keyword : keywordsList) {
				if(!keywordsSet.contains(keyword.toString())) {
					return true;
				}
			}
			
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return true;
	}

	@Override
	public Map<String, String> getKeywordMap(String category, String url) {
		// TODO Auto-generated method stub
		Map<String/*keyword*/, String/*keyword search url*/> map = new HashMap<String, String>();
		Set<String> keywordsSet = lastPubMap.get(category).keySet();
		XmlPage page = null;
		try {
			page = this.client.getPage(url);
			List<?> keywordsList = page.getByXPath("keywordPath");
			for(Object keyword : keywordsList) {
				if(!keywordsSet.contains(keyword.toString())) {
					map.put(keyword.toString(), "url"/*Dom中的url*/);
				}
			}
		} catch(Exception e) {
			
		}
		
		return map;
	}

	@Override
	public void doStrategy() {
		Set<String> rssKeys = this.rssMap.keySet();
		for (String key : rssKeys) {
			if (hasNewKeyword(key, rssMap.get(key))) {
				Map<String, String> keywordMap = this.getKeywordMap(key, rssMap.get(key));
				this.getArticles(keywordMap);
			}
		}
	}
	
}
