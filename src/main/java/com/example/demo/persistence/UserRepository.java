package com.example.demo.persistence;

import com.example.demo.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>{
    UserEntity findByUserId(String userId);
    Boolean existsByUserId(String userId);
    UserEntity findByUserIdAndPassword(String userId, String password);

}
