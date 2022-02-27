package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learning.entity.Register;
import com.learning.exception.IdNotFound;
import com.learning.exception.LoginFailed;
import com.learning.exception.RecordExists;
import com.learning.repo.RegisterRepository;
import com.learning.service.RegisterService;
@Service
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	RegisterRepository registerRepository;
	@Override
	public Register addUser(Register register) throws RecordExists {
		// TODO Auto-generated method stub'
		//add user data to database
//		System.out.println(register.getFood());
		if(registerRepository.existsByEmail(register.getEmail()))
			throw new RecordExists("User Already Exists");
		Register result=registerRepository.save(register);
		if(result!=null)
			return result;
		else
			return null;
		
	}

	@Override
	public String deleteUser(int id) throws IdNotFound {
		// TODO Auto-generated method stub
		//delete user from table
		Register register2=this.getUserById(id);
		if(register2!=null)
		{
			registerRepository.deleteById(id);
		    return "Success";
		}
		else
		{
			throw new IdNotFound("Sorry user with" +id+ "not found");
			
		}
		   
	}

	@Override
	public Register updateUser(int id, Register register) throws RecordExists, IdNotFound {
		// TODO Auto-generated method stub
		
		Register register2=this.getUserById(id);
		if(register2!=null)
		{
			register2.setName(register.getName());
			register2.setEmail(register.getEmail());
			register2.setPassword(register.getPassword());
//			System.out.println(register2.getPassword());
			//save used to save value to database
			Register result=registerRepository.save(register2);
			if(result!=null)
				return result;
			else
				return null;
		}
		else
		{
			throw new IdNotFound("Sorry user with" +id+ "not found");
			
		}
			
	}

	@Override
	public Optional<List<Register>> getAllUsers() {
		// TODO Auto-generated method stub
		//return list of Register object
		return Optional.ofNullable(registerRepository.findAll());
	}

	@Override
	public Register getUserById(int id) throws IdNotFound {
		// TODO Auto-generated method stub
		//findby Id :searches for id in database
		Optional<Register> optional=registerRepository.findById(id);
		if(optional.isEmpty())
			throw new IdNotFound("Sorry user with" +id+ "not found");
		
		else
			return optional.get();
	}

	@Override
	public String authentication(String email, String password) throws LoginFailed {
		// TODO Auto-generated method stub
		if(registerRepository.existsByEmailAndPassword(email, password))
			return "Login Successful";
		else
			throw new LoginFailed("login failed");
	}

}
