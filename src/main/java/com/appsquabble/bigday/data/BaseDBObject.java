package com.appsquabble.bigday.data;

import org.mongojack.Id;
import org.mongojack.ObjectId;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data abstract class BaseDBObject {

	@Id
	@ObjectId
	@JsonProperty(FIELD_ID)
	private String id;
	
	
	public static final String FIELD_ID = "_id";
	
	
}
