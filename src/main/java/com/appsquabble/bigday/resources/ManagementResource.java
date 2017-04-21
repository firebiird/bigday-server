package com.appsquabble.bigday.resources;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.appsquabble.bigday.data.Wedding;
import com.appsquabble.bigday.packages.EmailApprovalPackage;
import com.appsquabble.bigday.services.Services;

import static com.appsquabble.bigday.helpers.JsonResponses.JsonForbidden;
import static com.appsquabble.bigday.helpers.JsonResponses.JsonSuccess;
import static com.appsquabble.bigday.helpers.JsonResponses.JsonSuccessWithData;


import java.util.Optional;


@Path("/v1")
public class ManagementResource {

	
	public Services services;
	
	
	public ManagementResource(Services services)
	{
		this.services = services;
	}
	
	
	@GET
	@Path("/wedding")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getWeddingForID(@HeaderParam("X-Auth-Token")String token)
	{
		
		if(services.accountService.isUserAuthenticated(token))
		{
			
			Optional<Wedding> w = services.accountService.getWeddingForUser(token);
			
			if(w.isPresent())
			{
				return new JsonSuccessWithData(w.get()).toResponse();
			}
			
			return new JsonForbidden("Failed to find wedding").toResponse();
			
		}
		
		return new JsonForbidden("Please login").toResponse();
		
		
	}
	
	
	@POST
	@Path("/wedding/approveUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Response approveUser(@HeaderParam("X-Auth-Token")String token,EmailApprovalPackage eap)
	{
		
		if(services.accountService.isUserAuthenticated(token))
		{
			if(services.accountService.approveUser(token,eap))
			{
				return new JsonSuccess("User Approved").toResponse();
			}
			else
			{
				return new JsonForbidden("Failed to approve user").toResponse();
			}
		}
		
		return new JsonForbidden("Please login").toResponse();
		
	}
	
}


