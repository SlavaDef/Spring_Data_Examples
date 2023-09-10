package com.example.springdataexample.data.models.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoDto {

    private UserDto userDto;
    private List<String> adresses;
}
