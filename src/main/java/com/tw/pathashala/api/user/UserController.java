package com.tw.pathashala.api.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@CrossOrigin
class UserController {
    @Autowired
    UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signUp")
    User signUp(@Valid @RequestBody User user) {
        System.out.println(user.getPassword());
        userService.register(user);
        return user;
    }
}
