package com.ecom.dtos;

import com.ecom.model.UserRole;
import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
//    private UserRole role;
    private AddressDTO address;
}
