package com.ecom.controller;

import com.ecom.dtos.UserRequest;
import com.ecom.dtos.UserResponse;
import com.ecom.userservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("")
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id){
//        if(userService.getUser(id)==null){
//            return ResponseEntity.notFound().build();
//        }
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<String>createUser(@RequestBody UserRequest userRequest){
        userService.createUser(userRequest);
        return new ResponseEntity<>("User added successfully",HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>updateUser(@PathVariable Long id , @RequestBody UserRequest userRequest){
        if(userService.updateUser(id,userRequest)){
            return ResponseEntity.ok("User Updated Successfully");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
