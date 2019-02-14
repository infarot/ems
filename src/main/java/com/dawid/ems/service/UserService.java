package com.dawid.ems.service;

import com.dawid.ems.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void addUser(User user);
    User findByUsername(String name);
}
