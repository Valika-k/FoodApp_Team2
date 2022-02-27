package com.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.entity.Register;
import com.learning.exception.IdNotFound;
import com.learning.exception.LoginFailed;
import com.learning.exception.RecordExists;

public interface RegisterService {
	public Register addUser(Register register) throws RecordExists;
	public String deleteUser(int id) throws IdNotFound;
	public Register updateUser(int id,Register register) throws RecordExists, IdNotFound;
	public Optional<List<Register>> getAllUsers();
	public Register getUserById(int id) throws IdNotFound;
	public String authentication(String email,String password) throws LoginFailed;
	

}
