package com.searchweb.bot;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.stereotype.Component;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.searchweb.entity.Article;

@Component("baiduBot")
public class BaiduBot extends Abot{
	
	public BaiduBot() {
		super();
	}
	
	public BaiduBot(String keyword, String url) {
		super(keyword, url);
	}

	@Override
	public Article getArticle(String keyword, String url) {
		// TODO Auto-generated method stub
		try {
			HtmlPage searchPage = this.client.getPage(url);
			HtmlAnchor anchor = searchPage.getFirstAnchorByText(keyword);
			Article article = UseGateBaiduToConvert(anchor.getHrefAttribute());
			return article;
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
	}

	@Override
	public void sayA() {
		repo1.add(new Article());
	}
}
