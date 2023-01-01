package com.example.demo.service;


import com.example.demo.model.UserEntity;
import com.example.demo.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Slf4j
@Service

public class UserService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private UserRepository userRepository;

    public UserEntity create(UserEntity userEntity){

        String userId = userEntity.getUserId();
        if(userRepository.existsByUserId(userId)){
            throw new RuntimeException("이미 가입된 회원입니다.");
        }

        return userRepository.save(userEntity);
    }
    public UserEntity getByCredentials(String userId, String password){

        UserEntity originalUser = userRepository.findByUserId(userId);

        //아이디값 선택후 로그인 창에 입력 받은 비밀번호와 기존 등록된 비밀번호와 비교

        if(originalUser != null && encoder.matches(password, originalUser.getPassword())){
            return originalUser;
        }
        return null;

    }

}
