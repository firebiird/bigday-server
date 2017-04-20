package com.appsquabble.bigday.data;

import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.appsquabble.bigday.data.Wedding.WeddingBuilder;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public  @Data @NoArgsConstructor @AllArgsConstructor @Builder @Entity(name="user") class User extends BaseDBObject {

	@JsonProperty(FIELD_EMAIL)
	@NotNull
	@NotEmpty
	private String email;
	
	@JsonProperty(FIELD_PASSWORD)
	@NotNull
	@NotEmpty
	private String password;
	
	@JsonProperty(FIELD_WEDDING)
	@NotNull
	@NotEmpty
	private String wedding;
	
	@Nullable
	@JsonProperty(FIELD_SESSION)
	private String session;
	
	@Nullable
	@JsonProperty(FIELD_TIMEOUT)
	private long sessionTimeout;
	
	
	public static final String FIELD_EMAIL = "e";
	public static final String FIELD_PASSWORD = "p";
	public static final String FIELD_WEDDING = "w";
	public static final String FIELD_SESSION = "s";
	public static final String FIELD_TIMEOUT = "t";
	
	
	
	
}
