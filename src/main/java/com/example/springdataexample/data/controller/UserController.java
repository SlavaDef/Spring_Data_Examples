package com.example.springdataexample.data.controller;

import com.example.springdataexample.data.models.User;
import com.example.springdataexample.data.models.dto.DeleteUserResponse;
import com.example.springdataexample.data.models.dto.SaveUserResponse;
import com.example.springdataexample.data.models.dto.UserDto;
import com.example.springdataexample.data.service.UserService;
import com.example.springdataexample.data.service.UserValidateService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public List<UserDto> search (
            @RequestParam(name="query", required = false)
            String query){


    }


}
