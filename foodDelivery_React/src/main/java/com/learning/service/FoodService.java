package com.learning.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.learning.entity.Food;
import com.learning.entity.FoodType;
import com.learning.exception.IdNotFound;
@Service
public interface FoodService {
	public Food addFood(Food food);
	public String deleteFood(int id) throws IdNotFound;
	public Food updateFood(int id,Food food) throws IdNotFound;
	public Optional<List<Food>> getAllFoods();
	public Food getFoodById(int id) throws IdNotFound;
	public List<Food> getAllFoodsBasedOnType(FoodType foodType);
}
