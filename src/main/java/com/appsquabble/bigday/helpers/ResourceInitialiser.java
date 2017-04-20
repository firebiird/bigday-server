package com.appsquabble.bigday.helpers;

import com.appsquabble.bigday.config.BigDayConfiguration;
import com.appsquabble.bigday.data.User;
import com.appsquabble.bigday.data.Wedding;
import com.appsquabble.bigday.services.AccountService;
import com.appsquabble.bigday.services.Services;
import com.mongodb.DB;

import io.dropwizard.client.HttpClientBuilder;
import io.dropwizard.setup.Environment;

import java.net.UnknownHostException;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import javax.persistence.Entity;

import org.apache.http.client.HttpClient;
import org.mongojack.JacksonDBCollection;

public class ResourceInitialiser {

	final BigDayConfiguration config;

	private final MongoClient mongo;

	public ResourceInitialiser(BigDayConfiguration config) throws UnknownHostException {
		this.config = config;

		this.mongo = new MongoClient(config.getMongoConfiguration().getHost(),
				config.getMongoConfiguration().getPort());

	}

	public Services getInitialisedServices(Environment env) {

		DB db = mongo.getDB(config.getMongoConfiguration().getDatabaseName());
		HttpClient http = new HttpClientBuilder(env).using(config.getHttpConfiguration()).build("http");

		JacksonDBCollection<Wedding, String> wedding = getWrappedJacksonMongoCollection(db, Wedding.class);
		JacksonDBCollection<User, String> users = getWrappedJacksonMongoCollection(db, User.class);
		
		AccountService as = new AccountService(wedding,users);

		Services s = new Services(config, as);

		return s;
	}

	private static <T> JacksonDBCollection<T, String> getWrappedJacksonMongoCollection(DB db, Class<T> clazz) {

		return JacksonDBCollection.wrap(db.getCollection(clazz.getAnnotation(Entity.class).name()), clazz,
				String.class);
	}

	public Mongo getMongo() {
		return this.mongo;
	}

}