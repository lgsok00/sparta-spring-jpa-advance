package com.sparta.jpaadvance.relation;

import com.sparta.jpaadvance.entity.Food;
import com.sparta.jpaadvance.entity.User;
import com.sparta.jpaadvance.repository.FoodRepository;
import com.sparta.jpaadvance.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class ManyToManyTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  FoodRepository foodRepository;

  @Test
  @Rollback(value = false)
  @DisplayName("N대M 단방향 테스트")
  void test1() {

    User user = new User();
    user.setName("Robbie");

    User user2 = new User();
    user2.setName("Robbert");

    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);
    food.getUserList().add(user);  // 외래 키 설정
    food.getUserList().add(user2);  // 외래 키 설정

    userRepository.save(user);
    userRepository.save(user2);
    foodRepository.save(food);

    // 자동으로 중간 테이블 orders 가 CREATE 되고, INSERT 됨을 확인할 수 있다..
  }

  @Test
  @Rollback(value = false)
  @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패")
  void test2() {

    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);

    Food food2 = new Food();
    food.setName("양념 치킨");
    food2.setPrice(20000);

    // 외래 키의 주인이 아닌 User 에서 Food 를 저장
    User user = new User();
    user.setName("Robbie");
    user.getFoodList().add(food);
    user.getFoodList().add(food2);

    userRepository.save(user);
    foodRepository.save(food);
    foodRepository.save(food2);

    // orders 테이블에 food_id, user_id 값이 들어가 있지 않음...
  }

  @Test
  @Rollback(value = false)
  @DisplayName("N대M 양방향 테스트 : 외래 키 저장 실패 -> 성공")
  void test3() {

    Food food = new Food();
    food.setName("후라이드 치킨");
    food.setPrice(15000);

    Food food2 = new Food();
    food.setName("양념 치킨");
    food2.setPrice(20000);

    // 외래 키의 주인이 아닌 User 에서 Food 를 쉽게 저장하기 위해 addFoodList() 메서드 생성
    // 외래 키(연관 관계) 설정을 위해 Food 에서 userList 를 호출해 User 객체 자신을 add
    User user = new User();
    user.setName("Robbie");
    user.addFoodList(food);
    user.addFoodList(food2);

    userRepository.save(user);
    foodRepository.save(food);
    foodRepository.save(food2);
  }

  @Test
  @Rollback(value = false)
  @DisplayName("N대M 양방향 테스트")
  void test4() {

    User user = new User();
    user.setName("Robbie");

    User user2 = new User();
    user2.setName("Robbert");

    Food food = new Food();
    food.setName("아보카도 피자");
    food.setPrice(50000);
    food.getUserList().add(user);  // 외래 키(연관 관계) 설정
    food.getUserList().add(user2);  // 외래 키(연관 관계) 설정

    Food food2 = new Food();
    food2.setName("고구마 피자");
    food2.setPrice(30000);
    food2.getUserList().add(user);  // 외래 키(연관 관계) 설정

    userRepository.save(user);
    userRepository.save(user2);
    foodRepository.save(food);
    foodRepository.save(food2);

    // User 를 통해 Food 정보 조회
    System.out.println("user.getName() = " + user.getName());

    List<Food> foodList = user.getFoodList();
    for (Food f : foodList) {
      System.out.println("f.getName() = " + f.getName());
      System.out.println("f.getPrice() = " + f.getPrice());
    }

    // 외래 키의 주인이 아닌 User 객체에 Food 정보를 넣어주지 않아도 DB 저장에는 문제 없음
    // 이처럼 User 를 사용하여 food 정보를 조회할 수 없음
  }

  @Test
  @Rollback(value = false)
  @DisplayName("N대M 양방향 테스트 : 객체와 양방향의 장점 활용")
  void test5() {

    User user = new User();
    user.setName("Robbie");

    User user2 = new User();
    user2.setName("Robbert");

    // addUserList() 메서드를 생성해 User 정보 추가
    // 해당 메서드에 객체 활용을 통해 User 객체에 Food 정보를 추가하는 코드 추가
    Food food = new Food();
    food.setName("아보카도 피자");
    food.setPrice(50000);
    food.addUserList(user);
    food.addUserList(user2);

    Food food2 = new Food();
    food2.setName("고구마 피자");
    food2.setPrice(30000);
    food2.addUserList(user);

    userRepository.save(user);
    userRepository.save(user2);
    foodRepository.save(food);
    foodRepository.save(food2);

    // User 를 통해 Food 정보 조회
    System.out.println("user.getName() = " + user.getName());

    List<Food> foodList = user.getFoodList();
    for (Food f : foodList) {
      System.out.println("f.getName() = " + f.getName());
      System.out.println("f.getPrice() = " + f.getPrice());
    }
  }
}
