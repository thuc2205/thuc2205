package com.example.democuatao.controller;

import com.example.democuatao.Service.UserServiceImpl;
import com.example.democuatao.dtos.UserDTO;
import com.example.democuatao.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    @PostMapping("/dangki")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        if(result.hasErrors()){
            List<String> errVoucher = result.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList());
        }
       try {
           if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
               return ResponseEntity.badRequest().body("Khong trung Mat Khau");
           }
//add
           userService.createUser(userDTO);
           return ResponseEntity.ok(userDTO);
       }catch (Exception e){
           return ResponseEntity.badRequest().body(e.getMessage());

       }
    }
    @PostMapping("/login")
    @Deprecated
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO){
        try {
            String token = userService.login(userLoginDTO.getPhoneNumber(),userLoginDTO.getPassword());
            return ResponseEntity.ok(token);

        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
