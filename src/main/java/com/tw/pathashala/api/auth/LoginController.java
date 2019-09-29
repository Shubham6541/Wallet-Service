package com.tw.pathashala.api.auth;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
class LoginController {
    @GetMapping("/login")
    void login() {

    }
}
