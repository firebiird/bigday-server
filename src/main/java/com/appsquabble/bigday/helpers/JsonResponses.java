package com.appsquabble.bigday.helpers;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public abstract class JsonResponses {
	
	public Status status;
	public String message;
	
	public JsonResponses(String message)
	{
		this.message = message;
	}
	
	public Response toResponse()
	{
		return Response.status(status).entity(this).build();
		
	}
	
	
	public static class JsonForbidden extends JsonResponses{
		
		public JsonForbidden(String message)
		{
			super(message);
			this.status = Status.FORBIDDEN;
			
		}
		
	}
	
	public static class JsonSuccess extends JsonResponses{
		
		public JsonSuccess(String message)
		{
			super(message);
			this.status = Status.OK;
		}
		
	}
	
	
}
