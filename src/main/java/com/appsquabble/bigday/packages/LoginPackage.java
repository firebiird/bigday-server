package com.appsquabble.bigday.packages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @AllArgsConstructor @NoArgsConstructor @Builder class LoginPackage {
	
	private String email;
	
	private String password;
	
	

}
