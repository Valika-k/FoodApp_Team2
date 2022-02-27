package com.learning.payload.request;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.learning.entity.FoodType;

import lombok.Data;
@Data
public class FoodRequest {
	@NotBlank
	@Size(max=50)
	private String foodName;
	@NotNull
	private int foodCost;
	
	private String foodType;
	@NotBlank
	@Size(max=50)
	private String description;
	@NotBlank
	private String foodPic;
}
