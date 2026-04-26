package com.ecom.service;

import com.ecom.dtos.AddressDTO;
import com.ecom.dtos.UserRequest;
import com.ecom.dtos.UserResponse;
import com.ecom.model.Address;
import com.ecom.repository.UserRepository;
import com.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
//    private List<User> userList = new ArrayList<>();

    public List<UserResponse>getUserList(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
//        this::mapToUserResponse = user->mapToUserResponse(user)
    }
    private Long id = 0L;
    public void createUser(UserRequest userRequest){
//        id++;
//        user.setId(id);
//        userList.add(user);
//        return userList;
        User user = new User();
        updateUserFromRequest(user , userRequest);
        userRepository.save(user);
    }

    private void updateUserFromRequest(User user, UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress()!=null){
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }
    }

    public Optional<UserResponse> getUser(Long id) {
//        for(User user : userList){
//            if(Objects.equals(user.getId(), id)){
//                return user;
//            }
//        }
//        return null;
//        return userList.stream()
//                .filter(user->user.getId().equals(id))
//                .findFirst();
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }

    public Boolean updateUser(Long id , UserRequest updatedUser){
//        return userList.stream()
//                .filter(user->user.getId().equals(id))
//                .findFirst()
//                .map(existingUser->{
//                    existingUser.setFirstName(updatedUser.getFirstName());
//                    existingUser.setLastName(updatedUser.getLastName());
//                    return true;
//                }).orElse(false);

        return userRepository.findById(id)
                .map(existingUser->{
                    updateUserFromRequest(existingUser,updatedUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }

    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setRole(user.getRole());

        if(user.getAddress()!=null){
            AddressDTO dto = new AddressDTO();
            dto.setStreet(user.getAddress().getStreet());
            dto.setCity(user.getAddress().getCity());
            dto.setState(user.getAddress().getState());
            dto.setCountry(user.getAddress().getCountry());
            dto.setZipcode(user.getAddress().getZipcode());
            userResponse.setAddress(dto);
        }
        return userResponse;
    }
}
