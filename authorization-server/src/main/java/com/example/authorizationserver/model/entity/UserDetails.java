package com.example.authorizationserver.model.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@AllArgsConstructor
@ToString
@Setter
public class UserDetails {

    @Id
    private String username;

    private String password;

}
