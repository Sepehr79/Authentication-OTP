package com.example.authorizationserver.model.repo;


import com.example.authorizationserver.model.entity.UserOtp;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserOtpRepository extends CrudRepository<UserOtp, String> {

}
