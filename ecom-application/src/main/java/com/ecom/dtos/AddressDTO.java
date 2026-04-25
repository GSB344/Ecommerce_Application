package com.ecom.dtos;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

@Data
public class AddressDTO {
    private String street;
    private String city ;
    private String state;
    private String country;
    private String zipcode;
}
