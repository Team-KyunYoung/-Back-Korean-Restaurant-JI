package com.example.koreanrestaurantji.repository;

import com.example.koreanrestaurantji.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository //DB나 파일같은 외부 I/O 작업을 처리 (JpaRepository를 상속하면 없어도 되긴 함)
                                // Entity의 기본적인 CRUD가 가능하도록 JpaRepository 인터페이스를 제공 (JpaRepository<T, ID>)
public interface UserRepository extends JpaRepository<User, Long> {
    //기본적으로 save(), findOne(), findAll(), count(), delete() 메서드가 제공된다.
    //아래는 기본 제공 메서드 외에 직접 선언해야 하는 메서드 중 쓸 것을 정의한다.

    //Optional<User> : user 형식의 데이터를 리턴한다.(Optional은 orElseThrow을 쓰기위해 정의)
    //findByUserEmail : 메서드 자체는 jpa제공. find는 db에서 data를 찾아 반환하겠다. By컬럼명: 목표 데이터
    //()안의 값: 해당 값을 db에서 by~컬럼을 조희
    Optional<User> findByUserEmail(String email);
    Optional<User> findByUserNumber(Long userNumber);

    //existsByUserEmail() : ()안의 값이이 db의 useEmail 컬럼에서 있는지 없는지 조회
    Optional<Boolean> existsByUserEmail(String email);
    Optional<Boolean> existsByUserNickname(String nickname);
}
