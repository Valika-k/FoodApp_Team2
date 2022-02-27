package com.learning.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learning.entity.Food;
import com.learning.entity.FoodType;
import com.learning.entity.Register;
import com.learning.exception.IdNotFound;
import com.learning.exception.LoginFailed;
import com.learning.exception.RecordExists;
import com.learning.payload.request.FoodRequest;
import com.learning.payload.response.MessageResponse;
import com.learning.service.FoodService;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FoodController {
	@Autowired
	FoodService foodService1;
	@PostMapping("/food")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> addFood(@RequestBody FoodRequest foodRequest) 
	{
		Food food=new Food(foodRequest.getFoodName(), foodRequest.getFoodCost(), foodRequest.getDescription(), foodRequest.getFoodPic());
		switch (foodRequest.getFoodType()) {
		case "Indian":
			food.setFoodType(FoodType.Indian);
			break;
		case "Mexican":
			food.setFoodType(FoodType.Mexican);
			break;
		default:
			food.setFoodType(FoodType.Chinese);
			break;
		}
			
		Food food2=foodService1.addFood(food);
		return ResponseEntity.status(201).body(new MessageResponse("Food added successfully"));
			
	}

	@GetMapping("/food")
	public ResponseEntity<?> updateFood()
	{

		Optional<java.util.List<Food>> result=foodService1.getAllFoods();
		if(result.isEmpty())
		{
			return ResponseEntity.status(200).body(result.get());
			
			
			
		}
		else
			return ResponseEntity.status(200).body(result.get());
		
	}
	
	@GetMapping(value="/food/{id}")
	public ResponseEntity<?> getFoodById(@PathVariable("id") int id) throws IdNotFound
	{
		System.out.println(id+"--");
		Food food=foodService1.getFoodById(id);
		return ResponseEntity.status(200).body(food);
		
	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/food/{id1}")
	public ResponseEntity<?> updateFood(@PathVariable("id1") int id,@RequestBody Food food) throws IdNotFound, RecordExists
	{
		Food food1=foodService1.updateFood(id,food);
		return ResponseEntity.status(200).body(food1);

	}
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/food/{id2}")
	public ResponseEntity<?> deleteFood(@PathVariable("id2") int id) throws IdNotFound
	{
		String result=foodService1.deleteFood(id);
		Map<String,String> hashMap=new HashMap<>();
		if(result=="Success")
		{
		hashMap.put("message","food Item deleted");
		return ResponseEntity.status(200).body(hashMap);
		}
		hashMap.put("message","food Item not found");
		return ResponseEntity.status(404).body(hashMap);
		
	}
	
	@GetMapping("/food/foodType/{foodType}")
	public ResponseEntity<?> getFoodById(@PathVariable("foodType") String foodType) throws IdNotFound
	{
		FoodType foodType1=FoodType.valueOf(foodType);
		List<Food> food=foodService1.getAllFoodsBasedOnType(foodType1);
		if(food==null)
		{
			Map<String, String> map=new HashMap<>();
		map.put("message", "No records found");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(map);
		}
		return ResponseEntity.status(200).body(food);
		
	}
	

}
