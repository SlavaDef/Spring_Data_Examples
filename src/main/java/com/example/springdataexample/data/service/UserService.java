package com.example.springdataexample.data.service;

import com.example.springdataexample.data.models.User;
import com.example.springdataexample.data.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;

    public List<User> findAll() {
        return repository.findAll();
    }

    public void save(User user) {
        repository.save(user);
    }


    public boolean exist(String email) { // перед видаленням перевіряємо чи є такий юзер
        if (email == null) {
            return false;
        }

      return  repository.existsById(email);

    }

    public void deleteByEmail(String email){

        repository.deleteById(email);
    }

}
