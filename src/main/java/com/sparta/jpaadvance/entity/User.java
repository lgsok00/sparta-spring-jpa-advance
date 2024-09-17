package com.sparta.jpaadvance.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @ManyToMany(mappedBy = "userList")
  private List<Food> foodList = new ArrayList<>();

  public void addFoodList(Food food) {
    this.foodList.add(food);
    food.getUserList().add(this);  // 외래 키(연관 관계) 설정
  }
}
