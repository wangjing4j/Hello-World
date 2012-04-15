package com.searchweb.db;

import java.util.List;

import com.searchweb.entity.Article;

public interface IRepository {

	public void add(Article article);
	
	public List<String> getActiveKeywords();
	
	public void updateOldKeywords();
}
