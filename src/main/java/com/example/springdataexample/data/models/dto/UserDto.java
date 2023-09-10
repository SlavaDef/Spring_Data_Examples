package com.example.springdataexample.data.models.dto;

import com.example.springdataexample.data.models.Gender;
import com.example.springdataexample.data.models.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Builder
@Data
public class UserDto { // суть класу зберігати данні у тому вигляді який віддаємо


    private String email;

    private String fullName;

    private LocalDate birthday;

    private int age;

    private Gender gender;

    public static UserDto fromUser(User user) { // проміжний метод для конвертації
            // ChronoUnit поверне різницю між ріком народження і зараз
        int age = (int) ChronoUnit.YEARS.between(user.getBirthday(), LocalDate.now());

        return UserDto.builder()
                .email(user.getEmail())
                .fullName(user.getFullName())
                .birthday(user.getBirthday())
                .age(age)
                .gender(user.getGender())
                .build();

    }

    public static User fromDto(UserDto userDto){
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFullName(userDto.getFullName());
        user.setBirthday(userDto.getBirthday());
        user.setGender(userDto.getGender());

        return user;

    }


}
