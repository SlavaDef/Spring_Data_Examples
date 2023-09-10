package com.example.springdataexample.data.models.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class SaveUserResponse { // клас для інфи чи успішне збереження юзера

    private boolean success;
    private Error error;

    public enum Error {
        ok,
        invalidEmail
    }

    public static SaveUserResponse success(){
        return SaveUserResponse.builder()
                .success(true)
                .error(Error.ok)
                .build();
    }

    public static SaveUserResponse faled(Error error){

        return SaveUserResponse.builder()
                .success(false)
                .error(error)
                .build();
    }




}
