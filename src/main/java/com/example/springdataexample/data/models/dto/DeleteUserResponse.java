package com.example.springdataexample.data.models.dto;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class DeleteUserResponse {


    private boolean success;
    private Error error;

    public enum Error {
        ok,
        userNotFound
    }

    public static DeleteUserResponse success(){
        return DeleteUserResponse.builder()
                .success(true)
                .error(DeleteUserResponse.Error.ok)
                .build();
    }

    public static DeleteUserResponse failed(DeleteUserResponse.Error error){

        return DeleteUserResponse.builder()
                .success(false)
                .error(error)
                .build();
    }

}
