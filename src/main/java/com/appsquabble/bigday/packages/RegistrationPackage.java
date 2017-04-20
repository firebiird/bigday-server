package com.appsquabble.bigday.packages;

import javax.annotation.Nullable;
import javax.validation.constraints.Null;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public @Data @NoArgsConstructor @AllArgsConstructor @Builder class RegistrationPackage {
	
	private String email;
	
	private String password;
	
	@Null
	private String ewi; // Existing Wedding ID;

}
