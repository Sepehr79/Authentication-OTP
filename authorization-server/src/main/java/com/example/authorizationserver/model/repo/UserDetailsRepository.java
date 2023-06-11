package com.example.authorizationserver.model.repo;

import com.example.authorizationserver.model.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, String> {
}
