package com.appsquabble.bigday;
import com.appsquabble.bigday.config.BigDayConfiguration;
import com.appsquabble.bigday.helpers.ResourceInitialiser;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.appsquabble.bigday.resources.AccountsResource;
import com.appsquabble.bigday.resources.ClientResource;
import com.appsquabble.bigday.resources.ManagementResource;
import com.appsquabble.bigday.services.Services;

public class MainApplication extends Application<BigDayConfiguration> {

	public static void main(String[] args) throws Exception {
		new MainApplication().run(args);
	}

	
	@Override
	public void initialize(Bootstrap<BigDayConfiguration> bootstrap) {
		// TODO Auto-generated method stub
		
	
		
	}

	@Override
	public void run(BigDayConfiguration configuration, Environment environment) throws Exception {
	
		
		Services services = new ResourceInitialiser(configuration).getInitialisedServices(environment);
		
		
		environment.jersey().register(new AccountsResource(services));
		environment.jersey().register(new ManagementResource());
		environment.jersey().register(new ClientResource());
		
		
	}

}
