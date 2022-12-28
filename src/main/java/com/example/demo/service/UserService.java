package com.example.demo.service;


import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity create(final UserEntity userEntity){
        if(userEntity == null || userEntity.getUserId()==null){
            throw new RuntimeException("가입된 회원이 아닙니다.");
        }
        final String userId = userEntity.getUserId();
        if(userRepository.existsByUserId(userId)){
            log.warn("이미 가입된 회원입니다.",userId);
            throw new RuntimeException("이미 가입된 아닙니다.");
        }

        return userRepository.save(userEntity);
    }
    public UserEntity getByCredentials(final String userId, final String password){
        return userRepository.findByUserIdAndPassword(userId, password);

    }

}
