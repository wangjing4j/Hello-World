package com.searchweb.db;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.searchweb.entity.Article;


@Repository("mysqlRepo")
public class MySqlRepository implements IRepository{
	
	@Resource
	public JdbcTemplate jdbcTemplate;
	

	@Override
	public void add(Article article) {
		// TODO Auto-generated method stub
		System.out.println("I'm mysqlReop");
	}

	@Override
	public List<String> getActiveKeywords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateOldKeywords() {
		// TODO Auto-generated method stub
		
	}

	
}
