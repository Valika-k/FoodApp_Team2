package com.learning.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Erole;
import com.learning.entity.Register;
import com.learning.entity.Role;
import com.learning.exception.IdNotFound;
import com.learning.exception.LoginFailed;
import com.learning.exception.RecordExists;
import com.learning.payload.request.LoginRequest;
import com.learning.payload.request.SignUpRequest;
import com.learning.payload.response.JwtResponse;
import com.learning.payload.response.MessageResponse;
import com.learning.repo.RegisterRepository;
import com.learning.repo.RoleRepository;
import com.learning.security.jwt.JwtUtils;
import com.learning.security.services.UserDetailsImpl;
import com.learning.service.RegisterService;
//import com.zee.zee5app.payload.response.JwtResponse;

import antlr.collections.List;
import org.springframework.web.bind.annotation.RequestHeader;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class UserController {
	@Autowired
	RegisterService registerService1;
	@Autowired
	RoleRepository repository;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RegisterRepository registerRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
@PostMapping("/register")
//@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")

public ResponseEntity<?> addUser(@RequestBody SignUpRequest signUpRequest) throws RecordExists
{
	if(registerRepository
			.existsByEmail(signUpRequest.getEmail()))
	{
		return ResponseEntity.badRequest().body(new MessageResponse("Error:Email is already in use"));
	}
	Set<Role> roles = new HashSet<>();
	Register register2=new Register(signUpRequest.getEmail(), signUpRequest.getName(), passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getAddress());
	System.out.println(signUpRequest.getRoles());
	if(signUpRequest.getRoles()==null)
	{
		Role role1=repository.findByRole(Erole.ROLE_USER).orElseThrow(()->new RuntimeException("Role Not Found"));
		roles.add(role1);	
	}
	else {
	signUpRequest.getRoles().forEach(e->{
		switch (e) {
		case "admin":
			Role role=repository.findByRole(Erole.ROLE_ADMIN).orElseThrow(()->new RuntimeException("Role Not Found"));
			System.out.println(role);
			roles.add(role);
			break;
			
		default:
			Role role1=repository.findByRole(Erole.ROLE_USER).orElseThrow(()->new RuntimeException("Role Not Found"));
			roles.add(role1);
			break;
		}
	});}
	register2.setRoles(roles);
	registerService1.addUser(register2);
	return ResponseEntity.status(201).body(new MessageResponse("User Created Successfully"));
		
}
@GetMapping("/auth")
public ResponseEntity<?> getUser(@RequestHeader(name="Authorization") String token)
{
	String token1=token.substring(7, token.length());
	String userName=jwtUtils.getUserNameFromJwtToken(token1);
	System.out.println(userName);
	Optional<Register> register=registerRepository.findByEmail(userName);
	
	
	return ResponseEntity.status(200).body(register.get());
	
}
@PostMapping("/authenticate")
//@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> authenticate(@RequestBody LoginRequest loginRequest) throws LoginFailed
{
	Authentication authentication=authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
	SecurityContextHolder.getContext().setAuthentication(authentication);
	String token=jwtUtils.generateToken(authentication);
	UserDetailsImpl userDetailsImpl=(UserDetailsImpl) authentication.getPrincipal();
	java.util.List<String> roles = userDetailsImpl.getAuthorities()
			.stream()
			.map(i->i.getAuthority())
			.collect(Collectors.toList());

return ResponseEntity.ok(new JwtResponse(token, userDetailsImpl.getEmail(), userDetailsImpl.getId(), userDetailsImpl.getPassword(),roles));
	
}
@GetMapping("/users")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public ResponseEntity<?> updateUser()
{

	Optional<java.util.List<Register>> result=registerService1.getAllUsers();
	if(result.isEmpty())
	{
		Map<String, String> map=new HashMap<>();
		map.put("message", "No records found");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		
		
		
	}
	else
		return ResponseEntity.status(200).body(result.get());
	
}
@GetMapping("/users/{id}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> getUserById(@PathVariable("id") int id) throws IdNotFound
{
	Register register=registerService1.getUserById(id);
	return ResponseEntity.status(200).body(register);
	
}
@PutMapping("/users/{id}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> updateUser(@PathVariable("id") int id,@RequestBody Register register) throws IdNotFound, RecordExists
{
	Register register1=registerService1.updateUser(id,register);
	return ResponseEntity.status(200).body(register1);

}
@DeleteMapping("/users/{id}")
@PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
public ResponseEntity<?> deleteUser(@PathVariable("id") int id) throws IdNotFound
{
	String register=registerService1.deleteUser(id);
	return ResponseEntity.status(200).body(register);
	
}
//{
//    
//    "email":"goutham2@gmailcom",
//    "name":"goutham2",
//    "password":"goutham2",
//    "address":"Bangalore"
//
//    
//}

}
