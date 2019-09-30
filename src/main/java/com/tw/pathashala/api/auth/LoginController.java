package com.tw.pathashala.api.auth;

import com.tw.pathashala.api.user.User;
import com.tw.pathashala.api.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
class LoginController {
    @Autowired
    UserService userService;

    @GetMapping("/login")
    Map<String, Object> login(Principal principal) {
        String username = principal.getName();
        User user = userService.findUserByUsername(username);
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", username);
        userDetails.put("wallet_id", user.walletId());
        return userDetails;
    }
}
