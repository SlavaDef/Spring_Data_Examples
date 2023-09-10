package com.example.springdataexample.data.repo;

import com.example.springdataexample.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,String> {


}
