package com.ecom.controller;

import com.ecom.user.User;
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

    private List<User>userList = new ArrayList<>();

    @GetMapping("")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getUserList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id){
//        if(userService.getUser(id)==null){
//            return ResponseEntity.notFound().build();
//        }
//        return new ResponseEntity<>(userService.getUser(id), HttpStatus.OK);
        return userService.getUser(id)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<List<User>>createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String>updateUser(@PathVariable Long id , @RequestBody User user){
        if(userService.updateUser(id,user)){
            return ResponseEntity.ok("User Updated Successfully");
        }else{
            return ResponseEntity.notFound().build();
        }
    }
}
