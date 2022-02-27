package com.learning.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.util.Set;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
@Data
@Entity
@Table(name="Food",uniqueConstraints = @UniqueConstraint(columnNames = "foodName"))
@NoArgsConstructor
//@AllArgsConstructor

public class Food {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Id;
	@NotBlank
	@Size(max=50)
	private String foodName;
	@NotNull
	private int foodCost;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private FoodType foodType;
	@NotBlank
	@Size(max=50)
	private String description;
	
	@NotBlank
	private String foodPic;
//	@ManyToMany(mappedBy = "food")
//	private Set<Register> user;
	
	public Food(@NotBlank @Size(max = 50) String foodName, @NotNull int foodCost,
			@NotBlank @Size(max = 50) String description, @NotBlank String foodPic) {
		super();
		this.foodName = foodName;
		this.foodCost = foodCost;
		this.description = description;
		this.foodPic = foodPic;
	}
}
