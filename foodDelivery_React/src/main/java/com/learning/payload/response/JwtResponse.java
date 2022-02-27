package com.learning.payload.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
//@AllArgsConstructor
@Data
public class JwtResponse {
	public JwtResponse(String token, String username, int id, String password,List<String> roles) {
		super();
		this.token = token;
		this.username = username;
		this.id = id;
		this.password = password;
		this.roles=roles;
	}
	private String token;
	private String type="Bearer";
	private String username;
	private int id;
	private String password;
	private List<String> roles;
}
