package com.example.springdataexample.data.controller;

import com.example.springdataexample.data.models.User;
import com.example.springdataexample.data.models.dto.DeleteUserResponse;
import com.example.springdataexample.data.models.dto.SaveUserResponse;
import com.example.springdataexample.data.models.dto.UserDto;
import com.example.springdataexample.data.models.dto.UserInfoDto;
import com.example.springdataexample.data.service.UserService;
import com.example.springdataexample.data.service.UserValidateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;
    private final UserValidateService userValidateService;

    @GetMapping("/list")
    public List<UserDto> list() {
        return userService.findAll().stream().map(it -> UserDto.fromUser(it)).collect(Collectors.toList());
    }

    @PostMapping("/save")
    public SaveUserResponse save(@RequestBody UserDto userDto) { // запит в постмені
        // якщо переврок багато то треба виносити в окремий клас
        if (!userValidateService.isAmailValide(userDto.getEmail())) {
            return SaveUserResponse.faled(SaveUserResponse.Error.invalidEmail);
        }

        User user = UserDto.fromDto(userDto);
        userService.save(user);
        return SaveUserResponse.success(); // якщо все коректно сберіглося повертаємо удачу

    }

    @PostMapping("/delete/{email}")
    public DeleteUserResponse delete(@PathVariable("email")
                                     String email, HttpServletResponse response) {
        if (!userService.exist(email)) {
            // + також можна прокинути відповід через HttpServletResponse
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);

            return DeleteUserResponse.failed(DeleteUserResponse.Error.userNotFound);
        }
        userService.deleteByEmail(email);

        return DeleteUserResponse.success();
    }

    @GetMapping("/search")
    public List<UserDto> search(
            @RequestParam(name = "query", required = false)
            String query, HttpServletResponse response) {

        if (!userValidateService.isSearchQueryValid(query)) { // якщо запит не коретний
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.emptyList();
        }

        return userService.search(query)
                .stream().map(UserDto::fromUser).collect(Collectors.toList());
    }

    @GetMapping("/countOlderThan/{age}")
    public int getCountPeopleAlderThen(@PathVariable("age") int age) {
        return userService.countPeopleOlderThen(age);
    }

    @PostMapping("/deleteAll")
    public DeleteUserResponse deleteAll(@RequestBody List<String> emails) { // очікуємо масив емейлів
        // якщо є хоча б один не існуючий емейл буде тру
        boolean trueEmails = emails == null || emails.stream()
                .anyMatch(email -> !userService.exist(email));
        if (trueEmails) {
            return DeleteUserResponse.failed(DeleteUserResponse.Error.userNotFound);
        }
        userService.deleteByIds(emails);

        return DeleteUserResponse.success();
    }

    // метод який буде повертати інфу з двох таблиць
    @GetMapping("/info/{email}")
    public UserInfoDto getUserInfo(@PathVariable("email") String email) {

        return userService.getUserInfo(email);
    }


}
