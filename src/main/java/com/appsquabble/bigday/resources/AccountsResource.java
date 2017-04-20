package com.appsquabble.bigday.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.appsquabble.bigday.MainApplication;
import com.appsquabble.bigday.data.User;
import com.appsquabble.bigday.packages.LoginPackage;
import com.appsquabble.bigday.packages.RegistrationPackage;
import com.appsquabble.bigday.packages.WeddingPackage;

import static com.appsquabble.bigday.helpers.JsonResponses.JsonForbidden;
import static com.appsquabble.bigday.helpers.JsonResponses.JsonSuccess;

import java.util.Optional;

import com.appsquabble.bigday.services.Services;

@Path("/v1")
public class AccountsResource {

	public static final String AUTH_HEADER = "X-Auth-Token";
	
	public Services services;
	
	
	public AccountsResource(Services services)
	{
		this.services = services;
	}
	
	@Path("/register")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(RegistrationPackage rp)
	{
	
		if(services.accountService.accountExists(rp))
		{
			return new JsonForbidden("Account already exists").toResponse();	
		}
		
		Optional<String> response = services.accountService.createNewAccount(rp);
		
		if(response.isPresent())
			return new JsonForbidden(response.get()).toResponse();
		
		return new JsonSuccess("added").toResponse();
	}
	
	
	@Path("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(LoginPackage lp)
	{
	
		Optional<String> response = services.accountService.loginUser(lp);
		
		
		if(response.isPresent())
			return new JsonSuccess(response.get()).toResponse();
		else
			return new JsonForbidden("Either your email or password is incorrect.").toResponse();
		
	}
	
	
	@Path("/wedding/login")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response weddingLogin(@HeaderParam("password")String password)
	{
		
		
		if(services.accountService.isPasswordValid(password))
		{
			return Response.ok(services.accountService.getWeddingForPassword(password)).build();
		}
		
		return new JsonForbidden("Incorrect Password").toResponse();
		
	}
	
	
	
	@Path("/wedding/create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response createWedding(@HeaderParam(AUTH_HEADER) String token,WeddingPackage wp)
	{
		
		Optional<User> response = services.accountService.getUserForToken(token);
		if(!response.isPresent())
		{
			return new JsonForbidden("Please Login").toResponse();
		}
		else
		{
			Optional<String> weddingResponse = services.accountService.createNewWedding(response.get(), wp);
			
			if(weddingResponse.isPresent())
			{
				return new JsonForbidden(weddingResponse.get()).toResponse();
			}
			
			return new JsonSuccess("created").toResponse();
		}
	}
	
	
	

	
	
}
