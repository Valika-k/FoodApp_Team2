package com.learning.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import antlr.collections.List;

//import com.zee.zee5app.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Register")
public class Register {
	public Register( String email, String name,
			 String password,  String address) {
		super();
		this.email = email;
		this.name = name;
		this.password = password;
		this.address = address;
		this.roles = roles;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
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

	
//	@JoinTable(name="ordered_details",joinColumns=@JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="foodId"))
////	private Set<Food> food=new HashSet<Food>();
//	//values are added when users enters the food he selects
//	private java.util.List<Food> food=new ArrayList<Food>();	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name="role_table",joinColumns = @JoinColumn(name="userId"),inverseJoinColumns = @JoinColumn(name="roleId"))
	private Set<Role> roles=new HashSet<>();
}
