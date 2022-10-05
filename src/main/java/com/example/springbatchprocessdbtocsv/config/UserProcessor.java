package com.example.springbatchprocessdbtocsv.config;

import com.example.springbatchprocessdbtocsv.model.User;
import org.springframework.batch.item.ItemProcessor;

public class UserProcessor implements ItemProcessor<User, User> {

    @Override
    public User process(User user) throws Exception {
        return user;
    }
}
