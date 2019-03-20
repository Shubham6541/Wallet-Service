package com.tw.pathashala.wallet.api;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HelloWorldController {

    private static final String GREETING_MESSAGE = "Hello World";

    @GetMapping("/greeting")
    String greeting() {
        return GREETING_MESSAGE;
    }
}
