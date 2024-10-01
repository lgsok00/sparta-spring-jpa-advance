package com.sparta.jpaadvance.cascade;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CascadeTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  FoodRepository foodRepository;

  @Test
  @DisplayName("Robbie 음식 주문")
  void test1() {
    // 고객 Robbie가 후라이드 치킨과 양념 치킨을 주문합니다.
    User user = new User();
    user.setName("Robbie");

    // 후라이드 치킨 주문
    Food food1 = new Food();
    food1.setName("후라이드 치킨");
    food1.setPrice(15000);

    user.addFoodList(food1);

    // 양념 치킨 주문
    Food food2 = new Food();
    food2.setName("양념 치킨");
    food2.setPrice(20000);

    user.addFoodList(food2);

    userRepository.save(user);
    foodRepository.save(food1);
    foodRepository.save(food2);
  }

  @Test
  @DisplayName("영속성 전이 저장")
  void test2() {
    // 고객 Robbie가 후라이드 치킨과 양념 치킨을 주문합니다.
    User user = new User();
    user.setName("Robbie");

    // 후라이드 치킨 주문
    Food food1 = new Food();
    food1.setName("후라이드 치킨");
    food1.setPrice(15000);

    user.addFoodList(food1);

    // 양념 치킨 주문
    Food food2 = new Food();
    food2.setName("양념 치킨");
    food2.setPrice(20000);

    user.addFoodList(food2);

    userRepository.save(user);
  }
}
