package com.example.springbatchprocessdbtocsv.repository;

import com.example.springbatchprocessdbtocsv.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String> {
}
