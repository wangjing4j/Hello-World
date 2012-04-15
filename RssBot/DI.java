package com.searchweb.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Repository;

@Repository("di")
public class DI implements InitializingBean{
	public void add() {
		System.out.println("add ..");
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
