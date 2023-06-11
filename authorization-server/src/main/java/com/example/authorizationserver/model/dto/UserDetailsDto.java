package com.example.authorizationserver.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDetailsDto {

    private String username;

    private String password;

}
