package com.appsquabble.bigday.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor @AllArgsConstructor @Builder @Entity(name="wedding") class Wedding extends BaseDBObject {

	
	public static final String FIELD_PASSWORD = "p";
	public static final String FIELD_NAME = "n";
	public static final String FIELD_EMAILS = "e";
	public static final String FIELD_DATE = "d";
	public static final String FIELD_USERS_TO_BE_AUTHED = "tba";
	
	

	@JsonProperty(FIELD_NAME)
	private String name;

	
	@JsonProperty(FIELD_PASSWORD)
	@NotNull
	@NotEmpty
	private String password;
	

	@JsonProperty(FIELD_DATE)
	@NotEmpty
	@NotNull
	private Date date;
	
	@JsonProperty(FIELD_EMAILS)
	private List<String> emails = new ArrayList<String>();
	@JsonProperty(FIELD_USERS_TO_BE_AUTHED)
	private List<String> usersToBeAuthed = new ArrayList<String>();
	
	
	
	
	
	
	
	
	
	
}
