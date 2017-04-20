package com.appsquabble.bigday.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import org.mongojack.DBQuery;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import com.appsquabble.bigday.data.User;
import com.appsquabble.bigday.data.Wedding;
import com.appsquabble.bigday.helpers.Utils;
import com.appsquabble.bigday.packages.LoginPackage;
import com.appsquabble.bigday.packages.RegistrationPackage;
import com.appsquabble.bigday.packages.WeddingPackage;
import com.mongodb.BasicDBObject;

public class AccountService {

	
	public JacksonDBCollection<Wedding, String> weddings;
	public JacksonDBCollection<User, String> users;
	
	
	public AccountService(JacksonDBCollection<Wedding,String> weddings,JacksonDBCollection<User,String> users)
	{
		this.weddings = weddings;
		this.users = users;
	}
	
	public boolean isPasswordValid(String password)
	{
		System.out.println(weddings.count(new BasicDBObject(Wedding.FIELD_PASSWORD,password)));
		return weddings.count(new BasicDBObject(Wedding.FIELD_PASSWORD,password)) == 1;
		
	}

	public Wedding getWeddingForPassword(String password) {
		
		Wedding wedding = weddings.findOne(DBQuery.is(Wedding.FIELD_PASSWORD, password));
		wedding.setPassword("*********");
		return wedding;
		
		
	}
	
	public Optional<String> createNewWedding(User user,WeddingPackage wp)
	{
		if(user == null)
		{
			return Optional.of("User not found!");
		}
		
		if(weddings.find(DBQuery.is(Wedding.FIELD_PASSWORD, wp.getPassword())).count() == 0)
		{
			// None have this password - woo
			
			Wedding weddingToInsert = Wedding.builder().date(wp.getDate()).emails(new ArrayList<String>(){{add(user.getEmail());}}).password(wp.getPassword()).name(wp.getName()).usersToBeAuthed(new ArrayList<String>()).build();
			
			WriteResult<Wedding, String> res = weddings.insert(weddingToInsert);
			
			String id = res.getSavedId();
			
			if(id == null)
			{
				return Optional.of("Failed to create Wedding - Please contact support.");
			}
			
			user.setWedding(id);
			users.save(user);
			
		}
		
		
		return Optional.empty();
	}
	

	public Optional<String> createNewAccount(RegistrationPackage rp) {
		Wedding wedding = null;
		if(rp.getEwi() != null)
		{
			 wedding = weddings.findOne(DBQuery.is(Wedding.FIELD_ID, rp.getEwi()));
			if(wedding == null)
			{
				return Optional.of("Wedding did not exist.");
			}
		}
		
		User userToInsert = User.builder().email(rp.getEmail()).password(Utils.hashPassword(rp.getPassword())).wedding(wedding != null ? wedding.getId() : null).build();
		
		WriteResult<User, String> userInsertResult = users.insert(userToInsert);
		String id = userInsertResult.getSavedId();
		
		if(id != null && wedding != null)
		{
			wedding.getUsersToBeAuthed().add(userToInsert.getEmail());
			weddings.save(wedding);
		}
		
		return Optional.empty();
		
		
	}

	public boolean accountExists(RegistrationPackage rp) {
	
		return users.count(new BasicDBObject(User.FIELD_EMAIL,rp.getEmail())) != 0;
		
	}

	public boolean isUserAuthenticated(String token) {
		
		User u = users.findOne(DBQuery.is(User.FIELD_SESSION, token));
		
		if(u == null)
		{
			return false;
		}
		else
		{
			// Check its not timed out.
			if(u.getSessionTimeout() < System.currentTimeMillis())
			{
				u.setSession(null);
				u.setSessionTimeout(0);
				users.save(u);
				return false;
			}
				
		}
		return true;
	}

	public Optional<User> getUserForToken(String token) {
		
		if(isUserAuthenticated(token))
		{
			return Optional.ofNullable(users.findOne(DBQuery.is(User.FIELD_SESSION, token)));
		}
		else
		{
			return Optional.empty();
		}
		
	}

	public Optional<String> loginUser(LoginPackage lp) {
		
		User u = users.findOne(DBQuery.is(User.FIELD_EMAIL, lp.getEmail()));
		
		if(u == null)
			return Optional.empty();
		
		if(Utils.checkPassword(lp.getPassword(), u.getPassword()))
		{
			if(u.getSession() == null || u.getSessionTimeout() < System.currentTimeMillis())
			{
				u.setSession(Utils.generateRandomKey());
				u.setSessionTimeout(Utils.return15MinsFromNow());
				users.save(u);
			}
			
			return Optional.of(u.getSession());
		}
		else
		{
			return Optional.empty();
		}
		
	}
	
	
	
	
}
