package com.learning.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learning.entity.Food;
import com.learning.entity.FoodType;
@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
	//to return all food details based on food type.
   Optional<List<Food>> findByFoodType(FoodType foodType);
}
