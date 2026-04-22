package com.ecom.userservice;

import com.ecom.user.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private List<User> userList = new ArrayList<>();

    public List<User>getUserList(){
        return userList;
    }
    private Long id = 0L;
    public List<User>createUser(User user){
        id++;
        user.setId(id);
        userList.add(user);
        return userList;
    }

    public Optional<User> getUser(Long id) {
//        for(User user : userList){
//            if(Objects.equals(user.getId(), id)){
//                return user;
//            }
//        }
//        return null;
        return userList.stream()
                .filter(user->user.getId().equals(id))
                .findFirst();
    }

    public Boolean updateUser(Long id , User updatedUser){
        return userList.stream()
                .filter(user->user.getId().equals(id))
                .findFirst()
                .map(existingUser->{
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    return true;
                }).orElse(false);
    }
}
