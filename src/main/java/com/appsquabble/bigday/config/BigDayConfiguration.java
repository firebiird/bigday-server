package com.appsquabble.bigday.config;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;
import io.dropwizard.client.HttpClientConfiguration;
import lombok.Data;

public @Data class BigDayConfiguration extends Configuration {

	
	
	private MongoConfiguration mongoConfiguration = new MongoConfiguration();
	
	@Valid
    @NotNull
    @JsonProperty
	private HttpClientConfiguration httpConfiguration = new HttpClientConfiguration();
	
	
}
