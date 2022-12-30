package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.UserDTO;
import com.example.demo.model.UserEntity;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
@CrossOrigin(originPatterns = "http://localhost:3000")
public class UserController {


    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO){

        System.out.println(userDTO.getUserId());
        try {

            // 비밀번호 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String securePassword = encoder.encode(userDTO.getPassword());


            UserEntity user = UserEntity.builder()
                    .userId(userDTO.getUserId())
                    .password(securePassword)
                    .build();
            UserEntity registeredUser = userService.create(user);
            UserDTO responseDTO = UserDTO.builder()
                    .userId(registeredUser.getUserId())
                    .id(registeredUser.getId())
                    .build();
                return ResponseEntity.ok().body(responseDTO);
        }
        catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
                return ResponseEntity.badRequest().body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO){

    UserEntity user = userService.getByCredentials(
            userDTO.getUserId(),
            userDTO.getPassword());

    if(user != null){

        UserDTO responseUserDTO = UserDTO.builder()
                .userId(user.getUserId()).password(user.getPassword())
                .build();
        System.out.println(ResponseEntity.ok().body(responseUserDTO));
        return ResponseEntity.ok().body(responseUserDTO);
    }
    else {

        ResponseDTO responseDTO = ResponseDTO.builder().error("로그인 실패하였습니다.").build();
        return ResponseEntity.badRequest().body(responseDTO);
    }

    }
}
