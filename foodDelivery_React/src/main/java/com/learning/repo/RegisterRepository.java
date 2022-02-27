package com.learning.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Register;
@Repository
public interface RegisterRepository extends JpaRepository<Register, Integer> {
	Boolean existsByEmail(String email);
	//Login
	Boolean existsByEmailAndPassword(String email,String password);
	Optional<Register> findByName(String name);
	Optional<Register> findByEmail(String email);
}
