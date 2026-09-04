package com.akp.core_service.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class UserModel {


    @NotNull
    private String username;
    @NotNull
    private String password;
}
