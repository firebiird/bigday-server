package com.appsquabble.bigday.services;

import com.appsquabble.bigday.config.BigDayConfiguration;

public class Services {

	
	
	public Services(BigDayConfiguration config, AccountService as) {
		this.config = config;
		this.accountService = as;
	}

	public BigDayConfiguration config;
	
	public AccountService accountService;
	
	
	
}
