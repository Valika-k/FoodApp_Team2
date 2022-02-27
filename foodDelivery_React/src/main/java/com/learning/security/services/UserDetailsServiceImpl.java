package com.learning.security.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.learning.entity.Register;
import com.learning.repo.RegisterRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	RegisterRepository repository;
	
	@Transactional
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Register user=repository.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("invalid username"));
		
		return UserDetailsImpl.build(user);
	}

}
