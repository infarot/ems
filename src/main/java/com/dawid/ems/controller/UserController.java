package com.dawid.ems.controller;

import com.dawid.ems.repository.UserRepository;
import com.dawid.ems.security.CurrentUser;
import com.dawid.ems.security.UserPrincipal;
import com.dawid.ems.payload.UserSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user/me")
    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        return new UserSummary(currentUser.getId(), currentUser.getUsername());
    }

    @GetMapping("/user/checkUsernameAvailability")
    public boolean checkUsernameAvailability(@RequestParam(value = "username") String username) {
        return !userRepository.existsByUsername(username);
    }


}
