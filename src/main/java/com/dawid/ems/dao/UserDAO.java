package com.dawid.ems.dao;

import com.dawid.ems.entity.User;

public interface UserDAO {
    void addUser(User user);
    User findByUsername(String name);
}
