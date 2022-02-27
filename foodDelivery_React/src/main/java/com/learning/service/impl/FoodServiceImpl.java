package com.learning.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

import com.learning.entity.Food;
import com.learning.entity.FoodType;
import com.learning.exception.IdNotFound;
import com.learning.repo.FoodRepository;
import com.learning.service.FoodService;
@Service
public class FoodServiceImpl implements FoodService {
	@Autowired
	FoodRepository foodRepository;
	@Override
	public Food addFood(Food food) {
		// TODO Auto-generated method stub
		System.out.println(food);
		System.out.println(food.getFoodType().getClass().getSimpleName());
		Food food2=foodRepository.save(food);
		if(food2!=null)
			return food2;
		else
			return null;
	}

	@Override
	public String deleteFood(int id) throws IdNotFound {
		// TODO Auto-generated method stub
		Food food=this.getFoodById(id);
		if(food!=null)
		{
			foodRepository.deleteById(id);
			return "Success";
		}
		else
			return "Failed";
			
	}

	@Override
	public Food updateFood(int id, Food food) throws IdNotFound {
		// TODO Auto-generated method stub
		Food food1=this.getFoodById(id);
		if(food1!=null)
		{
			food1.setFoodName(food.getFoodName());
			food1.setFoodCost(food.getFoodCost());
			food1.setDescription(food.getDescription());
			food1.setFoodPic(food.getFoodPic());
			food1.setFoodType(food.getFoodType());
			Food result=foodRepository.save(food1);
			if(result!=null)
				return food1;
			else
				return null;
		}
		throw new IdNotFound(" food not found");
		
	}

	@Override
	public Optional<List<Food>> getAllFoods() {
		// TODO Auto-generated method stub
		return Optional.ofNullable(foodRepository.findAll());
	}

	@Override
	public Food getFoodById(int id) throws IdNotFound {
		// TODO Auto-generated method stub
		Optional<Food> optional=foodRepository.findById(id);
		if(optional.isEmpty())
			//Exception is thrown when id is not found
			throw new IdNotFound(" food not found");
		else
			return optional.get();
		
				
	}

	@Override
	public List<Food> getAllFoodsBasedOnType(FoodType foodType) {
		// TODO Auto-generated method stub
		//find all details based on food type
		Optional<List<Food>> optional=foodRepository.findByFoodType(foodType);
		System.out.println(optional);
		if(optional.isEmpty())
			return null;
		else
		{
			return optional.get();
		}

	}

}
