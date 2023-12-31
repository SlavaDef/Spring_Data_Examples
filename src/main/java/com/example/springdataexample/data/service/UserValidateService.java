package com.example.springdataexample.data.service;

import org.springframework.stereotype.Service;

@Service
public class UserValidateService {

    public boolean isAmailValide(String email) {
        if (email == null) {
            return false;
        }
        if (email.isBlank()) {
            return false;
        }
        if (email.strip().length() > 255) {
            return false;
        }
        if (!email.contains("@")) {
            return false;
        }

        return true;
    }

    public boolean isSearchQueryValid(String query) {
        if (query == null || query.isBlank()) {
            return false;
        }
        return true;
    }

}
