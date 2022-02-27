package com.learning.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
@Data
public class SignUpRequest {
	@Email
	@NotBlank
	private String email;
	@Size(max=50)
	private String name;
	@NotBlank
	@Size(max=100)
	private String password;
	@NotBlank
	private String address;
	
	private Set<String> roles;
}
